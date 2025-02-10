package dev.ast.expressions;

import dev.ast.values.Value;

public interface Expression {
    Value eval();
}
