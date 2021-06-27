package net.adriantodt.notionapi.net.request.filter

import net.adriantodt.notionapi.net.request.filter.FilterCondition.FilterType.*
import net.adriantodt.notionapi.utils.JsonEnum
import net.adriantodt.notionapi.utils.jsonObjectOf
import net.adriantodt.notionapi.utils.lowercaseOfName

enum class FilterCondition(vararg types: FilterType) : JsonEnum {
    EQUALS(TEXT, NUMBER, TRUE, FALSE),
    DOES_NOT_EQUAL(TEXT, NUMBER, TRUE, FALSE),
    CONTAINS(TEXT),
    DOES_NOT_CONTAIN(TEXT),
    STARTS_WITH(TEXT),
    ENDS_WITH(TEXT),
    GREATER_THAN(NUMBER),
    LESS_THAN(NUMBER),
    GREATER_THAN_OR_EQUAL_TO(NUMBER),
    LESS_THAN_OR_EQUAL_TO(NUMBER),
    BEFORE(TEXT),
    AFTER(TEXT),
    ON_OR_BEFORE(TEXT),
    ON_OR_AFTER(TEXT),
    PAST_WEEK(EMPTY_OBJECT),
    PAST_MONTH(EMPTY_OBJECT),
    PAST_YEAR(EMPTY_OBJECT),
    NEXT_WEEK(EMPTY_OBJECT),
    NEXT_MONTH(EMPTY_OBJECT),
    NEXT_YEAR(EMPTY_OBJECT),
    IS_EMPTY(TRUE),
    IS_NOT_EMPTY(TRUE);

    enum class FilterType(val predicate: (Any) -> Boolean) {
        TEXT(instanceOf<String>()), NUMBER(instanceOf<Number>()),
        TRUE(true::equals), FALSE(false::equals),
        EMPTY_OBJECT(jsonObjectOf()::equals);
    }

    private val acceptedValues = types.toList()

    fun validate(value: Any) = acceptedValues.any { it.predicate(value) }

    override val value by lowercaseOfName()
    companion object : JsonEnum.Companion<FilterCondition>(::values) {
        inline fun <reified T> instanceOf(): (Any) -> Boolean = { it is T }
    }
}
