package dev;


import dev.ast.Statement;
import dev.parser.Lexer;
import dev.parser.Parser;
import dev.parser.Token;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        URI uri = Objects.requireNonNull(Main.class.getClassLoader().getResource("program.rsh")).toURI();
        String code = Files.readString(Paths.get(uri));

        List<Token> tokens = new Lexer(code).tokenize();
        for (Token token: tokens){
            System.out.println(token.toString());
        }
        System.out.println("\n==============================\n");
        List<Statement> statements = new Parser(tokens).parse();

        System.out.println("\n============OUTPUT============\n");
        for (Statement statement: statements){
            statement.execute();
        }

        System.out.println("\n==========STATEMENTS==========\n");
        for (Statement statement: statements){
            System.out.println(statement);
        }
    }
}