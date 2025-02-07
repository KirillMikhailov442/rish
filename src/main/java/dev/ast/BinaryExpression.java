package dev.ast;

public class BinaryExpression implements  Expression{
    final Expression expression1, expression2;
    final char operation;

    public BinaryExpression(Expression expression1, Expression expression2, char operation) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

    @Override
    public double eval() {
        switch (operation){
            case '+': return expression1.eval() + expression2.eval();
            case '-': return expression1.eval() - expression2.eval();
            case '/': return expression1.eval() / expression2.eval();
            case '*': return expression1.eval() * expression2.eval();
            default: return expression1.eval() + expression2.eval();

        }
    }

    @Override
    public String toString() {
        return String.format("[ %s %c %s ]", expression1, operation, expression2);
    }
}
