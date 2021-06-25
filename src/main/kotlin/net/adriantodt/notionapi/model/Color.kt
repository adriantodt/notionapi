package net.adriantodt.notionapi.model

import net.adriantodt.notionapi.utils.JsonEnum

enum class Color(override val value: String?) : JsonEnum {
    DEFAULT("default"),
    GRAY("gray"),
    BROWN("brown"),
    ORANGE("orange"),
    YELLOW("yellow"),
    GREEN("green"),
    BLUE("blue"),
    PURPLE("purple"),
    PINK("pink"),
    RED("red");

    companion object : JsonEnum.Companion<Color>(Color::values, { DEFAULT })
}
