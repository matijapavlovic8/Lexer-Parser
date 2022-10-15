package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.EmptyStackException;
import hr.fer.oprpp1.custom.collections.ObjectStack;

/**
 * Demonstration of usage of the Object stack implementation.
 * @author MatijaPav
 *
 */


public class StackDemo {
	
	public static void main(String[] args) {
		
		ObjectStack stack = new ObjectStack();
		
		if(args.length != 1) throw new IllegalArgumentException();
		
		String[] splits = args[0].split("\\s+");
		
		for(String str : splits) {
			try {
				Integer.parseInt(str);
				stack.push(str);
			} catch(NumberFormatException nfe) {
				try {
					int operand2 = Integer.parseInt(stack.pop().toString());
					int operand1 = Integer.parseInt(stack.pop().toString());
					
					if(str.equals("+")) {
						stack.push(operand1 + operand2);
					
					} else if(str.equals("-")) {
						stack.push(operand1 - operand2);
					
					} else if(str.equals("*")) {
						stack.push(operand1 * operand2);
					
					} else if(str.equals("/")) {
						if(operand2 == 0) {
							throw new IllegalArgumentException("Can't divide by zero!");
						} 
						stack.push(operand1 / operand2);
						
					} else if(str.equals("%")) {
						if(operand2 == 0) {
							throw new IllegalArgumentException("Can't divide by zero!");
						} 
						stack.push(operand1 % operand2);
					
					} else {
						throw new IllegalArgumentException("Illegal operator!");
					}
						

				} catch(EmptyStackException ese) {
					throw new EmptyStackException("Not enough arguments!");
				}
			}
		}
		if(stack.size() == 1) {
			System.out.println("The final result is: " + stack.pop());
		} else {
			System.err.println("Error! Too many operands.");
		}

	}

}
