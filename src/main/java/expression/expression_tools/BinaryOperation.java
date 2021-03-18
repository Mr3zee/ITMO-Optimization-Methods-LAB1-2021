package expression.expression_tools;

import expression.type.EType;

import java.util.Objects;
import java.util.function.Function;

public abstract class BinaryOperation<T extends Number> implements CommonExpression<T> {
    protected final CommonExpression<T> firstExp;
    protected final CommonExpression<T> secondExp;

    public BinaryOperation(CommonExpression<T> firstExp, CommonExpression<T> secondExp) {
        this.firstExp = firstExp;
        this.secondExp = secondExp;
    }

    @Override
    public EType<T> evaluate(EType<T> x) {
        return toCalculate(firstExp.evaluate(x), secondExp.evaluate(x));
    }

    protected abstract EType<T> toCalculate(EType<T> firstArg, EType<T> secondArg);

    @Override
    public String toString() {
        return toMiniString(Expression::toString);
    }

    @Override
    public String toTex() {
        return toMiniString(ToTex::toTex);
    }

    private String toMiniString(Function<CommonExpression<T>, String> f) {
        boolean firstHigherPriority = this.getPriority() > firstExp.getPriority();
        boolean secondHigherPriority = this.getPriority() > secondExp.getPriority() ||
                (this.getPriority() == secondExp.getPriority() && (this.dependsOnOrder() || secondExp.dependsOnOrder()));
        return (firstHigherPriority ? "(" : "") + f.apply(firstExp) + (firstHigherPriority ? ")" : "")
                + ' ' + getOperand() + ' ' +
                (secondHigherPriority ? "(" : "") + f.apply(secondExp) + (secondHigherPriority ? ")" : "");
    }

    protected abstract String getOperand();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        BinaryOperation<?> that = (BinaryOperation<?>) o;
        return Objects.equals(firstExp, that.firstExp) &&
                Objects.equals(secondExp, that.secondExp);
    }

    @Override
    public int hashCode() {
        final int MOD = 1073676287;
        final int PRIME = primary();
        int result = 3527;
        result = (PRIME * result + firstExp.hashCode()) % MOD;
        result = (PRIME * result + secondExp.hashCode()) % MOD;
        return result;
    }

    protected abstract int primary();
}
