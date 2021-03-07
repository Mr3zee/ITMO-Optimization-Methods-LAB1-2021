package algo;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

public enum Variant {
    VAR_1(x -> x * x + Math.exp(-0.35 * x), -2, 3, "VAR_1"),
    VAR_2(x -> Math.pow(x, 4) - 1.5 * Math.atan(x), -1, 1, "VAR_2"),
    VAR_3(x -> x * Math.sin(x) + 2 * Math.cos(x), -6, -4, "VAR_3"),
    VAR_4(x -> x - Math.log(x), 0.5, 5, "VAR_4"),
    VAR_5(x -> 10 * x * Math.log(x) - x * x / 2, 0.1, 2.5, "VAR_5"),
    VAR_6(x -> -5 * Math.pow(x, 5) + 4 * Math.pow(x, 4) - 12 * Math.pow(x, 3) + 11 * Math.pow(x, 2) - 2 * x  + 1, -0.5, 0.5, "VAR_6"),
    VAR_7(x -> Math.pow(Math.log10(x - 2), 2) + Math.pow(Math.log10(10 - x), 2) - Math.pow(x, 0.2), 6, 9.9, "VAR_7"),
    VAR_8(x -> -3 * Math.sin(0.75 * x) + Math.exp(-2 * x), 0, 2 * Math.PI, "VAR_8"),
    VAR_9(x -> Math.exp(3 * x) + 5 * Math.exp(-2 * x), 0, 1, "VAR_9"),
    VAR_10(x -> 0.2 * Math.log10(x) + Math.pow(x - 2.3, 2), 0.5, 2.5, "VAR_10"),
    CUSTOM(null, 0, 0, "CUSTOM"),
    ;

    private Function<Double, Double> f;
    private double left;
    private double right;
    private final String name;

    public Function<Double, Double> getF() {
        return f;
    }

    public double getLeft() {
        return left;
    }

    public double getRight() {
        return right;
    }

    public String getName() {
        return name;
    }

    public static final Map<String, Variant> VARIANTS = new TreeMap<>();

    public static void init() {
        VARIANTS.put(VAR_1.name, VAR_1);
        VARIANTS.put(VAR_2.name, VAR_2);
        VARIANTS.put(VAR_3.name, VAR_3);
        VARIANTS.put(VAR_4.name, VAR_4);
        VARIANTS.put(VAR_5.name, VAR_5);
        VARIANTS.put(VAR_6.name, VAR_6);
        VARIANTS.put(VAR_7.name, VAR_7);
        VARIANTS.put(VAR_8.name, VAR_8);
        VARIANTS.put(VAR_9.name, VAR_9);
        VARIANTS.put(VAR_10.name, VAR_10);
    }

    public static Variant createVariant(Function<Double, Double> f, double left, double right) {
        CUSTOM.f = f;
        CUSTOM.left = left;
        CUSTOM.right = right;
        return CUSTOM;
    }

    Variant(Function<Double, Double> f, double left, double right, String name) {
        this.f = f;
        this.left = left;
        this.right = right;
        this.name = name;
    }
}

