package datatype;

import socialGen.ADMAppendVisitor;
import socialGen.IAppendVisitor;

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
        return accept(new ADMAppendVisitor()).toString();
    }

    public IAppendVisitor accept(IAppendVisitor visitor) {
        return visitor.append("point(\"" + latitude + "," + longitude + "\")");
    }
}
