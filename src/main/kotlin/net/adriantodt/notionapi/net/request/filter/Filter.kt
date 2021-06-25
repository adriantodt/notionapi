package net.adriantodt.notionapi.net.request.filter

import com.grack.nanojson.JsonArray
import com.grack.nanojson.JsonObject
import net.adriantodt.notionapi.model.database.property.DatabaseProperty
import net.adriantodt.notionapi.model.property.PropertyType
import net.adriantodt.notionapi.utils.jsonObjectOf

sealed class Filter {
    open infix fun and(other: Filter): AllOf = AllOf(this, other)

    open infix fun or(other: Filter): AnyOf = AnyOf(this, other)

    data class AllOf(val children: List<Filter>) : Filter() {
        constructor(vararg children: Filter) : this(children.toList())

        init {
            require(children.isNotEmpty()) { "Filter must have at least one children." }
        }

        override fun and(other: Filter): AllOf {
            return if (other is AllOf) AllOf(children + other.children) else AllOf(children + other)
        }

        override fun toJson() = jsonObjectOf("and" to JsonArray(children.map(Filter::toJson)))
    }

    data class AnyOf(val children: List<Filter>) : Filter() {
        constructor(vararg children: Filter) : this(children.toList())

        init {
            require(children.isNotEmpty()) { "Filter must have at least one children." }
        }

        override fun or(other: Filter): AnyOf {
            return if (other is AnyOf) AnyOf(children + other.children) else AnyOf(children + other)
        }

        override fun toJson() = jsonObjectOf("or" to JsonArray(children.map(Filter::toJson)))
    }

    data class Property(val property: DatabaseProperty, val condition: FilterCondition, val value: Any) : Filter() {
        init {
            require(property.type != PropertyType.UNKNOWN) { "Property type for '${property.id}' is unknown." }
            require(condition.validate(value)) { "Value '$value' is not valid for condition $condition" }
        }

        override fun toJson() = jsonObjectOf(
            "property" to property.id, property.type.value!! to jsonObjectOf(condition.value to value)
        )
    }

    internal abstract fun toJson(): JsonObject
}


