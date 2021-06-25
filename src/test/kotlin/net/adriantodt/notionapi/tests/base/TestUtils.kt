package net.adriantodt.notionapi.tests.base

import com.grack.nanojson.JsonParser

open class TestUtils {
    fun classpathFileToString(name: String): String {
        return javaClass.getResourceAsStream(name).reader().readText()
    }

    fun String.parseJson(): Any? {
        return JsonParser.any().from(this)
    }
}

