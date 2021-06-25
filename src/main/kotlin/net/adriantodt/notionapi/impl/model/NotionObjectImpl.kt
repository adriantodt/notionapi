package net.adriantodt.notionapi.impl.model

import com.grack.nanojson.JsonArray
import com.grack.nanojson.JsonObject
import net.adriantodt.notionapi.model.NotionObject

object NotionObjectImpl {

    fun fromResponse(raw: Any?): NotionObject {
        require(raw is JsonObject) { "Response body is not a JsonObject" }
        return fromJsonObject(raw)
    }

    fun fromJsonArray(array: JsonArray): List<NotionObject> {
        require(array.all { it is JsonObject }) { "Invalid input, array should only contain JsonObjects." }
        return array.asSequence()
            .filterIsInstance<JsonObject>()
            .map { fromJsonObject(it) }
            .toList()
    }

    fun fromJsonObject(obj: JsonObject): NotionObject {
        return when(val type = obj.getString("object")) {
            "database" -> DatabaseImpl.fromJsonObject(obj)
            "block" -> BlockImpl.fromJsonObject(obj)
            "page" -> PageImpl.fromJsonObject(obj)
            else -> throw IllegalStateException("Unsupported object '$type'")
        }
    }
}
