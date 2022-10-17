package hr.fer.oprpp1.custom.collections;

/**
 * Class Collection represents some general collection of objects
 * @author MatijaPavlovic
 *
 */

public interface Collection {
	
	/**
	 * 
	 * @return Returns {@code true} if collection contains no objects and {@code false} otherwise.
	 */
	
	default boolean isEmpty() {
		return this.size() == 0;
	}
	
	/**
	 * 
	 * @return Returns the number of currently stored objects in this collection.
	 */
	
	int size();
	
	/**
	 * Adds the given object into this collection.
	 */
	
	void add(Object value) ;
	
	/**
	 * @return Returns {@code true} only if the collection contains given value, otherwise returns {@code false}
	 */
	
	boolean contains(Object value);
	
	/**
	 * @return Returns {@code true} only if the collection contains given value as determined by equals method and removes
	 * one occurrence of it
	 */
	
    boolean remove(Object value);
	
	/**
	 * @return Allocates new array with size equals to the size of this collection, fills it with collection content and
	 * returns the array.
	 */
	
	public Object[] toArray();
	
	/**
	 * Method calls processor.process() for each element of this collection. The order in which elements
	 * will be sent is undefined in this class.
	 * @param processor instance of a class {@code Processor} that processes all elements of the collection.
	 */
	
	default void forEach(Processor processor) {
        ElementsGetter getter = this.createElementsGetter();
        while(getter.hasNextElement()){
            processor.process(getter.getNextElement());
        }
    }
	
	/**
	 * 
	 * @param other is an instance of a {@code Collection} whose elements will be added to given collection.
	 */
	
	default void addAll(Collection other) {
		
		/*
		 * Local class that extends the Processor class. Overrides the method {@code process} and then uses it as an
		 * argument in the {@code forEach} method.
		 * @author MatijaPav
		 *
		 */
		
		class addEachItemProcessor implements Processor {
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		
		Processor processor = new addEachItemProcessor();
		other.forEach(processor);
		
	}
	
	/**
	 * Removes all elements from this collection.
	 */
	
	void clear() ;

    /**
     * Creates an element getter.
     */

    ElementsGetter createElementsGetter();

    default void addAllSatisfying(Collection col, Tester tester){
        ElementsGetter getter = col.createElementsGetter();
        while(getter.hasNextElement()){
            Object next = getter.getNextElement();
            if(tester.test(next))
                this.add(next);
        }
    }
}
