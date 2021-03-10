package expression.expression_tools;

import expression.type.EType;

public class Cos<T extends Number> extends UnaryOperation<T> {
    public Cos(CommonExpression<T> expression) {
        super(expression);
    }

    @Override
    protected EType<T> toCalculate(EType<T> arg) {
        return arg.cos();
    }

    @Override
    protected String getOperand() {
        return "cos";
    }

    @Override
    protected String getTexOperand() {
        return "\\cos";
    }

    @Override
    protected int primary() {
        return 2797;
    }
}
