package expression.expression_tools;

import expression.type.EType;

public class Exp<T extends Number> extends UnaryOperation<T> {
    public Exp(CommonExpression<T> expression) {
        super(expression);
    }

    @Override
    protected EType<T> toCalculate(EType<T> arg) {
        return arg.exp();
    }

    @Override
    protected String getOperand() {
        return "exp";
    }

    @Override
    protected String getTexOperand() {
        return "\\exp";
    }

    @Override
    protected int primary() {
        return 3469;
    }
}
