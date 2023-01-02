package leetcode;

import java.util.Arrays;
public class Help{
    int gcd(int a, int b) {return b == 0 ? a : gcd(b, a % b);}

    int lcm(int a, int b) {return a * b / gcd(a, b);}

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
