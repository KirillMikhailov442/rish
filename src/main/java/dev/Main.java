package dev;


import dev.ast.Expression;
import dev.ast.Statement;
import dev.parser.Lexer;
import dev.parser.Parser;
import dev.parser.Token;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String code = "name = 1 + 1\n name2 = PI + name\n PI = 3";
        List<Token> tokens = new Lexer(code).tokenize();
        for (Token token: tokens){
            System.out.println(token.toString());
        }
        System.out.println("----------");

        List<Statement> statements = new Parser(tokens).parse();

        for (Statement statement: statements){
            statement.execute();
        }

        for (Statement statement: statements){
            System.out.println(statement);
        }
    }
}