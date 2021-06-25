package net.adriantodt.notionapi.impl.model

import com.grack.nanojson.JsonArray
import com.grack.nanojson.JsonObject
import net.adriantodt.notionapi.model.Color
import net.adriantodt.notionapi.model.richtext.*
import net.adriantodt.notionapi.model.user.User
import java.time.LocalDate
import java.time.OffsetDateTime

data class RichTextImpl(
    override val plainText: String,
    override val href: String?,
    override val type: RichTextType,
    override val bold: Boolean,
    override val italic: Boolean,
    override val strikethrough: Boolean,
    override val underline: Boolean,
    override val code: Boolean,
    override val color: Color,
    override val backgroundColor: Color
) : RichText {
    data class TextImpl(
        override val plainText: String,
        override val href: String?,
        override val type: RichTextType,
        override val bold: Boolean,
        override val italic: Boolean,
        override val strikethrough: Boolean,
        override val underline: Boolean,
        override val code: Boolean,
        override val color: Color,
        override val backgroundColor: Color,
        override val content: String,
        override val linkUrl: String?
    ) : Text

    data class EquationImpl(
        override val plainText: String,
        override val href: String?,
        override val type: RichTextType,
        override val bold: Boolean,
        override val italic: Boolean,
        override val strikethrough: Boolean,
        override val underline: Boolean,
        override val code: Boolean,
        override val color: Color,
        override val backgroundColor: Color,
        override val expression: String,
    ) : Equation

    data class UserMentionImpl(
        override val plainText: String,
        override val href: String?,
        override val type: RichTextType,
        override val bold: Boolean,
        override val italic: Boolean,
        override val strikethrough: Boolean,
        override val underline: Boolean,
        override val code: Boolean,
        override val color: Color,
        override val backgroundColor: Color,
        override val mentionType: MentionType,
        override val value: User
    ) : UserMention

    data class DateMentionImpl(
        override val plainText: String,
        override val href: String?,
        override val type: RichTextType,
        override val bold: Boolean,
        override val italic: Boolean,
        override val strikethrough: Boolean,
        override val underline: Boolean,
        override val code: Boolean,
        override val color: Color,
        override val backgroundColor: Color,
        override val mentionType: MentionType,
        override val start: LocalDate,
        override val end: LocalDate?,
    ) : DateMention

    data class DateTimeMentionImpl(
        override val plainText: String,
        override val href: String?,
        override val type: RichTextType,
        override val bold: Boolean,
        override val italic: Boolean,
        override val strikethrough: Boolean,
        override val underline: Boolean,
        override val code: Boolean,
        override val color: Color,
        override val backgroundColor: Color,
        override val mentionType: MentionType,
        override val start: OffsetDateTime,
        override val end: OffsetDateTime?,
    ) : DateTimeMention

    data class UUIDMentionImpl(
        override val plainText: String,
        override val href: String?,
        override val type: RichTextType,
        override val bold: Boolean,
        override val italic: Boolean,
        override val strikethrough: Boolean,
        override val underline: Boolean,
        override val code: Boolean,
        override val color: Color,
        override val backgroundColor: Color,
        override val mentionType: MentionType,
        override val value: String
    ) : UUIDMention

    companion object {
        fun fromJsonArray(array: JsonArray): List<RichText> {
            require(array.all { it is JsonObject }) { "Invalid input, array should only contain JsonObjects." }
            return array.asSequence()
                .filterIsInstance<JsonObject>()
                .map { fromJsonObject(it) }
                .toList()
        }

        fun fromJsonObject(obj: JsonObject): RichText {
            val plainText = obj.getString("plain_text")
            val href = obj.getString("href")

            val annotations = obj.getObject("annotations")

            val bold = annotations.getBoolean("bold", false)
            val italic = annotations.getBoolean("italic", false)
            val strikethrough = annotations.getBoolean("strikethrough", false)
            val underline = annotations.getBoolean("underline", false)
            val code = annotations.getBoolean("code", false)
            val rawColor = annotations.getString("color")

            val color = if (rawColor.endsWith("_background")) Color.DEFAULT
            else Color.fromJson(rawColor)

            val backgroundColor = if (!rawColor.endsWith("_background")) Color.DEFAULT
            else Color.fromJson(rawColor.removeSuffix("_background"))

            return when (val type = RichTextType.fromJson(obj.getString("type"))) {
                RichTextType.TEXT -> {
                    val subObj = obj.getObject(type.value)
                    TextImpl(
                        plainText, href, type, bold, italic, strikethrough, underline, code, color, backgroundColor,
                        subObj.getString("content"), subObj.getString("link")
                    )
                }
                RichTextType.MENTION -> {
                    val subObj = obj.getObject(type.value)
                    when (val mentionType = MentionType.fromJson(subObj.getString("type"))) {
                        MentionType.USER -> UserMentionImpl(
                            plainText, href, type, bold, italic, strikethrough, underline, code, color, backgroundColor,
                            mentionType, UserImpl.fromJsonObject(subObj.getObject(mentionType.value))
                        )
                        MentionType.PAGE, MentionType.DATABASE -> UUIDMentionImpl(
                            plainText, href, type, bold, italic, strikethrough, underline, code, color, backgroundColor,
                            mentionType, subObj.getObject(mentionType.value).getString("id")
                        )
                        MentionType.DATE -> {
                            val dateObj = subObj.getObject(mentionType.value)
                            parseDateMention(
                                plainText, href, type, bold, italic, strikethrough, underline, code, color, backgroundColor,
                                mentionType,
                                dateObj.getString("start"),
                                dateObj.getString("end")
                            )
                        }
                        else -> throw IllegalArgumentException("No mention type found.")
                    }
                }
                RichTextType.EQUATION -> EquationImpl(
                    plainText, href, type, bold, italic, strikethrough, underline, code, color, backgroundColor,
                    obj.getObject(type.value).getString("expression")
                )
                RichTextType.UNKNOWN -> RichTextImpl(
                    plainText, href, type, bold, italic, strikethrough, underline, code, color, backgroundColor
                )
            }
        }

        private fun parseDateMention(
            plainText: String, href: String?, type: RichTextType, bold: Boolean, italic: Boolean,
            strikethrough: Boolean, underline: Boolean, code: Boolean, color: Color, backgroundColor: Color,
            mentionType: MentionType, startString: String, endString: String?
        ): Mention {
            val (startDateTime, startDate) = startString.tryParseDate()
            if (startDateTime != null) {
                return DateTimeMentionImpl(
                    plainText, href, type, bold, italic, strikethrough, underline, code, color, backgroundColor,
                    mentionType,
                    startDateTime,
                    endString?.toOffsetDateTime()
                )
            }
            if (startDate != null) {
                return DateMentionImpl(
                    plainText, href, type, bold, italic, strikethrough, underline, code, color, backgroundColor,
                    mentionType,
                    startDate,
                    endString?.toLocalDate()
                )
            }
            throw AssertionError("tryParseDate failed method contract.")
        }
    }
}
