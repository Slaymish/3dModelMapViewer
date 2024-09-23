package dev.hamishapps.buildingarchive.scene;

import dev.hamishapps.buildingarchive.Building;
import dev.hamishapps.buildingarchive.DataPoint;
import dev.hamishapps.buildingarchive.Persistency;

import java.util.List;

import org.apache.logging.log4j.*;

public class MapExplorer {
    //private Object worldSphere;

    // logger
    private static final Logger LOGGER = LogManager.getLogger(MapExplorer.class);

    public MapExplorer() {
        // set appenders
        LOGGER.info("MapExplorer created");

    }

    // Explore the map
    public void exploreMap() {
        System.out.println("Exploring the map...");
        for (DataPoint dataPoint : Persistency.getDataPoints()) {
            placeBuildingOnSphere(dataPoint);
        }
        selectBuilding();
    }

    // Place buildings on the sphere (with position and rotation)
    private void placeBuildingOnSphere(DataPoint dataPoint) {
        Building building = dataPoint.getBuilding();
        double latitude = building.getLatitude();
        double longitude = building.getLongitude();
        double[] offsets = dataPoint.getOffsets();
        double rotation = dataPoint.getRotationAngle();

        // Convert lat/long to Cartesian coordinates
        double radius = 6371; // Earth's radius in km
        double x = radius * Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(longitude));
        double y = radius * Math.sin(Math.toRadians(latitude));
        double z = radius * Math.cos(Math.toRadians(latitude)) * Math.sin(Math.toRadians(longitude));

        // Apply offsets
        if (offsets != null && offsets.length == 3) {
            x += offsets[0];
            y += offsets[1];
            z += offsets[2];
        }

        // Apply rotation
        LOGGER.info("Placing building '{}' at ({}, {}, {}) with rotation {} degrees", building.getName(), x, y, z, rotation);
    }

    // Select a building
    private void selectBuilding() {
        List<DataPoint> dataPointList = Persistency.getDataPoints();
        if (!dataPointList.isEmpty()) {
            DataPoint selectedDataPoint = dataPointList.get(0);
            viewBuildingDetails(selectedDataPoint);
        } else {
            LOGGER.warn("No buildings found in the data points list");
        }
    }

    // View building details
    private void viewBuildingDetails(DataPoint dataPoint) {
        Building building = dataPoint.getBuilding();
        LOGGER.info("Viewing building details for '{}'", building.getName());
    }

    // Upload a new building
    public void uploadBuilding(Building building) {
        DataPoint newPoint = new DataPoint(building, new double[]{0, 0, 0}, 0);
        Persistency.addDataPoint(newPoint);
        LOGGER.info("Uploaded building: {}", building.getName());
    }

    // Adjust position
    public void adjustBuildingPosition(DataPoint dataPoint, double[] newOffsets) {
        dataPoint.setOffsets(newOffsets);
        LOGGER.info("Adjusted position for building: {}", dataPoint.getBuilding().getName());
    }

    // Adjust rotation
    public void adjustBuildingRotation(DataPoint dataPoint, double newRotation) {
        dataPoint.setRotationAngle(newRotation);
        LOGGER.info("Adjusted rotation for building: {}", dataPoint.getBuilding().getName());
    }

    // Delete a building
    public void deleteBuilding(DataPoint dataPoint) {
        Persistency.deleteDataPoint(dataPoint);
        LOGGER.info("Deleted building: {}", dataPoint.getBuilding().getName());
    }
}
