package algo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Graph{
    private final String name;
    private final Function<Double, Double> f;
    private final List<Iteration> iterations;

    public Graph(String name, Function<Double, Double> f) {
        this.name = name;
        this.f = f;
        this.iterations = new ArrayList<>();
    }

    public void addIteration(double left, double right) {
        iterations.add(new Iteration(left, right));
    }

    public void addGraphToLastIteration(SingleGraph graph) {
        iterations.get(iterations.size() - 1).addGraph(graph);
    }

    public String getName() {
        return name;
    }

    public List<DataPoint> getPoints(int iterationIndex) {
        Iteration iteration = iterations.get(iterationIndex);
        double left = iteration.getLeft();
        double right = iteration.getRight();
        List<DataPoint> list = new ArrayList<>();
        for (double i = left; i < right; i += 0.01) {
            list.add(new DataPoint(i, f.apply(i)));
        }
        return list;
    }

    public double getLeft(int iterationIndex) {
        return iterations.get(iterationIndex).getLeft();
    }

    public double getRight(int iterationIndex) {
        return iterations.get(iterationIndex).getRight();
    }
}