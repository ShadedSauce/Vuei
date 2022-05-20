package gg.shaded.vuei

import io.reactivex.rxjava3.core.Observable
import java.lang.IllegalStateException
import kotlin.math.max
import kotlin.math.min

interface Parser<T> {
    fun parse(context: ParserContext): T
}

interface ParserContext {
    val length: Int

    fun peek(): Char?

    fun peek(amount: Int = 1): String

    fun read(): Char?

    fun read(amount: Int = 1): String

    fun readUntil(predicate: (Char) -> Boolean): String?

    fun readUntil(chars: Array<Char>): String?

    fun expect(expected: String)

    fun skipWhitespace()

    fun createSyntaxError(reason: String): Throwable
}

class SyntaxError(message: String): RuntimeException(message)

class DequeParserContext(
    private val body: String,
    private val deque: ArrayDeque<Char> = ArrayDeque(body.toCharArray().toList()),
    private var index: Int = 0
): ParserContext {
    override val length: Int
        get() = deque.size

    override fun peek(): Char? {
        if(deque.isEmpty()) return null

        return deque.first()
    }

    override fun peek(amount: Int): String {
        return deque.take(amount).joinToString("")
    }

    override fun read(): Char? {
        if(deque.isEmpty()) return null

        index++
        return deque.removeFirst()
    }

    override fun read(amount: Int): String {
        return (0 until amount).map { read() }.joinToString("")
    }

    override fun readUntil(predicate: (Char) -> Boolean): String? {
        var found = false

        val string = deque.takeWhile { char ->
            val result = predicate(char)

            if(result) {
                found = true
                return@takeWhile false
            }

            true
        }.joinToString("")

        if(!found) {
            return null
        }

        return read(string.length)
    }

    override fun readUntil(chars: Array<Char>): String? {
        return readUntil { !chars.contains(it) }
    }

    override fun expect(expected: String) {
        expected.forEach { char ->
            val actual = read()

            if(actual != char) {
                throw createSyntaxError("Unexpected character '${actual}'. Expected: '${char}'")
            }
        }
    }

    override fun skipWhitespace() {
        while(peek() == ' ' || peek() == '\n') read()
    }

    override fun createSyntaxError(reason: String): Throwable {
        val near = body.substring(max(0, index - 1)..min(index + 10, body.length - 1))
            .replace("\n", "")

        return SyntaxError("Syntax error near '${near}'. Reason: ${reason}.")
    }
}