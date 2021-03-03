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
            printBounds(left, right);
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
        double delta = (right - left) * REVERSED_GOLDEN_CONST;
        double x1 = left + delta;
        double x2 = right - delta;
        do {
            if (f.apply(x1) > f.apply(x2)) {
                left = x1;
                x1 = x2;
                x2 = right + left - x1;
            } else {
                right = x2;
                x2 = x1;
                x1 = right + left - x2;
            }
            printBounds(left, right);
        } while (checkBounds(left, right, epsilon));
        return new OptimizationResult(f.apply(getMiddle(left, right)));
    });

    public static final Algorithm FIBONACCI = unwrappedAlgo((f, left, right) -> {
        int n = (int) ((right - left) / epsilon) + 1;
        List<Double> fibonacci = getNFibonacci(n);
        int k = 0;
        double lambda = getFibonacciVar(fibonacci, left, right, n - k - 2, n - k);
        double mu = getFibonacciVar(fibonacci, left, right, n - k - 1, n - k);

        double an, bn;
        while (true) {
            k++;
            if (k == n - 2) {
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
                mu = getFibonacciVar(fibonacci, left, right, n - k - 1, n - k);
            } else {
                right = mu;
                mu = lambda;
                lambda = getFibonacciVar(fibonacci, left, right, n - k - 2, n - k);
            }
            printBounds(left, right);
        }
        return new OptimizationResult(f.apply(getMiddle(an, bn)));
    });

    private static double getFibonacciVar(List<Double> fibonacci, double a, double b, int i, int j) {
        return a + fibonacci.get(i) / fibonacci.get(j) * (b - a);
    }

    private static List<Double> getNFibonacci(int n) {
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
