package net.adriantodt.notionapi.model.user

interface Person : User {
    /**
     * Email address of person.
     */
    val email: String
}
