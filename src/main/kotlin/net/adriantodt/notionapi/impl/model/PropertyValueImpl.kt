package net.adriantodt.notionapi.impl.model

import com.grack.nanojson.JsonArray
import com.grack.nanojson.JsonObject
import net.adriantodt.notionapi.model.page.PropertyValue
import net.adriantodt.notionapi.model.property.*
import net.adriantodt.notionapi.model.property.PropertyType.*

data class PropertyValueImpl(
    override val id: String,
    override val name: String,
    override val value: Value
) : PropertyValue {
    companion object {
        fun fromJsonObjectMap(obj: JsonObject): Map<String, PropertyValue> {
            return obj.mapValues { (k, v) ->
                require(v is JsonObject) { "Invalid input, object should only contain JsonObjects." }
                fromJsonObject(k, v)
            }
        }

        private fun fromJsonObject(name: String, obj: JsonObject): PropertyValue {
            return PropertyValueImpl(obj.getString("id"), name, valueFromJsonObject(obj))
        }

        private fun valueFromJsonArray(array: JsonArray): List<Value> {
            require(array.all { it is JsonObject }) { "Invalid input, array should only contain JsonObjects." }
            return array.asSequence()
                .filterIsInstance<JsonObject>()
                .map { valueFromJsonObject(it) }
                .toList()
        }

        private fun valueFromJsonObject(obj: JsonObject): Value {
            return when (val type = PropertyType.fromJson(obj.getString("type"))) {
                TITLE,
                RICH_TEXT -> RichTextValue(type, RichTextImpl.fromJsonArray(obj.getArray(type.value)))
                NUMBER -> NumberValue(type, obj.getNumber(type.value))
                SELECT -> SelectValue(type, SelectOptionImpl.fromJsonObject(obj.getObject(type.value)))
                MULTI_SELECT -> MultiSelectValue(type, SelectOptionImpl.fromJsonArray(obj.getArray(type.value)))
                DATE -> {
                    val subObj = obj.getObject(type.value)
                    parseDateRangeValue(type, subObj.getString("start"), subObj.getString("end"))
                }
                PEOPLE -> PeopleValue(type, UserImpl.fromJsonArray(obj.getArray(type.value)))
                FILES -> FilesValue(type, FileImpl.fromJsonArray(obj.getArray(type.value)))
                CHECKBOX -> BooleanValue(type, obj.getBoolean(type.value))
                URL,
                EMAIL,
                PHONE_NUMBER -> StringValue(type, obj.getString(type.value))
                FORMULA -> {
                    val subObj = obj.getObject(type.value)
                    when (subObj.getString("type")) {
                        "string" -> StringValue(type, subObj.getString("string"))
                        "number" -> NumberValue(type, subObj.getNumber("number"))
                        "boolean" -> BooleanValue(type, subObj.getBoolean("boolean"))
                        "date" -> {
                            val dateObj = subObj.getObject(type.value)
                            parseDateRangeValue(type, dateObj.getString("start"), dateObj.getString("end"))
                        }
                        else -> throw IllegalArgumentException("Unknown type '$type'")
                    }
                }
                RELATION -> RelationValue(
                    type,
                    obj.getArray(type.value).filterIsInstance<JsonObject>().map { it.getString("id") }
                )
                ROLLUP -> {
                    val subObj = obj.getObject(type.value)
                    when (subObj.getString("type")) {
                        "number" -> NumberValue(type, subObj.getNumber("number"))
                        "date" -> {
                            val dateObj = subObj.getObject(type.value)
                            parseDateRangeValue(
                                type,
                                dateObj.getString("start"),
                                dateObj.getString("end")
                            )
                        }
                        "array" -> ListValue(type, valueFromJsonArray(obj.getArray(type.value)))
                        else -> throw IllegalArgumentException("Unknown type '$type'")
                    }
                }
                CREATED_TIME,
                LAST_EDITED_TIME -> parseDateValue(type, obj.getString(type.value))
                CREATED_BY,
                LAST_EDITED_BY -> UserValue(type, UserImpl.fromJsonObject(obj.getObject(type.value)))
                UNKNOWN -> throw IllegalArgumentException("Unknown type '${obj.getString("type")}'")
            }
        }

        private fun parseDateRangeValue(type: PropertyType, startString: String, endString: String?): Value {
            val (startDateTime, startDate) = startString.tryParseDate()
            if (startDateTime != null) {
                return DateTimeRangeValue(type, startDateTime, endString?.toOffsetDateTime())
            }
            if (startDate != null) {
                return DateRangeValue(type, startDate, endString?.toLocalDate())
            }
            throw AssertionError("tryParseDate failed method contract.")
        }

        private fun parseDateValue(type: PropertyType, dateString: String): Value {
            val (dateTime, date) = dateString.tryParseDate()
            if (dateTime != null) {
                return DateTimeValue(type, dateTime)
            }
            if (date != null) {
                return DateValue(type, date)
            }
            throw AssertionError("tryParseDate failed method contract.")
        }
    }
}
