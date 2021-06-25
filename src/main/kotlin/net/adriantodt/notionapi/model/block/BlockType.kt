package net.adriantodt.notionapi.model.block

import net.adriantodt.notionapi.utils.JsonEnum

enum class BlockType : JsonEnum {
    PARAGRAPH, HEADING_1, HEADING_2, HEADING_3, BULLETED_LIST_ITEM,
    NUMBERED_LIST_ITEM, TO_DO, TOGGLE, CHILD_PAGE, UNSUPPORTED;

    override val value: String get() = name.lowercase()
    companion object : JsonEnum.Companion<BlockType>(::values, { UNSUPPORTED })
}
