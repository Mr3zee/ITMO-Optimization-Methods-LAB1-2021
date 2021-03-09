package expression.expression_tools;

import expression.type.EType;

public class Negate<T extends Number> extends UnaryOperations<T> {

    public Negate(CommonExpression<T> expression) {
        super(expression);
    }

    @Override
    protected EType<T> toCalculate(EType<T> arg) {
        return arg.negate();
    }

    @Override
    protected String getOperand() {
        return "-";
    }

    @Override
    protected String getTexOperand() {
        return "-";
    }

    @Override
    public String toTex() {
        return toString();
    }

    @Override
    protected int primary() {
        return 2027;
    }

    @Override
    public String toString() {
        return String.format("-%s", expression);
    }
}
