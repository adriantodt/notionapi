package net.adriantodt.notionapi.tests.building.richtext

import net.adriantodt.notionapi.net.request.richtext.EquationBuilder
import net.adriantodt.notionapi.net.request.richtext.MentionBuilder
import net.adriantodt.notionapi.net.request.richtext.TextBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID.randomUUID

class RichTextBuilding {
    @ParameterizedTest
    @MethodSource("net.adriantodt.notionapi.tests.building.richtext.RichTextParamsKt#allCombinations")
    fun `build equation`(params: RichTextParams) {
        val json = EquationBuilder().apply {
            applyParams(params)
            +"/alpha"
        }.toJson()

        assertParams(params, json.getObject("annotations"))

        assertEquals("equation", json.getString("type"))
        assertTrue(json.has("equation"))

        val equation = json.getObject("equation")
        assertEquals("/alpha", equation.getString("expression"))
    }

    @ParameterizedTest
    @MethodSource("net.adriantodt.notionapi.tests.building.richtext.RichTextParamsKt#allCombinations")
    fun `build text`(params: RichTextParams) {
        val json = TextBuilder().apply {
            applyParams(params)
            +"This is a example."
        }.toJson()

        assertParams(params, json.getObject("annotations"))

        assertEquals("text", json.getString("type"))
        assertTrue(json.has("text"))

        val text = json.getObject("text")
        assertTrue(text.has("content"))
        assertEquals("This is a example.", text.getString("content"))
    }

    @ParameterizedTest
    @MethodSource("net.adriantodt.notionapi.tests.building.richtext.RichTextParamsKt#allCombinations")
    fun `build link`(params: RichTextParams) {
        val json = TextBuilder().apply {
            applyParams(params)
            +"This is a example."
            linkUrl = "http://example.com"
        }.toJson()

        assertParams(params, json.getObject("annotations"))

        assertEquals("text", json.getString("type"))
        assertTrue(json.has("text"))

        val text = json.getObject("text")
        assertTrue(text.has("content"))
        assertEquals("This is a example.", text.getString("content"))
        assertTrue(text.has("link"))

        val link = text.getObject("link")
        assertTrue(link.has("url"))
        assertEquals("http://example.com", link.getString("url"))
    }

    @ParameterizedTest
    @MethodSource("net.adriantodt.notionapi.tests.building.richtext.RichTextParamsKt#allCombinations")
    fun `build page mention`(params: RichTextParams) {
        val uuid = randomUUID().toString()
        val json = MentionBuilder().apply {
            page(uuid)
            applyParams(params)
        }.toJson()

        assertParams(params, json.getObject("annotations"))

        assertEquals("mention", json.getString("type"))
        assertTrue(json.has("mention"))

        val mention = json.getObject("mention")
        assertTrue(mention.has("type"))
        assertEquals("page", mention.getString("type"))
        assertTrue(mention.has("page"))

        val page = mention.getObject("page")
        assertTrue(page.has("id"))
        assertEquals(uuid, page.getString("id"))
    }

    @ParameterizedTest
    @MethodSource("net.adriantodt.notionapi.tests.building.richtext.RichTextParamsKt#allCombinations")
    fun `build database mention`(params: RichTextParams) {
        val uuid = randomUUID().toString()
        val json = MentionBuilder().apply {
            database(uuid)
            applyParams(params)
        }.toJson()

        assertParams(params, json.getObject("annotations"))

        assertEquals("mention", json.getString("type"))
        assertTrue(json.has("mention"))

        val mention = json.getObject("mention")
        assertTrue(mention.has("type"))
        assertEquals("database", mention.getString("type"))
        assertTrue(mention.has("database"))

        val database = mention.getObject("database")
        assertTrue(database.has("id"))
        assertEquals(uuid, database.getString("id"))
    }

