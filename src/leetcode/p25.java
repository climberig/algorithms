package leetcode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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

    static class s2521{//Distinct Prime Factors of Product of Array
        public int distinctPrimeFactors(int[] a) {
            Set<Integer> primes = new HashSet<>();
            for (int n : a) {
                for (int d = 2; d <= n; d++)
                    if (n % d == 0) {
                        primes.add(d);
                        for (; n % d == 0; n /= d) ;
                    }
            }
            return primes.size();
        }
    }

    static class s2522{//Partition String Into Substrings With Values at Most K
        public int minimumPartition(String s, int k) {
            int r = 0;
            for (int i = 0; i < s.length(); r++) {
                long curr = s.charAt(i++) - '0';
                if (curr > k)
                    return -1;
                for (; i < s.length() && curr <= k; i++)
                    curr = 10 * curr + (s.charAt(i) - '0');
                i = curr > k ? i - 1 : i;
            }
            return r;
        }
    }

    static class s2523{//Closest Prime Numbers in Range
        public int[] closestPrimes(int left, int right) {
            boolean[] prime = primes(right + 1);
            int minDiff = Integer.MAX_VALUE, a = IntStream.range(left, right + 1).filter(n -> prime[n]).findFirst().orElse(0), r[] = {-1, -1};
            for (int i = a + 1; i <= right; i++)
                if (prime[i]) {
                    if (i - a < minDiff) {
                        minDiff = i - a;
                        r = new int[]{a, i};
                    }
                    a = i;
                }
            return r;
        }
        boolean[] primes(int n) {
            boolean[] prime = new boolean[n + 1];
            Arrays.fill(prime, 2, prime.length, true);
            for (int p = 2; p * p <= n; p++)
                if (prime[p])
                    for (int i = p * p; i <= n; i += p)
                        prime[i] = false;
            return prime;
        }
    }

    static class s2525{//Categorize Box According to Criteria
        public String categorizeBox(int l, int w, int h, int mass) {
            boolean bulky = l >= 1e4 || w >= 1e4 || h >= 1e4 || (long) l * w * h >= 1e9;
            boolean heavy = mass >= 100;
            if (bulky && heavy)
                return "Both";
            return bulky ? "Bulky" : (heavy ? "Heavy" : "Neither");
        }
    }

    static class s2526{//Find Consecutive Integers from a Data Stream
        class DataStream{
            int count = 0, val, k;

            public DataStream(int val, int k) {
                this.val = val;
                this.k = k;
            }

            public boolean consec(int n) {
                count = n == val ? count + 1 : 0;
                return count >= k;
            }
        }
    }

    static class s2529{//Maximum Count of Positive Integer and Negative Integer
        public int maximumCount(int[] a) {
            return (int) Math.max(Arrays.stream(a).filter(n -> n > 0).count(), Arrays.stream(a).filter(n -> n < 0).count());
        }
    }

    static class s2530{//Maximal Score After Applying K Operations
        public long maxKelements(int[] a, int k) {
            PriorityQueue<Integer> q = new PriorityQueue<>(Comparator.reverseOrder());
            Arrays.stream(a).forEach(q::offer);
            long r = 0;
            while (k-- > 0) {
                Integer n = q.poll();
                r += n;
                q.offer((int) Math.ceil(n / 3.0));
            }
            return r;
        }
    }

    static class s2531{//Make Number of Distinct Characters Equal
        public boolean isItPossible(String w1, String w2) {
            int[] f1 = new int[26], f2 = new int[26];
            w1.chars().forEach(c -> f1[c - 'a']++);
            w2.chars().forEach(c -> f2[c - 'a']++);
            for (int i = 0; i < f1.length; i++)
                for (int j = 0; j < f2.length; j++)
                    if (f1[i] > 0 && f2[j] > 0) {
                        f1[i]--;
                        f2[i]++;
                        f1[j]++;
                        f2[j]--;
                        if (uniq(f1) == uniq(f2))
                            return true;
                        f1[i]++;
                        f2[i]--;
                        f1[j]--;
                        f2[j]++;
                    }
            return false;
        }
        long uniq(int[] f) {return Arrays.stream(f).filter(n -> n > 0).count();}
    }
}
