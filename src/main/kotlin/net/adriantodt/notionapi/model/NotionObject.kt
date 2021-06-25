package net.adriantodt.notionapi.model

import java.time.OffsetDateTime

interface NotionObject {
    /**
     * Unique identifier for the object.
     */
    val id: String

    /**
     * Date and time when this object was created.
     */
    val createdTime: OffsetDateTime

    /**
     * Date and time when this object was last updated.
     */
    val lastEditedTime: OffsetDateTime
}
