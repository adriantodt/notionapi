package net.adriantodt.notionapi.net.request.sort

import net.adriantodt.notionapi.utils.JsonEnum

enum class Direction : JsonEnum {
    ASCENDING, DESCENDING;

    override val value: String get() = name.lowercase()

    companion object : JsonEnum.Companion<Direction>(::values)
}
