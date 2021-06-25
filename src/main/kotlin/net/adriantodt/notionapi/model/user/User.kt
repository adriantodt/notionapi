package net.adriantodt.notionapi.model.user

interface User {

    /**
     * Unique identifier for this user.
     */
    val id: String

    /**
     * Type of the user.
     */
    val type: UserType

    /**
     * User's name, as displayed in Notion.
     */
    val name: String

    /**
     * Chosen avatar image.
     */
    val avatarUrl: String?
}

