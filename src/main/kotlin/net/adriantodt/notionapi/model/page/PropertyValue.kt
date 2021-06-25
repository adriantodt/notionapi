package net.adriantodt.notionapi.model.page

import net.adriantodt.notionapi.model.property.Value

interface PropertyValue {
    /**
     * Underlying identifier for the property.
     * This identifier is guaranteed to remain constant when the property name changes.
     * It may be a UUID, but is often a short random string.
     */
    val id: String

    val name: String

    val value: Value
}

