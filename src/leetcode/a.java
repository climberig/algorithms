package leetcode;

public class a {
    public static void main(String[] args) {
        System.out.println(new a().minimizedMaximum(7, new int[]{15, 10, 10}));
    }
    public int minimizedMaximum(int n, int[] quantities) {
        int lo = 1, hi = 100_000;
        while (lo < hi) {
            int perStore = (lo + hi) / 2, sum = 0;
            for (int i = 0; i < quantities.length && sum <= n; i++)
                sum += Math.ceil(1.0 * quantities[i] / perStore);
            if (sum > n)
                lo = perStore + 1;
            else hi = perStore;
        }
        return hi;
    }
}

