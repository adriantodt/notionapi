package net.adriantodt.notionapi.impl.model

import com.grack.nanojson.JsonArray
import com.grack.nanojson.JsonObject
import net.adriantodt.notionapi.model.property.File

data class FileImpl(override val name: String) : File {
    companion object {
        fun fromJsonArray(array: JsonArray): List<FileImpl> {
            require(array.all { it is JsonObject }) { "Invalid input, array should only contain JsonObjects." }
            return array.asSequence()
                .filterIsInstance<JsonObject>()
                .map { fromJsonObject(it) }
                .toList()
        }

        private fun fromJsonObject(obj: JsonObject): FileImpl {
            return FileImpl(obj.getString("name"))
        }
    }
}
