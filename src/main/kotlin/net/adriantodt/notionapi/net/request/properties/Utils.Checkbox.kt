package net.adriantodt.notionapi.net.request.properties

import net.adriantodt.notionapi.model.database.property.DatabaseProperty
import net.adriantodt.notionapi.model.property.PropertyType

operator fun PropertiesBuilder.set(property: DatabaseProperty, value: Boolean) {
    set(property.id, property.type, value)
}

operator fun PropertiesBuilder.set(property: String, value: Boolean) {
    checkbox(property, value)
}

operator fun PropertiesBuilder.set(property: String, type: PropertyType, value: Boolean) {
    require(type == PropertyType.CHECKBOX) { "Value only allowed for Checkbox properties" }
    checkbox(property, value)
}
