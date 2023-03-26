package leetcode;
public class p26{
    static class s2600{//K Items With the Maximum Sum
        public int kItemsWithMaximumSum(int nOnes, int nZero, int nNegOnes, int k) {
            return Math.min(nOnes, k) - Math.max(0, k - nOnes - nZero);
        }
    }
}
