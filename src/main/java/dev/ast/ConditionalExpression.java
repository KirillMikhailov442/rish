package dev.ast;

public class ConditionalExpression implements  Expression{
    final Expression expression1, expression2;
    final char operation;

    public ConditionalExpression(Expression expression1, Expression expression2, char operation) {
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
                case '=': return new NumberValue(string1.equals(string2));
                case '>': return new NumberValue(string1.compareTo(string2) > 0);
                case '<': return new NumberValue(string1.compareTo(string2) < 0);
                default: return new NumberValue(string1.equals(string2));
            }
        }

        final double num1 = value1.asDouble();
        final double num2 = value2.asDouble();

        switch (operation){
            case '=': return new NumberValue(num1 == num2);
            case '>': return new NumberValue(num1 > num2);
            case '<': return new NumberValue(num1 < num2);
            default: return new NumberValue(num1 == num2);
        }
    }

    @Override
    public String toString() {
        return String.format("[ %s %c %s ]", expression1, operation, expression2);
    }
}
