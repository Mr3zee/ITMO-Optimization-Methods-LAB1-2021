package expression.expression_tools;

import expression.type.EType;

public class Abs<T extends Number> extends UnaryOperations<T> {
    public Abs(CommonExpression<T> expression) {
        super(expression);
    }

    @Override
    protected EType<T> toCalculate(EType<T> x) {
        return x.abs();
    }

    @Override
    protected String getOperand() {
        return "abs ";
    }

    @Override
    public String toTex() {
        return String.format("\\left|%s\\right|", expression);
    }

    @Override
    protected int primary() {
        return 1607;
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
