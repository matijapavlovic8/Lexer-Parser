package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Inherits {@code Element} and has a single read-only {@code double} variable.
 *
 * @author MatijaPav
 */

public class ElementConstantDouble extends Element{

    /**
     * Read-only variable. Represents the value of {@code ElementConstantInteger}.
     */
    private double value;

    public ElementConstantDouble(double value){
        this.value = value;
    }

    /**
     * Getter for read-only variable value.
     * @return {@code int} value of {@code ElementConstantInteger}.
     */
    public double getValue() {
        return value;
    }

    @Override
    public String asText(){
        return String.valueOf(value);
    }
}
