package net.adriantodt.notionapi.net.request

import net.adriantodt.notionapi.model.database.Database
import net.adriantodt.notionapi.model.page.Page
import net.adriantodt.notionapi.model.page.PageParent
import net.adriantodt.notionapi.net.request.annotation.NotionDsl
import net.adriantodt.notionapi.net.request.block.PageBuilder
import net.adriantodt.notionapi.net.request.properties.PropertiesBuilder
import net.adriantodt.notionapi.utils.buildJsonObject
import net.adriantodt.notionapi.utils.jsonObjectOf

class CreatePageParams {
    var parent: PageParent? = null
        set(value) {
            require(value !is PageParent.Workspace) { "Workspace page parent is not supported." }
            field = value
        }

    @NotionDsl
    fun parent(database: Database) {
        this.parent = database.toParent()
    }

    @NotionDsl
    fun parent(page: Page) {
        this.parent = page.toParent()
    }

    var properties = PropertiesBuilder()

    @NotionDsl
    fun properties(block: PropertiesBuilder.() -> Unit) {
        this.properties = this.properties.apply(block)
    }

    var children: PageBuilder? = null

    @NotionDsl
    fun children(block: PageBuilder.() -> Unit) {
        this.children = (this.children ?: PageBuilder()).apply(block)
    }

    internal fun toJson() = buildJsonObject {
        parent?.let {
            val pair = when (it) {
                is PageParent.Database -> "database_id" to it.id
                is PageParent.Page -> "page_id" to it.id
                PageParent.Workspace -> throw AssertionError("Workspace page parent is not supported.")
            }
            put("parent", jsonObjectOf(pair))
        } ?: throw IllegalStateException("parent is a required parameter")

        put("properties", properties.toJson())

        children?.let { put("children", it.toJson()) }
    }
}
