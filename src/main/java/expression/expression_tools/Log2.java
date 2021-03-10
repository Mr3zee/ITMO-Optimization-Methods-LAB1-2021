package expression.expression_tools;

import expression.type.EType;

public class Log2<T extends Number> extends UnaryOperation<T> {
    public Log2(CommonExpression<T> expression) {
        super(expression);
    }

    @Override
    protected EType<T> toCalculate(EType<T> arg) {
        return arg.log2();
    }

    @Override
    protected String getOperand() {
        return "log2";
    }

    @Override
    public String toTex() {
        return String.format(getTexOperand(), expression.toTex());
    }

    @Override
    protected String getTexOperand() {
        return "(\\log_2 %s)";
    }

    @Override
    protected int primary() {
        return 4271;
    }
}
