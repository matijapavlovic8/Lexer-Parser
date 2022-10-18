package hr.fer.oprpp1.hw02.prob1;

/**
 * Implementation of a lexer.
 *
 * @author MatijaPav
 */

public class Lexer {
    /**
     * Input text.
     */
    private char[] data;

    /**
     * Current token.
     */
    private Token token;

    /**
     * Index of the first unprocessed character.
     */
    private int currentIndex;
    /**
     * Lexers current state.
     */
    private LexerState state;

    public Lexer(String text){
        if(text == null) throw new NullPointerException("Argument can't be null!");
        this.data = text.trim().toCharArray();
        this.token = null;
        this.currentIndex = 0;
        this.state = LexerState.BASIC;
    }

    /**
     * Gets the index of first unprocessed character.
     * @return {@code int}
     */

    public int getCurrentIndex() {
        return currentIndex;
    }

    public Token getToken() {
        if(this.token == null) throw new LexerException("Can't return Token if none was created!");
        return this.token;
    }

    public void setState(LexerState state){
        if(state == null)
            throw new NullPointerException();
        this.state = state;
    }

    public Token nextToken(){
        if(this.token != null && getToken().getType() == TokenType.EOF) throw new LexerException("Can't tokenize after EOF is reached!");
        if(checkEOF())
            return processEOF();

        if(checkWhitespace()) skipWhitespace();

        if(checkEOF())
            return processEOF();

        if(this.state == LexerState.EXTENDED)
            return processExtended();

        if(checkLetter()) return processWord();

        if(checkDigit()) return processNumber();

        if(checkEscape()) return processWord();

        if(checkSymbol()) return processSymbol();

        throw new LexerException("Can't create token!");
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
     * Checks if EOF is reached.
     * @return {@code true} if EOF is reached, {@code false} otherwise.
     */
    private boolean checkEOF() {
        return currentIndex >= this.data.length;
    }

    /**
     * Sets the token to EOF token ad returns it.
     * @return Token of type EOF and value {@code null}.
     */
    private Token processEOF(){
        this.token = new Token(TokenType.EOF, null);
        return this.token;
    }

    /**
     * Checks if char at the current index is a letter.
     * @return {@code true} if char is a letter, {@code false} otherwise.
     */

    private boolean checkLetter(){
       return Character.isLetter(this.data[currentIndex]);
    }

    /**
     * Checks if chara at the current index is a digit.
     * @return {@code true} if char is a digit, {@code false} otherwise.
     */

    private boolean checkDigit(){
        return Character.isDigit(this.data[currentIndex]);
    }

    /**
     * Checks if char at the current index is a symbol.
     * @return {@code true} if char is a symbol, {@code false} otherwise.
     */

    private boolean checkSymbol(){
        return !checkDigit() && !checkLetter() && !checkEscape();
    }

    /**
     * Checks if char at the current index is an escape symbol.
     * @return {@code true} if char is an escape symbol, {@code false} otherwise.
     */
    private boolean checkEscape(){
        return this.data[currentIndex] == '\\';
    }

    /**
     * Processes strings of letters.
     * @return {@code Token} of type WORD.
     */

    private Token processWord(){
        String word = "";
        while(currentIndex < this.data.length && (checkLetter() || checkEscape())) {
            if (checkEscape()) {
                currentIndex++;
                if (checkEOF() || checkLetter() || checkWhitespace())
                    throw new LexerException("Invalid use of escape mechanism.");
            }
            word += this.data[currentIndex];
            currentIndex++;
        }
        this.token = new Token(TokenType.WORD, word);
        return this.token;
    }
    /**
     * Processes strings of digits.
     * @return {@code Token} of type NUMBER.
     */
    private Token processNumber(){
        String num = "";
        while(currentIndex < this.data.length && checkDigit()){
            num += this.data[currentIndex];
            currentIndex++;
        }
        try {
            long number = Long.parseLong(num);
            this.token = new Token(TokenType.NUMBER, number);
            return this.token;
        } catch (NumberFormatException e){
            throw new LexerException(e.getMessage());
        }
    }

    /**
     * Creates a {@code Token} of a type SYMBOL.
     * @return {@code Token}
     */

    private Token processSymbol(){
        this.token = new Token(TokenType.SYMBOL, this.data[currentIndex++]);
        return this.token;
    }

    private Token processExtended(){
        String word = "";
        while(this.currentIndex < this.data.length && this.data[currentIndex] != '#' && !checkWhitespace()){
            word += this.data[currentIndex++];
        }
        if(word.length() == 0){
            if(this.currentIndex >= this.data.length || checkWhitespace())
                return processEOF();
            if(this.data[currentIndex] == '#') {
                setState(LexerState.BASIC);
                return processSymbol();
            }
            throw new LexerException();
        }
        if(this.data[currentIndex] == '#')
            setState(LexerState.BASIC);
        return new Token(TokenType.WORD, word);

    }


}
