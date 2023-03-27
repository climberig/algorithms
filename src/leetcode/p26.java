package leetcode;
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
}
