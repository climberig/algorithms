package leetcode;

import java.util.HashSet;
import java.util.Set;
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

    static class s3804 {//Number of Centered Subarrays
        public int centeredSubarrays(int[] a) {
            int r = 0;
            for (int i = 0; i < a.length; i++) {
                int sum = 0;
                Set<Integer> s = new HashSet<>();
                for (int j = i; j < a.length; j++) {
                    sum += a[j];
                    s.add(a[j]);
                    if (s.contains(sum))
                        r++;
                }
            }
            return r;
        }
    }

    static class s3813 {//Vowel-Consonant Score
        public int vowelConsonantScore(String s) {
            int c = 0, v = 0;
            for (char ch : s.toCharArray())
                if (Character.isLetter(ch)) {
                    if (isVowel(ch))
                        v++;
                    else c++;
                }
            return c == 0 ? 0 : Math.floorDiv(v, c);
        }

        boolean isVowel(char c) {
            return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
        }
    }
}
