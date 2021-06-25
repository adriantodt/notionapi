package net.adriantodt.notionapi.net.request.block

import com.grack.nanojson.JsonObject
import net.adriantodt.notionapi.model.block.BlockType
import net.adriantodt.notionapi.net.request.annotation.NotionDsl
import net.adriantodt.notionapi.net.request.richtext.EquationBuilder
import net.adriantodt.notionapi.net.request.richtext.MentionBuilder
import net.adriantodt.notionapi.net.request.richtext.RichTextListBuilder
import net.adriantodt.notionapi.net.request.richtext.TextBuilder
import net.adriantodt.notionapi.utils.jsonObjectOf

@NotionDsl
open class TextBlockBuilder(val type: BlockType) {
    protected val richText = RichTextListBuilder()

    @NotionDsl
    operator fun String.unaryPlus() {
        richText.text { +this@unaryPlus }
    }

    @NotionDsl
    fun equation(block: EquationBuilder.() -> Unit) {
        richText.equation(block)
    }

    @NotionDsl
    fun mention(block: MentionBuilder.() -> Unit) {
        richText.mention(block)
    }

    @NotionDsl
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
