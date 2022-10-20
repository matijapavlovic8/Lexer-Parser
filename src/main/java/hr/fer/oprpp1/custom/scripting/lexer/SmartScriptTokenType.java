package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Enumerates all possible types of tokens that {@code SmartScriptLexer} can generate.
 */

public enum SmartScriptTokenType {
    EOF, TAG, OPERATOR, TEXT, FUNCTION, INTEGER, DOUBLE, TAG_TEXT, VARIABLE
}
