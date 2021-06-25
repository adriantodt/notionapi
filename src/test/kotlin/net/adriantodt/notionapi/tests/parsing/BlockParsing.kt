package net.adriantodt.notionapi.tests.parsing

import net.adriantodt.notionapi.impl.model.BlockImpl
import net.adriantodt.notionapi.model.block.*
import net.adriantodt.notionapi.model.richtext.Text
import net.adriantodt.notionapi.tests.base.ModelTests
import net.adriantodt.notionapi.tests.base.assertBlock
import net.adriantodt.notionapi.tests.base.assertPlainText
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

class BlockParsing {
    @Test
    fun `parse heading 1`() {
        val block = BlockImpl.fromResponse(Block1.json) as? TextBlock ?: fail("Block is not a TextBlock")
        assertBlock(Block1, block)
        assertFalse(block is ChildPageBlock)
        assertFalse(block is TextBlockWithChildren)

        val text = block.text.single() as? Text ?: fail("Block's rich text is not Text.")
        assertPlainText(Block1.text, text)
    }

    @Test
    fun `parse heading 2`() {
        val block = BlockImpl.fromResponse(Block2.json) as? TextBlock ?: fail("Block is not a TextBlock")
        assertBlock(Block2, block)
        assertFalse(block is ChildPageBlock)
        assertFalse(block is TextBlockWithChildren)

        val text = block.text.single() as? Text ?: fail("Block's rich text is not Text.")
        assertPlainText(Block2.text, text)
    }

    @Test
    fun `parse heading 3`() {
        val block = BlockImpl.fromResponse(Block3.json) as? TextBlock ?: fail("Block is not a TextBlock")
        assertBlock(Block3, block)
        assertFalse(block is ChildPageBlock)
        assertFalse(block is TextBlockWithChildren)

        val text = block.text.single() as? Text ?: fail("Block's rich text is not Text.")
        assertPlainText(Block3.text, text)
    }

    @Test
    fun `parse paragraph`() {
        val block = BlockImpl.fromResponse(Block4.json) as? TextBlock ?: fail("Block is not a TextBlock")
        assertBlock(Block4, block)
        assertFalse(block is ChildPageBlock)
        assertFalse(block is TextBlockWithChildren)

        val text = block.text.single() as? Text ?: fail("Block's rich text is not Text.")
        assertPlainText(Block4.text, text)
    }

    @Test
    fun `parse checked to-do block`() {
        val block = BlockImpl.fromResponse(Block5.json) as? ToDoBlock ?: fail("Block is not a ToDoBlock")
        assertBlock(Block5, block)
        assertFalse(block is ChildPageBlock)
        assertFalse(block is TextBlockWithChildren)

        val text = block.text.single() as? Text ?: fail("Block's rich text is not Text.")
        assertPlainText(Block5.text, text)

        assertTrue(block.checked)
    }

    @Test
    fun `parse unchecked to-do block`() {
        val block = BlockImpl.fromResponse(Block6.json) as? ToDoBlock ?: fail("Block is not a ToDoBlock")
        assertBlock(Block6, block)
        assertFalse(block is ChildPageBlock)
        assertFalse(block is TextBlockWithChildren)

        val text = block.text.single() as? Text ?: fail("Block's rich text is not Text.")
        assertPlainText(Block6.text, text)

        assertFalse(block.checked)
    }

    @Test
    fun `parse checked to-do block with children`() {
        val block = BlockImpl.fromResponse(Block7.json) as? ToDoBlock ?: fail("Block is not a ToDoBlock")
        assertBlock(Block7, block)
        assertFalse(block is ChildPageBlock)
        assertFalse(block is TextBlockWithChildren)

        val text = block.text.single() as? Text ?: fail("Block's rich text is not Text.")
        assertPlainText(Block7.text, text)

        assertTrue(block.checked)
    }

    @Test
    fun `parse unchecked to-do block with children`() {
        val block = BlockImpl.fromResponse(Block8.json) as? ToDoBlock ?: fail("Block is not a ToDoBlock")
        assertBlock(Block8, block)
        assertFalse(block is ChildPageBlock)
        assertFalse(block is TextBlockWithChildren)

        val text = block.text.single() as? Text ?: fail("Block's rich text is not Text.")
        assertPlainText(Block8.text, text)

        assertFalse(block.checked)
    }

    @Test
    fun `parse bulleted item`() {
        val block = BlockImpl.fromResponse(Block9.json) as? TextBlock ?: fail("Block is not a TextBlock")
        assertBlock(Block9, block)
        assertFalse(block is ChildPageBlock)
        assertFalse(block is TextBlockWithChildren)

        val text = block.text.single() as? Text ?: fail("Block's rich text is not Text.")
        assertPlainText(Block9.text, text)
    }

    @Test
    fun `parse bulleted item with children`() {
        val block = BlockImpl.fromResponse(Block10.json) as? TextBlock ?: fail("Block is not a TextBlock")
        assertBlock(Block10, block)
        assertFalse(block is ChildPageBlock)
        assertFalse(block is TextBlockWithChildren)

        val text = block.text.single() as? Text ?: fail("Block's rich text is not Text.")
        assertPlainText(Block10.text, text)
    }

