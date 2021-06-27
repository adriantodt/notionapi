package net.adriantodt.notionapi.model.database.property

import net.adriantodt.notionapi.utils.JsonEnum
import net.adriantodt.notionapi.utils.lowercaseOfName

enum class RollupFunction : JsonEnum {
    COUNT_ALL, COUNT_VALUES, COUNT_UNIQUE_VALUES, COUNT_EMPTY, COUNT_NOT_EMPTY, PERCENT_EMPTY, PERCENT_NOT_EMPTY,
    SUM, AVERAGE, MEDIAN, MIN, MAX, RANGE;

    override val value: String by lowercaseOfName()

    companion object : JsonEnum.Companion<RollupFunction>(::values)
}
