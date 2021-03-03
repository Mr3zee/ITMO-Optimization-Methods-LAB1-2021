package algo;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
interface TFunction<A,B,C,R> {

    R apply(A a, B b, C c);

    default <V> TFunction<A, B, C, V> andThen(
            Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (A a, B b, C c) -> after.apply(apply(a, b, c));
    }
}
