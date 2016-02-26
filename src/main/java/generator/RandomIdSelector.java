package generator;

import java.util.HashSet;
import java.util.Random;

public class RandomIdSelector {

    private static Random random = new Random();

    public static long[] getKFromN(int k, long n) {
        if (n < k) {
            throw new IllegalArgumentException("n < k");
        }
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
