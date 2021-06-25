package net.adriantodt.notionapi.model.richtext

import net.adriantodt.notionapi.model.Color

interface RichText {
    /**
     * The plain text without annotations.
     */
    val plainText: String

    /**
     * The URL of any link or internal Notion mention in this text, if any.
     */
    val href: String?

    /**
     * Type of this rich text object.
     */
    val type: RichTextType

    // BELOW: Inlined annotations

    /**
     * Whether the text is bolded.
     */
    val bold: Boolean

    /**
     * Whether the text is italicized.
     */
    val italic: Boolean

    /**
     * 	Whether the text is struck through.
     */
    val strikethrough: Boolean

    /**
     * Whether the text is underlined.
     */
    val underline: Boolean

    /**
     * Whether the text is code style.
     */
    val code: Boolean

    /**
     * Color of the text.
     * A given text can have either a custom color or background color, but never both.
     */
    val color: Color

    /**
     * Background Color of the text.
     * A given text can have either a custom color or background color, but never both.
     */
    val backgroundColor: Color
}

