package dev.ast.statements;

import dev.ast.expressions.Expression;

public class IfStatement implements Statement {
    final Expression expression;
    final Statement ifStatement;
    final Statement elseStatement;

    public IfStatement(Statement ifStatement, Statement elseStatement, Expression expression) {
        this.expression = expression;
        this.ifStatement = ifStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public void execute() {
        final double result = expression.eval().asDouble();
        if(result != 0){
            ifStatement.execute();
        } else if(elseStatement != null){
            elseStatement.execute();
        }
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        result.append("IF ").append(expression).append(" ").append(ifStatement);
        if(elseStatement != null){
            result.append("\nELSE ").append(" ").append(elseStatement);
        }

        return result.toString();
    }
}
