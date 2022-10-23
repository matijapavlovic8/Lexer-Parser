package hr.fer.oprpp1.hw02;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SmartScriptTester {

    public static void main(String[] args) throws IOException {

        String docBody = Files.readString(Paths.get(args[0]));
        SmartScriptParser parser = new SmartScriptParser(docBody);
        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = document.toString();

        SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
        DocumentNode document2 = parser2.getDocumentNode();
        // now document and document2 should be structurally identical trees
        boolean same = document.equals(document2); // ==> "same" must be true

        System.out.println(same);

        System.out.println(originalDocumentBody);

        System.out.println(document2.toString());
        System.out.println(document.numberOfChildren());
        System.out.println(document2.numberOfChildren());

    }
}
