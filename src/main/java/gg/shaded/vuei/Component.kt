package gg.shaded.vuei

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
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
            .map { it.plus("ctx" to context) }
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

class OptionalProp(
    override val name: String,
): Prop {
    override fun validate(value: Any?) = value
}

interface SetupContext {
    val props: Map<String, Any?>

    val tasks: PublishSubject<Completable>

    val uiScheduler: Scheduler

    val backgroundScheduler: Scheduler

    val errorHandler: ErrorHandler

    val queue: Completable
        get() = tasks.flatMapCompletable { it }

    fun emit(e: String, vararg args: Any?) {
        try {
            props["emit:$e"]?.invoke(*args)
        }
        catch(t: Throwable) {
            throw t
            throw RuntimeException("Error emitting $e.", t)
        }
    }

    fun throwError(t: Throwable) {
        tasks.onError(t)
    }

    fun <T> getProp(prop: String) = props[prop] as? T

    fun <T> getRequiredProp(prop: String) = props[prop] as? T
        ?: throw IllegalStateException("Required prop '$prop' not present.")

    fun enqueue(task: Completable)
}

class SimpleSetupContext(
    override val props: Map<String, Any?>,
    override val tasks: PublishSubject<Completable>,
    override val uiScheduler: Scheduler,
    override val backgroundScheduler: Scheduler,
    override val errorHandler: ErrorHandler
): SetupContext {
    override fun enqueue(task: Completable) {
        tasks.onNext(task)
    }
}

fun Map<String, Any?>.sync(): Observable<Map<String, Any?>>
    = Observable.just(this)