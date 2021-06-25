package net.adriantodt.notionapi.net.request.sort

import com.grack.nanojson.JsonObject
import net.adriantodt.notionapi.utils.jsonObjectOf

sealed class Sort {
    data class Property(val property: String, val direction: Direction) : Sort() {
        override fun toJson() = jsonObjectOf("property" to property, "direction" to direction.value)
    }

    data class Timestamp(val timestamp: TimestampField, val direction: Direction) : Sort() {
        override fun toJson() = jsonObjectOf("timestamp" to timestamp.value, "direction" to direction.value)
    }

    internal abstract fun toJson(): JsonObject
}
