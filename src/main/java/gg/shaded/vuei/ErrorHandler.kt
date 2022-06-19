package gg.shaded.vuei

import org.bukkit.entity.Player

fun interface ErrorHandler {
    fun handle(t: Throwable, viewers: Iterable<Player>)
}