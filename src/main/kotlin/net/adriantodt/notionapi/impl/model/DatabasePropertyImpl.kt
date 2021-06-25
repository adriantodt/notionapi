package net.adriantodt.notionapi.impl.model

import com.grack.nanojson.JsonObject
import net.adriantodt.notionapi.model.database.property.*
import net.adriantodt.notionapi.model.property.PropertyType
import net.adriantodt.notionapi.model.property.SelectOption

data class DatabasePropertyImpl(
    override val id: String,
    override val name: String,
    override val type: PropertyType
) : DatabaseProperty {

    data class FormulaDatabasePropertyImpl(
        override val id: String,
        override val name: String,
        override val type: PropertyType,
        override val expression: String
    ) : FormulaDatabaseProperty

    data class NumberDatabasePropertyImpl(
        override val id: String,
        override val name: String,
        override val type: PropertyType,
        override val format: NumberFormat
    ) : NumberDatabaseProperty

    data class RelationDatabasePropertyImpl(
        override val id: String,
        override val name: String,
        override val type: PropertyType,
        override val databaseId: String,
        override val syncedPropertyName: String,
        override val syncedPropertyId: String
    ) : RelationDatabaseProperty

    data class RollupDatabasePropertyImpl(
        override val id: String,
        override val name: String,
        override val type: PropertyType,
        override val relationPropertyName: String,
        override val relationPropertyId: String,
        override val rollupPropertyName: String,
        override val rollupPropertyId: String,
        override val function: RollupFunction
    ) : RollupDatabaseProperty

    data class SelectDatabasePropertyImpl(
        override val id: String,
        override val name: String,
        override val type: PropertyType,
        override val options: List<SelectOption>
    ) : SelectDatabaseProperty

    companion object {
        fun fromJsonObjectMap(obj: JsonObject): Map<String, DatabaseProperty> {
            return obj.mapValues { (k, v) ->
                require(v is JsonObject) { "Invalid input, object should only contain JsonObjects." }
                fromJsonObject(k, v)
            }
        }

        fun fromJsonObject(name: String, obj: JsonObject): DatabaseProperty {
            val id = obj.getString("id")
            return when (val type = PropertyType.fromJson(obj.getString("type"))) {
                PropertyType.NUMBER -> NumberDatabasePropertyImpl(
                    id, name, type,
                    NumberFormat.fromJson(obj.getObject(type.value).getString("format"))
                )
                PropertyType.SELECT, PropertyType.MULTI_SELECT -> SelectDatabasePropertyImpl(
                    id, name, type,
                    SelectOptionImpl.fromJsonArray(obj.getObject(type.value).getArray("options"))
                )
                PropertyType.FORMULA -> FormulaDatabasePropertyImpl(
                    id, name, type,
                    obj.getObject(type.value).getString("expression")
                )
                PropertyType.RELATION -> {
                    val subObj = obj.getObject(type.value)
                    RelationDatabasePropertyImpl(
                        id, name, type,
                        obj.getString("database_id")!!,
                        subObj.getString("synced_property_name"),
                        subObj.getString("synced_property_id"),
                    )
                }
                PropertyType.ROLLUP -> {
                    val subObj = obj.getObject(type.value)
                    RollupDatabasePropertyImpl(
                        id, name, type,
                        subObj.getString("relation_property_name"),
                        subObj.getString("relation_property_id"),
                        subObj.getString("rollup_property_name"),
                        subObj.getString("rollup_property_id"),
                        RollupFunction.fromJson(subObj.getString("function"))
                    )
                }
                else -> DatabasePropertyImpl(id, name, type)
            }
        }
    }
}
