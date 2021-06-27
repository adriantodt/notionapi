package net.adriantodt.notionapi.model.block

/**
 * A block object representing a page within Notion.
 *
 * [Notion API Page about this entity](https://developers.notion.com/reference/block#child-page-blocks).
 */
interface ChildPageBlock : Block {
    /**
     * Plain text of page title.
     */
    val title: String
}
