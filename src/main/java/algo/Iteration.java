package algo;

import java.util.ArrayList;
import java.util.List;

public class Iteration {
    private final double left;
    private final double right;
    private final List<Graph> graphs;

    public Iteration(double left, double right) {
        this.left = left;
        this.right = right;
        this.graphs = new ArrayList<>();
    }

    public void addGraph(Graph graph) {
        this.graphs.add(graph);
    }

    public List<Graph> getGraphs() {
        return graphs;
    }

    public double getLeft() {
        return left;
    }

    public double getRight() {
        return right;
    }
}
