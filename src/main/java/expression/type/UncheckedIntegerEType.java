package expression.type;

import expression.exceptions.DivisionByZeroEException;

public class UncheckedIntegerEType extends AbstractEType<Integer> implements ForbiddenDivisionByZero<Integer> {
    public UncheckedIntegerEType(Integer value) {
        super(value);
    }

    @Override
    protected Integer calcAdd(Integer v) {
        return value() + v;
    }

    @Override
    protected Integer calcSubtract(Integer v) {
        return value() - v;
    }

    @Override
    protected Integer calcMultiply(Integer v) {
        return value() * v;
    }

    @Override
    protected Integer calcDivide(Integer v) throws DivisionByZeroEException {
        checkDivisionByZero(v);
        return value() / v;
    }

    @Override
    protected Integer calcNegate() {
        return -value();
    }

    @Override
    protected Integer calcBitCount() {
        return Integer.bitCount(value());
    }

    @Override
    protected Integer calcAbs() {
        return Math.abs(value());
    }

    @Override
    protected Integer calcPow2() {
        return (int) Math.pow(value(), 2);
    }

    @Override
    protected Integer calcPow(Integer v) {
        return (int) Math.pow(value(), v);
    }

    @Override
    protected Integer calcLog2() {
        return (int) (Math.log(value()) / Math.log(2));
    }

    @Override
    protected Integer calcLog(Integer v) {
        return (int) (Math.log(value()) / Math.log(v));
    }

    @Override
    public EType<Integer> valueOf(Integer v) {
        return new UncheckedIntegerEType(v);
    }

    public static EType<Integer> parseUncheckedInteger(String v) {
        return new UncheckedIntegerEType(Integer.parseInt(v));
    }

    @Override
    protected int primary() {
        return 2213;
    }

    @Override
    public Integer getZero() {
        return 0;
    }
}
