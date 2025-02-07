package dev.ast;

import dev.parser.Variable;

public class AssignmentStatement implements  Statement{

    final String variable;
    final Expression expression;

    public AssignmentStatement(String variable, Expression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public void execute() {
        final Value result = expression.eval();
        Variable.set(variable, result);
    }

    @Override
    public String toString() {
        return variable + " = " + expression.eval();
    }
}
