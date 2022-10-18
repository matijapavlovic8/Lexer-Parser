package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Inherits {@code Element} and has a single read-only {@code String} variable.
 *
 * @author MatijaPav
 */

public class ElementOperator extends Element{

    /**
     * Read-only variable. Represents the value of {@code ElementConstantInteger}.
     */
    private String symbol;

    public ElementOperator(String symbol){
        this.symbol = symbol;
    }

    /**
     * Getter for read-only variable value.
     * @return {@code int} value of {@code ElementConstantInteger}.
     */
    public String getValue() {
        return symbol;
    }

    @Override
    public String asText(){
        return String.valueOf(symbol);
    }
}
