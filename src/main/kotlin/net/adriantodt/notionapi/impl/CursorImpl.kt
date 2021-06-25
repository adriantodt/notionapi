package net.adriantodt.notionapi.impl

import com.grack.nanojson.JsonArray
import com.grack.nanojson.JsonObject
import net.adriantodt.notionapi.net.Cursor
import net.adriantodt.notionapi.utils.AsyncCursorResponse

data class CursorImpl<T>(
    override val results: List<T>,
    override val hasMore: Boolean,
    override val nextCursor: String?,
    private val nextBlock: (String) -> AsyncCursorResponse<T>
) : Cursor<T> {
    override fun next(): AsyncCursorResponse<T> {
        if (nextCursor == null) {
            throw IllegalStateException("There is no further cursors to call")
        }
        return nextBlock(nextCursor)
    }

    companion object {
        fun <T> fromResponse(
            raw: Any?,
            resultBlock: (JsonArray) -> List<T>,
            nextBlock: (String) -> AsyncCursorResponse<T>
        ): CursorImpl<T> {
            require(raw is JsonObject) { "Response body is not a JsonObject" }
            return fromJsonObject(raw, resultBlock, nextBlock)
        }

        fun <T> fromJsonObject(
            obj: JsonObject,
            resultBlock: (JsonArray) -> List<T>,
            nextBlock: (String) -> AsyncCursorResponse<T>
        ): CursorImpl<T> {
            return CursorImpl(
                resultBlock(obj.getArray("results")),
                obj.getBoolean("has_more"),
                obj.getString("next_cursor"),
                nextBlock
            )
        }
    }
}
