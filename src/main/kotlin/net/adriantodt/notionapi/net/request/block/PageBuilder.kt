package net.adriantodt.notionapi.net.request.block

import com.grack.nanojson.JsonArray
import com.grack.nanojson.JsonObject
import net.adriantodt.notionapi.model.block.BlockType
import net.adriantodt.notionapi.net.request.annotation.NotionDsl
import net.adriantodt.notionapi.utils.jsonObjectOf

@NotionDsl
class PageBuilder {
    var children = mutableListOf<JsonObject>()

    @NotionDsl
    operator fun String.unaryPlus() {
        paragraph { +this@unaryPlus }
    }

    @NotionDsl
    fun paragraph(block: TextBlockWithChildrenBuilder.() -> Unit) {
        children += TextBlockWithChildrenBuilder(BlockType.PARAGRAPH).apply(block).toJson()
    }

    @NotionDsl
    fun heading1(block: TextBlockBuilder.() -> Unit) {
        children += TextBlockBuilder(BlockType.HEADING_1).apply(block).toJson()
    }

    @NotionDsl
    fun heading2(block: TextBlockBuilder.() -> Unit) {
        children += TextBlockBuilder(BlockType.HEADING_2).apply(block).toJson()
    }

    @NotionDsl
    fun heading3(block: TextBlockBuilder.() -> Unit) {
        children += TextBlockBuilder(BlockType.HEADING_3).apply(block).toJson()
    }

    @NotionDsl
    fun bulletedListItem(block: TextBlockWithChildrenBuilder.() -> Unit) {
        children += TextBlockWithChildrenBuilder(BlockType.BULLETED_LIST_ITEM).apply(block).toJson()
    }

    @NotionDsl
    fun numberedListItem(block: TextBlockWithChildrenBuilder.() -> Unit) {
        children += TextBlockWithChildrenBuilder(BlockType.NUMBERED_LIST_ITEM).apply(block).toJson()
    }

    @NotionDsl
    fun toDoBlock(block: TodoBlockBuilder.() -> Unit) {
        children += TodoBlockBuilder().apply(block).toJson()
    }

    @NotionDsl
    fun toggleBlock(block: TextBlockWithChildrenBuilder.() -> Unit) {
        children += TextBlockWithChildrenBuilder(BlockType.TOGGLE).apply(block).toJson()
    }

    @NotionDsl
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
