package gg.shaded.vuei

import rx.Observable
import rx.Single
import rx.subjects.PublishSubject

interface Component {
    val template: Template

    val imports: Map<String, Component>
        get() = HashMap()

    fun setup(context: SetupContext): Observable<Map<String, Observable<out Any>>> {
        return Observable.just(HashMap())
    }
}

interface SetupContext {
    val props: Map<String, Observable<out Any>>

    val events: PublishSubject<in Any>
        get() = props["events"] as? PublishSubject<in Any>
            ?: throw IllegalStateException("Must pass events (is it a PublishSubject?)")
}

class SimpleSetupContext(
    override val props: Map<String, Observable<out Any>>
): SetupContext

fun Map<String, Observable<out Any>>.sync() = Observable.just(this)