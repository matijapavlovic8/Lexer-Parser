package hr.fer.oprpp1.custom.collections;

/**
 * Implementation of a stack-like collection. Uses the Adapter pattern with the ArrayIndexedCollection as the adaptee.
 * @author MatijaPav
 *
 */

public class ObjectStack {
	
	private ArrayIndexedCollection adaptee;
	
	public ObjectStack() {
		adaptee = new ArrayIndexedCollection();
	}
	
	/**
	 * Method that determines if the collection is empty.
	 * @return Returns {@code true} if collection contains no objects and {@code false} otherwise.
	 */
	
	public boolean isEmpty() {
		return adaptee.isEmpty();
	}
	
	/**
	 * Method that determines the size of the collection.
	 * @return Returns the number of elements in the collection.
	 */
	
	public int size() {
		return adaptee.size();
	}
	
	/**
	 * Places an object on top of the stack.
	 * @param value Value that is to be pushed on top of the stack.
	 * @throws {@code NullPointerException}
	 */
	
	public void push(Object value) {
		adaptee.add(value);
	}
	
	public Object pop() {
		if(this.isEmpty()) throw new EmptyStackException("Can't pop an empty stack!");
		
		Object popped = adaptee.get(this.size() - 1);
		adaptee.remove(this.size() - 1);
		return popped;	
	}
	/**
	 * Peeks the top of the stack.
	 * @return returns the object from the top of the stack.
	 */
	
	public Object peek() {
		if(this.isEmpty()) throw new EmptyStackException("Can't peek an empty stack!");
		
		return adaptee.get(this.size() - 1);
	}
	
	/**
	 * Clears the collection.
	 */
	
	public void clear() {
		adaptee.clear();
	}

}
