package org.example;

import com.alibaba.checker.Java9Lexer;
import com.alibaba.checker.Java9Parser;
import org.antlr.v4.runtime.*;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Hello world!
 *
 */
public class SyntaxChecker {

    public int parse(CharStream input) {
        Java9Lexer lexer = new Java9Lexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        Java9Parser parser = new Java9Parser(tokens);

        final StringBuilder errorMessages = new StringBuilder();
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                String err = String.format("Failed to parse at line %d:%d due to %s", line, charPositionInLine + 1, msg);
                errorMessages.append(err);
                errorMessages.append(System.lineSeparator());
            }
        });

        parser.compilationUnit();
        return parser.getNumberOfSyntaxErrors();
    }

    public static void main(String[] args) throws SAXException, IOException {
        ANTLRInputStream input = new ANTLRFileStream(args[0]);
        SyntaxChecker checker = new SyntaxChecker();
        int syntaxErrors = checker.parse(input);

        if (syntaxErrors == 0) {
            System.out.println(args[0] + ": PASS");
        } else {
            System.out.println(args[0] + ": FAILED (" + syntaxErrors + ") syntax errors");
        }

    }

    public static void analyzeResources(String file) throws IOException {
        InputStream is = SyntaxChecker.class.getClassLoader().getResourceAsStream(file);
        SyntaxChecker checker = new SyntaxChecker();
        int syntaxErrors = checker.parse(CharStreams.fromStream(is));

        if (syntaxErrors == 0) {
            System.out.println(file + ": PASS");
        } else {
            System.out.println(file + ": FAILED (" + syntaxErrors + ") syntax errors");
        }

    }
}
