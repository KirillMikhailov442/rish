package dev.parser;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    final String input;
    List<Token> tokens;
    int length;
    int position;

    static final String OPERATOR_CHARS = "+-*/()=";
    static final TokenType[] OPERATOR_TOKENS = {
            TokenType.PLUS, TokenType.MINUS,
            TokenType.STAR, TokenType.SLASH,
            TokenType.LPAREN, TokenType.RPAREN,
            TokenType.EQUALS
    };

    public Lexer(String input){
        this.input = input;
        this.length = input.length();
        this.tokens = new ArrayList<>();
    }

    public List<Token> tokenize(){
        while(position < length){
            final char current = peek(0);
            if(Character.isDigit((current))) tokenizeNumber();
            else if(Character.isLetter(current)) tokenizeWord();
            else if(OPERATOR_CHARS.indexOf(current) != -1){
                tokenizeOperation();
            }else{
                next();
            }
        };
        return tokens;
    }

    public void tokenizeNumber(){
        final StringBuilder buffer = new StringBuilder();
        char current = peek(0);

        while(true){
            if(current == '.'){
                if(buffer.indexOf(".") != -1) throw new RuntimeException("Invalid float number");
            }
            else if(!Character.isDigit(current)) break;
            buffer.append(current);
            current = next();
        }
        addToken(TokenType.NUMBER, buffer.toString());
    }

    public void tokenizeWord(){
        final StringBuilder buffer = new StringBuilder();
        char current = peek(0);

        while (true){
            if(!Character.isLetterOrDigit(current) && (current != '_') && (current != '$')) break;

            buffer.append(current);
            current = next();
        }

        addToken(TokenType.WORD, buffer.toString());
    }

    public void tokenizeOperation(){
        final int localPosition = OPERATOR_CHARS.indexOf(peek(0));
        addToken(OPERATOR_TOKENS[localPosition]);
        next();
    }

    public char next(){
        position++;
        return peek(0);
    }

    public char peek(int relativePosition){
        final int localPosition = position + relativePosition;

        if(localPosition >= length) return  '\0';
        return input.charAt(localPosition);
    }

    public void addToken(TokenType type){
        addToken(type, "");
    }

    public void addToken(TokenType type, String text){
        tokens.add(new Token(type, text));
    }
}
