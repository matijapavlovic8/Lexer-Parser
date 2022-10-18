package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Inherits {@code Element} and has a single read-only {@code String} variable.
 *
 * @author MatijaPav
 */

public class ElementString extends Element{

    /**
     * Read-only variable. Represents the value of {@code ElementConstantInteger}.
     */
    private String value;

    public ElementString(String value){
        this.value = value;
    }

    /**
     * Getter for read-only variable value.
     * @return {@code int} value of {@code ElementConstantInteger}.
     */
    public String getValue() {
        return value;
    }

    @Override
    public String asText(){
        return String.valueOf(value);
    }
}
