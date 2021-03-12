package algo;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
interface SenaryFunction<A, B, C, D, E, F, R> {

    R apply(A a, B b, C c, D d, E e, F f);

    default <V> SenaryFunction<A, B, C, D, E, F, V> andThen(
            Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (A a, B b, C c, D d, E e, F f) -> after.apply(apply(a, b, c, d, e, f));
    }
}
