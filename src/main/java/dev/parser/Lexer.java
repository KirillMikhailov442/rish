package dev.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lexer {
    final String input;
    List<Token> tokens;
    int length;
    int position;

    static final String OPERATOR_CHARS = "+-*/()=<>!&|";

    static final Map<String, TokenType> OPERATORS;
    static {
        OPERATORS = new HashMap<>();
        OPERATORS.put("+", TokenType.PLUS);
        OPERATORS.put("-", TokenType.MINUS);
        OPERATORS.put("*", TokenType.STAR);
        OPERATORS.put("/", TokenType.SLASH);
        OPERATORS.put("(", TokenType.LPAREN);
        OPERATORS.put(")", TokenType.RPAREN);
        OPERATORS.put("=", TokenType.EQUALS);
        OPERATORS.put("<", TokenType.LT);
        OPERATORS.put(">", TokenType.GT);

        OPERATORS.put("!", TokenType.EXCL);
        OPERATORS.put("&", TokenType.AMP);
        OPERATORS.put("|", TokenType.BAR);

        OPERATORS.put("**", TokenType.STARSTAR);

        OPERATORS.put("==", TokenType.EQEQ);
        OPERATORS.put("!=", TokenType.EXCLEQ);
        OPERATORS.put("<=", TokenType.LTEQ);
        OPERATORS.put(">=", TokenType.GTEQ);

        OPERATORS.put("&&", TokenType.AMPAMP);
        OPERATORS.put("||", TokenType.BARBAR);
    }


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
            else if(current == '"') tokenizeText();
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

    public void tokenizeText(){
        next();
        final StringBuilder buffer = new StringBuilder();
        char current = peek(0);

        while(true){
            if(current == '\\'){
                current = next();
                switch (current){
                    case '"': current = next(); buffer.append('"'); continue;
                    case 'n': current = next(); buffer.append('\n'); continue;
                    case 't': current = next(); buffer.append('\t'); continue;
                }
                buffer.append('\\');
                continue;
            }

            if (current == '"') break;
            buffer.append(current);
            current = next();
        }
        next();

        addToken(TokenType.TEXT, buffer.toString());

    }

    public void tokenizeWord(){
        final StringBuilder buffer = new StringBuilder();
        char current = peek(0);

        while (true){
            if(!Character.isLetterOrDigit(current) && (current != '_') && (current != '$')) break;

            buffer.append(current);
            current = next();
        }

        if(buffer.toString().equals(KeyWords.get("PRINT"))){
            addToken(TokenType.PRINT);
        }

        else if(buffer.toString().equals(KeyWords.get("IF"))){
            addToken(TokenType.IF);
        }

        else if(buffer.toString().equals(KeyWords.get("ELSE"))){
            addToken(TokenType.ELSE);
        }

        else{
            addToken(TokenType.WORD, buffer.toString());
        }
    }

    public void tokenizeOperation(){
        char current = peek();
        if(current == '/'){
            if(peek(1) == '/'){
                next(2);
                tokenizeComment();
                return;
            }
            else if(peek(1) == '*'){
                next(2);
                tokenizeMultilineComment();
                return;
            }
        }

        final StringBuilder buffer = new StringBuilder();
        while(true){
            final String text = buffer.toString();
            if(!OPERATORS.containsKey(text + current) && !text.isEmpty()){
                addToken(OPERATORS.get(text));
                return;
            }
            buffer.append(current);
            current = next();
        }

    }

    public void tokenizeMultilineComment(){
        char current = peek();
        while(true){
            if(current == '\0') throw new RuntimeException("Missing close tag */");
            if(current == '*' && peek(1) == '/') break;
            current = next();
        }
        next(2);
    }

    public void tokenizeComment(){
        char current = peek();
        while("\n\r\0".indexOf(current) == -1){
            current = next();
        }
        next();
    }

    public char next(){
        position++;
        return peek(0);
    }

    public char next(int relativePosition){
        position += relativePosition;
        return peek(relativePosition);
    }

    public char peek(int relativePosition){
        final int localPosition = position + relativePosition;

        if(localPosition >= length) return  '\0';
        return input.charAt(localPosition);
    }

    public char peek(){
        if(position >= length) return  '\0';
        return input.charAt(position);
    }

    public void addToken(TokenType type){
        addToken(type, "");
    }

    public void addToken(TokenType type, String text){
        tokens.add(new Token(type, text));
    }
}
