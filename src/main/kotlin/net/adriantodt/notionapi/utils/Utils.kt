package net.adriantodt.notionapi.utils

import com.grack.nanojson.JsonArray
import com.grack.nanojson.JsonObject
import net.adriantodt.notionapi.net.Cursor
import net.adriantodt.notionapi.net.ParameterizedRequest
import net.adriantodt.notionapi.net.Request
import net.adriantodt.notionapi.net.Response
import java.util.concurrent.CompletableFuture

typealias Closure<T> = T.() -> Unit

typealias RequestClosure = Closure<Request>

typealias ParameterizedRequestClosure<T> = Closure<ParameterizedRequest<T>>

typealias AsyncResponse<T> = CompletableFuture<Response<T>>

typealias AsyncCursorResponse<T> = AsyncResponse<Cursor<T>>

typealias Range<T> = Pair<T, T?>

fun jsonObjectOf(): JsonObject = JsonObject()

fun jsonObjectOf(pair: Pair<String, Any?>): JsonObject = JsonObject(mapOf(pair))

fun jsonObjectOf(vararg pairs : Pair<String, Any?>): JsonObject {
    return if (pairs.isNotEmpty()) JsonObject(pairs.toMap()) else JsonObject()
}

fun buildJsonObject(block: JsonObject.() -> Unit): JsonObject {
    return jsonObjectOf().apply(block)
}

fun jsonArrayOf(): JsonArray = JsonArray()

fun jsonArrayOf(pair: Any?): JsonArray = JsonArray.from(pair)

fun jsonArrayOf(vararg pairs : Any?): JsonArray {
    return if (pairs.isNotEmpty()) JsonArray.from(*pairs) else JsonArray()
}

fun buildJsonArray(block: JsonArray.() -> Unit): JsonArray {
    return jsonArrayOf().apply(block)
}

operator fun <T> Cursor<T>.iterator(): Iterator<T> {
    return generateSequence(this) { if (!it.hasMore) null else it.next().join().body }
        .flatMap { it.results }
        .iterator()
}

internal inline fun <reified T : Enum<T>> T.lowercaseOfName(): Lazy<String> {
    return lazy { this.name.lowercase() }
}
