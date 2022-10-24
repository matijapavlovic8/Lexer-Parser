package hr.fer.oprpp1.hw02.prob1;

import static org.junit.jupiter.api.Assertions.*;

import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptTokenType;
import hr.fer.oprpp1.custom.scripting.nodes.Node;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import org.junit.jupiter.api.Test;

public class TestSmartScript {

    @Test
    public void testNullAsSSLexerArgument(){
        assertThrows(NullPointerException.class, () -> new SmartScriptLexer(null));
    }

    @Test
    public void testNullAsSSParserArgument(){
        assertThrows(NullPointerException.class, ()-> new SmartScriptParser(null));
    }

    @Test
    public void testReturnNode(){
        SmartScriptParser parser = new SmartScriptParser("document");
        assertNotNull(parser.getDocumentNode());
    }

    @Test
    public void testNumberOfChildren(){
        SmartScriptParser parser = new SmartScriptParser("document {$= tag $}");
        assertEquals(2, parser.getDocumentNode().numberOfChildren());
    }

    @Test
    public void testNullAsArgumentNode(){
        Node node = new Node();
        assertThrows(NullPointerException.class, () -> node.addChildNode(null));
    }

    @Test
    public void testGetToken(){
        SmartScriptLexer lexer = new SmartScriptLexer("input");
        assertThrows(LexerException.class, lexer::getToken);
    }

    @Test
    public void testNextToken(){
        SmartScriptLexer lexer = new SmartScriptLexer("input");
        assertEquals(SmartScriptTokenType.TEXT, lexer.nextToken().getType());
        assertEquals(SmartScriptTokenType.TEXT, lexer.getToken().getType());
        assertEquals("input", lexer.getToken().getValue());
        assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType());
    }

    
}
