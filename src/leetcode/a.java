package leetcode;

public class a {
    public static void main(String[] args) {
        System.out.println(new a().hammingDistance(1, 4));
    }

    public int hammingDistance(int x, int y) {
        return Integer.bitCount(x ^ y);
    }
}
