package algo;

import java.util.List;
import java.util.function.Function;

public class SingleGraph extends Graph{
    public SingleGraph(String name, Function<Double, Double> f, double left, double right) {
        super(name, f);
        addIteration(left, right);
    }

    public List<DataPoint> getPoints() {
        return super.getPoints(0);
    }

    @Override
    public void addIteration(double left, double right) {
        throw new UnsupportedOperationException("addIteration");
    }

    @Override
    public List<DataPoint> getPoints(int iterationIndex) {
        throw new UnsupportedOperationException("getPoints");
    }

    @Override
    public double getLeft(int iterationIndex) {
        throw new UnsupportedOperationException("getLeft");
    }

    @Override
    public double getRight(int iterationIndex) {
        throw new UnsupportedOperationException("getRight");
    }
}
