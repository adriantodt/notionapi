package net.adriantodt.notionapi.net.request.properties

import net.adriantodt.notionapi.model.database.property.DatabaseProperty
import net.adriantodt.notionapi.model.property.PropertyType
import net.adriantodt.notionapi.utils.Range
import java.time.LocalDate
import java.time.OffsetDateTime

operator fun PropertiesBuilder.set(property: DatabaseProperty, value: LocalDate) {
    set(property.id, property.type, value)
}

operator fun PropertiesBuilder.set(property: String, value: LocalDate) {
    date(property, value)
}

operator fun PropertiesBuilder.set(property: String, type: PropertyType, value: LocalDate) {
    require(type == PropertyType.DATE) { "Value only allowed for DATE properties" }
    date(property, value)
}

@JvmName("setDate")
operator fun PropertiesBuilder.set(property: DatabaseProperty, value: Range<LocalDate>) {
    set(property.id, property.type, value)
}

@JvmName("setDate")
operator fun PropertiesBuilder.set(property: String, value: Range<LocalDate>) {
    date(property, value)
}

@JvmName("setDate")
operator fun PropertiesBuilder.set(property: String, type: PropertyType, value: Range<LocalDate>) {
    require(type == PropertyType.DATE) { "Value only allowed for DATE properties" }
    date(property, value)
}

operator fun PropertiesBuilder.set(property: DatabaseProperty, value: OffsetDateTime) {
    set(property.id, property.type, value)
}

operator fun PropertiesBuilder.set(property: String, value: OffsetDateTime) {
    date(property, value)
}

operator fun PropertiesBuilder.set(property: String, type: PropertyType, value: OffsetDateTime) {
    require(type == PropertyType.DATE) { "Value only allowed for DATE properties" }
    date(property, value)
}

@JvmName("setDateTime")
operator fun PropertiesBuilder.set(property: DatabaseProperty, value: Range<OffsetDateTime>) {
    set(property.id, property.type, value)
}

@JvmName("setDateTime")
operator fun PropertiesBuilder.set(property: String, value: Range<OffsetDateTime>) {
    date(property, value)
}

@JvmName("setDateTime")
operator fun PropertiesBuilder.set(property: String, type: PropertyType, value: Range<OffsetDateTime>) {
    require(type == PropertyType.DATE) { "Value only allowed for DATE properties" }
    date(property, value)
}

@JvmName("setDateString")
operator fun PropertiesBuilder.set(property: DatabaseProperty, value: Range<String>) {
    set(property.id, property.type, value)
}

@JvmName("setDateString")
operator fun PropertiesBuilder.set(property: String, value: Range<String>) {
    date(property, value)
}

@JvmName("setDateString")
operator fun PropertiesBuilder.set(property: String, type: PropertyType, value: Range<String>) {
    require(type == PropertyType.DATE) { "Value only allowed for DATE properties" }
    date(property, value)
}
