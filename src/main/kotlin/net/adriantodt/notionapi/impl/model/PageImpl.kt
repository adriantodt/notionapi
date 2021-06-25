package net.adriantodt.notionapi.impl.model

import com.grack.nanojson.JsonArray
import com.grack.nanojson.JsonObject
import net.adriantodt.notionapi.model.page.Page
import net.adriantodt.notionapi.model.page.PageParent
import net.adriantodt.notionapi.model.page.PropertyValue
import java.time.OffsetDateTime

data class PageImpl(
    override val id: String,
    override val createdTime: OffsetDateTime,
    override val lastEditedTime: OffsetDateTime,
    override val archived: Boolean,
    override val properties: Map<String, PropertyValue>,
    override val parent: PageParent
) : Page {
    companion object {
        fun fromResponse(raw: Any?): PageImpl {
            require(raw is JsonObject) { "Response body is not a JsonObject" }
            return fromJsonObject(raw)
        }

        fun fromJsonArray(array: JsonArray): List<PageImpl> {
            require(array.all { it is JsonObject }) { "Invalid input, array should only contain JsonObjects." }
            return array.asSequence()
                .filterIsInstance<JsonObject>()
                .map { fromJsonObject(it) }
                .toList()
        }

        fun fromJsonObject(obj: JsonObject): PageImpl {
            require(obj.getString("object") == "page") { "Object is not a page" }

            return PageImpl(
                obj.getString("id")!!,
                obj.getString("created_time")!!.toOffsetDateTime(),
                obj.getString("last_edited_time")!!.toOffsetDateTime(),
                obj.getBoolean("archived"),
                PropertyValueImpl.fromJsonObjectMap(obj.getObject("properties")),
                parentFromJsonObject(obj.getObject("parent"))
            )
        }

        private fun parentFromJsonObject(obj: JsonObject): PageParent {
            return when (val type = obj.getString("type")) {
                "database_id" -> PageParent.Database(obj.getString("database_id")!!)
                "page_id" -> PageParent.Page(obj.getString("page_id")!!)
                "workspace" -> PageParent.Workspace
                else -> throw IllegalArgumentException("Illegal type '$type'")
            }
        }
    }
}
