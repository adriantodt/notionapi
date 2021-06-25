package net.adriantodt.notionapi.model.page

sealed class PageParent {
    data class Database(val id: String) : PageParent()
    data class Page(val id: String) : PageParent()
    object Workspace : PageParent() {
        override fun toString(): String = javaClass.simpleName
    }
}
