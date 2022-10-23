package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.hw02.prob1.LexerException;

/**
 * Implementation of a lexer.
 *
 * @author MatijaPav
 */

public class SmartScriptLexer {

    /**
     * Input text.
     */
    private final char[] data;

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
            return processTextState();
        else if(getState() == SmartScriptLexerState.TAG)
            return processTagState();
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
    private SmartScriptToken processTextState(){
        if(checkTagStart()) return processTagStart();
        return processText();
    }

    /**
     * Used to check if starting tag is reached.
     * @return True if tag is reached, false otherwise.
     */
    private boolean checkTagStart(){
        if(currentIndex + 1 >= this.data.length) return false;
        String tag = "{$";
        String checked = "" + this.data[currentIndex] + this.data[currentIndex + 1];
        if(tag.equals(checked)){
            if(this.currentIndex > 0 && this.data[currentIndex - 1] == '\\') return false;
            setState(SmartScriptLexerState.TAG);
            currentIndex += 2;
            return true;
        }
        return false;
    }
    /**
     * Used to check if end tag is reached.
     * @return True if tag is reached, false otherwise.
     */
    private boolean checkTagEnd(){
        String tag = "$}";
        if(currentIndex + 1 >= this.data.length) return false;
        String checked = "" + this.data[currentIndex] + this.data[currentIndex + 1];
        if(tag.equals(checked)){
            currentIndex += 2;
            setState(SmartScriptLexerState.TEXT);
            return true;
        }
        return false;
    }

    /**
     * Checks if char at the current index is a letter.
     * @return {@code true} if char is a letter, {@code false} otherwise.
     */

    private boolean checkLetter(){
        return Character.isLetter(this.data[currentIndex]);
    }

    /**
     * Checks if char at the current index is a digit.
     * @return {@code true} if char is a digit, {@code false} otherwise.
     */

    private boolean checkDigit(){
        return Character.isDigit(this.data[currentIndex]);
    }

    /**
     * Checks if chara at the current index is a valid operator.
     * @return {@code true} if char is an operator, {@code false} otherwise.
     */

    private boolean checkOperator(){
        char c = this.data[currentIndex];
        return (c == '+' || c == '-' || c == '*' || c == '/' || c == '^');
    }


    /**
     * Creates a Start tag token
     * @return {@code SmartScriptToken}
     */
    private SmartScriptToken processTagStart(){
        setToken(SmartScriptTokenType.TAG, "{$");
        return this.token;
    }

    /**
     * Creates an End tag token
     * @return {@code SmartScriptToken}
     */
    private SmartScriptToken processTagEnd(){
        setToken(SmartScriptTokenType.TAG, "$}");
        return this.token;
    }

    /**
     * Processes strings in text state.
     * @return {@code SmartScriptToken} of type {@code SmartScriptTokenType.TEXT}
     */

    private SmartScriptToken processText(){
        String value = "";
        while(this.currentIndex < this.data.length && !checkTagStart()){
            if(this.data[currentIndex] == '{') throw new LexerException("{ must be escaped." + this.data[currentIndex + 1]);
            if(this.data[currentIndex] == '\\'){
                if(this.currentIndex + 1 >= this.data.length || (this.data[currentIndex + 1] != '\\' && this.data[currentIndex + 1] != '{'))
                    throw new LexerException("Wrong use of escaping mechanism");
                value += "\\";
                value += this.data[++currentIndex];
                currentIndex++;
            } else
                value += this.data[currentIndex++];
        }
        setToken(SmartScriptTokenType.TEXT, value);

        return this.token;
    }

    /**
     * Processes strings when Lexer is in tag state.
     * @return {@code SmartScriptToken}
     */

    private SmartScriptToken processTagState(){
        if(checkWhitespace()) skipWhitespace();
        if(checkTagEnd()) return processTagEnd();
        return processTag();


    }

    /**
     * Processes strings when Lexer is in tag state.
     * @return {@code SmartScriptToken}
     */

    private SmartScriptToken processTag(){
        if(checkWhitespace())
            skipWhitespace();
        if(this.data[currentIndex] == '=') return processEqual();
        if(checkLetter()) return processVariable();
        if(checkDigit()) return processNumber();
        if(this.data[currentIndex] == '@') return processFunction();
        if(this.data[currentIndex] == '-' && this.currentIndex < this.data.length && Character.isDigit(this.data[currentIndex + 1]))
            return processNegative();
        if(checkOperator()) return processOperator();
        if(this.data[currentIndex] == '"') return processStringInTag();
        throw new LexerException("Can't create token." + this.data[currentIndex]);


    }

