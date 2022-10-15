package hr.fer.oprpp1.custom.collections;

/**
 * {@code ElementGetter} iterates through a collection.
 * @author MatijaPav
 */

public interface ElementsGetter {

    /**
     * Determines if a collection has unfetched elements.
     * @return Returns {@code true} if collection has unfetched elements and {@code false} otherwise.
     */

    boolean hasNextElement();

    /**
     * Fetches the next element from a collection.
     * @return Returns the next element.
     */

    Object getNextElement();

    /**
     * Processes remaining elements from a collection.
     * @param p Processor
     */
    default void processRemaining(Processor p){
        if(p == null) throw new NullPointerException("Argument can't be null!");
        while(this.hasNextElement()){
            p.process(this.getNextElement());
        }
    }


}
