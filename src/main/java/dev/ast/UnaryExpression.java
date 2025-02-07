package dev.ast;

public class UnaryExpression implements Expression{
    final Expression expression1;
    final char operation;

    public UnaryExpression(Expression expression1, char operation) {
        this.expression1 = expression1;
        this.operation = operation;
    }

    @Override
    public Value eval() {
        switch (operation){
            case '-': return new NumberValue(-expression1.eval().asDouble());
            case '+':
            default: return new NumberValue(expression1.eval().asDouble());

        }
    }

    @Override
    public String toString() {
        return String.format("%c %s", operation, expression1);
    }
}
