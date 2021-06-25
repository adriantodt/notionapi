package net.adriantodt.notionapi.impl.model

import com.grack.nanojson.JsonArray
import com.grack.nanojson.JsonObject
import net.adriantodt.notionapi.model.Color
import net.adriantodt.notionapi.model.property.SelectOption

data class SelectOptionImpl(
    override val id: String,
    override val name: String,
    override val color: Color
): SelectOption {
    companion object {
        fun fromJsonArray(array: JsonArray): List<SelectOption> {
            require(array.all { it is JsonObject }) {
                "Invalid input, array should only contain JsonObjects."
            }
            return array.asSequence()
                .filterIsInstance<JsonObject>()
                .map { fromJsonObject(it) }
                .toList()

        }

        fun fromJsonObject(obj: JsonObject): SelectOption {
            return SelectOptionImpl(
                obj.getString("id"),
                obj.getString("name"),
                Color.fromJson(obj.getString("color"))
            )
        }
    }
}
