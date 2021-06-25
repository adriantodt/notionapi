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
        override val checked: Boolean
    ) : ToDoBlock

    data class ToDoBlockWithChildrenImpl(
        override val id: String,
        override val type: BlockType,
        override val createdTime: OffsetDateTime,
        override val lastEditedTime: OffsetDateTime,
        override val hasChildren: Boolean,
        override val text: List<RichText>,
        override val children: List<Block>,
        override val checked: Boolean
    ) : ToDoBlock, TextBlockWithChildren

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
                BlockType.HEADING_3,
                BlockType.PARAGRAPH,
                BlockType.BULLETED_LIST_ITEM,
                BlockType.NUMBERED_LIST_ITEM,
                BlockType.TOGGLE -> parseTextBlock(
                    id,
                    type,
                    createdTime,
                    lastEditedTime,
                    hasChildren,
                    obj.getObject(type.value)
                )
                BlockType.TO_DO -> parseToDoBlock(
                    id,
                    type,
                    createdTime,
                    lastEditedTime,
                    hasChildren,
                    obj.getObject(type.value)
                )
                BlockType.CHILD_PAGE -> ChildPageBlockImpl(
                    id, type, createdTime, lastEditedTime, hasChildren,
                    obj.getObject(type.value).getString("title")
                )
                BlockType.UNSUPPORTED -> BlockImpl(id, type, createdTime, lastEditedTime, hasChildren)
            }
        }

        private fun parseTextBlock(
            id: String, type: BlockType, createdTime: OffsetDateTime, lastEditedTime: OffsetDateTime,
            hasChildren: Boolean, subObj: JsonObject
        ): TextBlock {
            val text = RichTextImpl.fromJsonArray(subObj.getArray("text", jsonArrayOf()))
            val children = subObj.getArray("children")

            if (children.isNullOrEmpty()) {
                return TextBlockImpl(id, type, createdTime, lastEditedTime, hasChildren, text)
            }
            return TextBlockWithChildrenImpl(
                id, type, createdTime, lastEditedTime, hasChildren, text, fromJsonArray(children)
            )
        }

        private fun parseToDoBlock(
            id: String, type: BlockType, createdTime: OffsetDateTime, lastEditedTime: OffsetDateTime,
            hasChildren: Boolean, subObj: JsonObject
        ): TextBlock {
            val text = RichTextImpl.fromJsonArray(subObj.getArray("text", jsonArrayOf()))
            val checked = subObj.getBoolean("checked", false)
            val children = subObj.getArray("children")

            if (children.isNullOrEmpty()) {
                return ToDoBlockImpl(id, type, createdTime, lastEditedTime, hasChildren, text, checked)
            }
            return ToDoBlockWithChildrenImpl(
                id, type, createdTime, lastEditedTime, hasChildren, text, fromJsonArray(children), checked
            )
        }
    }
}
