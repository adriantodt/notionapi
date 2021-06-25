package net.adriantodt.notionapi.net.request.properties

import net.adriantodt.notionapi.model.database.property.DatabaseProperty
import net.adriantodt.notionapi.model.property.PropertyType
import java.net.URI
import java.net.URL

operator fun PropertiesBuilder.set(property: DatabaseProperty, value: URI) {
    set(property.id, property.type, value)
}

operator fun PropertiesBuilder.set(property: String, value: URI) {
    url(property, value)
}

operator fun PropertiesBuilder.set(property: String, type: PropertyType, value: URI) {
    require(type == PropertyType.URL) { "Value only allowed for URL properties" }
    url(property, value)
}

operator fun PropertiesBuilder.set(property: DatabaseProperty, value: URL) {
    set(property.id, property.type, value)
}

operator fun PropertiesBuilder.set(property: String, value: URL) {
    url(property, value)
}

operator fun PropertiesBuilder.set(property: String, type: PropertyType, value: URL) {
    require(type == PropertyType.URL) { "Value only allowed for URL properties" }
    url(property, value)
}
