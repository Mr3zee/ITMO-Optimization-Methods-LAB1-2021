package algo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Optimization {

    private static double epsilon = 0.00001;

    public static void setEpsilon(double epsilon) {
        Optimization.epsilon = epsilon;
    }

    private static Algorithm unwrappedAlgo(TFunction<Function<Double, Double>, Double, Double, OptimizationResult> algo) {
        return new Algorithm(variant -> algo.apply(variant.f, variant.left, variant.right));
    }

    public static OptimizationResult run(Algorithm function, Variant variant) {
        return function.f.apply(variant);
    }

    public static final Algorithm DICHOTOMY = unwrappedAlgo((f, left, right) -> {
        double x;
        do {
            x = getMiddle(left, right);
            double f1 = f.apply(x - epsilon);
            double f2 = f.apply(x + epsilon);
            if (f1 < f2) {
                right = x;
            } else {
                left = x;
            }
        } while (checkBounds(left, right, epsilon));
        return new OptimizationResult(f.apply(x));
    });

    private static double getMiddle(double a, double b) {
        return (a - b) / 2 + b;
    }

    private static boolean checkBounds(double left, double right, double epsilon) {
        return Math.abs(left - right) >= epsilon;
    }

    private static final double REVERSED_GOLDEN_CONST = (Math.sqrt(5) - 1) / 2;

    public static final Algorithm GOLDEN_SECTION = unwrappedAlgo((f, left, right) -> {
        do {
            double delta = (right - left) * REVERSED_GOLDEN_CONST;
            double x1 = right - delta;
            double x2 = left + delta;
            if (f.apply(x1) > f.apply(x2)) {
                left = x1;
            } else {
                right = x2;
            }
        } while (checkBounds(left, right, epsilon));
        return new OptimizationResult(f.apply(getMiddle(left, right)));
    });

    private static final int FIBONACCI_ITERATIONS = 1475;

    public static final Algorithm FIBONACCI = unwrappedAlgo((f, left, right) -> {
        List<Double> fibonacci = getNFibonacci(FIBONACCI_ITERATIONS);
        int k = 0;
        double lambda = getFibonacciVar(fibonacci, left, right, FIBONACCI_ITERATIONS - k - 2, FIBONACCI_ITERATIONS - k);
        double mu = getFibonacciVar(fibonacci, left, right, FIBONACCI_ITERATIONS - k - 1, FIBONACCI_ITERATIONS - k);

        double an, bn;
        while (true) {
            k++;
            if (k == FIBONACCI_ITERATIONS - 2) {
                mu = lambda + epsilon;
                if (f.apply(mu) >= f.apply(lambda)) {
                    an = lambda;
                    bn = right;
                } else {
                    an = left;
                    bn = mu;
                }
                break;
            }
            if (f.apply(lambda) > f.apply(mu)) {
                left = lambda;
                lambda = mu;
                mu = getFibonacciVar(fibonacci, left, right, FIBONACCI_ITERATIONS - k - 1, FIBONACCI_ITERATIONS - k);
            } else {
                right = mu;
                mu = lambda;
                lambda = getFibonacciVar(fibonacci, left, right, FIBONACCI_ITERATIONS - k - 2, FIBONACCI_ITERATIONS - k);
            }
            printBounds(left, right);
        }
        return new OptimizationResult(f.apply(getMiddle(an, bn)));
    });

    private static double getFibonacciVar(List<Double> fibonacci, double a, double b, int i, int j) {
        return a + fibonacci.get(i) / fibonacci.get(j) * (b - a);
    }

    public static List<Double> getNFibonacci(int n) {
        List<Double> arr = new ArrayList<>(n + 1);
        arr.add(1.0);
        arr.add(1.0);
        for (int i = 2; i <= n; i++) {
            arr.add(arr.get(i - 1) + arr.get(i - 2));
        }
        return arr;
    }

    private static void printBounds(double left, double right) {
        System.out.format("[%s, %s]\n", left, right);
    }
}
