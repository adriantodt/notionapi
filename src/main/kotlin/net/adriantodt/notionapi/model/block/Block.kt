package net.adriantodt.notionapi.model.block

import net.adriantodt.notionapi.model.NotionObject
import java.time.OffsetDateTime

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

