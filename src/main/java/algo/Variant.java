package algo;

import app.Controller;
import expression.expression_tools.DoubleEParser;
import expression.expression_tools.Expression;
import expression.type.DoubleEType;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

public enum Variant {
    VAR_1("x * x + exp(-0.35 * x)", -2d, 3d, "VAR_1"),
    VAR_2("x ^ 4 - 1.5 * arctan(x)", -1d, 1d, "VAR_2"),
    VAR_3("x * sin(x) + 2 * cos(x)", -6d, -4d, "VAR_3"),
    VAR_4("x - ln(x)", 0.5, 5d, "VAR_4"),
    VAR_5("10 * x * ln(x) - x ^ 2 / 2", 0.1, 2.5, "VAR_5"),
    VAR_6("-5 * x ^ 5 + 4 * x ^ 4 - 12 * x ^ 3 + 11 * x ^ 2 - 2 * x + 1", -0.5, 0.5, "VAR_6"),
    VAR_7("((x - 2) // 10 ) ^ 2 + ((10 - x) // 10) ^ 2", 6d, 9.9, "VAR_7"),
    VAR_8("-3 * sin(0.75 * x) + exp(-2 * x)", 0d, 2 * Math.PI, "VAR_8"),
    VAR_9("exp(3 * x) + 5 * (exp-2 * x)", 0d, 1d, "VAR_9"),
    VAR_10("0.2 * (x // 10) + (x - 2.3) ^ 2", 0.5, 2.5, "VAR_10"),
    CUSTOM(null, null, null, "CUSTOM"),
    ERROR(null, null, null, "ERROR"),
    ;

    private Expression<Double> f;
    private Double left;
    private Double right;
    private final String name;

    public Function<Double, Double> getFunction() {
        return f.toFunction(DoubleEType::toType);
    }

    public Double getLeft() {
        return left;
    }

    public Double getRight() {
        return right;
    }

    public String getName() {
        return name;
    }

    public String getTex() {
        return f.toTex();
    }

    public static final Map<String, Variant> VARIANTS = new TreeMap<>();

    private static final DoubleEParser PARSER = DoubleEParser.getInstance();

    public static Variant createVariant(String f, Double left, Double right) {
        CUSTOM.f = PARSER.parse(f);
        CUSTOM.left = left;
        CUSTOM.right = right;
        return CUSTOM;
    }

    Variant(String f, Double left, Double right, String name) {
        this.f = DoubleEParser.getInstance().parse(f);
        this.left = left;
        this.right = right;
        this.name = name;
    }

    @Override
    public String toString() {
        return f.toString();
    }

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
}

