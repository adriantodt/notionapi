package net.adriantodt.notionapi.net.request.sort

import net.adriantodt.notionapi.utils.JsonEnum
import net.adriantodt.notionapi.utils.lowercaseOfName

enum class Direction : JsonEnum {
    ASCENDING, DESCENDING;

    override val value by lowercaseOfName()

    companion object : JsonEnum.Companion<Direction>(::values)
}
