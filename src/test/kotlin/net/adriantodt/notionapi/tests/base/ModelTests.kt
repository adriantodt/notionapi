package net.adriantodt.notionapi.tests.base

import net.adriantodt.notionapi.model.block.BlockType

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
}
