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

import java.util.HashSet;
import java.util.Random;

public class RandomIdSelector {

    private final Random random;

    public RandomIdSelector(long seed) {
        this.random = new Random(seed);
    }

    public long[] getKFromN(int k, long n) {
        long[] result = new long[k];
        int cnt = 0;
        HashSet<Long> values = new HashSet<Long>();
        while (cnt < k) {
            long val = ((long) (random.nextDouble() * (n + 1)));
            if (values.contains(val)) {
                continue;
            }
            result[cnt++] = val;
            values.add(val);
        }
        return result;
    }
}
