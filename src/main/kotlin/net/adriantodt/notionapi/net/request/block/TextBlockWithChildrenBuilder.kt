package net.adriantodt.notionapi.net.request.block

import net.adriantodt.notionapi.model.block.BlockType
import net.adriantodt.notionapi.net.request.annotation.NotionDsl

@NotionDsl
open class TextBlockWithChildrenBuilder(type: BlockType) : TextBlockBuilder(type) {
    private val children = PageBuilder()

    @NotionDsl
    fun paragraph(block: TextBlockWithChildrenBuilder.() -> Unit) {
        children.paragraph(block)
    }

    @NotionDsl
    fun heading1(block: TextBlockBuilder.() -> Unit) {
        children.heading1(block)
    }

    @NotionDsl
    fun heading2(block: TextBlockBuilder.() -> Unit) {
        children.heading2(block)
    }

    @NotionDsl
    fun heading3(block: TextBlockBuilder.() -> Unit) {
        children.heading3(block)
    }

    @NotionDsl
    fun bulletedListItem(block: TextBlockWithChildrenBuilder.() -> Unit) {
        children.bulletedListItem(block)
    }

    @NotionDsl
    fun numberedListItem(block: TextBlockWithChildrenBuilder.() -> Unit) {
        children.numberedListItem(block)
    }

    @NotionDsl
    fun toDoBlock(block: TodoBlockBuilder.() -> Unit) {
        children.toDoBlock(block)
    }

    @NotionDsl
    fun toggleBlock(block: TextBlockWithChildrenBuilder.() -> Unit) {
        children.toggleBlock(block)
    }

    @NotionDsl
    fun childPage(title: String) {
        children.childPage(title)
    }

    override fun blockSpecificData() = super.blockSpecificData().apply {
        put("children", children.toJson())
    }
}
