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
