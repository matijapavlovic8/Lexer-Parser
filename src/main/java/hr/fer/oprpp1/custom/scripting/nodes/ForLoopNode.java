package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

/**
 * A node representing a single for-loop construct.
 *
 * @author MatijaPav
 */

public class ForLoopNode extends Node {
    /**
     * Variable of ForLoopNode.
     */
    private ElementVariable variable;

    /**
     * Start expression of ForLoopNode.
     */
    private Element startExpression;

    /**
     * End expression of ForLoopNode.
     */
    private Element endExpression;

    /**
     * Step expression of ForLoopNode.
     */
    private Element stepExpression;

    public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression, Element stepExpression){
        if(variable == null || startExpression == null || endExpression == null) throw new NullPointerException("Arguments of ForLoopNode constructor can't be null!");
        this.variable = variable;
        this.startExpression = startExpression;
        this.endExpression = endExpression;
        this.stepExpression = stepExpression;
    }

    public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression){
        this(variable,startExpression, endExpression, null);
    }

    /**
     * Getter for variable of ElementVariable.
     * @return {@code ElementVariable} variable.
     */

    public ElementVariable getVariable(){
        return this.variable;
    }

    /**
     * Getter for start expression of ElementVariable.
     * @return {@code Element} start expression.
     */

    public Element getStartExpression() {
        return startExpression;
    }

    /**
     * Getter for end expression of ElementVariable.
     * @return {@code Element} end expression.
     */

    public Element getEndExpression() {
        return endExpression;
    }
    /**
     * Getter for step expression of ElementVariable.
     * @return {@code Element} step expression.
     */

    public Element getStepExpression() {
        return stepExpression;
    }

    @Override

    public String toString(){
        String res = "{$ FOR ";

        res += getVariable().asText() + " " + getStartExpression().asText() + " " + getEndExpression();
        if(stepExpression != null)
            res += " " + getStepExpression().asText();
        res += " $}";

        for(int i = 0; i < numberOfChildren(); i++)
            res += getChild(i).toString();

        res += "{$END$}";

        return res;

    }
}
