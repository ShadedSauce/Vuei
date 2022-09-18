package gg.shaded.vuei.layout

import gg.shaded.vuei.*
import io.reactivex.rxjava3.core.Observable
import org.bukkit.inventory.ItemStack
import org.graalvm.polyglot.Value

class IconLayout(
    private val itemFactory: ItemFactory,
    private val i18n: I18n
): Layout {
    override fun allocate(context: LayoutContext): Observable<List<Renderable>> {
        val type = context.getAttributeBinding("type")?.observe() as? Observable<Any>
            ?: throw IllegalStateException("Icon has no type.")

        val name = context.getAttributeBinding("name")?.observe() as? Observable<Any>
            ?: Observable.just("")

        val description = context.getAttributeBinding("description")?.observe() as? Observable<List<Any>>
            ?: Observable.just(ArrayList())

        return Observable.combineLatest(
            type, name, description
        )
        { t, n, d ->
            val item = when(t) {
                is ItemStack -> itemFactory.create(
                    t,
                    n.takeIf { it !is String || it.isNotEmpty() }
                        ?.let(this::translate),
                    d.takeIf { it.isNotEmpty() }?.map(this::translate)
                )
                is String -> itemFactory.create(
                    t,
                    n.takeIf { it !is String || it.isNotEmpty() }
                        ?.let(this::translate),
                    d.takeIf { it.isNotEmpty() }?.map(this::translate)
                )
                else -> throw IllegalStateException("Icon type must be Material or ItemStack.")
            }

            SimpleRenderable(
                x = 0,
                y = 0,
                width = 1,
                height = 1,
                element = context.element,
                item = item
            ).toList()
        }
    }

    private fun translate(any: Any): net.kyori.adventure.text.Component {
        return when (any) {
            is String -> i18n.translate(any)
            is net.kyori.adventure.text.Component -> any
            is List<*> -> {
                i18n.translate(
                    any.first() as String,
                    *any.drop(1).filterNotNull().toTypedArray()
                )
            }
            else -> throw IllegalArgumentException("$any not translatable.")
        }
    }
}