package leetcode;
import java.util.*;
import java.util.stream.IntStream;
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

    static class s2606{//Find the Substring With Maximum Cost
        public int maximumCostSubstring(String s, String chars, int[] vals) {
            Map<Character, Integer> m = new HashMap<>();
            for (int i = 0; i < chars.length(); i++)
                m.put(chars.charAt(i), vals[i]);
            int r = 0, cur = 0;
            for (int i = 0; i < s.length(); ++i) {
                cur = Math.max(cur + m.getOrDefault(s.charAt(i), s.charAt(i) - 'a' + 1), 0);
                r = Math.max(r, cur);
            }
            return r;
        }
    }

    static class s2609{//Find the Longest Balanced Substring of a Binary String
        public int findTheLongestBalancedSubstring(String s) {
            char[] a = s.toCharArray();
            int r = 0;
            for (int i = 1; i < a.length; i++)
                r = Math.max(r, balanceLen(i - 1, i, a));
            return r;
        }
        int balanceLen(int i, int j, char[] a) {
            int r = 0;
            for (; i >= 0 && j < a.length; i--, j++, r += 2)
                if (a[i] != '0' || a[j] != '1')
                    return r;
            return r;
        }
    }

    static class s2610{//Convert an Array Into a 2D Array With Conditions
        public List<List<Integer>> findMatrix(int[] a) {
            List<List<Integer>> r = new ArrayList<>();
            int[] count = new int[201];
            Arrays.stream(a).forEach(n -> count[n]++);
            for (int n = 1; n < count.length; n++)
                for (int i = 0; i < count[n]; i++) {
                    if (r.size() == i)
                        r.add(new ArrayList<>());
                    r.get(i).add(n);
                }
            return r;
        }
    }

    static class s2611{//Mice and Cheese
        public int miceAndCheese(int[] reward1, int[] reward2, int k) {
            int r = 0, diff[] = new int[reward1.length];
            for (int i = 0; i < diff.length; i++) {
                r += reward2[i];
                diff[i] = reward1[i] - reward2[i];
            }
            Arrays.sort(diff);
            for (int i = diff.length - 1; k > 0; i--, k--)
                r += diff[i];
            return r;
        }
    }

    static class s2614{//Prime In Diagonal
        public int diagonalPrime(int[][] a) {
            int r = 0;
            for (int i = 0, j = 0, k = a.length - 1; i < a.length; i++, j++, k--) {
                r = Math.max(r, prime(a[i][j]));
                r = Math.max(r, prime(a[i][k]));
            }
            return r;
        }

        int prime(int p) {
            for (int d = 2; d <= Math.sqrt(p); d++)
                if (p % d == 0)
                    return 0;
            return p > 1 ? p : 0;
        }
    }

    static class s2615{//Sum of Distances
        public long[] distance(int[] a) {
            long[] r1 = distance(a, 0, 1), r2 = distance(a, a.length - 1, -1);
            return IntStream.range(0, a.length).mapToLong(i -> r1[i] + r2[i]).toArray();
        }
        public long[] distance(int[] a, int start, int inc) {
            long[] r = new long[a.length];
            Map<Integer, Integer> counts = new HashMap<>(), lastIdx = new HashMap<>();
            Map<Integer, Long> lastSum = new HashMap<>();
            for (int i = start; i >= 0 && i < a.length; i += inc) {
                if (lastIdx.containsKey(a[i]))
                    r[i] = lastSum.get(a[i]) + (long) counts.get(a[i]) * Math.abs(i - lastIdx.get(a[i]));
                lastIdx.put(a[i], i);
                counts.put(a[i], counts.getOrDefault(a[i], 0) + 1);
                lastSum.put(a[i], r[i]);
            }
            return r;
        }
    }

    static class s2639{//Find the Width of Columns of a Grid
        public int[] findColumnWidth(int[][] g) {
            int[] r = new int[g[0].length];
            Arrays.fill(r, 1);
            for (int j = 0; j < r.length; j++)
                for (int[] row : g)
                    if (row[j] != 0)
                        r[j] = Math.max(r[j], 1 + (int) Math.log10(Math.abs(row[j])) + (row[j] < 0 ? 1 : 0));
            return r;
        }
    }

    static class s2640{//Find the Score of All Prefixes of an Array
        public long[] findPrefixScore(int[] a) {
            long r[] = new long[a.length], sum = 0, max = 0;
            for (int i = 0; i < a.length; i++) {
                max = Math.max(max, a[i]);
                sum += a[i] + max;
                r[i] = sum;
            }
            return r;
        }
    }
}
