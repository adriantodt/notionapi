package net.adriantodt.notionapi.model.block

/**
 * Extension of a text block which also contain children.
 */
interface TextBlockWithChildren : TextBlock {
    /**
     * Any nested children blocks of the paragraph block.
     */
    val children: List<Block>
}
