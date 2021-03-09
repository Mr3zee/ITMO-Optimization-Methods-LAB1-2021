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
    protected Double calcAbs() {
        return Math.abs(value());
    }

    @Override
    protected Double calcPow2() {
        return Math.pow(2, value());
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
    protected Double calcLn() {
        return Math.log(value());
    }

    @Override
    protected Double calcExp() {
        return Math.exp(value());
    }

    @Override
    protected Double calcSin() {
        return Math.sin(value());
    }

    @Override
    protected Double calcAsin() {
        return Math.asin(value());
    }

    @Override
    protected Double calcCos() {
        return Math.cos(value());
    }

    @Override
    protected Double calcAcos() {
        return Math.acos(value());
    }

    @Override
    protected Double calcTan() {
        return Math.tan(value());
    }

    @Override
    protected Double calcAtan() {
        return Math.atan(value());
    }

    @Override
    public EType<Double> valueOf(Double v) {
        return new DoubleEType(v);
    }

    public static EType<Double> toType(Double v) {
        return new DoubleEType(v);
    }

    public static EType<Double> parseDouble(String v) {
        return new DoubleEType(Double.parseDouble(v));
    }

    @Override
    protected int primary() {
        return 1637;
    }

    @Override
    public String toString() {
        double v = value();
        return v == (long) v
                ? String.format("%d", (long) v)
                : String.format("%s", v);
    }
}
