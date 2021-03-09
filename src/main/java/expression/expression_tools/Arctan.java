package expression.expression_tools;

import expression.type.EType;

public class Arctan<T extends Number> extends UnaryOperations<T> {
    public Arctan(CommonExpression<T> expression) {
        super(expression);
    }

    @Override
    protected EType<T> toCalculate(EType<T> arg) {
        return arg.atan();
    }

    @Override
    protected String getOperand() {
        return "arctan";
    }

    @Override
    protected String getTexOperand() {
        return "\\arctan";
    }

    @Override
    protected int primary() {
        return 7877;
    }
}
