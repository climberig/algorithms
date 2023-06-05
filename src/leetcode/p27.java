package leetcode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
public class p27{
    static class s2707{//Extra Characters in a String
        public int minExtraChar(String s, String[] dictionary) {
            Set<String> words = Arrays.stream(dictionary).collect(Collectors.toSet());
            Map<String, Integer> dp = new HashMap<>();
            return minChar(s, words, dp);
        }

        int minChar(String s, Set<String> words, Map<String, Integer> dp) {
            if (words.contains(s))
                return 0;
            if (dp.containsKey(s))
                return dp.get(s);
            int r = s.length();
            for (int i = 1; i < s.length(); i++) {
                String prefix = s.substring(0, i), rest = s.substring(i);
                if (words.contains(prefix))
                    r = Math.min(r, minChar(rest, words, dp));
                else r = Math.min(r, i + minChar(rest, words, dp));
            }
            dp.put(s, r);
            return r;
        }
    }

    static class s2716{//Minimize String Length
        public int minimizedStringLength(String s) {return (int) s.chars().distinct().count();}
    }

    static class s2717{//Semi-Ordered Permutation
        public int semiOrderedPermutation(int[] a) {
            int onePosition = 0, nPosition = 0;
            for (int i = 0; i < a.length; i++)
                if (a[i] == 1)
                    onePosition = i;
                else if (a[i] == a.length)
                    nPosition = i;
            return onePosition + a.length - 1 - nPosition - (onePosition > nPosition ? 1 : 0);
        }
    }
}
