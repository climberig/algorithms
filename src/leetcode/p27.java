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

    static class s2733{//Neither Minimum nor Maximum
        public int findNonMinOrMax(int[] a) {
            if (a.length < 3)
                return -1;
            int max = Math.max(a[0], Math.max(a[1], a[2])), min = Math.min(a[0], Math.min(a[1], a[2]));
            return a[0] + a[1] + a[2] - min - max;
        }
    }

    static class s2739{//Total Distance Traveled
        public int distanceTraveled(int mainTank, int additionalTank) {
            if (additionalTank == 0 || mainTank < 5)
                return 10 * mainTank;
            return 50 + distanceTraveled(mainTank - 4, additionalTank - 1);
        }
    }

    static class s2760{//Longest Even Odd Subarray With Threshold
        public int longestAlternatingSubarray(int[] a, int max) {
            int r = 0;
            for (int i = 0; i < a.length; i++)
                if (a[i] % 2 == 0 && a[i] <= max) {
                    int len = 1;
                    for (int j = i + 1; j < a.length && a[j] <= max && a[j - 1] % 2 != a[j] % 2; j++, len++) ;
                    r = Math.max(r, len);
                }
            return r;
        }
    }
}
