package leetcode;
public class p31{
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
