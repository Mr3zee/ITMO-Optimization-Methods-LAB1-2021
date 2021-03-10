package expression.expression_tools;

import expression.type.EType;

public class Arccos<T extends Number> extends UnaryOperation<T> {
    public Arccos(CommonExpression<T> expression) {
        super(expression);
    }

    @Override
    protected EType<T> toCalculate(EType<T> arg) {
        return arg.acos();
    }

    @Override
    protected String getOperand() {
        return "arccos";
    }

    @Override
    protected String getTexOperand() {
        return "\\arccos";
    }

    @Override
    protected int primary() {
        return 6361;
    }
}
