package expression.expression_tools;

import expression.type.EType;

public class Logarithm<T extends Number> extends BinaryOperations<T> {
    public Logarithm(CommonExpression<T> firstExp, CommonExpression<T> secondExp) {
        super(firstExp, secondExp);
    }

    @Override
    protected EType<T> toCalculate(EType<T> firstArg, EType<T> secondArg) {
        return firstArg.log(secondArg);
//        int ans = 0;
//        while (firstArg != 0) {
//            firstArg /= secondArg;
//            ans++;
//        }
//        return ans - 1;
    }

    @Override
    protected String getOperand() {
        return "//";
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
        return false;
    }
}
