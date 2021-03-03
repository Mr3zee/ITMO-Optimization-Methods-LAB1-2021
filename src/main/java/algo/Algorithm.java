package algo;

import java.util.function.Function;

public class Algorithm {
    final Function<Variant, OptimizationResult> f;

    Algorithm(Function<Variant, OptimizationResult> f) {
        this.f = f;
    }
}
