package hr.fer.oprpp1.hw02.prob1;

import static org.junit.jupiter.api.Assertions.*;

import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexer;
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

    
}
