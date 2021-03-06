package expression.type;

import expression.exceptions.DivisionByZeroEException;

import java.math.BigInteger;

public class BigIntegerEType extends AbstractEType<BigInteger> implements ForbiddenDivisionByZero<BigInteger> {
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
    protected BigInteger calcBitCount() {
        return new BigInteger(String.valueOf(value().bitCount()));
    }

    @Override
    protected BigInteger calcAbs() {
        return value().abs();
    }

    @Override
    protected BigInteger calcPow2() {
        return value().pow(2);
    }

    @Override
    protected BigInteger calcPow(BigInteger v) {
        BigInteger result = BigInteger.ONE, acc = value();
        while (v.compareTo(BigInteger.ZERO) > 0) {
            if (v.testBit(1)) {
                result = result.multiply(acc);
            }
            acc = acc.multiply(acc);
            v = v.shiftLeft(1);
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
