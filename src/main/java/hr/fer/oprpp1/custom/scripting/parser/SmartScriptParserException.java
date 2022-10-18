package hr.fer.oprpp1.custom.scripting.parser;

/**
 * Exception that occurs when {@code SmartScriptParser} runs in to an exception.
 */

public class SmartScriptParserException extends RuntimeException{

    /**
     * Generic instance of {@code SmartScriptParserException}.
     */
    public SmartScriptParserException(){
        super();
    }

    /**
     * Instance of {@code SmartScriptParserException} with a detailed description of the exception.
     * @param message detailed description of the exception.
     */

    public SmartScriptParserException(String message){
        super(message);
    }


}
