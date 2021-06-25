package net.adriantodt.notionapi.model.property

import net.adriantodt.notionapi.model.Color

interface SelectOption {
    /**
     * Identifier of the option, which does not change if the name is changed.
     * These are sometimes, but not always, UUIDs.
     */
    val id: String

    /**
     * Name of the option as it appears in Notion.
     */
    val name: String

    /**
     * Color of the option.
     */
    val color: Color
}
