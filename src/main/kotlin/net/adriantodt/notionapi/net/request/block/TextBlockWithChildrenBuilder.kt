package net.adriantodt.notionapi.net.request.block

import net.adriantodt.notionapi.model.block.BlockType

open class TextBlockWithChildrenBuilder(type: BlockType) : TextBlockBuilder(type) {
    private val children = PageBuilder()

    fun paragraph(block: TextBlockWithChildrenBuilder.() -> Unit) {
        children.paragraph(block)
    }

    fun heading1(block: TextBlockBuilder.() -> Unit) {
        children.heading1(block)
    }

    fun heading2(block: TextBlockBuilder.() -> Unit) {
        children.heading2(block)
    }

    fun heading3(block: TextBlockBuilder.() -> Unit) {
        children.heading3(block)
    }

    fun bulletedListItem(block: TextBlockWithChildrenBuilder.() -> Unit) {
        children.bulletedListItem(block)
    }

    fun numberedListItem(block: TextBlockWithChildrenBuilder.() -> Unit) {
        children.numberedListItem(block)
    }

    fun toDoBlock(block: TodoBlockBuilder.() -> Unit) {
        children.toDoBlock(block)
    }

    fun toggleBlock(block: TextBlockWithChildrenBuilder.() -> Unit) {
        children.toggleBlock(block)
    }

    fun childPage(title: String) {
        children.childPage(title)
    }

    override fun blockSpecificData() = super.blockSpecificData().apply {
        put("children", children.toJson())
    }
}