    @Test
    fun `parse numbered item`() {
        val block = BlockImpl.fromResponse(Block11.json) as? TextBlock ?: fail("Block is not a TextBlock")
        assertBlock(Block11, block)
        assertFalse(block is ChildPageBlock)
        assertFalse(block is TextBlockWithChildren)

        val text = block.text.single() as? Text ?: fail("Block's rich text is not Text.")
        assertPlainText(Block11.text, text)
    }

    @Test
    fun `parse numbered item with children`() {
        val block = BlockImpl.fromResponse(Block12.json) as? TextBlock ?: fail("Block is not a TextBlock")
        assertBlock(Block12, block)
        assertFalse(block is ChildPageBlock)
        assertFalse(block is TextBlockWithChildren)

        val text = block.text.single() as? Text ?: fail("Block's rich text is not Text.")
        assertPlainText(Block12.text, text)
    }

    @Test
    fun `parse toggle block`() {
        val block = BlockImpl.fromResponse(Block13.json) as? TextBlock ?: fail("Block is not a TextBlock")
        assertBlock(Block13, block)
        assertFalse(block is ChildPageBlock)
        assertFalse(block is TextBlockWithChildren)

        val text = block.text.single() as? Text ?: fail("Block's rich text is not Text.")
        assertPlainText(Block13.text, text)
    }

    @Test
    fun `parse toggle block with children`() {
        val block = BlockImpl.fromResponse(Block14.json) as? TextBlock ?: fail("Block is not a TextBlock")
        assertBlock(Block14, block)
        assertFalse(block is ChildPageBlock)
        assertFalse(block is TextBlockWithChildren)

        val text = block.text.single() as? Text ?: fail("Block's rich text is not Text.")
        assertPlainText(Block14.text, text)
    }

    @Test
    fun `parse child page`() {
        val block = BlockImpl.fromResponse(Block15.json) as? ChildPageBlock ?: fail("Block is not a ChildPageBlock")
        assertBlock(Block15, block)
        assertFalse(block is TextBlock)

        assertEquals(Block15.text, block.title)
    }

    @Test
    fun `parse unsupported`() {
        val block = BlockImpl.fromResponse(Block16.json)
        assertBlock(Block16, block)
        assertFalse(block is TextBlock)
        assertFalse(block is TextBlockWithChildren)
        assertFalse(block is ChildPageBlock)
    }


    @Test
    fun `parse unsupported hypothetical component`() {
        val block = BlockImpl.fromResponse(Block17.json)
        assertBlock(Block17, block)
        assertFalse(block is TextBlock)
        assertFalse(block is TextBlockWithChildren)
        assertFalse(block is ChildPageBlock)
    }

