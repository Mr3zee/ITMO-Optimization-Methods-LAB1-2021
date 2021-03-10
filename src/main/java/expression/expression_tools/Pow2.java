package expression.expression_tools;

import expression.type.EType;

public class Pow2<T extends Number> extends UnaryOperation<T> {
    public Pow2(CommonExpression<T> expression) {
        super(expression);
    }

    @Override
    protected EType<T> toCalculate(EType<T> arg) {
        return arg.pow2();
    }

    @Override
    protected String getOperand() {
        return "pow2";
    }

    @Override
    public String toTex() {
        return String.format(getTexOperand(), expression.toTex());
    }

    @Override
    protected String getTexOperand() {
        return "(2 ^ %s)";
    }

    @Override
    protected int primary() {
        return 4079;
    }

}
