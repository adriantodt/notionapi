package net.adriantodt.notionapi.model.block

import net.adriantodt.notionapi.model.NotionObject
import java.time.OffsetDateTime

/**
 * A block object represents content within Notion. Blocks can be text, lists, media, and more.
 * A page is a type of block, too!
 * Some blocks have more content nested inside them. Some examples are indented paragraphs, lists, and toggles.
 * The nested content is called children, and children are blocks, too!
 *
 * [Notion API Page about this entity](https://developers.notion.com/reference/block).
 */
interface Block : NotionObject {
    /**
     * Unique identifier for the block.
     */
    override val id: String

    /**
     * Type of block.
     */
    val type: BlockType

    /**
     * Date and time when this block was created.
     */
    override val createdTime: OffsetDateTime

    /**
     * Date and time when this block was last updated.
     */
    override val lastEditedTime: OffsetDateTime

    /**
     * Whether or not the block has children blocks nested within it.
     */
    val hasChildren: Boolean
}

