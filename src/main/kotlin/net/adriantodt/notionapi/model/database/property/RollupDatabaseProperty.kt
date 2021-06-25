package net.adriantodt.notionapi.model.database.property

interface RollupDatabaseProperty : DatabaseProperty {
    /**
     * The name of the relation property this property is responsible for rolling up.
     */
    val relationPropertyName: String

    /**
     * The id of the relation property this property is responsible for rolling up.
     */
    val relationPropertyId: String

    /**
     * The name of the property of the pages in the related database that is used as an input to function.
     */
    val rollupPropertyName: String

    /**
     * The id of the property of the pages in the related database that is used as an input to function.
     */
    val rollupPropertyId: String

    /**
     * The function that is evaluated for every page in the relation of the rollup.
     */
    val function: RollupFunction
}
