package net.adriantodt.notionapi.net.request.richtext

import com.grack.nanojson.JsonObject
import net.adriantodt.notionapi.model.database.Database
import net.adriantodt.notionapi.model.page.Page
import net.adriantodt.notionapi.model.user.User
import net.adriantodt.notionapi.utils.buildJsonObject
import net.adriantodt.notionapi.utils.jsonObjectOf
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE
import java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME

class MentionBuilder : RichTextBuilder() {
    var data: MentionData? = null

    fun page(page: Page) = page(page.id)
    fun page(id: String) {
        data = PageMention(id)
    }

    fun database(database: Database) = page(database.id)
    fun database(id: String) {
        data = DatabaseMention(id)
    }

    fun user(user: User) = user(user.id)
    fun user(id: String) {
        data = UserMention(id)
    }

    fun date(start: OffsetDateTime, end: OffsetDateTime? = null) = date(
        start.format(ISO_OFFSET_DATE_TIME),
        end?.format(ISO_OFFSET_DATE_TIME)
    )
    fun date(start: LocalDate, end: LocalDate? = null) = date(
        start.format(ISO_LOCAL_DATE),
        end?.format(ISO_LOCAL_DATE)
    )
    fun date(start: String, end: String? = null) {
        data = DateMention(start, end)
    }

    override fun toJson() = super.toJson().apply {
        put("type", "mention")
        put("mention", data?.toJson() ?: throw IllegalStateException("No mention data was specified."))
    }

    sealed class MentionData {
        internal abstract fun toJson(): JsonObject
    }

    data class PageMention(val id: String) : MentionData() {
        override fun toJson() = jsonObjectOf("type" to "page", "page" to jsonObjectOf("id" to id))
    }

    data class DatabaseMention(val id: String) : MentionData() {
        override fun toJson() = jsonObjectOf("type" to "database", "database" to jsonObjectOf("id" to id))
    }

    data class UserMention(val id: String) : MentionData() {
        override fun toJson() = jsonObjectOf("type" to "user", "user" to jsonObjectOf("id" to id))
    }

    data class DateMention(val start: String, val end: String?) : MentionData() {
        override fun toJson() = jsonObjectOf(
            "type" to "date",
            "date" to buildJsonObject {
                put("start", start)
                end?.let { put("end", it) }
            }
        )
    }
}
