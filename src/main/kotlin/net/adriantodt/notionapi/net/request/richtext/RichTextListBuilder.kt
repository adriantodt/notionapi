package net.adriantodt.notionapi.net.request.richtext

import com.grack.nanojson.JsonArray
import com.grack.nanojson.JsonObject

class RichTextListBuilder {
    var richText = mutableListOf<JsonObject>()

    fun formula(block: FormulaBuilder.() -> Unit) {
        richText += FormulaBuilder().apply(block).toJson()
    }

    fun mention(block: MentionBuilder.() -> Unit) {
        richText += MentionBuilder().apply(block).toJson()
    }

    fun text(block: TextBuilder.() -> Unit) {
        richText += TextBuilder().apply(block).toJson()
    }

    internal fun toJson() = JsonArray(richText)
}
