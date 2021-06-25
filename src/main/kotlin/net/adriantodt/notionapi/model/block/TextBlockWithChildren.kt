package net.adriantodt.notionapi.model.block

interface TextBlockWithChildren : TextBlock {
    /**
     * Any nested children blocks of the paragraph block.
     */
    val children: List<Block>
}
