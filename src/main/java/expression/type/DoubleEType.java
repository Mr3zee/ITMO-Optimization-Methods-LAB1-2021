package expression.type;

public class DoubleEType extends AbstractEType<Double> {
    public DoubleEType(Double value) {
        super(value);
    }

    @Override
    protected Double calcAdd(Double v) {
        return value() + v;
    }

    @Override
    protected Double calcSubtract(Double v) {
        return value() - v;
    }

    @Override
    protected Double calcMultiply(Double v) {
        return value() * v;
    }

    @Override
    protected Double calcDivide(Double v) {
        return value() / v;
    }

    @Override
    protected Double calcNegate() {
        return -value();
    }

    @Override
    protected Double calcBitCount() {
        return (double) Long.bitCount(Double.doubleToLongBits(value()));
    }

    @Override
    protected Double calcAbs() {
        return Math.abs(value());
    }

    @Override
    protected Double calcPow2() {
        return Math.pow(value(), 2);
    }

    @Override
    protected Double calcPow(Double v) {
        return Math.pow(value(), v);
    }

    @Override
    protected Double calcLog2() {
        return Math.log(value()) / Math.log(2);
    }

    @Override
    protected Double calcLog(Double v) {
        return Math.log(value()) / Math.log(v);
    }

    @Override
    public EType<Double> valueOf(Double v) {
        return new DoubleEType(v);
    }

    public static EType<Double> parseDouble(String v) {
        return new DoubleEType(Double.parseDouble(v));
    }

    @Override
    protected int primary() {
        return 1637;
    }
}
