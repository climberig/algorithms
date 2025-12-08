package leetcode;

public class p37 {
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
