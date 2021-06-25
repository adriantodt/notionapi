package net.adriantodt.notionapi.model.database.property

interface FormulaDatabaseProperty : DatabaseProperty {
    /**
     * Formula to evaluate for this property.
     */
    val expression: String
}
