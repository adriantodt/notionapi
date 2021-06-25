package net.adriantodt.notionapi.net.request

open class CursorParams {
    var startCursor: String? = null

    var pageSize: Int? = null
        set(value) {
            require(value == null || value in 1..100) { "Value is not valid (null, or between 1 and 100)" }
            field = value
        }
}
