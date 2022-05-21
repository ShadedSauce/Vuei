package gg.shaded.vuei

interface Template {
    val element: Element
}

class HtmlTemplate(
    private val body: String,
    private val itemFactory: ItemFactory = ItemBuilderItemFactory()
): Template {
    override val element: Element
        get() = HtmlLanguage(itemFactory).parse(DequeParserContext(body)).first()
}

fun String.interpolate() = this.replace("$%", "$")
