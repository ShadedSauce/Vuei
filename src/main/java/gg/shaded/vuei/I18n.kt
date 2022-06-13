package gg.shaded.vuei

import net.kyori.adventure.text.Component

interface I18n {
    fun translate(string: String, vararg args: Any): Component
}

class SimpleI18n: I18n {
    override fun translate(string: String, vararg args: Any): Component {
        return Component.text(string.format(args))
    }
}