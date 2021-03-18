package expression.type;

import expression.exceptions.UOEException;

import java.math.BigInteger;

public abstract class IntegerCalculationsType<T extends Number> extends AbstractEType<T> {
    public IntegerCalculationsType(T value) {
        super(value);
    }

    @Override
    protected T calcLn() {
        throw new UOEException("ln");
    }

    @Override
    protected T calcExp() {
        throw new UOEException("exp");
    }

    @Override
    protected T calcSin() {
        throw new UOEException("sin");
    }

    @Override
    protected T calcAsin() {
        throw new UOEException("arcsin");
    }

    @Override
    protected T calcCos() {
        throw new UOEException("cos");
    }

    @Override
    protected T calcAcos() {
        throw new UOEException("arccos");
    }

    @Override
    protected T calcTan() {
        throw new UOEException("tan");
    }

    @Override
    protected T calcAtan() {
        throw new UOEException("arctan");
    }
}
