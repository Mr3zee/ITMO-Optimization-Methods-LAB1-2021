package expression.expression_tools;

import expression.type.EType;

public class Abs<T extends Number> extends UnaryOperation<T> {
    public Abs(CommonExpression<T> expression) {
        super(expression);
    }

    @Override
    protected EType<T> toCalculate(EType<T> x) {
        return x.abs();
    }

    @Override
    protected String getOperand() {
        return "abs";
    }

    @Override
    public String toTex() {
        return String.format(getTexOperand(), expression);
    }

    @Override
    protected String getTexOperand() {
        return "\\left|%s\\right|";
    }

    @Override
    protected int primary() {
        return 1607;
    }
}
