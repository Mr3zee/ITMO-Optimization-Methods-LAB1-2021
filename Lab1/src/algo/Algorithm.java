package algo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Algorithm {
    public static double dichotomy(
            Function<Double, Double> f,
            double left,
            double right,
            double epsilon
    ) {
        double x;
        do {
            x = getMiddle(left, right);
            double f1 = f.apply(x - epsilon);
            double f2 = f.apply(x + epsilon);
            if (f1 < f2) {
                left = x;
            } else {
                right = x;
            }
        } while (checkBounds(left, right, epsilon));
        return f.apply(x);
    }

    private static double getMiddle(double a, double b) {
        return (a - b) / 2 + b;
    }

    private static boolean checkBounds(double left, double right, double epsilon) {
        return Math.abs(left - right) >= epsilon;
    }

    private static final double REVERSED_GOLDEN_CONST = (Math.sqrt(5) - 1) / 2;

    public static double goldenSection(
            Function<Double, Double> f,
            double left,
            double right,
            double epsilon
    ) {
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
        } while (checkBounds(left, right, epsilon));
        return f.apply(getMiddle(left, right));
    }

    public static double fibonacci(
            Function<Double, Double> f,
            double left,
            double right,
            double epsilon
    ) {
        int n = (int) ((right - left) / epsilon) + 1;
        List<Double> fibonacci = getNFibonacci(n);
        int k = 0;
        double lambda = fibFuck(fibonacci, left, right, n - k - 2, n - k);
        double mu = fibFuck(fibonacci, left, right, n - k - 1, n - k);

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
                mu = fibFuck(fibonacci, left, right, n - k - 1, n - k);
            } else {
                right = mu;
                mu = lambda;
                lambda = fibFuck(fibonacci, left, right, n - k - 2, n - k);
            }
        }
        return f.apply(getMiddle(an, bn));
    }

    private static double fibFuck(List<Double> fibonacci, double a, double b, int i, int j) {
        return a + fibonacci.get(i) / fibonacci.get(j) * (b - a);
    }

    private static List<Double> getNFibonacci(int n) {
        List<Double> arr = new ArrayList<>(n + 1);
        arr.set(0, 1.0);
        arr.set(1, 1.0);
        for (int i = 2; i < n; i++) {
            arr.set(i, arr.get(i - 1) + arr.get(i - 2));
        }
        return arr;
    }
}
