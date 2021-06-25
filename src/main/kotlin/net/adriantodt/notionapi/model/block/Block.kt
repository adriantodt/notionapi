package net.adriantodt.notionapi.model.block

import net.adriantodt.notionapi.model.NotionObject
import java.time.OffsetDateTime

interface Block : NotionObject {
    /**
     * Unique identifier for the block.
     */
    val id: String

    /**
     * Type of block.
     */
    val type: BlockType

    /**
     * Date and time when this block was created.
     */
    val createdTime: OffsetDateTime

    /**
     * Date and time when this block was last updated.
     */
    val lastEditedTime: OffsetDateTime

    /**
     * Whether or not the block has children blocks nested within it.
     */
    val hasChildren: Boolean
}

