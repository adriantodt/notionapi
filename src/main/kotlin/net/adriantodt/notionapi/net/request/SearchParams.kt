package net.adriantodt.notionapi.net.request

import net.adriantodt.notionapi.utils.buildJsonObject

class SearchParams : CursorParams() {
    var query: String? = null

//    var filter: Filter? = null

//    var sort: List<Sort>? = null

    internal fun toJson() = buildJsonObject {
        query?.let { put("query", it) }
//        filter?.let { put("filter", it.toJson()) }
//        sort?.let { put("sort", JsonArray(it.map(Sort::toJson))) }
        startCursor?.let { put("start_cursor", it) }
        pageSize?.let { put("page_size", it) }
    }
}
