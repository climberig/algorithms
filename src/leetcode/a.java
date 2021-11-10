package leetcode;

public class a {
    public int[] countBits(int n) {
        int[] r = new int[n + 1];
        for (int i = 1; i < r.length; i++)
            r[i] = r[i / 2] + i % 2;
        return r;
    }
}
