package algo;

import java.util.ArrayList;
import java.util.List;

public class Iteration {
    private final double left;
    private final double right;
    private final List<SingleGraph> graphs;
    private final List<VLineGraph> vLineGraphs;

    public Iteration(double left, double right) {
        this.left = left;
        this.right = right;
        this.graphs = new ArrayList<>();
        this.vLineGraphs = new ArrayList<>();
        this.vLineGraphs.add(new VLineGraph("Left", left));
        this.vLineGraphs.add(new VLineGraph("Right", right));
    }

    public void addGraph(SingleGraph graph) {
        this.graphs.add(graph);
    }

    public List<SingleGraph> getSingleGraphs() {
        return graphs;
    }

    public List<VLineGraph> getVLineGraphs() {
        return vLineGraphs;
    }

    public double getLeft() {
        return left;
    }

    public double getRight() {
        return right;
    }
}
