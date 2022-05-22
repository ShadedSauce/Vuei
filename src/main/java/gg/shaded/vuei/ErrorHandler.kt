package gg.shaded.vuei

fun interface ErrorHandler {
    fun handle(t: Throwable)
}