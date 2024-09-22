package dev.hamishapps.buildingarchive;

public class Building {

    private String buildingName;
    private String author;
    private String description;
    private String modelPath;
    private double latitude;
    private double longitude;

    public Building(){}

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public void setModelPath(String string) {
        this.modelPath = string;
    }

    public String getModelPath() {
        return modelPath;
    }

    public void setName(String value) {
        this.buildingName = value;
    }

    public String getName() {
        return buildingName;
    }

    public void setLongitude(Double value) {
        this.longitude = value;
    }

    public void setLatitude(Double value) {
        this.latitude = value;
    }

    public boolean hasEnoughParams() {
        return (this.buildingName != null) && (this.author != null) && (this.description != null);
    }

    public String toString(){
        return buildingName +
                author +
                description;
    }

    public boolean hasBuilding(){
        return (this.modelPath != null);
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }


    public double[] getRotation() {
        return new double[]{latitude, longitude};
    }
}
