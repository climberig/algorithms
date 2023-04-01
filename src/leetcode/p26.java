package leetcode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class p26{
    static class s2600{//K Items With the Maximum Sum
        public int kItemsWithMaximumSum(int nOnes, int nZero, int nNegOnes, int k) {
            return Math.min(nOnes, k) - Math.max(0, k - nOnes - nZero);
        }
    }

    static class s2601{//Prime Subtraction Operation
        public boolean primeSubOperation(int[] a) {
            int prev = 0;
            for (int n : a) {
                int maxP = 0;
                for (int p = 2; p < n; p++)
                    if (n - p > prev && prime(p))
                        maxP = p;
                if (prev >= n - maxP)
                    return false;
                prev = n - maxP;
            }
            return true;
        }
        boolean prime(int p) {
            for (int d = 2; d <= Math.sqrt(p); d++)
                if (p % d == 0)
                    return false;
            return true;
        }
    }

    static class s2602{//Minimum Operations to Make All Array Elements Equal
        public List<Long> minOperations(int[] a, int[] queries) {
            Arrays.sort(a);
            List<Long> r = new ArrayList<>();
            long[] cs = new long[a.length + 1];
            for (int i = 1; i <= a.length; i++)
                cs[i] = cs[i - 1] + a[i - 1];
            for (int x : queries) {
                int i = Arrays.binarySearch(a, x);
                if (i < 0)
                    i = -(i + 1);
                r.add(1L * x * (2 * i - a.length) + cs[a.length] - 2 * cs[i]);
            }
            return r;
        }
    }

    static class s2605{//Form Smallest Number From Two Digit Arrays
        public int minNumber(int[] a1, int[] a2) {
            int[] d = new int[10];
            Arrays.stream(a1).forEach(n -> d[n]++);
            Arrays.stream(a2).forEach(n -> d[n]++);
            for (int n = 1; n <= 9; n++)
                if (d[n] == 2)
                    return n;
            Arrays.sort(a1);
            Arrays.sort(a2);
            return 10 * Math.min(a1[0], a2[0]) + Math.max(a1[0], a2[0]);
        }
    }
}
