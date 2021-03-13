package algo;

import java.util.ArrayList;
import java.util.List;

public class OptimizationResult {
    private final String name;
    private Double result = null;
    private Graph graph;
    private final double leftBound;
    private final double rightBound;

    OptimizationResult(String name, double leftBound, double rightBound) {
        this.name = name;
        this.leftBound = leftBound;
        this.rightBound = rightBound;
    }

    public double getLeftBound() {
        return leftBound;
    }

    public double getRightBound() {
        return rightBound;
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
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
