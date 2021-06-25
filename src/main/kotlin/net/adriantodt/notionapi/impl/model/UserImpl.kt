package net.adriantodt.notionapi.impl.model

import com.grack.nanojson.JsonArray
import com.grack.nanojson.JsonObject
import net.adriantodt.notionapi.model.user.Person
import net.adriantodt.notionapi.model.user.User
import net.adriantodt.notionapi.model.user.UserType

data class UserImpl(
    override val id: String,
    override val type: UserType,
    override val name: String,
    override val avatarUrl: String?
) : User {
    data class PersonImpl(
        override val id: String,
        override val type: UserType,
        override val name: String,
        override val avatarUrl: String?,
        override val email: String
    ) : Person

    companion object {
        fun fromResponse(raw: Any?): User {
            require(raw is JsonObject) { "Response body is not a JsonObject" }
            return fromJsonObject(raw)
        }

        fun fromJsonArray(array: JsonArray): List<User> {
            require(array.all { it is JsonObject }) { "Invalid input, array should only contain JsonObjects." }
            return array.asSequence()
                .filterIsInstance<JsonObject>()
                .map { fromJsonObject(it) }
                .toList()
        }

        fun fromJsonObject(obj: JsonObject): User {
            require(obj.getString("object") == "user") { "Object is not a User" }

            val id = obj.getString("id")!!
            val name = obj.getString("name")
            val avatarUrl = obj.getString("avatar_url")

            return when (val type = UserType.fromJson(obj.getString("type"))) {
                UserType.PERSON -> PersonImpl(
                    id, type, name, avatarUrl,
                    obj.getObject("person").getString("email")
                )
                else -> UserImpl(id, type, name, avatarUrl)
            }
        }
    }
}
