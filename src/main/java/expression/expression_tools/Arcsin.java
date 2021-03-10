package expression.expression_tools;

import expression.type.EType;

public class Arcsin<T extends Number> extends UnaryOperation<T> {
    public Arcsin(CommonExpression<T> expression) {
        super(expression);
    }

    @Override
    protected EType<T> toCalculate(EType<T> arg) {
        return arg.asin();
    }

    @Override
    protected String getOperand() {
        return "arcsin";
    }

    @Override
    protected String getTexOperand() {
        return "\\arcsin";
    }

    @Override
    protected int primary() {
        return 6379;
    }

}
