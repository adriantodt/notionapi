package net.adriantodt.notionapi

import net.adriantodt.notionapi.model.block.Block
import net.adriantodt.notionapi.model.block.ChildPageBlock
import net.adriantodt.notionapi.model.database.Database
import net.adriantodt.notionapi.model.page.Page
import net.adriantodt.notionapi.model.page.PageParent
import net.adriantodt.notionapi.net.request.CursorParams
import net.adriantodt.notionapi.net.request.block.PageBuilder
import net.adriantodt.notionapi.utils.AsyncCursorResponse
import net.adriantodt.notionapi.utils.AsyncResponse
import net.adriantodt.notionapi.utils.ParameterizedRequestClosure
import net.adriantodt.notionapi.utils.RequestClosure

fun Page.retrieveBlockChildren(
    notion: NotionApi,
    block: ParameterizedRequestClosure<CursorParams> = {}
): AsyncCursorResponse<Block> {
    return notion.retrieveBlockChildren(id, block)
}

fun ChildPageBlock.retrieve(
    notion: NotionApi,
    block: RequestClosure = {}
): AsyncResponse<Page> {
    return notion.retrievePage(id, block)
}

fun PageParent.Page.retrieve(
    notion: NotionApi,
    block: RequestClosure = {}
): AsyncResponse<Page> {
    return notion.retrievePage(id, block)
}

fun PageParent.Database.retrieve(
    notion: NotionApi,
    block: RequestClosure = {}
): AsyncResponse<Database> {
    return notion.retrieveDatabase(id, block)
}

fun Page.appendBlockChildren(
    notion: NotionApi,
    block: ParameterizedRequestClosure<PageBuilder> = {}
): AsyncResponse<Block> {
    return notion.appendBlockChildren(id, block)
}

fun Block.retrieveBlockChildren(
    notion: NotionApi,
    block: ParameterizedRequestClosure<CursorParams> = {}
): AsyncCursorResponse<Block> {
    return notion.retrieveBlockChildren(id, block)
}
