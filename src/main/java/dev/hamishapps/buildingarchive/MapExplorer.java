package dev.hamishapps.buildingarchive;

import static dev.hamishapps.buildingarchive.Persistency.dataPoints;

public class MapExplorer {
    private Object worldSphere;

    public MapExplorer() {
        createWorldSphere();
    }

    // Create the world sphere
    private void createWorldSphere() {
        System.out.println("World sphere created.");
    }

    // Explore the map
    public void exploreMap() {
        System.out.println("Exploring the map...");
        for (DataPoint dataPoint : dataPoints) {
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
        System.out.printf("Placing building '%s' at (%.2f, %.2f, %.2f) with rotation %.2f degrees\n",
                building.getName(), x, y, z, rotation);
    }

    // Select a building
    private void selectBuilding() {
        if (!dataPoints.isEmpty()) {
            DataPoint selectedDataPoint = dataPoints.get(0);
            viewBuildingDetails(selectedDataPoint);
        } else {
            System.out.println("No buildings available.");
        }
    }

    // View building details
    private void viewBuildingDetails(DataPoint dataPoint) {
        Building building = dataPoint.getBuilding();
        System.out.println("Selected Building: " + building.getName());
        System.out.println("Author: " + building.getAuthor());
        System.out.println("Description: " + building.getDescription());
    }

    // Upload a new building
    public void uploadBuilding(Building building) {
        DataPoint newPoint = new DataPoint(building, new double[]{0, 0, 0}, 0);
        dataPoints.add(newPoint);
        System.out.println("Uploaded building: " + building.getName());
    }

    // Adjust position
    public void adjustBuildingPosition(DataPoint dataPoint, double[] newOffsets) {
        dataPoint.setOffsets(newOffsets);
        System.out.println("Adjusted position for building: " + dataPoint.getBuilding().getName());
    }

    // Adjust rotation
    public void adjustBuildingRotation(DataPoint dataPoint, double newRotation) {
        dataPoint.setRotationAngle(newRotation);
        System.out.println("Adjusted rotation for building: " + dataPoint.getBuilding().getName());
    }

    // Delete a building
    public void deleteBuilding(DataPoint dataPoint) {
        dataPoints.remove(dataPoint);
        System.out.println("Deleted building: " + dataPoint.getBuilding().getName());
    }
}
