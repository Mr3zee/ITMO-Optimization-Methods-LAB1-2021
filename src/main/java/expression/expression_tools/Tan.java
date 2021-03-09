package expression.expression_tools;

import expression.type.EType;

public class Tan<T extends Number> extends UnaryOperations<T> {
    public Tan(CommonExpression<T> expression) {
        super(expression);
    }

    @Override
    protected EType<T> toCalculate(EType<T> arg) {
        return arg.tan();
    }

    @Override
    protected String getOperand() {
        return "tan";
    }

    @Override
    protected String getTexOperand() {
        return "\\tan";
    }

    @Override
    protected int primary() {
        return 7873;
    }
}
