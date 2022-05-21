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

    fun emit(e: Any) {
        val emitter = props["emit"] ?: throw IllegalStateException("Emit not defined.")

        emitter.invoke(e)
    }
}

class SimpleSetupContext(
    override val props: Map<String, Any>
): SetupContext

fun Map<String, Any>.sync() = Observable.just(this)