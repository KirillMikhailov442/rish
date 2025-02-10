package dev.ast.expressions;

import dev.ast.values.NumberValue;
import dev.ast.values.StringValue;
import dev.ast.values.Value;

public class ValueExpression implements Expression {
    Value value;

    public ValueExpression(double value) {
        this.value = new NumberValue(value);
    }

    public ValueExpression(String value) {
        this.value = new StringValue(value);
    }

    @Override
    public Value eval() {
        return value;
    }

    @Override
    public String toString() {
        return value.asString();
    }
}
