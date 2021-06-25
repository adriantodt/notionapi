package net.adriantodt.notionapi.model.richtext

import net.adriantodt.notionapi.model.user.User

interface UserMention : Mention {
    val value: User
}
