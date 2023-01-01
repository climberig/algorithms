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

    static class s2515{//Shortest Distance to Target String in a Circular Array
        public int closetTarget(String[] words, String target, int startIndex) {
            int r = Integer.MAX_VALUE;
            for (int i = 0; i < words.length; i++)
                if (target.equals(words[i])) {
                    int d = Math.abs(i - startIndex);
                    r = Math.min(r, d);
                    r = Math.min(r, words.length - d);
                }
            return r == Integer.MAX_VALUE ? -1 : r;
        }
    }

    static class s2516{//Take K of Each Character From Left and Right
        public int takeCharacters(String str, int k) {
            int r = Integer.MAX_VALUE, f[] = {0, 0, 0};
            str.chars().forEach(c -> f[c - 'a']++);
            if (f[0] < k || f[1] < k || f[2] < k)
                return -1;
            for (int s = 0, e = 0; e < str.length(); e++) {
                f[str.charAt(e) - 'a']--;
                while (s <= e && (f[0] < k || f[1] < k || f[2] < k))
                    f[str.charAt(s++) - 'a']++;
                r = Math.min(r, str.length() - (e - s + 1));
            }
            return r;
        }
    }

    static class s2517{//Maximum Tastiness of Candy Basket
        public int maximumTastiness(int[] prices, int k) {
            Arrays.sort(prices);
            int lo = 0, hi = 1_000_000_000, r = 0;
            while (lo <= hi) {
                int mid = (lo + hi) / 2;
                if (works(prices, mid, k)) {
                    r = mid;
                    lo = mid + 1;
                } else hi = mid - 1;
            }
            return r;
        }

        boolean works(int[] prices, int diff, int k) {
            int count = 1, idx = 0;
            for (int i = 1; i < prices.length && count < k; i++)
                if (prices[i] - prices[idx] >= diff) {
                    count++;
                    idx = i;
                }
            return count == k;
        }
    }

    static class s2520{//Count the Digits That Divide a Number
        public int countDigits(int n) {
            return (int) String.valueOf(n).chars().filter(c -> n % (c - '0') == 0).count();
        }
    }
}
