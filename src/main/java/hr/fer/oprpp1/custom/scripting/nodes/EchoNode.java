package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;

/**
 * A node representing a command which generates some textual output dynamically.
 *
 * @author MatijaPav
 */

public class EchoNode {
    private Element[] elements;

    public EchoNode(Element[] elements){
        this.elements = elements;
    }

    public Element[] getElements(){
        return this.elements;
    }


}
