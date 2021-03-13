package algo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class MainGraph implements Graph {
    private final String name;
    private final Function<Double, Double> f;
    private final List<Iteration> iterations;

    public MainGraph(String name, Function<Double, Double> f) {
        this.name = name;
        this.f = f;
        this.iterations = new ArrayList<>();
    }

    public void addIteration(double left, double right) {
        iterations.add(new Iteration(left, right));
    }

    public void addIteration() {
        iterations.add(new Iteration());
    }

    public void addGraphToLastIteration(SingleGraph graph) {
        iterations.get(iterations.size() - 1).addGraph(graph);
    }

    public void addVLineGraphToLastIteration(VLineGraph graph) {
        iterations.get(iterations.size() - 1).addVLineGraph(graph);
    }

    public Iteration getIteration(int iterationIndex) {
        return iterations.get(iterationIndex);
    }

    public String getName() {
        return name;
    }

    public List<DataPoint> getPoints(double left, double right) {
        List<DataPoint> list = new ArrayList<>();
        double delta = (right - left) / 500;
        for (double i = left; i < right; i += delta) {
            DataPoint dataPoint = new DataPoint(i, f.apply(i));
            list.add(dataPoint);
        }
        return list;
    }

    public double getMax(double left, double right) {
        return getExtremum(Double.MIN_VALUE, Math::max, left, right);
    }

    public double getMin(double left, double right) {
        return getExtremum(Double.MAX_VALUE, Math::min, left, right);
    }

    private double getExtremum(double initial, BinaryOperator<Double> optimization, double left, double right) {
        double delta = (right - left) / 500;
        for (double i = left; i < right; i += delta) {
            double y =  f.apply(i);
            if (!Double.isNaN(y)) {
                initial = optimization.apply(initial, y);
            }
        }
        return initial;
    }

    public int getNIterations() {
        return iterations.size();
    }
}