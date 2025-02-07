package dev.ast;

public class UnaryExpression implements Expression{
    final Expression expression1;
    final char operation;

    public UnaryExpression(Expression expression1, char operation) {
        this.expression1 = expression1;
        this.operation = operation;
    }

    @Override
    public double eval() {
        switch (operation){
            case '-': return -expression1.eval();
            case '+':
            default: return expression1.eval();

        }
    }

    @Override
    public String toString() {
        return String.format("%c %s", operation, expression1);
    }
}
