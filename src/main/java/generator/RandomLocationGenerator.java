/*
 * Copyright by The Regents of the University of California
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * you may obtain a copy of the License from
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
