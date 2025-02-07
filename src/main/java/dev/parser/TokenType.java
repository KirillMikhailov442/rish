package dev.parser;

public enum TokenType {
    NUMBER,
    PLUS,
    MINUS,
    STAR,
    SLASH,
    EQUALS,

    //keywords
    PRINT,
    INPUT,

    LPAREN,
    RPAREN,

    WORD,
    TEXT,

    EOF
}
