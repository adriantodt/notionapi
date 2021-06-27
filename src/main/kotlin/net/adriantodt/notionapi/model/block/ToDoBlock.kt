package net.adriantodt.notionapi.model.block

/**
 * A block object representing a to do block within Notion.
 *
 * [Notion API Page about this entity](https://developers.notion.com/reference/block#to-do-blocks).
 */
interface ToDoBlock : TextBlock {
    /**
     * Whether the to_do is checked or not.
     */
    val checked: Boolean
}
