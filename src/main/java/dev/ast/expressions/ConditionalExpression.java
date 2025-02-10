package dev.ast.expressions;

import dev.ast.values.NumberValue;
import dev.ast.values.StringValue;
import dev.ast.values.Value;

public class ConditionalExpression implements Expression {
    public static enum Operator {
        PLUS("+"),
        MINUS("-"),
        MULTIPLY("*"),
        DIVIDE("/"),

        EQUALS("=="),
        NOT_EQUALS("!="),

        LT("<"),
        LTEQ("<="),
        GT(">"),
        GTEQ(">="),

        AND("&&"),
        OR("||");

        final String name;
        Operator(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
    }

    final Expression expression1, expression2;
    final Operator operation;

    public ConditionalExpression(Expression expression1, Expression expression2, Operator operation) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

    @Override
    public Value eval() {
        final Value value1 = expression1.eval();
        final Value value2 = expression2.eval();

        double num1, num2;
        if(value1 instanceof StringValue){
            num1 = value1.asString().compareTo(value2.asString());
            num2 = 0;
        }else{
            num1 = value1.asDouble();
            num2 = value2.asDouble();
        }

        boolean result = switch (operation) {
            case LT -> num1 < num2;
            case LTEQ -> num1 <= num2;
            case GT -> num1 > num2;
            case GTEQ -> num1 >= num2;
            case NOT_EQUALS -> num1 != num2;
            case AND -> (num1 != 0) && (num2 != 0);
            case OR -> (num1 != 0) || (num2 != 0);
            default -> num1 == num2;
        };
        return new NumberValue(result);
    }

    @Override
    public String toString() {
        return String.format("[ %s %s %s ]", expression1, operation.getName(), expression2);
    }
}
