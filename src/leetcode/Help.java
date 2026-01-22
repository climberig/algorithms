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

    boolean isPrime(int p) {
        for (int d = 2; d <= Math.sqrt(p); d++)
            if (p % d == 0)
                return false;
        return p > 1;
    }

    boolean isVowel(char c){
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }
}
