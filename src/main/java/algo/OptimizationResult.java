package algo;

import java.util.ArrayList;
import java.util.List;

public class OptimizationResult {
    private final String name;
    private Double result = null;
    private final List<Graph> graphs;
    private final double leftBound;
    private final double rightBound;

    OptimizationResult(String name, double leftBound, double rightBound) {
        this.name = name;
        this.graphs = new ArrayList<>();
        this.leftBound = leftBound;
        this.rightBound = rightBound;
    }

    public double getLeftBound() {
        return leftBound;
    }

    public double getRightBound() {
        return rightBound;
    }

    public List<Graph> getGraphs() {
        return graphs;
    }

    public void addGraph(Graph graph) {
        this.graphs.add(graph);
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public double getResult() {
        return result;
    }
}
