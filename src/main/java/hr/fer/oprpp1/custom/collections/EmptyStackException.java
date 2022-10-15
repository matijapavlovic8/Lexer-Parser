package hr.fer.oprpp1.custom.collections;

public class EmptyStackException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates an exception.
	 */
	
	public EmptyStackException() {
		
	}
	
	/**
	 * Creates an exception with detailed description.
	 * @param message Description of the exception. 
	 */
	
	public EmptyStackException(String message) {
		super(message);
	}
}
