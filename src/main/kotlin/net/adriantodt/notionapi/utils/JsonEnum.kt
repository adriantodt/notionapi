package net.adriantodt.notionapi.utils

interface JsonEnum {
    val value: String?

    abstract class Companion<T : JsonEnum>(
        block: () -> Array<T>,
        val default: () -> T = { throw IllegalArgumentException("Value not found.") }
    ) {
        val values by lazy(block)

        fun fromJson(value: String): T {
            return values.find { it.value == value } ?: default()
        }
    }
}
