package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Inherits {@code Element} and has a single read-only {@code String} variable.
 *
 * @author MatijaPav
 */

public class ElementFunction extends Element{

    /**
     * Read-only variable. Represents the value of {@code ElementConstantInteger}.
     */
    private String name;

    public ElementFunction(String name){
        this.name = name;
    }

    /**
     * Getter for read-only variable value.
     * @return {@code int} value of {@code ElementConstantInteger}.
     */
    public String getValue() {
        return name;
    }

    @Override
    public String asText(){
        return String.valueOf(name);
    }
}
