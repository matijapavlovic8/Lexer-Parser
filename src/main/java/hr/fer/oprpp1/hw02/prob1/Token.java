package hr.fer.oprpp1.hw02.prob1;

/**
 * Token is a lexical unit that groups one or more characters from the input text.
 * @author MatijaPav
 */

public class Token {

    /**
     * Type of the token.
     */
    private TokenType type;

    /**
     * Value of the token
     */
    private Object value;

    public Token(TokenType type, Object value){
        if(type == null) throw new NullPointerException("Type can not be null!");
        if(value == null && type != TokenType.EOF) throw new NullPointerException("Value can only be null in case of EOF token type.");
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

    public TokenType getType() {
        return type;
    }
}
