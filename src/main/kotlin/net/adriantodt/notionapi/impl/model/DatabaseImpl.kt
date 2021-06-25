package net.adriantodt.notionapi.impl.model

import com.grack.nanojson.JsonArray
import com.grack.nanojson.JsonObject
import net.adriantodt.notionapi.model.database.Database
import net.adriantodt.notionapi.model.database.property.DatabaseProperty
import net.adriantodt.notionapi.model.richtext.RichText
import java.time.OffsetDateTime

data class DatabaseImpl(
    override val id: String,
    override val createdTime: OffsetDateTime,
    override val lastEditedTime: OffsetDateTime,
    override val title: List<RichText>,
    override val properties: Map<String, DatabaseProperty>
) : Database {
    companion object {
        fun fromResponse(raw: Any?): DatabaseImpl {
            require(raw is JsonObject) { "Response body is not a JsonObject" }
            return fromJsonObject(raw)
        }

        fun fromJsonArray(array: JsonArray): List<DatabaseImpl> {
            require(array.all { it is JsonObject }) { "Invalid input, array should only contain JsonObjects." }
            return array.asSequence()
                .filterIsInstance<JsonObject>()
                .map { fromJsonObject(it) }
                .toList()
        }

        fun fromJsonObject(obj: JsonObject): DatabaseImpl {
            require(obj.getString("object") == "database") { "Object is not a database" }

            return DatabaseImpl(
                obj.getString("id")!!,
                obj.getString("created_time")!!.toOffsetDateTime(),
                obj.getString("last_edited_time")!!.toOffsetDateTime(),
                RichTextImpl.fromJsonArray(obj.getArray("title")),
                DatabasePropertyImpl.fromJsonObjectMap(obj.getObject("properties"))
            )
        }
    }
}
