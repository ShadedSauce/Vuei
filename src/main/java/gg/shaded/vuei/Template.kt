package gg.shaded.vuei

import rx.Observable

interface Template {
    fun getElement(bindings: Map<String, Observable<out Any>>): Element
}

class HtmlTemplate(
    private val body: String,
    private val itemFactory: ItemFactory = ItemBuilderItemFactory()
): Template {
    override fun getElement(bindings: Map<String, Observable<out Any>>) =
        HtmlLanguage(itemFactory).parse(DequeParserContext(body, bindings)).first()
}