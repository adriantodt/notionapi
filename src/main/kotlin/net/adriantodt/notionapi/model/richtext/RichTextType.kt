package net.adriantodt.notionapi.model.richtext

import net.adriantodt.notionapi.utils.JsonEnum
import net.adriantodt.notionapi.utils.lowercaseOfName

enum class RichTextType : JsonEnum {
    TEXT,
    MENTION,
    EQUATION;

    override val value by lowercaseOfName()

    companion object : JsonEnum.Companion<RichTextType>(::values)
}

