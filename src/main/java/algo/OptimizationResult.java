package algo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.DoubleStream;

public class OptimizationResult {
    private final String name;
    private Double result = null;
    private final List<Graph> graphs;

    OptimizationResult(String name) {
        this.name = name;
        this.graphs = new ArrayList<>();
    }

    public void addGraph(Graph graph) {
        graphs.add(graph);
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public List<Graph> getGraphs() {
        return graphs;
    }

    public String getName() {
        return name;
    }

    public double getResult() {
        return result;
    }
}
