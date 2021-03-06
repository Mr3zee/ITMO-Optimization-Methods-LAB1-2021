package expression.expression_tools;

import expression.type.EType;

public class Pow2<T extends Number> extends UnaryOperations<T> {
    public Pow2(CommonExpression<T> expression) {
        super(expression);
    }

    @Override
    protected EType<T> toCalculate(EType<T> arg) {
        return arg.pow2();
    }

    @Override
    protected String getOperand() {
        return "pow2 ";
    }

    @Override
    protected int primary() {
        return 4079;
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
