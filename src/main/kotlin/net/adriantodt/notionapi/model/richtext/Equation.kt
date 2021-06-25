package net.adriantodt.notionapi.model.richtext

interface Equation : RichText {
    /**
     * The LaTeX string representing this inline equation.
     */
    val expression: String
}
