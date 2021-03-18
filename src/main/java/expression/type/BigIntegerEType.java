package expression.type;

import expression.exceptions.DivisionByZeroEException;
import expression.exceptions.UOEException;

import java.math.BigInteger;

public class BigIntegerEType extends IntegerCalculationsType<BigInteger> implements ForbiddenDivisionByZero<BigInteger> {
    public BigIntegerEType(BigInteger value) {
        super(value);
    }

    @Override
    protected BigInteger calcAdd(BigInteger v) {
        return value().add(v);
    }

    @Override
    protected BigInteger calcSubtract(BigInteger v) {
        return value().subtract(v);
    }

    @Override
    protected BigInteger calcMultiply(BigInteger v) {
        return value().multiply(v);
    }

    @Override
    protected BigInteger calcDivide(BigInteger v) throws DivisionByZeroEException {
        checkDivisionByZero(v);
        return value().divide(v);
    }

    @Override
    protected BigInteger calcNegate() {
        return value().negate();
    }

    @Override
    protected BigInteger calcAbs() {
        return value().abs();
    }

    @Override
    protected BigInteger calcPow2() {
        return power(BigInteger.TWO, value());
    }

    @Override
    protected BigInteger calcPow(BigInteger v) {
        return power(value(), v);
    }

    private static BigInteger power(BigInteger a, BigInteger b) {
        BigInteger result = BigInteger.ONE;
        while (b.compareTo(BigInteger.ZERO) > 0) {
            if (b.testBit(1)) {
                result = result.multiply(a);
            }
            a = a.multiply(a);
            b = b.shiftLeft(1);
        }
        return result;
    }

    @Override
    protected BigInteger calcLog2() {
        return null;
    }

    @Override
    protected BigInteger calcLog(BigInteger v) {
        return null;
    }

    @Override
    public EType<BigInteger> valueOf(BigInteger v) {
        return new BigIntegerEType(v);
    }

    public static EType<BigInteger> toType(BigInteger v) {
        return new BigIntegerEType(v);
    }

    public static EType<BigInteger> parseBigInteger(String v) {
        return new BigIntegerEType(new BigInteger(v));
    }

    @Override
    public BigInteger getZero() {
        return BigInteger.ZERO;
    }

    @Override
    protected int primary() {
        return 2969;
    }
}
