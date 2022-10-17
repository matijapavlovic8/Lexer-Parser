package hr.fer.oprpp1.custom.collections;

public interface List extends Collection{

    /**
     * Gets the element at the specified index of the list.
     * @param index indicates the index of the desired element.
     * @return Object
     */

    Object get(int index);

    /**
     * Inserts the {@code Object} in the list.
     * @param value value of the object that is to be inserted.
     * @param position position on which the element will be inserted.
     */

    void insert(Object value, int position);

    /**
     * Returns the index of the first appearance of the given value in the list.
     * @param value Value of the Object whose index is being requested.
     * @return int
     */
    int indexOf(Object value);

    /**
     * Removes the object on the given index of the list.
     * @param index Index of the object that will be removed.
     */
    void remove(int index);

}
