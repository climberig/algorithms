package leetcode;

import java.util.Arrays;

public class p37 {
    static class s3745{//Maximize Expression of Three Elements
        public int maximizeExpressionOfThree(int[] a) {
            Arrays.sort(a);
            return a[a.length - 1] + a[a.length - 2] - a[0];
        }
    }

    static class s3754 {//Concatenate Non-Zero Digits and Multiply by Sum I
        public long sumAndMultiply(int n) {
            int x = 0, sum = 0;
            for (int m = 1; n > 0; n /= 10) {
                int d = n % 10;
                if (d > 0) {
                    x = m * d + x;
                    sum += d;
                    m *= 10;
                }
            }
            return 1L * x * sum;
        }
    }
}
