package leetcode;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
public class p25{
    static class s2500{//Delete Greatest Value in Each Row
        public int deleteGreatestValue(int[][] g) {
            Arrays.stream(g).forEach(Arrays::sort);
            int r = 0;
            for (int i = 0; i < g[0].length; i++) {
                int max = 0;
                for (int[] row : g)
                    max = Math.max(max, row[i]);
                r += max;
            }
            return r;
        }
    }

    static class s2501{//Longest Square Streak in an Array
        public int longestSquareStreak(int[] a) {
            Set<Integer> s = Arrays.stream(a).boxed().collect(Collectors.toSet());
            int r = 0;
            for (int n : a) {
                int streak = 1;
                for (; s.contains(n * n); streak++, n = n * n) ;
                r = Math.max(r, streak);
            }
            return r > 1 ? r : -1;
        }
    }

    static class s2506{//Count Pairs Of Similar Strings
        public int similarPairs(String[] words) {
            int r = 0;
            for (int i = 0; i < words.length; i++)
                for (int j = i + 1; j < words.length; j++)
                    if (toInt(words[i]) == toInt(words[j]))
                        r++;
            return r;
        }

        int toInt(String w) {
            int r = 0;
            for (char c : w.toCharArray())
                r |= 1 << (c - 'a');
            return r;
        }
    }

    static class s2507{//Smallest Value After Replacing With Sum of Prime Factors
        public int smallestValue(int n) {
            while (true) {
                int sum = 0, k = n;
                for (int f = 2; k > 1; )
                    if (k % f == 0) {
                        sum += f;
                        k /= f;
                    } else f++;
                if (sum == n) break;
                n = sum;
            }
            return n;
        }
    }

    static class s2511{//Maximum Enemy Forts That Can Be Captured
        public int captureForts(int[] forts) {
            int r = 0;
            for (int i = 0, j = 0; i < forts.length; i++)
                if (forts[i] != 0) {
                    if (forts[j] == -forts[i])
                        r = Math.max(r, i - j - 1);
                    j = i;
                }
            return r;
        }
    }
}
