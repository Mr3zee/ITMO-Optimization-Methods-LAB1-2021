package expression.expression_tools;

import expression.type.EType;

public class Sin<T extends Number> extends UnaryOperations<T> {
    public Sin(CommonExpression<T> expression) {
        super(expression);
    }

    @Override
    protected EType<T> toCalculate(EType<T> arg) {
        return arg.sin();
    }

    @Override
    protected String getOperand() {
        return "sin";
    }

    @Override
    protected int primary() {
        return 3463;
    }

    @Override
    public String getTexOperand() {
        return "\\sin";
    }
}
