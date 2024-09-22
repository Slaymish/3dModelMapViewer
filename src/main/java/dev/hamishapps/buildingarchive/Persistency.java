package dev.hamishapps.buildingarchive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Persistency {

    static final List<DataPoint> dataPoints = new ArrayList<DataPoint>();

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

    public static DataPoint getMostRecentDataPoint() {
        return new DataPoint(dataPoints.get(dataPoints.size() - 1));
    }

    public static void updateDataPoint(DataPoint dataPoint) {
        for (DataPoint dp : dataPoints) {
            if (dp.getBuilding().equals(dataPoint.getBuilding())) {
                dp.setOffsets(dataPoint.getOffsets());
                dp.setBuilding(dataPoint.getBuilding());
                dp.setBuilding(dataPoint.getBuilding());
            }
        }
    }
}
