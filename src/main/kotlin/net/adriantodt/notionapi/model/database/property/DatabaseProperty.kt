package net.adriantodt.notionapi.model.database.property

import net.adriantodt.notionapi.model.property.PropertyType

interface DatabaseProperty {
    /**
     * The ID of the property, usually a short string of random letters and symbols.
     * Some automatically generated property types have special human-readable IDs.
     * For example, all Title properties have an ID of "title".
     */
    val id: String

    val name: String

    val type: PropertyType
}
