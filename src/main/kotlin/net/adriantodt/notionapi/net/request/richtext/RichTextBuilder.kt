package net.adriantodt.notionapi.net.request.richtext

import com.grack.nanojson.JsonObject
import net.adriantodt.notionapi.model.Color
import net.adriantodt.notionapi.utils.buildJsonObject

abstract class RichTextBuilder {
    // BELOW: Inlined annotations

    /**
     * Whether the text is bolded.
     */
    var bold: Boolean = false

    /**
     * Whether the text is italicized.
     */
    var italic: Boolean = false

    /**
     * 	Whether the text is struck through.
     */
    var strikethrough: Boolean = false

    /**
     * Whether the text is underlined.
     */
    var underline: Boolean = false

    /**
     * Whether the text is code style.
     */
    var code: Boolean = false

    /**
     * Color of the text.
     * A given text can have either a custom color or background color, but never both.
     */
    var color: Color = Color.DEFAULT
        set(value) {
            field = value
            if (value != Color.DEFAULT) {
                backgroundColor = Color.DEFAULT
            }
        }

    /**
     * Background Color of the text.
     * A given text can have either a custom color or background color, but never both.
     */
    var backgroundColor: Color = Color.DEFAULT
        set(value) {
            field = value
            if (value != Color.DEFAULT) {
                color = Color.DEFAULT
            }
        }

    internal open fun toJson() = buildJsonObject {
        buildAnnotations()?.let { put("annotations", it) }
    }

    private fun buildAnnotations(): JsonObject? {
        val annotations = mutableListOf<Pair<String, Any?>>()

        if (bold) {
            annotations += "bold" to true
        }
        if (italic) {
            annotations += "italic" to true
        }
        if (strikethrough) {
            annotations += "strikethrough" to true
        }
        if (underline) {
            annotations += "underline" to true
        }
        if (code) {
            annotations += "code" to true
        }
        if (backgroundColor != Color.DEFAULT) {
            annotations += "color" to "${backgroundColor.value}_background"
        } else if (color != Color.DEFAULT) {
            annotations += "color" to color.value!!
        }

        if (annotations.isEmpty()) {
            return null
        }

        return JsonObject(annotations.toMap())
    }
}
