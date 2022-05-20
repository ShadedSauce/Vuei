package gg.shaded.vuei

import io.reactivex.rxjava3.core.Observable

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