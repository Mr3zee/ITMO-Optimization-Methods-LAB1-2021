package expression.expression_tools;

import expression.type.EType;

public class Power<T extends Number> extends BinaryOperations<T> {
    public Power(CommonExpression<T> firstExp, CommonExpression<T> secondExp) {
        super(firstExp, secondExp);
    }

    @Override
    protected EType<T> toCalculate(EType<T> firstArg, EType<T> secondArg) {
        return firstArg.pow(secondArg);
    }

    @Override
    protected String getOperand() {
        return "**";
    }

    @Override
    protected int primary() {
        return 6827;
    }

    @Override
    public int getPriority() {
        return 30;
    }

    @Override
    public boolean dependsOnOrder() {
        return false;
    }
}
