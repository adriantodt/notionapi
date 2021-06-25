package net.adriantodt.notionapi.impl

import com.grack.nanojson.JsonObject
import com.grack.nanojson.JsonWriter
import net.adriantodt.notionapi.NotionApi
import net.adriantodt.notionapi.impl.model.*
import net.adriantodt.notionapi.model.NotionObject
import net.adriantodt.notionapi.model.block.Block
import net.adriantodt.notionapi.model.database.Database
import net.adriantodt.notionapi.model.page.Page
import net.adriantodt.notionapi.model.user.User
import net.adriantodt.notionapi.net.request.CreatePageParams
import net.adriantodt.notionapi.net.request.CursorParams
import net.adriantodt.notionapi.net.request.DatabaseQueryParams
import net.adriantodt.notionapi.net.request.SearchParams
import net.adriantodt.notionapi.net.request.block.PageBuilder
import net.adriantodt.notionapi.net.request.properties.PropertiesBuilder
import net.adriantodt.notionapi.utils.*
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.nio.charset.Charset
import java.net.http.HttpRequest.BodyPublishers.ofString as stringBody

data class NotionApiImpl(
    private val accessToken: String,
    override val baseUrl: String = "https://api.notion.com/v1",
    override val httpClient: HttpClient = HttpClient.newHttpClient(),
    private val notionVersion: String = "2021-05-13"
) : DeclarativeApi(), NotionApi {
    override fun retrieveDatabase(
        id: String,
        block: RequestClosure
    ): AsyncResponse<Database> {
        return RequestImpl()
            .apply(block)
            .endpoint("/databases/$id")
            .send { GET() }
            .thenApply { res -> ResponseImpl(res, DatabaseImpl::fromResponse) }
    }

    override fun queryDatabase(
        id: String,
        block: ParameterizedRequestClosure<DatabaseQueryParams>
    ): AsyncCursorResponse<Page> {
        val params = DatabaseQueryParams()
        return ParameterizedRequestImpl(params)
            .apply(block)
            .endpoint("/databases/$id/query")
            .sendParams { POST(stringBody(JsonWriter.string(it.toJson()), Charsets.UTF_8)) }
            .thenApply { res ->
                CursorResponseImpl(res, PageImpl::fromJsonArray) { nextCursor ->
                    this.queryDatabase(id) {
                        block()
                        params {
                            startCursor = nextCursor
                            pageSize = params.pageSize
                        }
                    }
                }
            }
    }

    override fun listDatabases(
        block: ParameterizedRequestClosure<CursorParams>
    ): AsyncCursorResponse<Database> {
        val params = CursorParams()
        return ParameterizedRequestImpl(params)
            .apply(block)
            .endpointParams { "/databases" + it.queryParams() }
            .send { GET() }
            .thenApply { res ->
                CursorResponseImpl(res, DatabaseImpl::fromJsonArray) { nextCursor ->
                    this.listDatabases {
                        block()
                        params {
                            startCursor = nextCursor
                            pageSize = params.pageSize
                        }
                    }
                }
            }
    }

    override fun retrievePage(
        id: String,
        block: RequestClosure
    ): AsyncResponse<Page> {
        return RequestImpl()
            .apply(block)
            .endpoint("/pages/$id")
            .send { GET() }
            .thenApply { res -> ResponseImpl(res, PageImpl::fromResponse) }
    }

    override fun createPage(
        block: ParameterizedRequestClosure<CreatePageParams>
    ): AsyncResponse<Page> {
        return ParameterizedRequestImpl(CreatePageParams())
            .apply(block)
            .endpoint("/pages")
            .sendParams { POST(stringBody(JsonWriter.string(it.toJson()), Charsets.UTF_8)) }
            .thenApply { res -> ResponseImpl(res, PageImpl::fromResponse) }
    }

    override fun updatePage(
        id: String,
        block: ParameterizedRequestClosure<PropertiesBuilder>
    ): AsyncResponse<Page> {
        return ParameterizedRequestImpl(PropertiesBuilder())
            .apply(block)
            .endpoint("/pages/$id")
            .sendParams { PATCH(stringBody(JsonWriter.string(it.toJson()), Charsets.UTF_8)) }
            .thenApply { res -> ResponseImpl(res, PageImpl::fromResponse) }
    }

    override fun retrieveBlockChildren(
        id: String,
        block: ParameterizedRequestClosure<CursorParams>
    ): AsyncCursorResponse<Block> {
        val params = CursorParams()
        return ParameterizedRequestImpl(params)
            .apply(block)
            .endpointParams { "/blocks/$id/children" + it.queryParams() }
            .send { GET() }
            .thenApply { res ->
                CursorResponseImpl(res, BlockImpl::fromJsonArray) { nextCursor ->
                    this.retrieveBlockChildren(id) {
                        block()
                        params {
                            startCursor = nextCursor
                            pageSize = params.pageSize
                        }
                    }
                }
            }
    }

    override fun appendBlockChildren(
        id: String,
        block: ParameterizedRequestClosure<PageBuilder>
    ): AsyncResponse<Block> {
        return ParameterizedRequestImpl(PageBuilder())
            .apply(block)
            .endpoint("/blocks/$id/children")
            .sendParams { PATCH(jsonObjectOf("children" to it.toJson()).toJsonString().asBody()) }
            .thenApply { res -> ResponseImpl(res, BlockImpl::fromResponse) }
    }

    override fun retrieveUser(id: String, block: RequestClosure): AsyncResponse<User> {
        return RequestImpl()
            .apply(block)
            .endpoint("/users/$id")
            .send { GET() }
            .thenApply { res -> ResponseImpl(res, UserImpl::fromResponse) }
    }

    override fun listUsers(block: ParameterizedRequestClosure<CursorParams>): AsyncCursorResponse<User> {
        val params = CursorParams()
        return ParameterizedRequestImpl(params)
            .apply(block)
            .endpointParams { "/users" + it.queryParams() }
            .send { GET() }
            .thenApply { res ->
                CursorResponseImpl(res, UserImpl::fromJsonArray) { nextCursor ->
                    this.listUsers {
                        block()
                        params {
                            startCursor = nextCursor
                            pageSize = params.pageSize
                        }
                    }
                }
            }
    }

    override fun search(block: ParameterizedRequestClosure<SearchParams>): AsyncCursorResponse<NotionObject> {
        val params = SearchParams()
        return ParameterizedRequestImpl(params)
            .apply(block)
            .endpointParams { "/search" }
            .sendParams { POST(it.toJson().toJsonString().asBody()) }
            .thenApply { res ->
                CursorResponseImpl(res, NotionObjectImpl::fromJsonArray) { nextCursor ->
                    this.search {
                        block()
                        params {
                            startCursor = nextCursor
                            pageSize = params.pageSize
                        }
                    }
                }
            }
    }

    // BELOW: Implementation details

    private val bearerToken by lazy { "Bearer $accessToken" }

    override fun newRequest(): HttpRequest.Builder {
        return HttpRequest.newBuilder()
            .header("Authorization", bearerToken)
            .header("Content-Type", "application/json")
            .header("Notion-Version", notionVersion)
    }

    private fun CursorParams.queryParams(): String {
        val list = mutableListOf<Pair<String, String>>()
        startCursor?.let { list += "start_cursor" to it }
        pageSize?.let { list += "page_size" to it.toString() }
        return queryParams(list)
    }

    fun JsonObject.toJsonString(): String {
        return JsonWriter.string(this)
    }

    fun String.asBody(charset: Charset = Charsets.UTF_8): HttpRequest.BodyPublisher {
        return HttpRequest.BodyPublishers.ofString(this, charset)
    }

    @Suppress("FunctionName")
    fun HttpRequest.Builder.PATCH(bodyPublisher: HttpRequest.BodyPublisher): HttpRequest.Builder {
        return method("PATCH", bodyPublisher)
    }

}
