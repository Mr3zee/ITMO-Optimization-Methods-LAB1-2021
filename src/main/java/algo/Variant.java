package algo;

import java.util.function.Function;

public enum Variant {
    VAR_1(x -> x * x + Math.exp(-0.35 * x), -2, 3),
    VAR_2(x -> Math.pow(x, 4) - 1.5 * Math.atan(x), -1, 1),
    VAR_3(x -> x * Math.sin(x) + 2 * Math.cos(x), -6, -4),
    VAR_4(x -> x - Math.log(x), 0.5, 5),
    VAR_5(x -> 10 * x * Math.log(x) - x * x / 2, 0.1, 2.5),
    VAR_6(x -> -5 * Math.pow(x, 5) + 4 * Math.pow(x, 4) - 12 * Math.pow(x, 3) + 11 * Math.pow(x, 2) - 2 * x  + 1, -0.5, 0.5),
    VAR_7(x -> Math.pow(Math.log10(x - 2), 2) + Math.pow(Math.log10(10 - x), 2) - Math.pow(x, 0.2), 6, 9.9),
    VAR_8(x -> -3 * Math.sin(0.75 * x) + Math.exp(-2 * x), 0, 2 * Math.PI),
    VAR_9(x -> Math.exp(3 * x) + 5 * Math.exp(-2 * x), 0, 1),
    VAR_10(x -> 0.2 * Math.log10(x) + Math.pow(x - 2.3, 2), 0.5, 2.5),
    ;

    public final Function<Double, Double> f;
    public double left;
    public final double right;

    Variant(Function<Double, Double> f, double left, double right) {
        assert left < right;
        this.f = f;
        this.left = left;
        this.right = right;
    }
}

