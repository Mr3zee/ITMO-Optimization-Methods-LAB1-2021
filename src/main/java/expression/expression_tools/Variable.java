package expression.expression_tools;

import expression.type.EType;

import java.util.Objects;

public class Variable<T extends Number> implements CommonExpression<T> {
    private final String name;

    public Variable(final String name) {
        this.name = name;
    }

    @Override
    public EType<T> evaluate(EType<T> x) {
        return x;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Variable<?> that = (Variable<?>) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
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
