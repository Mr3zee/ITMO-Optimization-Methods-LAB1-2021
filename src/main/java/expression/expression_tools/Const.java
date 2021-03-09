package expression.expression_tools;

import expression.type.EType;

import java.util.Objects;

public class Const<T extends Number> implements CommonExpression<T> {
    private final EType<T> value;

    public Const(final EType<T> value) {
        this.value = value;
    }

    @Override
    public EType<T> evaluate(EType<T> x) {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Const<?> that = (Const<?>) o;
        return value.equals(that.value);
    }

    @Override
    public String toTex() {
        return toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public int getPriority() {
        return 40;
    }

    @Override
    public boolean dependsOnOrder() {
        return false;
    }
}
