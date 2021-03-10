package expression.expression_tools;

import expression.type.EType;

public class Ln<T extends Number> extends UnaryOperation<T> {
    public Ln(CommonExpression<T> expression) {
        super(expression);
    }

    @Override
    protected EType<T> toCalculate(EType<T> arg) {
        return arg.ln();
    }

    @Override
    protected String getOperand() {
        return "ln";
    }

    @Override
    protected int primary() {
        return 7879;
    }

    @Override
    protected String getTexOperand() {
        return "\\ln";
    }
}
