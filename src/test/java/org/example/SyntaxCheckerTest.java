package org.example;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class SyntaxCheckerTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void mismatchedBraces() throws IOException {
        SyntaxChecker.analyzeResources("MismatchedBraces.java");
    }

    @Test
    public void missingSymbols() throws IOException {
        SyntaxChecker.analyzeResources("MissingSymbol.java");
    }


}
