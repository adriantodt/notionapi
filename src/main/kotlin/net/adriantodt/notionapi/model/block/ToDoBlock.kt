package net.adriantodt.notionapi.model.block

interface ToDoBlock : TextBlockWithChildren {
    /**
     * Whether the to_do is checked or not.
     */
    val checked: Boolean
}
