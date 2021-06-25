package net.adriantodt.notionapi.net.request.filter

import net.adriantodt.notionapi.impl.model.DatabasePropertyImpl
import net.adriantodt.notionapi.model.database.property.DatabaseProperty
import net.adriantodt.notionapi.model.property.PropertyType
import net.adriantodt.notionapi.net.request.filter.FilterCondition.*
import net.adriantodt.notionapi.utils.jsonObjectOf
import java.time.Instant
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter.ISO_INSTANT
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE

fun property(property: String, type: PropertyType): DatabaseProperty {
    return DatabasePropertyImpl(property, "", type)
}

fun allOf(vararg filters: Filter) = Filter.AllOf(*filters)

fun anyOf(vararg filters: Filter) = Filter.AnyOf(*filters)

fun DatabaseProperty.filterCondition(condition: FilterCondition, value: Any) = Filter.Property(this, condition, value)

infix fun DatabaseProperty.eq(value: String) = filterCondition(EQUALS, value)
infix fun DatabaseProperty.eq(value: Number) = filterCondition(EQUALS, value)
infix fun DatabaseProperty.eq(value: Boolean) = filterCondition(EQUALS, value)
infix fun DatabaseProperty.eq(value: OffsetDateTime) = eq(value.toInstant())
infix fun DatabaseProperty.eq(value: Instant) = filterCondition(EQUALS, ISO_INSTANT.format(value))
infix fun DatabaseProperty.eq(value: LocalDate) = filterCondition(EQUALS, ISO_LOCAL_DATE.format(value))

infix fun DatabaseProperty.neq(value: String) = filterCondition(DOES_NOT_EQUAL, value)
infix fun DatabaseProperty.neq(value: Number) = filterCondition(DOES_NOT_EQUAL, value)
infix fun DatabaseProperty.neq(value: Boolean) = filterCondition(DOES_NOT_EQUAL, value)
infix fun DatabaseProperty.neq(value: OffsetDateTime) = neq(value.toInstant())
infix fun DatabaseProperty.neq(value: Instant) = filterCondition(DOES_NOT_EQUAL, ISO_INSTANT.format(value))
infix fun DatabaseProperty.neq(value: LocalDate) = filterCondition(DOES_NOT_EQUAL, ISO_LOCAL_DATE.format(value))

infix fun DatabaseProperty.contains(value: String) = filterCondition(CONTAINS, value)

infix fun DatabaseProperty.doesNotContain(value: String) = filterCondition(DOES_NOT_CONTAIN, value)

infix fun DatabaseProperty.startsWith(value: String) = filterCondition(STARTS_WITH, value)

infix fun DatabaseProperty.endsWith(value: String) = filterCondition(ENDS_WITH, value)

infix fun DatabaseProperty.gt(value: Number) = filterCondition(GREATER_THAN, value)

infix fun DatabaseProperty.lt(value: Number) = filterCondition(LESS_THAN, value)

infix fun DatabaseProperty.gte(value: Number) = filterCondition(GREATER_THAN_OR_EQUAL_TO, value)

infix fun DatabaseProperty.lte(value: Number) = filterCondition(LESS_THAN_OR_EQUAL_TO, value)

infix fun DatabaseProperty.before(value: OffsetDateTime) = before(value.toInstant())
infix fun DatabaseProperty.before(value: Instant) = filterCondition(BEFORE, ISO_INSTANT.format(value))
infix fun DatabaseProperty.before(value: LocalDate) = filterCondition(BEFORE, ISO_LOCAL_DATE.format(value))

infix fun DatabaseProperty.after(value: OffsetDateTime) = after(value.toInstant())
infix fun DatabaseProperty.after(value: Instant) = filterCondition(AFTER, ISO_INSTANT.format(value))
infix fun DatabaseProperty.after(value: LocalDate) = filterCondition(AFTER, ISO_LOCAL_DATE.format(value))

infix fun DatabaseProperty.onOrBefore(value: OffsetDateTime) = onOrBefore(value.toInstant())
infix fun DatabaseProperty.onOrBefore(value: Instant) = filterCondition(ON_OR_BEFORE, ISO_INSTANT.format(value))
infix fun DatabaseProperty.onOrBefore(value: LocalDate) = filterCondition(ON_OR_BEFORE, ISO_LOCAL_DATE.format(value))

infix fun DatabaseProperty.onOrAfter(value: OffsetDateTime) = onOrAfter(value.toInstant())
infix fun DatabaseProperty.onOrAfter(value: Instant) = filterCondition(ON_OR_AFTER, ISO_INSTANT.format(value))
infix fun DatabaseProperty.onOrAfter(value: LocalDate) = filterCondition(ON_OR_AFTER, ISO_LOCAL_DATE.format(value))

fun DatabaseProperty.pastWeek() = filterCondition(PAST_WEEK, jsonObjectOf())

fun DatabaseProperty.pastMonth() = filterCondition(PAST_MONTH, jsonObjectOf())

fun DatabaseProperty.pastYear() = filterCondition(PAST_YEAR, jsonObjectOf())

fun DatabaseProperty.nextWeek() = filterCondition(NEXT_WEEK, jsonObjectOf())

fun DatabaseProperty.nextMonth() = filterCondition(NEXT_MONTH, jsonObjectOf())

fun DatabaseProperty.nextYear() = filterCondition(NEXT_YEAR, jsonObjectOf())

fun DatabaseProperty.isEmpty() = filterCondition(IS_EMPTY, true)

fun DatabaseProperty.isNotEmpty() = filterCondition(IS_NOT_EMPTY, true)
