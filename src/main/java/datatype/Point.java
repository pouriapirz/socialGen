package datatype;

public class Point {

    private float latitude;
    private float longitude;

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public Point(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void reset(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Point() {
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("point(\"" + latitude + "," + longitude + "\")");
        return builder.toString();
    }
}
