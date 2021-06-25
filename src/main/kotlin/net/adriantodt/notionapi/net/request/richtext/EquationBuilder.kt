package net.adriantodt.notionapi.net.request.richtext

import net.adriantodt.notionapi.net.request.annotation.NotionDsl
import net.adriantodt.notionapi.utils.jsonObjectOf

@NotionDsl
class EquationBuilder : RichTextBuilder() {
    /**
     * The LaTeX string representing this inline equation.
     */
    var expressionBuilder = StringBuilder()

    operator fun String.unaryPlus() {
        expressionBuilder.append(this)
    }

    override fun toJson() = super.toJson().apply {
        put("type", "equation")
        put("equation", jsonObjectOf("expression" to expressionBuilder.toString()))
    }
}
