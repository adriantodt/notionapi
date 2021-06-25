package net.adriantodt.notionapi.model.richtext

interface Text : RichText {
    /**
     * Text content.
     * This field contains the actual content of your text and is probably the field you'll use most often.
     */
    val content: String

    /**
     * Any inline link in this text.
     */
    val linkUrl: String?
}
