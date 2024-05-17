package leetcode;
public class p31{
    static class s3142{//Check if Grid Satisfies Conditions
        public boolean satisfiesConditions(int[][] g) {
            for (int r = 0; r < g.length; r++)
                for (int c = 0; c < g[0].length; c++) {
                    if (r < g.length - 1 && g[r][c] != g[r + 1][c])
                        return false;
                    if (c < g[0].length - 1 && g[r][c] == g[r][c + 1])
                        return false;
                }
            return true;
        }
    }

    static class s3146{//Permutation Difference between Two Strings
        public int findPermutationDifference(String s, String t) {
            int p[] = new int[26], r = 0;
            for (int i = 0; i < s.length(); i++)
                p[s.charAt(i) - 'a'] = i;
            for (int i = 0; i < t.length(); i++)
                r += Math.abs(i - p[t.charAt(i) - 'a']);
            return r;
        }
    }
}
