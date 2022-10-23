package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.EmptyStackException;
import hr.fer.oprpp1.custom.collections.ObjectStack;
import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantDouble;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantInteger;
import hr.fer.oprpp1.custom.scripting.elems.ElementFunction;
import hr.fer.oprpp1.custom.scripting.elems.ElementOperator;
import hr.fer.oprpp1.custom.scripting.elems.ElementString;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexerState;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptToken;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptTokenType;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.EchoNode;
import hr.fer.oprpp1.custom.scripting.nodes.ForLoopNode;
import hr.fer.oprpp1.custom.scripting.nodes.Node;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;
import hr.fer.oprpp1.hw02.prob1.Token;

/**
 * Implementation of a parser.
 *
 * @author MatijaPav
 */
public class SmartScriptParser {

    /**
     * Lexer used for lexical analysis.
     */
    private final SmartScriptLexer lexer;

    /**
     * Root of the document tree.
     */
    private DocumentNode documentNode;

    /**
     * Instance of ObjectStack class.
     */
    private ObjectStack stack;

    /**
     * Latest token created by the lexer.
     */
    private SmartScriptToken token;


    /**
     * Creates an instance of SmartScriptParser class, instance of ObjectStack and instance of SmartScriptLexer.
     * Parses the input document and builds document tree.
     * @param document String that is being parsed.
     */
    public SmartScriptParser(String document){
        if(document == null) throw new NullPointerException("Can't parse null.");

        this.lexer = new SmartScriptLexer(document);
        this.token = lexer.nextToken();
        this.stack = new ObjectStack();

        try{
            this.documentNode = this.parse();
        } catch (SmartScriptParserException e) {
            throw new SmartScriptParserException(e.getMessage());
        }
    }

    /**
     * Getter for Document node.
     * @return {@code DocumentNode}
     */
    public DocumentNode getDocumentNode(){
        return this.documentNode;
    }


    /**
     * Parses the document and creates a document tree.
     * @return {@code DocumentNode} root of the Document tree.
     */
    private DocumentNode parse(){
        DocumentNode root = new DocumentNode();
        stack.push(root);

        while(token.getType() != SmartScriptTokenType.EOF) {
            if (token.getType() == SmartScriptTokenType.TAG && token.getValue().toString().equals("{$")) {
                lexer.setState(SmartScriptLexerState.TAG);

            } else if (token.getType() == SmartScriptTokenType.TEXT){
                parseText();

            } else if (token.getType() == SmartScriptTokenType.VARIABLE) {
                parseTag();

            } else throw new SmartScriptParserException("Can't parse this token! " + token.getType());

            token = lexer.nextToken();

        }

        try{
            return (DocumentNode) stack.pop();
        } catch (EmptyStackException | ClassCastException e) {
            throw new SmartScriptParserException(e.getMessage());
        }
    }

    /**
     * Parses the elements of document located inside tags.
     */

    private void parseTag(){

        if(token.getValue().toString().equals("=")) {
            token = lexer.nextToken();
            currentNode().addChildNode(new EchoNode(parseEq()));
        }
        else if(token.getValue().toString().equalsIgnoreCase("FOR")){
            Element[] arr = parseFor();

            if(arr.length == 3) {
                ForLoopNode node = new ForLoopNode((ElementVariable) arr[0], arr[1], arr[2]);
                currentNode().addChildNode(node);
                stack.push(node);
            }
            else if(arr.length == 4) {
                ForLoopNode node = new ForLoopNode((ElementVariable) arr[0], arr[1], arr[2], arr[3]);
                currentNode().addChildNode(node);
                stack.push(node);
            }
            else
                throw new SmartScriptParserException("ForLoopNode takes 3 or 4 args.");


        }

        else if(token.getValue().toString().equalsIgnoreCase("END")) {
            token = lexer.nextToken();
            parseEnd();

        } else
            throw new SmartScriptParserException("Can't parse this token. " + lexer.getState());

        lexer.setState(SmartScriptLexerState.TEXT);

    }

    /**
     * Parses text elements of the document, creates a new {@code TextNode} and makes it a child of the previous node.
     */
    private void parseText(){
        String text = (String)token.getValue();
        Node newNode = new TextNode(text);
        currentNode().addChildNode(newNode);
    }

    /**
     * Parses elements following "=" in tags. Creates a new {@code EchoNode} and makes it a child of the previous node.
     * @return {@code Element[]}
     */
    private Element[] parseEq(){
        ArrayIndexedCollection elements = new ArrayIndexedCollection();

        while(token.getType() != SmartScriptTokenType.TAG){
            elements.add(fillElements());
            token = lexer.nextToken();
        }

        if(token.getValue().toString().equals("{$")) throw new SmartScriptParserException("Can't open a tag inside a tag");

        return createElementArray(elements);
    }

    private Element[] parseFor(){
        ArrayIndexedCollection elements = new ArrayIndexedCollection();
        token = lexer.nextToken();

        if(token.getType() != SmartScriptTokenType.VARIABLE)
            throw new SmartScriptParserException("For loop requires a variable!" + token.getType() + token.getValue());

        elements.add(new ElementVariable((String)token.getValue()));//add ElementVariable as first argument

        token = lexer.nextToken();

        while(token.getType() != SmartScriptTokenType.TAG){
            elements.add(fillElements());
            token = lexer.nextToken();
        }
        if(token.getValue().toString().equals("{$")) throw new SmartScriptParserException("Can't open a tag inside a tag");

        return createElementArray(elements);
    }

    private void parseEnd(){
        if(token.getType() != SmartScriptTokenType.TAG && !token.getValue().toString().equals("$}"))
            throw new SmartScriptParserException("Tags must be closed with end tags.");

        stack.pop();
    }


    /**
     * Peeks the stack and returns the {@code Node} on top of it.
     * @return {@code Node}
     */
    private Node currentNode(){
        return (Node)stack.peek();
    }

    /**
     * Method checks the {@code SmartScriptTokenType} and creates appropriate {@code Element}
     * @return {@code Element}
     */
    private Element fillElements(){
        if(token.getType() == SmartScriptTokenType.EOF)
            throw new SmartScriptParserException("Opened tags must be closed.");
        if(token.getType() == SmartScriptTokenType.VARIABLE){
            String value = (String)token.getValue();
            return new ElementVariable(value);
        }
        if(token.getType() == SmartScriptTokenType.DOUBLE){
            Double num = (Double) token.getValue();
            return new ElementConstantDouble(num);
        }
        if(token.getType() == SmartScriptTokenType.OPERATOR){
            String operator = (String)token.getValue();
            return new ElementOperator(operator);
        }
        if(token.getType() == SmartScriptTokenType.INTEGER){
            int num = (int) token.getValue();
            return new ElementConstantInteger(num);
        }
        if(token.getType() == SmartScriptTokenType.FUNCTION){
            String fun = (String) token.getValue();
            return new ElementFunction(fun);
        }
        if(token.getType() == SmartScriptTokenType.TAG_TEXT){
            String str = (String) token.getValue();
            return new ElementString(str);
        }

        throw new SmartScriptParserException("Can't parse this token!" + token.getValue());

    }

    public Element[] createElementArray(ArrayIndexedCollection col){
        Element[] arr = new Element[col.size()];
        for(int i = 0; i < arr.length; i++){
            arr[i] = (Element)col.get(i);
        }
        return arr;
    }



}