    /**
     * Processes variables after "equal" sign.
     * @return {@code SmartScriptToken}
     */

    private SmartScriptToken processEqual(){
        setToken(SmartScriptTokenType.VARIABLE, '=');
        currentIndex++;
        return this.token;
    }

    /**
     * Processes variables.
     * @return {@code SmartScriptToken}
     */

    private SmartScriptToken processVariable(){
        String variable = "";
        variable += this.data[currentIndex++];
        while(checkLetter() || checkDigit() || this.data[currentIndex] == '_')
            variable += this.data[currentIndex++];

        setToken(SmartScriptTokenType.VARIABLE, variable);
        return this.token;
    }

    /**
     * Processes functions.
     * @return {@code SmartScriptToken}
     */
    private SmartScriptToken processFunction(){
        String name = "@";
        currentIndex++;
        if(!checkLetter()) throw new LexerException("Invalid function name!");
        name += this.data[currentIndex];
        currentIndex++;
        while(checkLetter() || checkDigit() || this.data[currentIndex] == '_'){
            name += this.data[currentIndex++];
        }

        setToken(SmartScriptTokenType.FUNCTION, name);
        return this.token;


    }

    /**
     * Processes operators.
     * @return {@code SmartScriptToken}
     */

    private SmartScriptToken processOperator(){
        setToken(SmartScriptTokenType.OPERATOR, this.data[currentIndex++]);
        return this.token;
    }

    /**
     * Processes Doubles and Integers.
     * @return {@code SmartScriptToken}
     */

    private SmartScriptToken processNumber(){
        boolean decimal = false;
        String num = "";
        while(checkDigit() || (!decimal && this.data[currentIndex] == '.')){
            if(this.data[currentIndex] == '.')
                decimal = true;
            num += this.data[currentIndex++];
        }
        if(decimal){
            try{
                double value = Double.parseDouble(num);
                setToken(SmartScriptTokenType.DOUBLE, value);
                return this.token;
            } catch (NumberFormatException e) {
                throw new NumberFormatException(e.getMessage());
            }
        } else {
            try {
                int value = Integer.parseInt(num);
                setToken(SmartScriptTokenType.INTEGER, value);
                return this.token;
            } catch (NumberFormatException e){
                throw new NumberFormatException(e.getMessage());
            }
        }

    }

    /**
     * Processes negative numbers, uses method {@code processNumber()}
     * @return {@code SmartScriptToken}
     */

    private SmartScriptToken processNegative(){
        currentIndex++;
        SmartScriptToken pos = processNumber();
        if(pos.getType() == SmartScriptTokenType.DOUBLE){
            setToken(SmartScriptTokenType.DOUBLE, -1 * (Double)pos.getValue());
        } else {
            setToken(SmartScriptTokenType.INTEGER, -1 * (Integer) pos.getValue());
        }
        return this.token;
    }

    /**
     * Processes strings in tags.
     * @return {@code SmartScriptToken}
     */

    private SmartScriptToken processStringInTag(){
        currentIndex++;

        String value = "";

        while(this.currentIndex < this.data.length && this.data[currentIndex] != '"'){
            if(this.currentIndex + 1 < this.data.length && this.data[currentIndex] == '\\'){
                currentIndex++;
                if(this.data[currentIndex] == 'r') {
                    value += "\r";
                    currentIndex++;
                }
                else if(this.data[currentIndex] == 'n'){
                    value += "\n";
                    currentIndex++;
                }
                else if(this.data[currentIndex] == 't'){
                    value += "\t";
                    currentIndex++;
                }
                else if(this.data[currentIndex] == '"'){
                    value += '"';
                    currentIndex++;
                }
                else if(this.data[currentIndex] == '\\'){
                    value += "\\";
                    currentIndex++;
                } else {
                    throw new LexerException("Invalid use of escaping mechanism.");
                }
            } else
                value += this.data[currentIndex++];
        }
        if(this.data[currentIndex] == '"')
            currentIndex++;
        setToken(SmartScriptTokenType.TAG_TEXT, value);
        return this.token;
    }

}
