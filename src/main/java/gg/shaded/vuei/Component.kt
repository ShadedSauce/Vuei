package gg.shaded.vuei

import rx.Observable
import rx.subjects.Subject

interface Component {
    val template: Template

    fun setup(): Map<String, Observable<out Any>>
}