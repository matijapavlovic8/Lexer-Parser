package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;

/**
 * A node representing a command which generates some textual output dynamically.
 *
 * @author MatijaPav
 */

public class EchoNode extends Node{
    private Element[] elements;

    public EchoNode(Element[] elements){
        if(elements == null)
            throw new NullPointerException("Can't create an EchoNode with null as argument!");
        this.elements = elements;
    }

    public Element[] getElements(){
        return this.elements;
    }

    @Override

    public String toString(){
        String res = "{$= ";

        for(Element el : getElements()) {
            res += el.asText();
            res += " ";
        }

        res += "$}";

        return res;

    }

}
