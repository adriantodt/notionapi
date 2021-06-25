package net.adriantodt.notionapi.net

import net.adriantodt.notionapi.utils.Closure

interface ParameterizedRequest<T> : Request {
    val params: T

    fun params(block: Closure<T>)
}
