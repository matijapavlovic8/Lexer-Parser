package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Resizable array-backed collection of objects.
 * Duplicate elements are allowed.
 * Storage of {@code null} values is not.
 * @author MatijaPav
 *
 */

public class ArrayIndexedCollection implements List {
	
	
	/**
	 * Private variable that indicates the number of elements currently in collection.
	 */
	private int size;
	
	/**
	 * An array of object references which length determines its current capacity.
	 */
	
	private Object[] elements;
	
	/**
	 * Default size of an array used in the default constructor.
	 */
	
	private static final int DEFAULT_SIZE = 16;

    private long modificationCount;
	
	
	/**
	 * Default constructor. Creates an object array of size 16.
	 */
	
	public ArrayIndexedCollection() {
		this(DEFAULT_SIZE);
	}
	
	/**
	 *
	 * @param initialCapacity sets the initial size of the collection
	 */
	
	public ArrayIndexedCollection(int initialCapacity) {
		if(initialCapacity < 1) throw new IllegalArgumentException("Illegal argument");
		this.size = 0;
		this.elements = new Object[initialCapacity];
        this.modificationCount = 0;
		
	}
	/**
	 * 
	 * @param other A non-null reference to some other Collection which elements are copied into this newly
	 * constructed collection.
	 * @param initialCapacity sets the initial size of the collection. If the initial capacity is less than the size
	 * of the referenced collection, the size of the referenced collection is used.
	 */
	
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		if(other == null) throw new NullPointerException("Null value can't be used as a parameter!");
		if(initialCapacity < 1) throw new IllegalArgumentException("Initial capacity must be equal or greater than 1!");
		
		if(initialCapacity < other.size()) {
			this.elements = new Object[other.size()];
		} else {
			this.elements = new Object[initialCapacity];
		}
		
		this.addAll(other);
	}
	
	/**
	 * 
	 * @param other Collection that is to be added to the newly created collection.
	 */
	
	public ArrayIndexedCollection(Collection other) {
		this(other, DEFAULT_SIZE);
	}
	
	@Override
	
	
	public int size() {
		return this.size;
	}
	
	/**
	 * Returns the index of the given value.
	 * @param value Value whose index is requested.
	 * @return Returns -1 if the collection does not contain the given value, otherwise returns the index of the value.
	 */
	
	public int indexOf(Object value) {
		
		int index = -1;
		
		if(value == null) return index;
		if(!this.contains(value)) return index;
		
		for(int i = 0; i < this.size(); i++) {
			if(this.elements[i].equals(value))
				index = i;
		}
		
		return index;
		
	}
	
	@Override
	
	public Object[] toArray() {
		Object[] arr = new Object[this.size];
		for(int i = 0; i < this.size; i++) {
			arr[i] = this.elements[i];
		}
		
		return arr;
	}
	
	@Override
	
	public boolean contains(Object value) {
		for(int i = 0; i < this.size(); i++) {
			if(this.elements[i].equals(value))
				return true;
		}
		return false;
	}

	
	/**
	 * Removes the element from the collection found on the given index.
	 * @param index Index of the element that is to be removed.
	 */
	
	public void remove(int index) {
		if(index < 0 || index >= this.size) throw new IndexOutOfBoundsException("Index out of bounds!");

		for(int i = index; i < this.size - 1; i++) 
			this.elements[i] = this.elements[i + 1];
		
		this.elements[this.size--] = null;
        this.modificationCount++;
	}
	
	@SuppressWarnings("unused")
	@Override
	
	public boolean remove(Object value) {
		for(int i = 0; i < this.size(); i++) {
			if(this.elements[i].equals(value))
				this.remove(i);
			return true;
		}
		return false;
	}
	
	
	@Override
	
	public void add(Object value) {
		if(value == null) throw new NullPointerException("Can not add null to a collection!");
		
		if(this.size >= this.elements.length) 
			this.elements = new ArrayIndexedCollection(this, this.elements.length * 2).elements;

		this.elements[this.size++] = value;
        this.modificationCount++;
	}
	
	/**
	 * Inserts (does not overwrite) the given value at the given position in array (observe that before actual
	 * insertion elements at position and at greater positions must be shifted one place toward the end, so that an
     * empty place is created at position). The legal positions are 0 to size (both are included).
	 * @param value Value that is to be inserted in to the collection.
	 * @param position Position on which the element will be inserted.
	 */
	
	public void insert(Object value, int position) {
		if(position < 0 || position > this.size) throw new IndexOutOfBoundsException("Invalid position!");
		if(value == null) throw new NullPointerException("Can't add null.");
		if(this.size >= this.elements.length) 
			this.elements = new ArrayIndexedCollection(this, this.elements.length * 2).elements;
		
		for(int i = this.size; i > position; i--) {
			this.elements[i] = this.elements[i - 1];
		}
		this.elements[position] = value;
        this.modificationCount++;
		this.size++;
	}
		
	
	/**
	 * Gets the element of the collection found on the given index.
	 * @param index of the item that is to be fetched.
	 * @return object on the given position in the collection.
	 */
	
	public Object get(int index) {
		if(index < 0 || index >= size) throw new IllegalArgumentException("Index must be greater than 0 and less than the size - 1.");
		return this.elements[index];
	}
		
	@Override
	
	public void clear() {
		for(int i = 0; i < this.size; i++) {
			this.elements[i] = null;
		}
		this.size = 0;
        this.modificationCount++;
	}

    private static class ArrayCollectionElementsGetter implements ElementsGetter {
        private final ArrayIndexedCollection coll;
        private int nextIndex;
        private long savedModificationCount;

        private ArrayCollectionElementsGetter(ArrayIndexedCollection coll){
            if(coll == null) throw new NullPointerException("Can't pass null as argument!");
            this.coll = coll;
            this.nextIndex = 0;
            this.savedModificationCount = coll.modificationCount;
        }

        @Override
        public boolean hasNextElement() {
            if(savedModificationCount != coll.modificationCount) throw new ConcurrentModificationException("The collection was altered!");
            return nextIndex < coll.size();
        }

        @Override
        public Object getNextElement() {
            if(savedModificationCount != coll.modificationCount) throw new ConcurrentModificationException("The collection was altered!");
            if(!this.hasNextElement()) throw new NoSuchElementException("No more elements in this list!");
            return coll.get(nextIndex++);
        }




    }

    public ElementsGetter createElementsGetter() {
        return new ArrayCollectionElementsGetter(this);
    }
}
