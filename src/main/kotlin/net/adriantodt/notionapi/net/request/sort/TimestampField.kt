package net.adriantodt.notionapi.net.request.sort

import net.adriantodt.notionapi.utils.JsonEnum

enum class TimestampField : JsonEnum {
    CREATED_TIME, LAST_EDITED_TIME;

    override val value: String get() = name.lowercase()

    companion object : JsonEnum.Companion<TimestampField>(::values)
}
