package dev.hamishapps.buildingarchive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Persistency {

    private static final List<DataPoint> dataPoints = new ArrayList<DataPoint>();

    public Persistency(){}

    public static List<DataPoint> getDataPoints() {
        return Collections.unmodifiableList(dataPoints);
    }

    public static void addDataPoint(DataPoint dp) {
        dataPoints.add(dp);
    }

    public static void removeDataPoint(DataPoint dp) {
        dataPoints.remove(dp);
    }
}
