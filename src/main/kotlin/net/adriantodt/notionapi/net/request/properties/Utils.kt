package net.adriantodt.notionapi.net.request.properties

import net.adriantodt.notionapi.model.database.property.DatabaseProperty
import net.adriantodt.notionapi.model.property.PropertyType

operator fun PropertiesBuilder.set(property: DatabaseProperty, value: String) {
    set(property.id, property.type, value)
}

operator fun PropertiesBuilder.set(property: String, type: PropertyType, value: String) {
    when (type) {
        PropertyType.TITLE -> title(property, value)
        PropertyType.RICH_TEXT -> richText(property, value)
        PropertyType.NUMBER -> number(property, value)
        PropertyType.SELECT -> select(property, value)
        PropertyType.MULTI_SELECT -> multiSelect(property, value)
        PropertyType.DATE -> date(property, value)
        PropertyType.PEOPLE -> people(property, value)
        PropertyType.CHECKBOX -> checkbox(property, value)
        PropertyType.URL -> url(property, value)
        PropertyType.EMAIL -> email(property, value)
        PropertyType.PHONE_NUMBER -> phoneNumber(property, value)
        PropertyType.RELATION -> relation(property, value)
        else -> throw IllegalArgumentException("Unsupported property type $type")
    }
}

operator fun PropertiesBuilder.set(property: DatabaseProperty, value: List<String>) {
    set(property.id, property.type, value)
}

operator fun PropertiesBuilder.set(property: String, type: PropertyType, value: List<String>) {
    when (type) {
        PropertyType.MULTI_SELECT -> multiSelect(property, value)
        PropertyType.PEOPLE -> people(property, value)
        PropertyType.RELATION -> relation(property, value)

        PropertyType.DATE -> when (value.size) {
            1 -> date(property, value.single())
            2 -> date(property, value[0], value[1])
        }

        PropertyType.TITLE -> {
            require(value.size == 1) { "Property type $type accepts only a single String" }
            title(property, value.single())
        }
        PropertyType.RICH_TEXT -> {
            require(value.size == 1) { "Property type $type accepts only a single String" }
            richText(property, value.single())
        }
        PropertyType.NUMBER -> {
            require(value.size == 1) { "Property type $type accepts only a single String" }
            number(property, value.single())
        }
        PropertyType.SELECT -> {
            require(value.size == 1) { "Property type $type accepts only a single String" }
            select(property, value.single())
        }
        PropertyType.CHECKBOX -> {
            require(value.size == 1) { "Property type $type accepts only a single String" }
            checkbox(property, value.single())
        }
        PropertyType.URL -> {
            require(value.size == 1) { "Property type $type accepts only a single String" }
            url(property, value.single())
        }
        PropertyType.EMAIL -> {
            require(value.size == 1) { "Property type $type accepts only a single String" }
            email(property, value.single())
        }
        PropertyType.PHONE_NUMBER -> {
            require(value.size == 1) { "Property type $type accepts only a single String" }
            phoneNumber(property, value.single())
        }

        else -> throw IllegalArgumentException("Unsupported property type $type")
    }
}
