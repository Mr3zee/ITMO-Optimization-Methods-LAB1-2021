package expression.expression_tools;

import expression.type.EType;

public class Log2<T extends Number> extends UnaryOperations<T> {
    public Log2(CommonExpression<T> expression) {
        super(expression);
    }

    @Override
    protected EType<T> toCalculate(EType<T> arg) {
        return arg.log2();
    }

    @Override
    protected String getOperand() {
        return "log2 ";
    }

    @Override
    public String toTex() {
        return String.format("(\\log_2 %s)", expression.toTex());
    }

    @Override
    protected int primary() {
        return 4271;
    }

    @Override
    public int getPriority() {
        return 40;
    }

    @Override
    public boolean dependsOnOrder() {
        return false;
    }
}
