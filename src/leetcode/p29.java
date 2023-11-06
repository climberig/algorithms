package leetcode;
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
}
