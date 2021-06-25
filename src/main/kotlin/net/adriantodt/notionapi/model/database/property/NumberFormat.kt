package net.adriantodt.notionapi.model.database.property

import net.adriantodt.notionapi.utils.JsonEnum

enum class NumberFormat(override val value: String?) : JsonEnum {
    NUMBER("number"), NUMBER_WITH_COMMAS("number_with_commas"),
    PERCENT("percent"), DOLLAR("dollar"), EURO("euro"),
    POUND("pound"), YEN("yen"), RUBLE("ruble"),
    RUPEE("rupee"), WON("won"), YUAN("yuan"), UNKNOWN(null);

    companion object : JsonEnum.Companion<NumberFormat>(::values, { UNKNOWN })
}
