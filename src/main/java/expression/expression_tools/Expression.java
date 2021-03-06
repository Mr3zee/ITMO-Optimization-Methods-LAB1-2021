package expression.expression_tools;

import expression.type.EType;

public interface Expression<T extends Number> extends ToMiniString {
    EType<T> evaluate(EType<T> x);
}
