package dev.ast.statements;

import dev.ast.expressions.Expression;

public class PrintStatement implements Statement {
    final Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void execute() {
        System.out.print(expression.eval().asString());
    }

    @Override
    public String toString() {
        return "print " + expression.eval();
    }
}
