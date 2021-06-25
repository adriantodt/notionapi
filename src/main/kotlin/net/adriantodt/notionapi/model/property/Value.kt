package net.adriantodt.notionapi.model.property

import net.adriantodt.notionapi.model.richtext.RichText
import net.adriantodt.notionapi.model.user.User
import java.time.LocalDate
import java.time.OffsetDateTime

sealed class Value {
    /**
     * Type of the property.
     */
    abstract val type: PropertyType
}

data class BooleanValue(override val type: PropertyType, val value: Boolean) : Value()

data class DateValue(override val type: PropertyType, val value: LocalDate) : Value()

data class DateRangeValue(override val type: PropertyType, val start: LocalDate, val end: LocalDate?) : Value()

data class DateTimeValue(override val type: PropertyType, val value: OffsetDateTime) : Value()

data class DateTimeRangeValue(override val type: PropertyType, val start: OffsetDateTime, val end: OffsetDateTime?) : Value()

data class FilesValue(override val type: PropertyType, val value: List<File>) : Value()

data class MultiSelectValue(override val type: PropertyType, val value: List<SelectOption>) : Value()

data class NumberValue(override val type: PropertyType, val value: Number) : Value()

data class PeopleValue(override val type: PropertyType, val value: List<User>) : Value()

data class RelationValue(override val type: PropertyType, val value: List<String>) : Value()

data class RichTextValue(override val type: PropertyType, val value: List<RichText>) : Value()

data class ListValue(override val type: PropertyType, val value: List<Value>) : Value()

data class SelectValue(override val type: PropertyType, val value: SelectOption) : Value()

data class StringValue(override val type: PropertyType, val value: String) : Value()

data class UserValue(override val type: PropertyType, val value: User) : Value()
