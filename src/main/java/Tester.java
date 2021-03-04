import algo.Algorithm;
import algo.Optimization;
import algo.OptimizationResult;
import algo.Variant;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Tester {
    public static void main(String[] args) {
        VARIANTS.forEach((kV, vV) -> {
            ALGORITHMS.forEach((kA, vA) -> test(vA, kA, vV, kV));
            System.out.println();
        });
//        test(Optimization.PARABOLIC, "PARABOLIC", Variant.VAR_7, "VAR_7");
    }

    private static final Map<String, Variant> VARIANTS = Map.of(
            "VAR_1", Variant.VAR_1,
            "VAR_2", Variant.VAR_2,
            "VAR_3", Variant.VAR_3,
            "VAR_4", Variant.VAR_4,
            "VAR_5", Variant.VAR_5,
            "VAR_6", Variant.VAR_6,
            "VAR_7", Variant.VAR_7,
            "VAR_8", Variant.VAR_8,
            "VAR_9", Variant.VAR_9,
            "VAR_10", Variant.VAR_10
    );

    private static final Map<String, Algorithm> ALGORITHMS = Map.of(
            "DICHOTOMY", Optimization.DICHOTOMY,
            "GOLDEN_SECTION", Optimization.GOLDEN_SECTION,
            "FIBONACCI", Optimization.FIBONACCI,
            "PARABOLIC", Optimization.PARABOLIC,
            "BRENT", Optimization.BRENT
    );

    private static void test(Algorithm algorithm, String algoName, Variant variant, String varName) {
        OptimizationResult result = Optimization.run(algorithm, variant);
        System.out.format(Locale.US,"Algorithm %s, %s: %.18f\n", algoName, varName, result.getResult());
    }
}
