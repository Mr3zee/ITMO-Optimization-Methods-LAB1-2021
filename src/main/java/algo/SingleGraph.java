package algo;

import java.util.function.Function;

public class SingleGraph extends MainGraph {
    public SingleGraph(String name, Function<Double, Double> f) {
        super(name, f);
        super.addIteration();
    }

    @Override
    public void addIteration(double left, double right) {
        throw new UnsupportedOperationException("addIteration");
    }

    @Override
    public void addIteration() {
        throw new UnsupportedOperationException("addIteration");
    }
}
