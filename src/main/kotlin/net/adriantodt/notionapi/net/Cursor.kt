package net.adriantodt.notionapi.net

import net.adriantodt.notionapi.utils.AsyncCursorResponse

interface Cursor<T> {
    val results: List<T>
    val hasMore: Boolean
    val nextCursor: String?

    fun next(): AsyncCursorResponse<T>
}
