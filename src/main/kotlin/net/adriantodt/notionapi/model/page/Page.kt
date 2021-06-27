package net.adriantodt.notionapi.model.page

import net.adriantodt.notionapi.model.NotionObject
import java.time.OffsetDateTime

/**
 * The Page object contains the property values of a single Notion page. All pages have a parent.
 * If the parent is a database, the property values conform to the schema laid out database's properties.
 * Otherwise, the only property value is the title.
 *
 * Page content is available as blocks.
 * The content can be read using retrieve block children and appended using append block children.
 *
 * [Notion API Page about this entity](https://developers.notion.com/reference/page).
 */
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

    /**
     * The parent of this page. Can be a database, page, or workspace.
     */
    val parent: PageParent
}

