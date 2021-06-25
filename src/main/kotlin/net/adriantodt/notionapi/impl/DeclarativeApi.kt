package net.adriantodt.notionapi.impl

import com.grack.nanojson.JsonArray
import com.grack.nanojson.JsonParser
import net.adriantodt.notionapi.net.Cursor
import net.adriantodt.notionapi.net.ParameterizedRequest
import net.adriantodt.notionapi.net.Request
import net.adriantodt.notionapi.net.Response
import net.adriantodt.notionapi.utils.AsyncCursorResponse
import net.adriantodt.notionapi.utils.Closure
import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpHeaders
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration
import java.util.concurrent.CompletableFuture
import kotlin.properties.Delegates

abstract class DeclarativeApi {
    protected abstract val baseUrl: String
    protected abstract val httpClient: HttpClient

    protected abstract fun newRequest() : HttpRequest.Builder

    protected open inner class RequestImpl : Request {
        val builder = newRequest()

        override var timeout: Duration? by Delegates.observable(null) { _, _, value ->
            builder.timeout(value)
        }

        open fun endpoint(endpoint: String) = apply {
            builder.uri(URI.create(baseUrl + endpoint))
        }

        fun send(block: Closure<HttpRequest.Builder>): CompletableFuture<HttpResponse<String>> {
            return httpClient.sendAsync(builder.apply(block).build(), HttpResponse.BodyHandlers.ofString())
        }
    }

    protected inner class ParameterizedRequestImpl<T>(override val params: T) : RequestImpl(), ParameterizedRequest<T> {
        override fun params(block: Closure<T>) {
            params.apply(block)
        }

        override fun endpoint(endpoint: String) = apply {
            super.endpoint(endpoint)
        }

        fun endpointParams(block: (T) -> String) = apply {
            endpoint(block(params))
        }

        fun sendParams(block: HttpRequest.Builder.(T) -> Unit): CompletableFuture<HttpResponse<String>> {
            return httpClient.sendAsync(builder.apply { block(params) }.build(), HttpResponse.BodyHandlers.ofString())
        }
    }

    protected open inner class ResponseImpl<T>(val res: HttpResponse<String>, block: (Any?) -> T) : Response<T> {
        override val headers: HttpHeaders get() = res.headers()
        override val statusCode: Int get() = res.statusCode()
        override val rawBody: String get() = res.body()
        override val rawJsonBody: Any? by lazy { JsonParser.any().from(rawBody) }
        override val body: T by lazy { block(rawJsonBody) }
    }

    protected inner class CursorResponseImpl<T>(
        res: HttpResponse<String>,
        resultBlock: (JsonArray) -> List<T>,
        nextBlock: (String) -> AsyncCursorResponse<T>
    ) : ResponseImpl<Cursor<T>>(res, { CursorImpl.fromResponse(it, resultBlock, nextBlock) })

    protected fun queryParams(pairs: List<Pair<String, String>>): String {
        if (pairs.isEmpty()) return ""
        return pairs.joinToString(",", "?") { (k, v) -> "${urlEncode(k)}=${urlEncode(v)}" }
    }

    protected fun queryParams(vararg pairs: Pair<String, String>): String {
        if (pairs.isEmpty()) return ""
        return pairs.joinToString(",", "?") { (k, v) -> "${urlEncode(k)}=${urlEncode(v)}" }
    }

    private fun urlEncode(s: String) = URLEncoder.encode(s, Charsets.UTF_8)
}
