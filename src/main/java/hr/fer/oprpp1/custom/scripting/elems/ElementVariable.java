package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Inherits {@code Element} and has a single read-only {@code String} variable.
 *
 * @author MatijaPav
 */


public class ElementVariable extends Element{
    /**
     * Private read-only variable.
     */
    private String name;

    public ElementVariable(String name){
        this.name = name;
    }

    /**
     * Getter for the variable name.
     * @return
     */
    public String getName() {
        return name;
    }
    @Override
    public String asText(){
        return name;
    }
}
