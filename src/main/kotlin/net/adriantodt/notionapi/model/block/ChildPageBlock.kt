package net.adriantodt.notionapi.model.block

interface ChildPageBlock : Block {
    /**
     * Plain text of page title.
     */
    val title: String
}
