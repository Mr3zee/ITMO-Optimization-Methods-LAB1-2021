package expression.expression_tools;

import expression.type.EType;

public class Logarithm<T extends Number> extends BinaryOperations<T> {
    public Logarithm(CommonExpression<T> firstExp, CommonExpression<T> secondExp) {
        super(firstExp, secondExp);
    }

    @Override
    protected EType<T> toCalculate(EType<T> firstArg, EType<T> secondArg) {
        return firstArg.log(secondArg);
    }

    @Override
    protected String getOperand() {
        return "//";
    }

    @Override
    public String toTex() {
        return String.format("(\\log_{%s} %s)", secondExp.toTex(), firstExp.toTex());
    }

    @Override
    protected int primary() {
        return 5693;
    }

    @Override
    public int getPriority() {
        return 30;
    }

    @Override
    public boolean dependsOnOrder() {
        return true;
    }
}
