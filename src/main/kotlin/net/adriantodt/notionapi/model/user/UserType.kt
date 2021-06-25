package net.adriantodt.notionapi.model.user

import net.adriantodt.notionapi.utils.JsonEnum

enum class UserType(override val value: String?) : JsonEnum {
    PERSON("person"),
    BOT("bot"),
    UNKNOWN(null);

    companion object : JsonEnum.Companion<UserType>(UserType::values, { UNKNOWN })
}