    @ParameterizedTest
    @MethodSource("net.adriantodt.notionapi.tests.building.richtext.RichTextParamsKt#allCombinations")
    fun `build user mention`(params: RichTextParams) {
        val uuid = randomUUID().toString()
        val json = MentionBuilder().apply {
            user(uuid)
            applyParams(params)
        }.toJson()

        assertParams(params, json.getObject("annotations"))

        assertEquals("mention", json.getString("type"))
        assertTrue(json.has("mention"))

        val mention = json.getObject("mention")
        assertTrue(mention.has("type"))
        assertEquals("user", mention.getString("type"))
        assertTrue(mention.has("user"))

        val user = mention.getObject("user")
        assertTrue(user.has("object"))
        assertEquals("user", user.getString("object"))
        assertTrue(user.has("id"))
        assertEquals(uuid, user.getString("id"))
    }

    @ParameterizedTest
    @MethodSource("net.adriantodt.notionapi.tests.building.richtext.RichTextParamsKt#allCombinations")
    fun `build date mention`(params: RichTextParams) {
        val now = LocalDate.now()
        val json = MentionBuilder().apply {
            date(now)
            applyParams(params)
        }.toJson()

        assertParams(params, json.getObject("annotations"))

        assertEquals("mention", json.getString("type"))
        assertTrue(json.has("mention"))

        val mention = json.getObject("mention")
        assertTrue(mention.has("type"))
        assertEquals("date", mention.getString("type"))
        assertTrue(mention.has("date"))

        val date = mention.getObject("date")
        assertTrue(date.has("start"))
        assertEquals(now.format(DateTimeFormatter.ISO_LOCAL_DATE), date.getString("start"))
    }

    @ParameterizedTest
    @MethodSource("net.adriantodt.notionapi.tests.building.richtext.RichTextParamsKt#allCombinations")
    fun `build date range mention`(params: RichTextParams) {
        val start = LocalDate.now()
        val end = start.plusDays(1)
        val json = MentionBuilder().apply {
            date(start, end)
            applyParams(params)
        }.toJson()

        assertParams(params, json.getObject("annotations"))

        assertEquals("mention", json.getString("type"))
        assertTrue(json.has("mention"))

        val mention = json.getObject("mention")
        assertTrue(mention.has("type"))
        assertEquals("date", mention.getString("type"))
        assertTrue(mention.has("date"))

        val date = mention.getObject("date")
        assertTrue(date.has("start"))
        assertEquals(start.format(DateTimeFormatter.ISO_LOCAL_DATE), date.getString("start"))
        assertTrue(date.has("end"))
        assertEquals(end.format(DateTimeFormatter.ISO_LOCAL_DATE), date.getString("end"))
    }

    @ParameterizedTest
    @MethodSource("net.adriantodt.notionapi.tests.building.richtext.RichTextParamsKt#allCombinations")
    fun `build date time mention`(params: RichTextParams) {
        val now = OffsetDateTime.now()
        val json = MentionBuilder().apply {
            date(now)
            applyParams(params)
        }.toJson()

        assertParams(params, json.getObject("annotations"))

        assertEquals("mention", json.getString("type"))
        assertTrue(json.has("mention"))

        val mention = json.getObject("mention")
        assertTrue(mention.has("type"))
        assertEquals("date", mention.getString("type"))
        assertTrue(mention.has("date"))

        val date = mention.getObject("date")
        assertTrue(date.has("start"))
        assertEquals(now.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME), date.getString("start"))
    }

    @ParameterizedTest
    @MethodSource("net.adriantodt.notionapi.tests.building.richtext.RichTextParamsKt#allCombinations")
    fun `build date time range mention`(params: RichTextParams) {
        val start = OffsetDateTime.now()
        val end = start.plusHours(1)
        val json = MentionBuilder().apply {
            date(start, end)
            applyParams(params)
        }.toJson()

        assertParams(params, json.getObject("annotations"))

        assertEquals("mention", json.getString("type"))
        assertTrue(json.has("mention"))

        val mention = json.getObject("mention")
        assertTrue(mention.has("type"))
        assertEquals("date", mention.getString("type"))
        assertTrue(mention.has("date"))

        val date = mention.getObject("date")
        assertTrue(date.has("start"))
        assertEquals(start.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME), date.getString("start"))
        assertTrue(date.has("end"))
        assertEquals(end.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME), date.getString("end"))
    }
}

