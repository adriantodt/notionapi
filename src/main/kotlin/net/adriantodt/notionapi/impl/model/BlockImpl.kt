package net.adriantodt.notionapi.impl.model

import com.grack.nanojson.JsonArray
import com.grack.nanojson.JsonObject
import net.adriantodt.notionapi.model.block.*
import net.adriantodt.notionapi.model.richtext.RichText
import net.adriantodt.notionapi.utils.jsonArrayOf
import java.time.OffsetDateTime

data class BlockImpl(
    override val id: String,
    override val type: BlockType,
    override val createdTime: OffsetDateTime,
    override val lastEditedTime: OffsetDateTime,
    override val hasChildren: Boolean
) : Block {
    data class ChildPageBlockImpl(
        override val id: String,
        override val type: BlockType,
        override val createdTime: OffsetDateTime,
        override val lastEditedTime: OffsetDateTime,
        override val hasChildren: Boolean,
        override val title: String
    ) : ChildPageBlock

    data class TextBlockImpl(
        override val id: String,
        override val type: BlockType,
        override val createdTime: OffsetDateTime,
        override val lastEditedTime: OffsetDateTime,
        override val hasChildren: Boolean,
        override val text: List<RichText>
    ) : TextBlock

    data class TextBlockWithChildrenImpl(
        override val id: String,
        override val type: BlockType,
        override val createdTime: OffsetDateTime,
        override val lastEditedTime: OffsetDateTime,
        override val hasChildren: Boolean,
        override val text: List<RichText>,
        override val children: List<Block>
    ) : TextBlockWithChildren

    data class ToDoBlockImpl(
        override val id: String,
        override val type: BlockType,
        override val createdTime: OffsetDateTime,
        override val lastEditedTime: OffsetDateTime,
        override val hasChildren: Boolean,
        override val text: List<RichText>,
        override val children: List<Block>,
        override val checked: Boolean
    ) : ToDoBlock

    companion object {
        fun fromResponse(raw: Any?): Block {
            require(raw is JsonObject) { "Response body is not a JsonObject" }
            return fromJsonObject(raw)
        }

        fun fromJsonArray(array: JsonArray): List<Block> {
            require(array.all { it is JsonObject }) { "Invalid input, array should only contain JsonObjects." }
            return array.asSequence()
                .filterIsInstance<JsonObject>()
                .map { fromJsonObject(it) }
                .toList()
        }

        fun fromJsonObject(obj: JsonObject): Block {
            require(obj.getString("object") == "block") { "Object is not a block" }
            val id = obj.getString("id")
            val type = BlockType.fromJson(obj.getString("type"))
            val createdTime = obj.getString("created_time").toOffsetDateTime()
            val lastEditedTime = obj.getString("last_edited_time").toOffsetDateTime()
            val hasChildren = obj.getBoolean("has_children")

            return when (type) {
                BlockType.HEADING_1,
                BlockType.HEADING_2,
                BlockType.HEADING_3 -> TextBlockImpl(
                    id, type, createdTime, lastEditedTime, hasChildren,
                    RichTextImpl.fromJsonArray(obj.getObject(type.value).getArray("text", jsonArrayOf()))
                )
                BlockType.PARAGRAPH,
                BlockType.BULLETED_LIST_ITEM,
                BlockType.NUMBERED_LIST_ITEM,
                BlockType.TOGGLE -> {
                    val subObj = obj.getObject(type.value)
                    TextBlockWithChildrenImpl(
                        id, type, createdTime, lastEditedTime, hasChildren,
                        RichTextImpl.fromJsonArray(subObj.getArray("text", jsonArrayOf())),
                        fromJsonArray(subObj.getArray("children", jsonArrayOf()))
                    )
                }
                BlockType.TO_DO -> {
                    val subObj = obj.getObject(type.value)
                    ToDoBlockImpl(
                        id, type, createdTime, lastEditedTime, hasChildren,
                        RichTextImpl.fromJsonArray(subObj.getArray("text", jsonArrayOf())),
                        fromJsonArray(subObj.getArray("children", jsonArrayOf())),
                        subObj.getBoolean("checked", false)
                    )
                }
                BlockType.CHILD_PAGE -> ChildPageBlockImpl(
                    id, type, createdTime, lastEditedTime, hasChildren,
                    obj.getObject(type.value).getString("title")
                )
                BlockType.UNSUPPORTED -> BlockImpl(id, type, createdTime, lastEditedTime, hasChildren)
            }
        }
    }
}
