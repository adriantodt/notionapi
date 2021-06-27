package net.adriantodt.notionapi.model.richtext

import net.adriantodt.notionapi.utils.JsonEnum
import net.adriantodt.notionapi.utils.lowercaseOfName

enum class MentionType : JsonEnum {
    USER, PAGE, DATABASE, DATE;

    override val value by lowercaseOfName()

    companion object : JsonEnum.Companion<MentionType>(::values)
}
