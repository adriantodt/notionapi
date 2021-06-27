package net.adriantodt.notionapi.utils

/**
 * Utility interface to convert from a string enum to enum and back.
 */
interface JsonEnum {
    val value: String?

    abstract class Companion<T : JsonEnum>(
        block: () -> Array<T>,
        val default: (String) -> T = { throw IllegalArgumentException("Value '$it' not found.") }
    ) {
        val values by lazy(block)

        fun fromJson(value: String): T {
            return values.find { it.value == value } ?: default(value)
        }
    }
}
