package expression.type;

public interface EType<T extends Number> {

    T value();

    EType<T> add(EType<T> v);

    EType<T> subtract(EType<T> v);

    EType<T> divide(EType<T> v);

    EType<T> multiply(EType<T> v);

    EType<T> negate();

    EType<T> valueOf(T v);

    EType<T> abs();

    EType<T> pow2();

    EType<T> pow(EType<T> v);

    EType<T> log2();

    EType<T> log(EType<T> v);

    // TODO: 06.03.2021 ask Gosha
}
