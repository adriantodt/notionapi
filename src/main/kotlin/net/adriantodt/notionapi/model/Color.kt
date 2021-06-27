package net.adriantodt.notionapi.model

import net.adriantodt.notionapi.utils.JsonEnum
import net.adriantodt.notionapi.utils.lowercaseOfName

enum class Color : JsonEnum {
    DEFAULT, GRAY, BROWN, ORANGE, YELLOW, GREEN, BLUE, PURPLE, PINK, RED;

    override val value by lowercaseOfName()

    companion object : JsonEnum.Companion<Color>(::values, { DEFAULT })
}
