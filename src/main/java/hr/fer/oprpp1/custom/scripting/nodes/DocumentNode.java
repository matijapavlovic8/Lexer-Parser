package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * A node representing the document.
 *
 * @author MatijaPav
 */

public class DocumentNode extends Node{

    @Override
    public String toString(){
        String doc = "";
        for(int i = 0; i < this.numberOfChildren(); i++){
            doc += this.getChild(i).toString();
        }
        return doc;
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof DocumentNode))
            return false;
        return(this.toString().equals(o.toString()));
    }
}
