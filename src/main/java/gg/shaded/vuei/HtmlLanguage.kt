package gg.shaded.vuei

import gg.shaded.vuei.layout.*
import rx.Observable

class HtmlLanguage(
    private val itemFactory: ItemFactory
): Parser<List<Element>> {
    override fun parse(context: ParserContext): List<Element> {
        val elements = ArrayList<Element>()

        context.skipWhitespace()

        while(
            context.peek(context.length).replace(" ", "").run {
                this.startsWith("<") && !this.startsWith("</")
            }
        ) {
            elements.add(parseElement(context))
        }

        return elements
    }

    private fun parseElement(context: ParserContext): Element {
        context.expect("<")
        context.skipWhitespace()

        var scope = context
        var loop: For? = null
        val tag = context.readUntil { !it.isLetter() && it != '-' }
        val bindings = HashMap<String, Observable<out Any>>()
        val children = ArrayList<Element>()

        if(tag.isNullOrBlank()) {
            throw context.createSyntaxError("Expected tag name")
        }

        scope.skipWhitespace()

        while(context.peek()?.isLetterOrDigit() == true || scope.peek() == ':') {
            val attribute = parseAttribute(scope, bindings)

            scope = attribute.context

            if(loop == null) {
                loop = attribute.loop
            }
        }

        if(scope.peek() == '/') {
            scope.read()
            scope.skipWhitespace()
            scope.expect(">")
        }
        else {
            println("scanning children")
            scope.expect(">")
            scope.skipWhitespace()
            children.addAll(parse(scope))

            scope.expect("<")
            scope.skipWhitespace()
            scope.expect("/")
            scope.skipWhitespace()
            scope.expect(tag)
            scope.skipWhitespace()
            scope.expect(">")
        }

        scope.skipWhitespace()

        val layout = ForLayout(IfLayout(DerivedLayout(tag, itemFactory)))

        println("loop: $loop")
        return SimpleElement(children, layout, bindings, loop)
    }

    private fun parseAttribute(
        context: ParserContext,
        bindings: MutableMap<String, Observable<out Any>>
    ): AttributeResult {
        var scope = context
        var loop: For? = null
        val binding = scope.peek() == ':'

        if(binding) {
            scope.read()
        }

        val attribute = scope.readUntil { !it.isLetter() && it != '-' }
            ?.takeIf { it.isNotBlank() }
            ?: throw scope.createSyntaxError("Expected attribute name")

        scope.expect("=\"")

        if(attribute == "for") {
            loop = parseFor(scope)

            scope = context.scope(
                mapOf(
                    loop.variable to loop.reference,
                )
            )

            bindings["for"] = scope.getBinding(loop.binding)
        }
        else {
            val value = scope.readUntil { it == '"' }?.takeIf { it.isNotBlank() }
                ?: throw scope.createSyntaxError("Expected attribute value")

            bindings[attribute] = if(binding) scope.getBinding(value)
            else Observable.just(value)
        }

        scope.read() // "
        scope.skipWhitespace()

        return AttributeResult(scope, loop)
    }

    private fun parseFor(context: ParserContext): For {
        val variable = context.readUntil { !it.isLetterOrDigit() }
            ?.takeIf { it.isNotBlank() }
            ?: throw context.createSyntaxError("Expected for variable name")

        context.skipWhitespace()
        context.expect("in")
        context.skipWhitespace()

        val binding = context.readUntil { it == '"' }
            ?.takeIf { it.isNotBlank() }
            ?: throw context.createSyntaxError("Expected for binding name")

        return For(
            binding,
            variable,
            reference = context.getBinding(binding)
        )
    }
}

data class AttributeResult(
    val context: ParserContext,
    val loop: For?
)