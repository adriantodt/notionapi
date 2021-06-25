package net.adriantodt.notionapi.model.richtext

import net.adriantodt.notionapi.utils.JsonEnum

enum class RichTextType(override val value: String?) : JsonEnum {
    TEXT("text"),
    MENTION("mention"),
    EQUATION("equation"),
    UNKNOWN(null);

    companion object : JsonEnum.Companion<RichTextType>(::values, { UNKNOWN })
}

