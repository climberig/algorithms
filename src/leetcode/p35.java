package leetcode;

public class p35 {
    static class s3507 {//Minimum Pair Removal to Sort Array I
        public int minimumPairRemoval(int[] a) {
            int minSum = Integer.MAX_VALUE;
            boolean nonDecreasing = true;
            for (int i = 1; i < a.length; i++) {
                if (a[i - 1] > a[i])
                    nonDecreasing = false;
                minSum = Math.min(minSum, a[i - 1] + a[i]);
            }
            if (nonDecreasing)
                return 0;
            int[] b = new int[a.length - 1];
            for (int i = 0, j = 0; i < a.length; i++, j++)
                if (i < a.length - 1 && minSum == a[i] + a[i + 1]) {
                    b[j] = a[i] + a[i + 1];
                    i++;
                    minSum = Integer.MAX_VALUE;
                } else b[j] = a[i];
            return 1 + minimumPairRemoval(b);
        }
    }
}