    private companion object : ModelTests() {
        object Block1 : Snippet("/input/block/unit/1.json"), BlockDeclaration {
            override val id = "1d7dd368-abf4-4b21-9eb9-a67a59d0bf40"
            override val createdTime = "2021-06-25T13:15:53.207Z"
            override val lastEditedTime = "2021-06-25T13:16:00.000Z"
            override val type = BlockType.HEADING_1
            override val hasChildren = false
            const val text = "Heading 1"
        }

        object Block2 : Snippet("/input/block/unit/2.json"), BlockDeclaration {
            override val id = "73445189-2846-4800-9fa3-cac1a69d6fda"
            override val createdTime = "2021-06-25T13:16:05.682Z"
            override val lastEditedTime = "2021-06-25T13:16:00.000Z"
            override val type = BlockType.HEADING_2
            override val hasChildren = false
            const val text = "Heading 2"
        }

        object Block3 : Snippet("/input/block/unit/3.json"), BlockDeclaration {
            override val id = "ff7b5804-7115-4286-87da-d2e3e4954bef"
            override val createdTime = "2021-06-25T13:16:10.556Z"
            override val lastEditedTime = "2021-06-25T13:16:00.000Z"
            override val type = BlockType.HEADING_3
            override val hasChildren = false
            const val text = "Heading 3"
        }

        object Block4 : Snippet("/input/block/unit/4.json"), BlockDeclaration {
            override val id = "aa058d6b-b6e0-4412-b061-cf78ba1dd227"
            override val createdTime = "2021-06-25T13:16:00.000Z"
            override val lastEditedTime = "2021-06-25T13:20:00.000Z"
            override val type = BlockType.PARAGRAPH
            override val hasChildren = false
            const val text = "Paragraph"
        }

        object Block5 : Snippet("/input/block/unit/5.json"), BlockDeclaration {
            override val id = "e38046ba-f705-464f-9934-f321f272f462"
            override val createdTime = "2021-06-25T13:16:27.242Z"
            override val lastEditedTime = "2021-06-25T13:16:00.000Z"
            override val type = BlockType.TO_DO
            override val hasChildren = false
            const val text = "Checked To-do"
        }

        object Block6 : Snippet("/input/block/unit/6.json"), BlockDeclaration {
            override val id = "b093d630-2ea9-4cd5-a411-451756650096"
            override val createdTime = "2021-06-25T13:16:00.000Z"
            override val lastEditedTime = "2021-06-25T13:16:00.000Z"
            override val type = BlockType.TO_DO
            override val hasChildren = false
            const val text = "Unchecked To-do"
        }

        object Block7 : Snippet("/input/block/unit/7.json"), BlockDeclaration {
            override val id = "ce56a603-d72e-4f96-afd8-bf4f22c41e7e"
            override val createdTime = "2021-06-25T13:16:00.000Z"
            override val lastEditedTime = "2021-06-25T16:05:00.000Z"
            override val type = BlockType.TO_DO
            override val hasChildren = true
            const val text = "Checked To-do with children"
        }

        object Block8 : Snippet("/input/block/unit/8.json"), BlockDeclaration {
            override val id = "306af577-cb59-496e-a746-20182e109f4d"
            override val createdTime = "2021-06-25T13:17:31.592Z"
            override val lastEditedTime = "2021-06-25T13:17:00.000Z"
            override val type = BlockType.TO_DO
            override val hasChildren = true
            const val text = "Unchecked To-do with children"
        }

        object Block9 : Snippet("/input/block/unit/9.json"), BlockDeclaration {
            override val id = "f94ff0d6-174a-444a-a793-384e8a638f6a"
            override val createdTime = "2021-06-25T13:18:37.922Z"
            override val lastEditedTime = "2021-06-25T13:18:00.000Z"
            override val type = BlockType.BULLETED_LIST_ITEM
            override val hasChildren = false
            const val text = "Bulleted item"
        }

        object Block10 : Snippet("/input/block/unit/10.json"), BlockDeclaration {
            override val id = "f4a9a2f3-04de-4f7c-a7a4-225992f26e3f"
            override val createdTime = "2021-06-25T13:18:00.000Z"
            override val lastEditedTime = "2021-06-25T13:18:00.000Z"
            override val type = BlockType.BULLETED_LIST_ITEM
            override val hasChildren = true
            const val text = "Bulleted item with children"
        }

        object Block11 : Snippet("/input/block/unit/11.json"), BlockDeclaration {
            override val id = "fa25e84d-5e2f-42e4-873b-475cd4a77267"
            override val createdTime = "2021-06-25T13:18:51.613Z"
            override val lastEditedTime = "2021-06-25T13:18:00.000Z"
            override val type = BlockType.NUMBERED_LIST_ITEM
            override val hasChildren = false
            const val text = "Numbered item"
        }

        object Block12 : Snippet("/input/block/unit/12.json"), BlockDeclaration {
            override val id = "5bacbcf6-7a73-483d-9ead-e11f2db9885d"
            override val createdTime = "2021-06-25T13:18:00.000Z"
            override val lastEditedTime = "2021-06-25T13:19:00.000Z"
            override val type = BlockType.NUMBERED_LIST_ITEM
            override val hasChildren = true
            const val text = "Numbered item with children"
        }

        object Block13 : Snippet("/input/block/unit/13.json"), BlockDeclaration {
            override val id = "b6f1fb5f-6cf7-4b31-8d59-41e78e9b82c8"
            override val createdTime = "2021-06-25T13:19:08.085Z"
            override val lastEditedTime = "2021-06-25T13:19:00.000Z"
            override val type = BlockType.TOGGLE
            override val hasChildren = false
            const val text = "Toggle block"
        }

        object Block14 : Snippet("/input/block/unit/14.json"), BlockDeclaration {
            override val id = "2c522e66-9d48-4cf4-9d91-fc808cd8cb55"
            override val createdTime = "2021-06-25T13:19:00.000Z"
            override val lastEditedTime = "2021-06-25T13:19:00.000Z"
            override val type = BlockType.TOGGLE
            override val hasChildren = true
            const val text = "Toggle block with children"
        }

        object Block15 : Snippet("/input/block/unit/15.json"), BlockDeclaration {
            override val id = "397e4b83-3577-4a05-a036-36a598eae362"
            override val createdTime = "2021-06-25T13:19:46.031Z"
            override val lastEditedTime = "2021-06-25T13:19:00.000Z"
            override val type = BlockType.CHILD_PAGE
            override val hasChildren = false
            const val text = "Child page"
        }

        object Block16 : Snippet("/input/block/unit/16.json"), BlockDeclaration {
            override val id = "87fbf8da-5543-4b54-86b2-53a3facbaa1b"
            override val createdTime = "2021-06-25T13:20:00.242Z"
            override val lastEditedTime = "2021-06-25T13:20:00.000Z"
            override val type = BlockType.UNSUPPORTED
            override val hasChildren = false
        }

        object Block17 : Snippet("/input/block/unit/17.json"), BlockDeclaration {
            override val id = "87fbf8da-5543-4b54-86b2-53a3facbaa1b"
            override val createdTime = "2021-06-25T13:20:00.242Z"
            override val lastEditedTime = "2021-06-25T13:20:00.000Z"
            override val type = BlockType.UNSUPPORTED
            override val hasChildren = false
        }
    }
}
