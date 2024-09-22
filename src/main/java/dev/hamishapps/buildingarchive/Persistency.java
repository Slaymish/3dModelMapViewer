package dev.hamishapps.buildingarchive;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Persistency {

    static final List<DataPoint> dataPoints = new ArrayList<DataPoint>();

    private static final Logger LOGGER = LogManager.getLogger(Persistency.class);

    public Persistency(){
        LOGGER.info("Persistency created");
    }

    /**
     * Returns an immutable list of data points
     * @return the list of data points
     */
    public static List<DataPoint> getDataPoints() {
        return Collections.unmodifiableList(dataPoints);
    }

    public static void addDataPoint(DataPoint dp) {
        dataPoints.add(dp);

        LOGGER.info("Data point added for building: {}", dp.getBuilding().getName());
    }

    /**
     * Get the most recent data point
     * @return the most recent data point
     */
    public static DataPoint getMostRecentDataPoint() {
        return new DataPoint(dataPoints.get(dataPoints.size() - 1));
    }

    /**
     * Update the data point with the same building name
     * @param dataPoint the data point to update
     */
    public static void updateDataPoint(DataPoint dataPoint) {
        for (DataPoint dp : dataPoints) {
            if (dp.getBuilding().equals(dataPoint.getBuilding())) {
                dp.setOffsets(dataPoint.getOffsets());
                dp.setBuilding(dataPoint.getBuilding());
                dp.setBuilding(dataPoint.getBuilding());
                dp.setRotationAngle(dataPoint.getRotationAngle());
                return;
            }
        }

        LOGGER.warn("Data point not found for building: {}", dataPoint.getBuilding().getName());
    }

    public static void deleteDataPoint(DataPoint dataPoint) {
        dataPoints.remove(dataPoint);
    }
}
