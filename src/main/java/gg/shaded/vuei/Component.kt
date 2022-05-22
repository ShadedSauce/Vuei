package gg.shaded.vuei

import io.reactivex.rxjava3.core.Observable

interface Component {
    val template: Template

    val props: List<Prop>
        get() = emptyList()

    val imports: Map<String, Component>
        get() = emptyMap()

    fun setup(context: SetupContext): Observable<Map<String, Any?>> {
        return Observable.just(HashMap())
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

    fun emit(e: Any) {
        val emitter = props["emit"] ?: throw IllegalStateException("Emit not defined.")

        emitter.invoke(e)
    }
}

class SimpleSetupContext(
    override val props: Map<String, Any?>
): SetupContext

fun Map<String, Any?>.sync(): Observable<Map<String, Any?>>
    = Observable.just(this)