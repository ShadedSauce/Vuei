package gg.shaded.vuei

import rx.Observable

interface Component {
    val template: Template

    val imports: Map<String, Component>
        get() = HashMap()

    fun setup(): Map<String, Observable<out Any>> {
        return HashMap()
    }
}