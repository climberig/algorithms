package leetcode;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
public class p29{
    static class s2908{//Minimum Sum of Mountain Triplets I
        public int minimumSum(int[] a) {
            int minLeft = a[0], r = Integer.MAX_VALUE;
            for (int i = 1; i < a.length - 1; i++) {
                int minRight = a[i + 1];
                for (int j = i + 1; j < a.length; j++)
                    minRight = Math.min(a[j], minRight);
                if (minLeft < a[i] && a[i] > minRight)
                    r = Math.min(r, minLeft + a[i] + minRight);
                minLeft = Math.min(minLeft, a[i]);
            }
            return r == Integer.MAX_VALUE ? -1 : r;
        }
    }

    static class s2914{//Minimum Number of Changes to Make Binary String Beautiful
        public int minChanges(String s) {
            int r = 0;
            for (int i = 0; i < s.length(); i += 2)
                if (s.charAt(i) != s.charAt(i + 1))
                    r++;
            return r;
        }
    }

    static class s2917{//Find the K-or of an Array
        public int findKOr(int[] a, int k) {
            int[] dp = new int[31];
            for (int n : a)
                for (int i = 0; n > 0; n >>= 1, i++)
                    if ((n & 1) == 1)
                        dp[i]++;
            int r = 0;
            for (int i = 0; i < 31; i++)
                if (dp[i] >= k)
                    r += 1 << i;
            return r;
        }
    }

    static class s2923{//Find Champion I
        public int findChampion(int[][] g) {
            for (int i = 0; i < g.length; i++)
                if (Arrays.stream(g[i]).sum() == g.length - 1)
                    return i;
            return -1;
        }
    }

    static class s2928{//Distribute Candies Among Children I
        public int distributeCandies(int n, int limit) {return dist(3, n, limit);}

        int dist(int kids, int n, int limit) {
            if (kids == 0)
                return n == 0 ? 1 : 0;
            int r = 0;
            for (int i = 0; i <= Math.min(limit, n); i++)
                r += dist(kids - 1, n - i, limit);
            return r;
        }
    }

    static class s2937{//Make Three Strings Equal
        public int findMinimumOperations(String s1, String s2, String s3) {
            int i = 0, len = Math.min(s1.length(), Math.min(s2.length(), s3.length()));
            for (; i < len && s1.charAt(i) == s2.charAt(i) && s1.charAt(i) == s3.charAt(i); i++) ;
            return i == 0 ? -1 : s1.length() - i + s2.length() - i + s3.length() - i;
        }
    }

    static class s2946{//Matrix Similarity After Cyclic Shifts
        public boolean areSimilar(int[][] m, int k) {
            int len = m[0].length;
            k = k % len;
            for (int i = 0; i < m.length; i += 2)
                for (int j = 0; j < len; j++)
                    if (m[i][j] != m[i][(j + len - k) % len])
                        return false;
            for (int i = 0; i < m.length; i += 2)
                for (int j = 0; j < len; j++)
                    if (m[i][j] != m[i][(j + k) % len])
                        return false;
            return true;
        }
    }

    static class s2947{//Count Beautiful Substrings I
        public int beautifulSubstrings(String s, int k) {
            int r = 0;
            for (int i = 0; i < s.length(); i++)
                for (int j = i, v = 0, c = 0; j < s.length(); j++) {
                    if ("aeiou".indexOf(s.charAt(j)) >= 0)
                        v++;
                    else c++;
                    if (c == v && (c * v) % k == 0)
                        r++;
                }
            return r;
        }
    }

    static class s2960{//Count Tested Devices After Test Operations
        public int countTestedDevices(int[] p) {
            int r = 0;
            for (int d : p)
                r += d > r ? 1 : 0;
            return r;
        }
    }

    static class s2970{//Count the Number of Incremovable Subarrays I
        public int incremovableSubarrayCount(int[] a) {
            int r = 0;
            for (int i = 0; i < a.length; i++)
                for (int j = i; j < a.length; j++)
                    if (incremovable(a, i, j))
                        r++;
            return r;
        }
        boolean incremovable(int[] a, int start, int end) {
            for (int i = 1; i < start; i++)
                if (a[i - 1] >= a[i])
                    return false;
            for (int i = end + 2; i < a.length; i++)
                if (a[i - 1] >= a[i])
                    return false;
            return start <= 0 || end + 1 >= a.length || a[start - 1] < a[end + 1];
        }
    }

    static class s2996{//Smallest Missing Integer Greater Than Sequential Prefix Sum
        public int missingInteger(int[] a) {
            int preSum = a[0];
            Set<Integer> s = Arrays.stream(a).boxed().collect(Collectors.toSet());
            for (int i = 1; i < a.length && a[i] == a[i - 1] + 1; i++)
                preSum += a[i];
            int r = preSum;
            while (s.contains(r))
                r++;
            return r;
        }
    }
}
