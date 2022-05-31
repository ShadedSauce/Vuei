package gg.shaded.vuei

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

interface Component {
    val template: Template

    val props: List<Prop>
        get() = emptyList()

    val imports: Map<String, Component>
        get() = emptyMap()
    fun setup(context: SetupContext): Observable<Map<String, Any?>> {
        return Observable.just(HashMap())
    }

    fun setupWithQueue(context: SetupContext): Observable<Map<String, Any?>> {
        return setup(context)
            .map { it.plus("setup" to context) }
            .mergeWith(context.queue)
    }
}

interface Prop {
    val name: String

    fun validate(value: Any?): Any?
}

class SimpleProp(
    override val name: String,
    private val validator: ((Any?) -> Any?)?,
): Prop {
    override fun validate(value: Any?) = validator?.invoke(value)
}

class RequiredProp(
    override val name: String
): Prop {
    override fun validate(value: Any?) =
        value ?: throw IllegalArgumentException("Required prop '$name' not present.")
}

interface SetupContext {
    val props: Map<String, Any?>

    val tasks: PublishSubject<Completable>

    val queue: Completable
        get() = tasks.flatMapCompletable { it }
    fun emit(vararg e: Any) {
        val emitter = props["emit"] ?: throw IllegalStateException("Emit not defined.")

        emitter.invoke(*e)
    }

    fun enqueue(task: Completable) {
        tasks.onNext(task)
    }
}

class SimpleSetupContext(
    override val props: Map<String, Any?>,
    override val tasks: PublishSubject<Completable>
): SetupContext

fun Map<String, Any?>.sync(): Observable<Map<String, Any?>>
    = Observable.just(this)