package dev.ast.expressions;

import dev.ast.values.NumberValue;
import dev.ast.values.StringValue;
import dev.ast.values.Value;

public class BinaryExpression implements Expression {
    final Expression expression1, expression2;
    final char operation;

    public BinaryExpression(Expression expression1, Expression expression2, char operation) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

    @Override
    public Value eval() {
        final Value value1 = expression1.eval();
        final Value value2 = expression2.eval();

        if(value1 instanceof StringValue){
            final String string1 = value1.asString();
            final String string2 = value2.asString();

            switch (operation){
                case '+': return new StringValue(string1 + string2);
                case '*':
                    final int iterations = (int) value2.asDouble();
                    final StringBuilder buffer = new StringBuilder();

                    for (int i = 0; i < iterations; i++) {
                        buffer.append(string1);
                    }
                    return new StringValue(buffer.toString());
            }
        }

        final double num1 = value1.asDouble();
        final double num2 = value2.asDouble();

        switch (operation){
            case '+': return new NumberValue(num1 + num2);
            case '-': return new NumberValue(num1 - num2);
            case '/': return new NumberValue(num1 / num2);
            case '*': return new NumberValue(num1 * num2);
            default: return new NumberValue(num1 + num2);
        }
    }

    @Override
    public String toString() {
        return String.format("[ %s %c %s ]", expression1, operation, expression2);
    }
}
