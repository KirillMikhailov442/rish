package dev.parser;

public enum TokenType {
    NUMBER,
    PLUS,
    MINUS,
    STAR,
    SLASH,
    EQUALS,
    LT,
    GT,

    //keywords
    PRINT,
    INPUT,
    IF,
    ELSE,

    LPAREN,
    RPAREN,

    WORD,
    TEXT,

    EOF
}
