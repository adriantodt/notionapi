package net.adriantodt.notionapi.tests.base

import net.adriantodt.notionapi.impl.model.toOffsetDateTime
import net.adriantodt.notionapi.model.Color
import net.adriantodt.notionapi.model.NotionObject
import net.adriantodt.notionapi.model.block.Block
import net.adriantodt.notionapi.model.block.BlockType
import net.adriantodt.notionapi.model.richtext.RichTextType
import net.adriantodt.notionapi.model.richtext.Text
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals

open class ModelTests : TestUtils() {
    open inner class Snippet(name: String) {
        private val snippet by lazy { classpathFileToString(name) }
        val json by lazy { snippet.parseJson() }
    }

    interface NotionObjectDeclaration {
        val id: String
        val createdTime: String
        val lastEditedTime: String
    }

    interface BlockDeclaration : NotionObjectDeclaration {
        val type: BlockType
        val hasChildren: Boolean
    }

    // BEGIN: standardized assertions

    /**
     * Assist function to assert that a given NotionObject is equals to it's expected declaration.
     */
    fun assertNotionObject(expect: NotionObjectDeclaration, actual: NotionObject) {
        assertEquals(expect.id, actual.id)
        assertEquals(expect.createdTime.toOffsetDateTime(), actual.createdTime)
        assertEquals(expect.lastEditedTime.toOffsetDateTime(), actual.lastEditedTime)
    }

    /**
     * Assist function to assert that a given Block is equals to it's expected declaration.
     */
    fun assertBlock(expect: BlockDeclaration, actual: Block) {
        assertNotionObject(expect, actual)
        assertEquals(expect.type, actual.type)
        assertEquals(expect.hasChildren, actual.hasChildren)
    }

    /**
     * Assist function to assert that a given Text is a plain text with no formatting.
     */
    fun assertPlainText(expectedPlainText: String, actual: Text) {
        assertEquals(RichTextType.TEXT, actual.type)
        assertEquals(expectedPlainText, actual.plainText)
        Assertions.assertNull(actual.href)
        Assertions.assertFalse(actual.bold)
        Assertions.assertFalse(actual.italic)
        Assertions.assertFalse(actual.strikethrough)
        Assertions.assertFalse(actual.underline)
        Assertions.assertFalse(actual.code)
        assertEquals(Color.DEFAULT, actual.color)
        assertEquals(Color.DEFAULT, actual.backgroundColor)
        assertEquals(expectedPlainText, actual.content)
        Assertions.assertNull(actual.linkUrl)
    }

}
