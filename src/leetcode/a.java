package leetcode;

import java.util.Arrays;
import java.util.stream.IntStream;
public class a{
    static class s2523{//Closest Prime Numbers in Range
        public int[] closestPrimes(int left, int right) {
            boolean[] prime = primes(right + 1);
            int minDiff = Integer.MAX_VALUE, a = IntStream.range(left, right + 1).filter(n -> prime[n]).findFirst().orElse(0), r[] = {-1, -1};
            for (int i = a + 1; i <= right; i++)
                if (prime[i]) {
                    if (i - a < minDiff) {
                        minDiff = i - a;
                        r = new int[]{a, i};
                    }
                    a = i;
                }
            return r;
        }
        boolean[] primes(int n) {
            boolean[] prime = new boolean[n + 1];
            Arrays.fill(prime, 2, prime.length, true);
            for (int p = 2; p * p <= n; p++)
                if (prime[p])
                    for (int i = p * p; i <= n; i += p)
                        prime[i] = false;
            return prime;
        }
    }
}
