package dev.ast.expressions;

import dev.ast.values.Value;
import dev.parser.Variable;

public class VariableExpression implements Expression {
    final String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    @Override
    public Value eval() {
        if (!Variable.isExists(name)) throw new RuntimeException(String.format("Variable #%s# does not exists", name));
        return Variable.get(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
