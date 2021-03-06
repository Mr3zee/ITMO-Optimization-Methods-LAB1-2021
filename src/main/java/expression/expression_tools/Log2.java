package expression.expression_tools;

import expression.type.EType;

public class Log2<T extends Number> extends UnaryOperations<T> {
    public Log2(CommonExpression<T> expression) {
        super(expression);
    }

    @Override
    protected EType<T> toCalculate(EType<T> arg) {
        return arg.log2();
//        int ans = 0;
//        while (arg != 0) {
//            arg >>= 1;
//            ans++;
//        }
//        return ans - 1;
    }

    @Override
    protected String getOperand() {
        return "log2 ";
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
