package net.adriantodt.notionapi.tests.base

import net.adriantodt.notionapi.impl.model.toOffsetDateTime
import net.adriantodt.notionapi.model.Color
import net.adriantodt.notionapi.model.NotionObject
import net.adriantodt.notionapi.model.block.Block
import net.adriantodt.notionapi.model.richtext.RichTextType
import net.adriantodt.notionapi.model.richtext.Text
import org.junit.jupiter.api.Assertions.*

/**
 * Assist function to assert that a given NotionObject is equals to it's expected declaration.
 */
fun assertNotionObject(expect: ModelTests.NotionObjectDeclaration, actual: NotionObject) {
    assertEquals(expect.id, actual.id)
    assertEquals(expect.createdTime.toOffsetDateTime(), actual.createdTime)
    assertEquals(expect.lastEditedTime.toOffsetDateTime(), actual.lastEditedTime)
}

/**
 * Assist function to assert that a given Block is equals to it's expected declaration.
 */
fun assertBlock(expect: ModelTests.BlockDeclaration, actual: Block) {
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
    assertNull(actual.href)
    assertFalse(actual.bold)
    assertFalse(actual.italic)
    assertFalse(actual.strikethrough)
    assertFalse(actual.underline)
    assertFalse(actual.code)
    assertEquals(Color.DEFAULT, actual.color)
    assertEquals(Color.DEFAULT, actual.backgroundColor)
    assertEquals(expectedPlainText, actual.content)
    assertNull(actual.linkUrl)
}
