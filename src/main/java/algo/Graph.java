package algo;

import java.util.List;

public interface Graph {
    String getName();

    List<DataPoint> getPoints(double lowerBound, double upperBound);
}
