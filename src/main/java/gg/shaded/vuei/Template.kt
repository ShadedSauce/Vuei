package gg.shaded.vuei

interface Template {
    val elements: List<Element>
}

class HtmlTemplate(
    private val body: String,
    private val itemFactory: ItemFactory = ItemBuilderItemFactory(),
    private val i18n: I18n = SimpleI18n()
): Template {
    override val elements: List<Element>
        get() = HtmlLanguage(itemFactory, i18n).parse(DequeParserContext(body))
}

fun String.interpolate() = this.replace("$%", "$")
