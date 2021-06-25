package net.adriantodt.notionapi.net.request.properties

import com.grack.nanojson.JsonArray
import com.grack.nanojson.JsonObject
import net.adriantodt.notionapi.model.page.Page
import net.adriantodt.notionapi.model.property.PropertyType
import net.adriantodt.notionapi.model.property.SelectOption
import net.adriantodt.notionapi.model.user.User
import net.adriantodt.notionapi.net.request.richtext.RichTextListBuilder
import net.adriantodt.notionapi.utils.Range
import net.adriantodt.notionapi.utils.buildJsonObject
import net.adriantodt.notionapi.utils.jsonObjectOf
import java.net.URI
import java.net.URL
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE
import java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME

class PropertiesBuilder {
    internal val map = mutableMapOf<String, JsonObject>()

    fun title(property: String = "title", value: String) {
        title(property) { text { +value } }
    }

    fun title(property: String = "title", block: RichTextListBuilder.() -> Unit) {
        val value = RichTextListBuilder().apply(block).toJson()
        put(property, PropertyType.TITLE, value)
    }

    fun richText(property: String, value: String) {
        richText(property) { text { +value } }
    }

    fun richText(property: String, block: RichTextListBuilder.() -> Unit) {
        val value = RichTextListBuilder().apply(block).toJson()
        put(property, PropertyType.RICH_TEXT, value)
    }

    fun number(property: String, value: String) {
        val number = value.toDoubleOrNull() ?: throw IllegalStateException("String is not a valid Number")
        number(property, number)
    }

    fun number(property: String, value: Number) {
        put(property, PropertyType.NUMBER, value)
    }

    fun select(property: String, value: String) {
        put(property, PropertyType.SELECT, jsonObjectOf("id" to value))
    }

    fun select(property: String, value: SelectOption) {
        select(property, value.id)
    }

    fun multiSelect(property: String, value: String) {
        multiSelect(property, listOf(value))
    }

    fun multiSelect(property: String, value: SelectOption) {
        multiSelect(property, listOf(value))
    }

    fun multiSelect(property: String, value: List<String>) {
        put(property, PropertyType.MULTI_SELECT, value.mapTo(JsonArray()) { jsonObjectOf("id" to it) })
    }

    @JvmName("multiSelectOption")
    fun multiSelect(property: String, value: List<SelectOption>) {
        multiSelect(property, value.map(SelectOption::id))
    }

    fun date(property: String, start: String, end: String? = null) {
        val value = buildJsonObject {
            put("start", start)
            end?.let { put("end", it) }
        }
        put(property, PropertyType.DATE, value)
    }

    fun date(property: String, start: LocalDate, end: LocalDate? = null) {
        date(property, start.format(ISO_LOCAL_DATE), end?.format(ISO_LOCAL_DATE))
    }

    fun date(property: String, start: OffsetDateTime, end: OffsetDateTime? = null) {
        date(property, start.format(ISO_OFFSET_DATE_TIME), end?.format(ISO_OFFSET_DATE_TIME))
    }

    fun date(property: String, value: Range<String>) {
        date(property, value.first, value.second)
    }

    @JvmName("localDate")
    fun date(property: String, value: Range<LocalDate>) {
        date(property, value.first, value.second)
    }

    @JvmName("offsetDateTime")
    fun date(property: String, value: Range<OffsetDateTime>) {
        date(property, value.first, value.second)
    }

    fun people(property: String, value: String) {
        people(property, listOf(value))
    }

    fun people(property: String, value: User) {
        people(property, listOf(value))
    }

    fun people(property: String, value: List<String>) {
        val array = value.mapTo(JsonArray()) { jsonObjectOf("object" to "user", "id" to it) }
        put(property, PropertyType.PEOPLE, array)
    }

    @JvmName("peopleFromUsers")
    fun people(property: String, value: List<User>) {
        people(property, value.map(User::id))
    }

    fun checkbox(property: String, value: String) {
        val boolean = value.toBooleanStrictOrNull() ?: throw IllegalStateException("String is not a valid Boolean")
        checkbox(property, boolean)
    }

    fun checkbox(property: String, value: Boolean) {
        put(property, PropertyType.CHECKBOX, value)
    }

    fun url(property: String, value: String) {
        put(property, PropertyType.URL, value)
    }

    fun url(property: String, value: URL) {
        url(property, value.toString())
    }

    fun url(property: String, value: URI) {
        url(property, value.toString())
    }

    fun email(property: String, value: String) {
        put(property, PropertyType.EMAIL, value)
    }

    fun phoneNumber(property: String, value: String) {
        put(property, PropertyType.PHONE_NUMBER, value)
    }

    fun relation(property: String, value: String) {
        relation(property, listOf(value))
    }

    fun relation(property: String, value: Page) {
        relation(property, listOf(value))
    }

    fun relation(property: String, value: List<String>) {
        put(property, PropertyType.RELATION, value.mapTo(JsonArray()) { jsonObjectOf("id" to it) })
    }

    @JvmName("relationFromPages")
    fun relation(property: String, value: List<Page>) {
        relation(property, value.map(Page::id))
    }

    // BELOW : Implementation Details

    fun put(property: String, type: PropertyType, value: Any) {
        map[property] = jsonObjectOf(type.value to value)
    }

    internal fun toJson() = JsonObject(map)
}
