package expression.expression_tools;

import expression.type.EType;

import java.util.function.Function;

public interface Expression<T extends Number> extends ToTex {
    EType<T> evaluate(EType<T> x);

    default Function<T, T> toFunction(Function<T, EType<T>> toEType) {
        return x -> evaluate(toEType.apply(x)).value();
    }

}
