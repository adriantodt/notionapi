package net.adriantodt.notionapi.model.page

import net.adriantodt.notionapi.model.NotionObject
import java.time.OffsetDateTime

interface Page : NotionObject {
    /**
     * Unique identifier for the page.
     */
    override val id: String

    /**
     * Date and time when this page was created.
     */
    override val createdTime: OffsetDateTime

    /**
     * Date and time when this page was last updated.
     */
    override val lastEditedTime: OffsetDateTime

    /**
     * The archived status of the page.
     */
    val archived: Boolean

    /**
     * Property values of this page.
     * The key is the name of the property as it appears in Notion.
     */
    val properties: Map<String, PropertyValue>

    val parent: PageParent
}

