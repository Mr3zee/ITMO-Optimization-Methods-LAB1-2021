package algo;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Algorithm {
    final BiFunction<Variant, Double, OptimizationResult> f;

    Algorithm(BiFunction<Variant, Double, OptimizationResult> f) {
        this.f = f;
    }
}
