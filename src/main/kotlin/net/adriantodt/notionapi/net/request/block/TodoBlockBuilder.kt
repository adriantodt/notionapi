package net.adriantodt.notionapi.net.request.block

import net.adriantodt.notionapi.model.block.BlockType

class TodoBlockBuilder : TextBlockWithChildrenBuilder(BlockType.TO_DO) {
    /**
     * Whether the to_do is checked or not.
     */
    var checked: Boolean? = null

    override fun blockSpecificData() = super.blockSpecificData().apply {
        checked?.let { put("checked", it) }
    }
}
