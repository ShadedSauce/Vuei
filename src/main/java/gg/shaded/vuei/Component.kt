package gg.shaded.vuei

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.PublishSubject

interface Component {
    val template: Template

    val imports: Map<String, Component>
        get() = HashMap()

    fun setup(context: SetupContext): Observable<Map<String, Any>> {
        return Observable.just(HashMap())
    }
}

interface SetupContext {
    val props: Map<String, Any>

    val events: PublishSubject<in Any>
        get() = props["events"] as? PublishSubject<in Any>
            ?: throw IllegalStateException("Must pass events (is it a PublishSubject?)")
}

class SimpleSetupContext(
    override val props: Map<String, Any>
): SetupContext

fun Map<String, Any>.sync() = Observable.just(this)