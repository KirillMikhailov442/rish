package dev.parser;

public enum TokenType {
    NUMBER,
    PLUS,
    MINUS,
    STAR,
    STARSTAR,
    SLASH,
    EQUALS,
    EQEQ,
    LT,
    LTEQ,
    GT,
    GTEQ,
    EXCL,
    EXCLEQ,

    BAR,
    BARBAR,
    AMP,
    AMPAMP,

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
