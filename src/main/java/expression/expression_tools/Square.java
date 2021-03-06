package expression.expression_tools;

import expression.type.EType;

public class Square<T extends Number> extends UnaryOperations<T> {
    public Square(CommonExpression<T> expression) {
        super(expression);
    }

    @Override
    protected EType<T> toCalculate(EType<T> x) {
        return x.multiply(x);
    }

    @Override
    protected String getOperand() {
        return "square ";
    }

    @Override
    protected int primary() {
        return 1427;
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
