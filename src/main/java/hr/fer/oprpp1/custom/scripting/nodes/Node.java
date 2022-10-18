package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

/**
 * Base class for all graph nodes.
 *
 * @author MatijaPav
 */

public class Node {

    /**
     * All children of the node.
     */

    private ArrayIndexedCollection children;

    /**
     * Creates an instance of class {@code Node}.
     */
    public Node(){
        this.children = null;
    }

    /**
     * Adds a child node. Also initiates the collection on first call of the method.
     * @param child
     */

    public void addChildNode(Node child){
        if(child == null) throw new NullPointerException("Child node can't be null!");
        if(children == null){
            children = new ArrayIndexedCollection();
        }
        children.add(child);
    }

    /**
     * Returns the number of direct children of a {@code Node}.
     * @return {@code int}
     */
    public int numberOfChildren(){
        return children.size();
    }

    /**
     * Gets the child on the given index.
     * @param index of the child we are trying to get.
     * @return Child {@code Node} on the given {@code index}
     */

    public Node getChild(int index){
        if(this.children == null) throw new NullPointerException("Can't get element if the collection isn't initialized.");
        return (Node)children.get(index);
    }


}
