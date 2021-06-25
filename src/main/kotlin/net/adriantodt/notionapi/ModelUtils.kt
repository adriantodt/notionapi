package net.adriantodt.notionapi

import net.adriantodt.notionapi.model.block.Block
import net.adriantodt.notionapi.model.page.Page
import net.adriantodt.notionapi.net.request.CursorParams
import net.adriantodt.notionapi.utils.AsyncCursorResponse
import net.adriantodt.notionapi.utils.ParameterizedRequestClosure

fun Page.retrieveBlockChildren(
    notion: NotionApi,
    block: ParameterizedRequestClosure<CursorParams> = {}
): AsyncCursorResponse<Block> {
    return notion.retrieveBlockChildren(id, block)
}

fun Block.retrieveBlockChildren(
    notion: NotionApi,
    block: ParameterizedRequestClosure<CursorParams> = {}
): AsyncCursorResponse<Block> {
    return notion.retrieveBlockChildren(id, block)
}
