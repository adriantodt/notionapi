package net.adriantodt.notionapi.model.richtext

import net.adriantodt.notionapi.utils.JsonEnum

enum class MentionType(override val value: String?) : JsonEnum {
    USER("user"),
    PAGE("page"),
    DATABASE("database"),
    DATE("date"),
    UNKNOWN(null);

    companion object : JsonEnum.Companion<MentionType>(::values, { UNKNOWN })
}
