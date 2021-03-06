package expression.expression_tools;

import expression.type.EType;

import java.util.Objects;

public abstract class BinaryOperations<T extends Number> implements CommonExpression<T> {
    protected final CommonExpression<T> firstExp;
    protected final CommonExpression<T> secondExp;

    public BinaryOperations(CommonExpression<T> firstExp, CommonExpression<T> secondExp) {
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
        return String.format("(%s %s %s)", firstExp, getOperand(), secondExp);
    }

    @Override
    public String toTex() {
        return String.format("(%s %s %s)", firstExp.toTex(), getOperand(), secondExp.toTex());
    }

    protected abstract String getOperand();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        BinaryOperations<?> that = (BinaryOperations<?>) o;
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
