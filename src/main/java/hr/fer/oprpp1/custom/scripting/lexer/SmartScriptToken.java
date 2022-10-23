package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.hw02.prob1.TokenType;

/**
 * SmartScriptToken is a lexical unit that groups one or more characters from the input text.
 * @author MatijaPav
 */

public class SmartScriptToken {

    /**
     * Type of the token.
     */
    private SmartScriptTokenType type;


    /**
     * Value of the token
     */
    private Object value;

    public SmartScriptToken(SmartScriptTokenType type, Object value){
        if(type == null) throw new NullPointerException("Type can not be null!");
        if(value == null && type != SmartScriptTokenType.EOF) throw new NullPointerException("Value can only be null in case of EOF token type.");
        this.value = value;
        this.type = type;
    }

    /**
     * Gets the value of current token.
     * @return Returns value of the token.
     */

    public Object getValue() {
        return value;
    }

    /**
     * Gets the type of current token.
     * @return Returns the type of the token.
     */

    public SmartScriptTokenType getType() {
        return this.type;
    }
}
