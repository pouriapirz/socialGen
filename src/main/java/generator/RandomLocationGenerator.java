package generator;

import java.util.Random;

import datatype.Point;

public class RandomLocationGenerator {

    private final Random random;
    private final int beginLat;
    private final int endLat;
    private final int beginLong;
    private final int endLong;

    private Point point;

    public RandomLocationGenerator(int beginLat, int endLat, int beginLong, int endLong, long seed) {
        this.beginLat = beginLat;
        this.endLat = endLat;
        this.beginLong = beginLong;
        this.endLong = endLong;
        this.point = new Point();
        this.random = new Random(seed);
    }

    public Point getRandomPoint() {
        int latMajor = beginLat + random.nextInt(endLat - beginLat);
        int latMinor = random.nextInt(10000);
        float latitude = latMajor + ((float) latMinor) / 10000;

        int longMajor = beginLong + random.nextInt(endLong - beginLong);
        int longMinor = random.nextInt(10000);
        float longitude = longMajor + ((float) longMinor) / 10000;

        point.reset(latitude, longitude);
        return point;
    }

}
