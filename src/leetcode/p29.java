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
}
