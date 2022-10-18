package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.hw02.prob1.LexerException;
import hr.fer.oprpp1.hw02.prob1.LexerState;
import hr.fer.oprpp1.hw02.prob1.Token;
import hr.fer.oprpp1.hw02.prob1.TokenType;

/**
 * Implementation of a lexer.
 *
 * @author MatijaPav
 */

public class SmartScriptLexer {

    /**
     * Input text.
     */
    private char[] data;

    /**
     * Current token.
     */
    private SmartScriptToken token;

    /**
     * Index of the first unprocessed character.
     */
    private int currentIndex;
    /**
     * Lexers current state.
     */
    private SmartScriptLexerState state;

    /**
     * Creates an instance of SmartScriptLexer
     * @param input for lexical analysis.
     */

    public SmartScriptLexer(String input){
        if(input == null) throw new NullPointerException("Can't pass null as argument");

        this.data = input.trim().toCharArray();
        this.token = null;
        this.currentIndex = 0;
        this.state = SmartScriptLexerState.TEXT;
    }

    /**
     * Gets the current token.
     * @return {@code SmartScriptToken}
     */

    public SmartScriptToken getToken(){
        if(this.token == null) throw new LexerException("Can't return Token if none was created!");
        return this.token;
    }

    /**
     * Sets the type and value of current token.
     * @param type {@code SmartScriptTokenType}
     * @param value {@code SmartScriptToken} value.
     */

    public void setToken(SmartScriptTokenType type, Object value){
        if(type == null) throw new NullPointerException("Type can't be null.");
        if(type != SmartScriptTokenType.EOF && value == null) throw new NullPointerException("Value can only be null in case of EOF token");
        this.token = new SmartScriptToken(type, value);
    }

    /**
     * Gets the current index of the data array that is being analysed.
     * @return {@code int} index
     */

    public int getCurrentIndex() {
        return currentIndex;
    }

    /**
     * Sets the {@code SmartScriptLexer} to the desired state.
     * @param state {@code SmartScriptLexerState}.
     */

    public void setState(SmartScriptLexerState state) {
        if(state == null) throw new NullPointerException("State can't be null.");
        this.state = state;
    }

    /**
     * Gets the current state of {@code SmartScriptLexer}
     * @return Current {@code SmartScriptLexerState}
     */

    public SmartScriptLexerState getState(){
        return this.state;
    }

    /**
     * Analyses the given input and creates a lexical token.
     * @return {@code SmartScriptToken}
     */

    public SmartScriptToken nextToken(){
        if(this.token != null && getToken().getType() == SmartScriptTokenType.EOF) throw new LexerException("Can't tokenize after EOF is reached!");
        if(checkEOF())
            return processEOF();

        if(getState() == SmartScriptLexerState.TEXT)
            return processText();
        else if(getState() == SmartScriptLexerState.TAG)
            return processTag();
        else
            throw new LexerException("Can't create token.");



    }

    /**
     * Checks if EOF is reached.
     * @return {@code true} if EOF is reached, {@code false} otherwise.
     */

    private boolean checkEOF() {
        return currentIndex >= this.data.length;
    }

    /**
     * Sets the token to EOF token ad returns it.
     * @return {@code SmartScriptToken} of type EOF and value {@code null}.
     */

    private SmartScriptToken processEOF(){
        setToken(SmartScriptTokenType.EOF, null);
        return getToken();
    }

    /**
     * Used to check if char at the current index is a whitespace
     * @return {@code true} if char is a whitespace, {@code false} otherwise.
     */

    private boolean checkWhitespace() {
        char c = this.data[this.currentIndex];
        return (c == ' ' || c == '\r' || c == '\n' || c == '\t');
    }

    /**
     * Skips all whitespaces.
     */

    private void skipWhitespace(){
        while(checkWhitespace())
            this.currentIndex++;
    }

    /**
     * Processes input text when Lexer is in {@code SmartScriptLexerState.TEXT} state.
     * @return {@code SmartScriptToken}
     */
    private SmartScriptToken processText(){
        if(checkTagStart())
    }
}
