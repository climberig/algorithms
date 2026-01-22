package leetcode;

import java.util.Set;
public class a {
    static class ss3813 {//Vowel-Consonant Score
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
