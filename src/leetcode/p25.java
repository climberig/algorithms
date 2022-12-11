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
}
