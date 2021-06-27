package net.adriantodt.notionapi.model.block

import net.adriantodt.notionapi.model.richtext.RichText

/**
 * A block object representing text within Notion.
 */
interface TextBlock : Block {
    /**
     * Rich text in the block.
     */
    val text: List<RichText>
}
