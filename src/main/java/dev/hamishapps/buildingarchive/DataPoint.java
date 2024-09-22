package dev.hamishapps.buildingarchive;

public class DataPoint {

    private Building building;

    // vec3 of offsets from buildings lat/long
    private double[] offsets;

    public DataPoint(Building building){
        this.building = building;
        offsets = new double[]{0, 0, 0};
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Building getBuilding() {
        if (building == null)
            throw new NullPointerException("building not set");

        return building;
    }

    public void setOffsets(double[] offsets) {
        this.offsets = offsets;
    }

    public double[] getOffsets() {
        return offsets;
    }

    public String toString(){
        return building.toString() +
                "\n" +
                "X:" + offsets[0] +
                "\n" +
                "Y:" + offsets[1] +
                "\n" +
                "Z:" + offsets[2] +
                "\n";
    }
}
