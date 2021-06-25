package net.adriantodt.notionapi.model.block

import net.adriantodt.notionapi.model.richtext.RichText

interface TextBlock : Block {
    /**
     * Rich text in the block.
     */
    val text: List<RichText>
}
