package algo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Graph{
    private final String name;
    private final Function<Double, Double> f;
    private final double left;
    private final double right;

    public Graph(String name, Function<Double, Double> f, double left, double right) {
        this.name = name;
        this.f = f;
        this.left = left;
        this.right = right;
    }

    public String getName() {
        return name;
    }

    public List<DataPoint> getPoints() {
        List<DataPoint> list = new ArrayList<>();
        for (double i = left; i < right; i += 0.1) {
            list.add(new DataPoint(i, f.apply(i)));
        }
        return list;
    }
}