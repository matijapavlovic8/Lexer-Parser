package hr.fer.oprpp1.hw02.prob1;

/**
 * Exception raised when an exception occurs while tokenizing.
 *
 * @author MatijaPav
 */

public class LexerException extends RuntimeException{

    /**
     * Throws the exception without detailed message.
     */
    public LexerException(){
        super();
    }

    /**
     * Throws the exception with detailed description.
     * @param message Detailed description of the cause of exception.
     */

    public LexerException(String message){
        super(message);
    }
}
