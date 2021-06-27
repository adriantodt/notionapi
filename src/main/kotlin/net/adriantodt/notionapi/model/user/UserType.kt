package net.adriantodt.notionapi.model.user

import net.adriantodt.notionapi.utils.JsonEnum
import net.adriantodt.notionapi.utils.lowercaseOfName

enum class UserType : JsonEnum {
    PERSON,
    BOT;

    override val value by lowercaseOfName()

    companion object : JsonEnum.Companion<UserType>(::values)
}
