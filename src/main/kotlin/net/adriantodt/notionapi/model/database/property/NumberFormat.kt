package net.adriantodt.notionapi.model.database.property

import net.adriantodt.notionapi.utils.JsonEnum
import net.adriantodt.notionapi.utils.lowercaseOfName

enum class NumberFormat : JsonEnum {
    NUMBER, NUMBER_WITH_COMMAS, PERCENT, DOLLAR, EURO, POUND, YEN, RUBLE, RUPEE, WON, YUAN;

    override val value by lowercaseOfName()

    companion object : JsonEnum.Companion<NumberFormat>(::values)
}
