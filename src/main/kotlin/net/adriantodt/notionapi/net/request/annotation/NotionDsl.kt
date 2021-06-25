package net.adriantodt.notionapi.net.request.annotation

@DslMarker
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
annotation class NotionDsl()
