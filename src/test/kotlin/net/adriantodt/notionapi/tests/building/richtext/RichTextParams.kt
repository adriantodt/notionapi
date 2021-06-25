package net.adriantodt.notionapi.tests.building.richtext

import com.grack.nanojson.JsonObject
import net.adriantodt.notionapi.model.Color
import net.adriantodt.notionapi.net.request.richtext.RichTextBuilder
import org.junit.jupiter.api.Assertions.*
import java.util.stream.Stream
import kotlin.streams.asStream

data class RichTextParams(
    val bold: Boolean,
    val italic: Boolean,
    val strikethrough: Boolean,
    val underline: Boolean,
    val code: Boolean,
    val color: Color,
    val backgroundColor: Color
)

private val combinations by lazy {
    (0..0b11111).asSequence().flatMap { i ->
        val bold = i and 0b00001 != 0
        val italic = i and 0b00010 != 0
        val strikethrough = i and 0b00100 != 0
        val underline = i and 0b01000 != 0
        val code = i and 0b10000 != 0
        Color.values.flatMap { c ->
            sequenceOf(
                RichTextParams(bold, italic, strikethrough, underline, code, c, Color.DEFAULT),
                RichTextParams(bold, italic, strikethrough, underline, code, Color.DEFAULT, c)
            )
        }
    }.toList()
}

fun allCombinations(): Stream<RichTextParams> {
    return combinations.asSequence().asStream()
}

fun RichTextBuilder.applyParams(params: RichTextParams) {
    this.bold = params.bold
    this.italic = params.italic
    this.strikethrough = params.strikethrough
    this.underline = params.underline
    this.code = params.code
    this.color = params.color
    this.backgroundColor = params.backgroundColor
}

fun expectedKeys(params: RichTextParams): Set<String> {
    return mutableSetOf<String>().apply {
        if (params.bold) add("bold")
        if (params.italic) add("italic")
        if (params.strikethrough) add("strikethrough")
        if (params.underline) add("underline")
        if (params.code) add("code")
        if (params.color != Color.DEFAULT || params.backgroundColor != Color.DEFAULT) add("color")
    }
}

fun assertParams(expected: RichTextParams, actual: JsonObject?) {
    val expectedKeys = expectedKeys(expected)
    if (expectedKeys.isEmpty()) {
        assertNull(actual)
        return
    }
    assertNotNull(actual)
    assertEquals(expectedKeys, actual!!.keys)
    if (expected.bold) assertEquals(expected.bold, actual["bold"])
    if (expected.italic) assertEquals(expected.italic, actual["italic"])
    if (expected.strikethrough) assertEquals(expected.strikethrough, actual["strikethrough"])
    if (expected.underline) assertEquals(expected.underline, actual["underline"])
    if (expected.code) assertEquals(expected.code, actual["code"])
    if (expected.backgroundColor != Color.DEFAULT) {
        assertEquals("${expected.backgroundColor.value}_background", actual["color"])
    } else if (expected.color != Color.DEFAULT) {
        assertEquals(expected.color.value, actual["color"])
    }
}
