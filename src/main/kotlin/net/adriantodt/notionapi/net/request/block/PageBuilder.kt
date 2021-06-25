package net.adriantodt.notionapi.net.request.block

import com.grack.nanojson.JsonArray
import com.grack.nanojson.JsonObject
import net.adriantodt.notionapi.model.block.BlockType
import net.adriantodt.notionapi.utils.jsonObjectOf

class PageBuilder {
    var children = mutableListOf<JsonObject>()

    fun paragraph(block: TextBlockWithChildrenBuilder.() -> Unit) {
        children += TextBlockWithChildrenBuilder(BlockType.PARAGRAPH).apply(block).toJson()
    }

    fun heading1(block: TextBlockBuilder.() -> Unit) {
        children += TextBlockBuilder(BlockType.HEADING_1).apply(block).toJson()
    }

    fun heading2(block: TextBlockBuilder.() -> Unit) {
        children += TextBlockBuilder(BlockType.HEADING_2).apply(block).toJson()
    }

    fun heading3(block: TextBlockBuilder.() -> Unit) {
        children += TextBlockBuilder(BlockType.HEADING_3).apply(block).toJson()
    }

    fun bulletedListItem(block: TextBlockWithChildrenBuilder.() -> Unit) {
        children += TextBlockWithChildrenBuilder(BlockType.BULLETED_LIST_ITEM).apply(block).toJson()
    }

    fun numberedListItem(block: TextBlockWithChildrenBuilder.() -> Unit) {
        children += TextBlockWithChildrenBuilder(BlockType.NUMBERED_LIST_ITEM).apply(block).toJson()
    }

    fun toDoBlock(block: TodoBlockBuilder.() -> Unit) {
        children += TodoBlockBuilder().apply(block).toJson()
    }

    fun toggleBlock(block: TextBlockWithChildrenBuilder.() -> Unit) {
        children += TextBlockWithChildrenBuilder(BlockType.TOGGLE).apply(block).toJson()
    }

    fun childPage(title: String) {
        children += jsonObjectOf(
            "type" to BlockType.CHILD_PAGE.value,
            BlockType.CHILD_PAGE.value to jsonObjectOf("title" to title)
        )
    }

    internal fun toJson(): JsonArray {
        return JsonArray(children)
    }
}
