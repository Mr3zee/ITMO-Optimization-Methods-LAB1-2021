package algo;

import java.util.List;

public class VLineGraph implements Graph {
    private final String name;
    private final double xPoint;

    public VLineGraph(final String name, final double xPoint) {
        this.name = name;
        this.xPoint = xPoint;
    }

    public String getName() {
        return name;
    }

    public List<DataPoint> getPoints(double bottom, double top) {
        return List.of(new DataPoint(xPoint, top), new DataPoint(xPoint, bottom));
    }
}
