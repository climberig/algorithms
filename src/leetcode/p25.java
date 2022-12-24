package leetcode;
import java.util.Arrays;
import java.util.List;
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

    static class s2512{//Reward Top K Students
        public List<Integer> topStudents(String[] positive, String[] negative, String[] reports, int[] ids, int k) {
            Set<String> good = Arrays.stream(positive).collect(Collectors.toSet()), bad = Arrays.stream(negative).collect(Collectors.toSet());
            int[][] r = new int[ids.length][2];
            for (int i = 0; i < reports.length; i++)
                r[i] = new int[]{ids[i], Arrays.stream(reports[i].split(" ")).mapToInt(w -> good.contains(w) ? 3 : (bad.contains(w) ? -1 : 0)).sum()};
            Arrays.sort(r, (a, b) -> a[1] != b[1] ? b[1] - a[1] : a[0] - b[0]);
            return Arrays.stream(r).mapToInt(i -> i[0]).limit(k).boxed().collect(Collectors.toList());
        }
    }
}
