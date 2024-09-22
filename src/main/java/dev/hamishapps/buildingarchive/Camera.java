package dev.hamishapps.buildingarchive;

public class Camera {
    private double posX, posY, posZ;
    private double rotX, rotY, rotZ;

    public Camera(double posX, double posY, double posZ, double rotX, double rotY, double rotZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getPosZ() {
        return posZ;
    }

    public void setPosZ(double posZ) {
        this.posZ = posZ;
    }

    public double getRotX() {
        return rotX;
    }

    public void setRotX(double rotX) {
        this.rotX = rotX;
    }

    public double getRotY() {
        return rotY;
    }

    public void setRotY(double rotY) {
        this.rotY = rotY;
    }

    public double getRotZ() {
        return rotZ;
    }

    public void setRotZ(double rotZ) {
        this.rotZ = rotZ;
    }

    @Override
    public String toString() {
        return "Camera{" +
                "posX=" + posX +
                ", posY=" + posY +
                ", posZ=" + posZ +
                ", rotX=" + rotX +
                ", rotY=" + rotY +
                ", rotZ=" + rotZ +
                '}';
    }
}
