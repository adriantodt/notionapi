package net.adriantodt.notionapi.model.database.property

import net.adriantodt.notionapi.utils.JsonEnum

enum class RollupFunction : JsonEnum {
    COUNT_ALL, COUNT_VALUES, COUNT_UNIQUE_VALUES, COUNT_EMPTY, COUNT_NOT_EMPTY, PERCENT_EMPTY, PERCENT_NOT_EMPTY,
    SUM, AVERAGE, MEDIAN, MIN, MAX, RANGE, UNKNOWN;

    override val value: String by lazy {
        require(this != UNKNOWN) { "Unsupported property" }
        this.name.lowercase()
    }

    companion object : JsonEnum.Companion<RollupFunction>(::values, { UNKNOWN })
}
