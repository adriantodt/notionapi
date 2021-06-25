package net.adriantodt.notionapi.net.request.block

import com.grack.nanojson.JsonObject
import net.adriantodt.notionapi.model.block.BlockType
import net.adriantodt.notionapi.net.request.richtext.FormulaBuilder
import net.adriantodt.notionapi.net.request.richtext.MentionBuilder
import net.adriantodt.notionapi.net.request.richtext.RichTextListBuilder
import net.adriantodt.notionapi.net.request.richtext.TextBuilder
import net.adriantodt.notionapi.utils.jsonObjectOf

open class TextBlockBuilder(val type: BlockType) {
    private val richText = RichTextListBuilder()

    fun formula(block: FormulaBuilder.() -> Unit) {
        richText.formula(block)
    }

    fun mention(block: MentionBuilder.() -> Unit) {
        richText.mention(block)
    }

    fun text(block: TextBuilder.() -> Unit) {
        richText.text(block)
    }

    internal fun toJson(): JsonObject {
        return jsonObjectOf(
            "object" to "block",
            "type" to type.value,
            type.value to blockSpecificData()
        )
    }

    protected open fun blockSpecificData() = jsonObjectOf("text" to richText.toJson())
}
