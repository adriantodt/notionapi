package net.adriantodt.notionapi

import net.adriantodt.notionapi.model.NotionApiBuilder
import net.adriantodt.notionapi.model.NotionObject
import net.adriantodt.notionapi.model.block.Block
import net.adriantodt.notionapi.model.database.Database
import net.adriantodt.notionapi.model.page.Page
import net.adriantodt.notionapi.model.user.User
import net.adriantodt.notionapi.net.ParameterizedRequest
import net.adriantodt.notionapi.net.request.CreatePageParams
import net.adriantodt.notionapi.net.request.CursorParams
import net.adriantodt.notionapi.net.request.DatabaseQueryParams
import net.adriantodt.notionapi.net.request.SearchParams
import net.adriantodt.notionapi.net.request.block.PageBuilder
import net.adriantodt.notionapi.net.request.properties.PropertiesBuilder
import net.adriantodt.notionapi.utils.AsyncCursorResponse
import net.adriantodt.notionapi.utils.AsyncResponse
import net.adriantodt.notionapi.utils.ParameterizedRequestClosure
import net.adriantodt.notionapi.utils.RequestClosure

interface NotionApi {
    fun retrieveDatabase(
        id: String,
        block: RequestClosure = {}
    ): AsyncResponse<Database>

    fun queryDatabase(
        id: String,
        block: ParameterizedRequest<DatabaseQueryParams>.() -> Unit = {}
    ): AsyncCursorResponse<Page>

    fun listDatabases(
        block: ParameterizedRequestClosure<CursorParams> = {}
    ): AsyncCursorResponse<Database>

    fun retrievePage(
        id: String,
        block: RequestClosure = {}
    ): AsyncResponse<Page>

    fun createPage(
        block: ParameterizedRequestClosure<CreatePageParams> = {}
    ): AsyncResponse<Page>

    fun updatePage(
        id: String,
        block: ParameterizedRequestClosure<PropertiesBuilder> = {}
    ): AsyncResponse<Page>

    fun retrieveBlockChildren(
        id: String,
        block: ParameterizedRequestClosure<CursorParams> = {}
    ): AsyncCursorResponse<Block>

    fun appendBlockChildren(
        id: String,
        block: ParameterizedRequestClosure<PageBuilder> = {}
    ): AsyncResponse<Block>

    fun retrieveUser(
        id: String,
        block: RequestClosure = {}
    ): AsyncResponse<User>

    fun listUsers(
        block: ParameterizedRequestClosure<CursorParams> = {}
    ): AsyncCursorResponse<User>

    fun search(
        block: ParameterizedRequestClosure<SearchParams> = {}
    ): AsyncCursorResponse<NotionObject>

    companion object {
        operator fun invoke(block: NotionApiBuilder.() -> Unit): NotionApi {
            return NotionApiBuilder().apply(block).build()
        }
    }
}
