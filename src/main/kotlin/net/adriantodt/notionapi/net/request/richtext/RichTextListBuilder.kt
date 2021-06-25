package net.adriantodt.notionapi.net.request.richtext

import com.grack.nanojson.JsonArray
import com.grack.nanojson.JsonObject
import net.adriantodt.notionapi.net.request.annotation.NotionDsl

@NotionDsl
class RichTextListBuilder {
    var richText = mutableListOf<JsonObject>()

    @NotionDsl
    fun equation(block: EquationBuilder.() -> Unit) {
        richText += EquationBuilder().apply(block).toJson()
    }

    @NotionDsl
    fun mention(block: MentionBuilder.() -> Unit) {
        richText += MentionBuilder().apply(block).toJson()
    }

    @NotionDsl
    fun text(block: TextBuilder.() -> Unit) {
        richText += TextBuilder().apply(block).toJson()
    }

    internal fun toJson() = JsonArray(richText)
}
