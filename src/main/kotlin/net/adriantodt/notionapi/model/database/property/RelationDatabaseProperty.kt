package net.adriantodt.notionapi.model.database.property

interface RelationDatabaseProperty : DatabaseProperty {
    /**
     * The database this relation refers to. New linked pages must belong to this database in order to be valid.
     */
    val databaseId: String

    /**
     * By default, relations are formed as two synced properties across databases: if you make a change to one
     * property, it updates the synced property at the same time.
     *
     * This refers to the name of the property in the related database.
     */
    val syncedPropertyName: String

    /**
     * By default, relations are formed as two synced properties across databases: if you make a change to one
     * property, it updates the synced property at the same time.
     *
     * This refers to the id of the property in the related database.
     * This is usually a short string of random letters and symbols.
     */
    val syncedPropertyId: String
}
