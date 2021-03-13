package algo;

import java.util.ArrayList;
import java.util.List;

public class Iteration {
    private final List<SingleGraph> graphs;
    private final List<VLineGraph> vLineGraphs;

    public Iteration(double left, double right) {
        this();
        this.vLineGraphs.add(new VLineGraph("Left", left));
        this.vLineGraphs.add(new VLineGraph("Right", right));
    }

    public Iteration() {
        this.graphs = new ArrayList<>();
        this.vLineGraphs = new ArrayList<>();
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
}
