package leetcode;

public class p38 {
    static class s3803 {//Count Residue Prefixes
        public int residuePrefixes(String s) {
            int r = 0, b = 0;
            for (int i = 0; i < s.length(); i++) {
                b |= 1 << (s.charAt(i) - 'a');
                if (Integer.bitCount(b) == ((i + 1) % 3))
                    r++;
            }
            return r;
        }
    }
}
