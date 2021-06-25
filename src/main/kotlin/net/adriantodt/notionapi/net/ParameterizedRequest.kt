package net.adriantodt.notionapi.net

import net.adriantodt.notionapi.net.request.annotation.NotionDsl
import net.adriantodt.notionapi.utils.Closure

interface ParameterizedRequest<T> : Request {
    val params: T

    @NotionDsl
    fun params(block: Closure<T>)
}
