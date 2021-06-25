package net.adriantodt.notionapi.scripts

import com.grack.nanojson.JsonObject
import com.grack.nanojson.JsonWriter
import net.adriantodt.notionapi.NotionApi
import java.io.File

fun generateBlockTests(secret: String, id: String) {
    val notion = NotionApi {
        accessToken = secret
    }

    val body = notion.retrieveBlockChildren(id).join().rawJsonBody as JsonObject

    File("out_tests").mkdirs()

    var counter = 1
    for (result in body.getArray("results")) {
        val obj = result as JsonObject
        val i = counter++
        File("out_tests/$i.json").writeText(JsonWriter.string(obj))
        println(
            """
            object Block$i : Snippet("/snippets/block/unit/$i.json"), BlockDeclaration {
                override val id = "${obj["id"]}"
                override val createdTime = "${obj["created_time"]}"
                override val lastEditedTime = "${obj["last_edited_time"]}"
                override val type = BlockType.${obj.getString("type").uppercase()}
                override val hasChildren = ${obj["has_children"]}
            }
        """.trimIndent()
        )
    }
}
