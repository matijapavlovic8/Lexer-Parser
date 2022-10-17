package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Inherits {@code Element} and has a single read-only {@code int} variable.
 *
 * @author MatijaPav
 */

public class ElementConstantInteger extends Element{

    /**
     * Read-only variable. Represents the value of {@code ElementConstantInteger}.
     */
    private int value;

    public ElementConstantInteger(int value){
        this.value = value;
    }

    /**
     * Getter for read-only variable value.
     * @return {@code int} value of {@code ElementConstantInteger}.
     */
    public int getValue() {
        return value;
    }

    @Override
    public String asText(){
        return String.valueOf(value);
    }
}
