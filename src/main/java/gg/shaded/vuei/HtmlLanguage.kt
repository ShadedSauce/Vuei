package gg.shaded.vuei

import gg.shaded.vuei.layout.*
import rx.Observable
import rx.subjects.ReplaySubject

class HtmlLanguage(
    private val itemFactory: ItemFactory,
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
        val bindings = HashMap<String, String>()
        val values = HashMap<String, Any>()
        val children = ArrayList<Element>()

        if(tag.isNullOrBlank()) {
            throw context.createSyntaxError("Expected tag name")
        }

        scope.skipWhitespace()

        while(context.peek()?.isLetterOrDigit() == true || scope.peek() == ':') {
            val attribute = parseAttribute(scope, bindings, values)

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

        val layout = ClickableLayout(
            ForLayout(
                IfLayout(
                    DerivedLayout(tag, itemFactory)
                )
            )
        )

        return SimpleElement(children, layout, bindings, values, loop)
    }

    private fun parseAttribute(
        context: ParserContext,
        bindings: MutableMap<String, String>,
        values: MutableMap<String, Any>
    ): AttributeResult {
        val scope = context
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
        }
        else {
            if(scope.peek() == '[') {
                if(binding) {
                    throw context.createSyntaxError("Binding cannot be array.")
                }

                values[attribute] = parseArray(context)
            }
            else {
                val value = scope.readUntil { it == '"' }
                    ?.takeIf { it.isNotBlank() }
                    ?: throw scope.createSyntaxError("Expected attribute value")

                if(binding) bindings[attribute] = value
                else values[attribute] = value
            }
        }

        scope.read() // "
        scope.skipWhitespace()

        return AttributeResult(scope, loop)
    }

    private fun parseArray(context: ParserContext): List<String> {
        val array = ArrayList<String>()

        context.expect("[")
        context.skipWhitespace()

        while(context.peek() == '\'') {
            context.expect("'")
            val value = context.readUntil { it == '\'' }
                ?: throw context.createSyntaxError("Expected array value.")
            context.expect("'")

            if(context.peek() == ',') {
                context.expect(",")
                context.skipWhitespace()
            }

            array.add(value)
        }

        context.skipWhitespace()
        context.expect("]")
        return array
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

        return For(binding, variable)
    }
}

data class AttributeResult(
    val context: ParserContext,
    val loop: For?
)