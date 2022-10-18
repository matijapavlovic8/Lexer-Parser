package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * A node representing a piece of textual data.
 *
 * @author MatijaPav
 */

public class TextNode extends Node{

    /**
     * Read-only variable of TextNode.
     */
    private String text;

    /**
     * Creates an instance of class {@code TextNode}.
     * @param text
     */

    public TextNode(String text){
        this.text = text;
    }

    /**
     * Getter for the text variable.
     * @return {@code String}
     */

    public String getText(){
        return this.text;
    }
}
