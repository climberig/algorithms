package leetcode;

import java.util.Arrays;

public class p34{
    static class s3402{//Minimum Operations to Make Columns Strictly Increasing
        public int minimumOperations(int[][] g) {
            int r = 0;
            for (int col = 0; col < g[0].length; col++) {
                int val = g[0][col];
                for (int row = 1; row < g.length; row++) {
                    val = Math.max(g[row][col], val + 1);
                    r += val - g[row][col];
                }
            }
            return r;
        }
    }

    static class s3407{//Substring Matching Pattern
        public boolean hasMatch(String s, String p) {
            int k = p.indexOf('*');
            String pre = p.substring(0, k), post = p.substring(k + 1);
            int i = s.indexOf(pre), j = s.indexOf(post, i + pre.length());
            return i >= 0 && j >= 0;
        }
    }

    static class s3432 {//Count Partitions with Even Sum Difference

        public int countPartitions(int[] a) {
            return Arrays.stream(a).sum() % 2 == 0 ? a.length - 1 : 0;
        }
    }
}
