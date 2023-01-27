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

    static class s2502{//Design Memory Allocator
        class Allocator{
            int[] m;

            public Allocator(int n) {m = new int[n];}

            public int allocate(int size, int mID) {
                for (int i = 0; i < m.length; i++) {
                    int count = 0, j = i;
                    for (; j < m.length && m[j] == 0 && size > count; j++, count++) ;
                    if (size == count) {
                        Arrays.fill(m, i, i + count, mID);
                        return i;
                    }
                    i = j;
                }
                return -1;
            }

            public int free(int mID) {
                int r = 0;
                for (int i = 0; i < m.length; i++)
                    if (m[i] == mID) {
                        m[i] = 0;
                        r++;
                    }
                return r;
            }
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

    static class s2510{//Check if There is a Path With Equal Number of 0's And 1's
        public boolean isThereAPath(int[][] g) {
            Set<Integer>[][] dp = new Set[g.length][g[0].length];
            for (int i = 0; i < g.length; i++)
                for (int j = 0; j < g[0].length; j++)
                    dp[i][j] = new HashSet<>();
            for (int i = 1, diff = g[0][0] == 1 ? 1 : -1; i < g[0].length; i++) {
                diff += g[0][i] == 1 ? 1 : -1;
                dp[0][i].add(diff);
            }
            for (int i = 1, diff = g[0][0] == 1 ? 1 : -1; i < g.length; i++) {
                diff += g[i][0] == 1 ? 1 : -1;
                dp[i][0].add(diff);
            }
            for (int i = 1; i < g.length; i++)
                for (int j = 1; j < g[0].length; j++) {
                    int diff = g[i][j] == 1 ? 1 : -1, r = i, c = j;
                    dp[i - 1][j].forEach(n -> dp[r][c].add(n + diff));
                    dp[i][j - 1].forEach(n -> dp[r][c].add(n + diff));
                }
            return dp[g.length - 1][g[0].length - 1].contains(0);
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

    static class s2533{//Number of Good Binary Strings
        public int goodBinaryStrings(int minLength, int maxLength, int oneGroup, int zeroGroup) {
            long mod = 1_000_000_007, r = 0, dp[] = new long[maxLength + 1];
            dp[0] = 1;
            for (int n = 1; n <= maxLength; n++) {
                if (n >= zeroGroup)
                    dp[n] = dp[n - zeroGroup];
                if (n >= oneGroup)
                    dp[n] = (dp[n] + dp[n - oneGroup]) % mod;
                if (n >= minLength)
                    r = (r + dp[n]) % mod;
            }
            return (int) r;
        }
    }

    static class s2534{//Time Taken to Cross the Door
        public int[] timeTaken(int[] arrivals, int[] states) {
            DoorState doorState = DoorState.NOT_USED;
            int[] r = new int[arrivals.length];
            Queue<Integer> in = new LinkedList<>(), out = new LinkedList<>();
            for (int t = arrivals[0], i = 0; t <= arrivals[arrivals.length - 1] || !in.isEmpty() || !out.isEmpty(); t++) {
                while (i < arrivals.length && t == arrivals[i])
                    if (states[i] == 0)
                        in.offer(i++);
                    else out.offer(i++);
                switch (doorState) {
                    case NOT_USED, USED_OUT -> doorState = cross(out, DoorState.USED_OUT, in, DoorState.USED_IN, r, t);
                    case USED_IN -> doorState = cross(in, DoorState.USED_IN, out, DoorState.USED_OUT, r, t);
                }
            }
            return r;
        }

        DoorState cross(Queue<Integer> q1, DoorState s1, Queue<Integer> q2, DoorState s2, int[] r, int t) {
            if (!q1.isEmpty()) {
                r[q1.poll()] = t;
                return s1;
            } else if (!q2.isEmpty()) {
                r[q2.poll()] = t;
                return s2;
            } else return DoorState.NOT_USED;
        }

        enum DoorState{NOT_USED, USED_IN, USED_OUT}
    }

    static class s2535{//Difference Between Element Sum and Digit Sum of an Array
        public int differenceOfSum(int[] a) {
            int r = Arrays.stream(a).sum();
            for (int n : a)
                for (; n > 0; n = n / 10)
                    r -= n % 10;
            return Math.abs(r);
        }
    }

    static class s2536{//Increment Submatrices by One
        public int[][] rangeAddQueries(int n, int[][] queries) {
            int[][] m = new int[n][n];
            for (int[] q : queries) {
                int r1 = q[0], r2 = q[2], c1 = q[1], c2 = q[3];
                for (int r = r1; r <= r2; r++) {
                    m[r][c1] += 1;
                    if (c2 + 1 < n)
                        m[r][c2 + 1] -= 1;
                }
            }
            for (int r = 0; r < n; r++)
                for (int c = 1; c < n; c++)
                    m[r][c] += m[r][c - 1];
            return m;
        }
    }

    static class s2540{//Minimum Common Value
        public int getCommon(int[] a1, int[] a2) {
            for (int i = 0, j = 0; i < a1.length && j < a2.length; )
                if (a1[i] == a2[j])
                    return a1[i];
                else if (a1[i] < a2[j])
                    i++;
                else j++;
            return -1;
        }
    }

    static class s2541{//Minimum Operations to Make Array Equal II
        public long minOperations(int[] a1, int[] a2, int k) {
            if (k == 0)
                return Arrays.equals(a1, a2) ? 0 : -1;
            long pos = 0, neg = 0;
            for (int i = 0; i < a1.length; i++) {
                int diff = a1[i] - a2[i], f = Math.abs(diff) / k;
                if (diff % k != 0)
                    return -1;
                if (diff > 0)
                    pos += f;
                else if (diff < 0)
                    neg += f;
            }
            return pos == neg ? pos : -1;
        }
    }

    static class s2547{//Minimum Cost to Split an Array
        public int minCost(int[] a, int k) {
            int[][] trimmed = new int[a.length][a.length];
            for (int i = 0; i < a.length; i++) {
                int freq[] = new int[a.length], len = 0;
                for (int j = i; j < a.length; j++) {
                    freq[a[j]]++;
                    if (freq[a[j]] == 2)
                        len += 2;
                    else if (freq[a[j]] > 2)
                        len++;
                    trimmed[i][j] = len;
                }
            }
            int[] dp = new int[a.length + 1];
            for (int i = 1; i <= a.length; i++) {
                int min = Integer.MAX_VALUE;
                for (int j = 0; j < i; j++)
                    min = Math.min(min, dp[j] + k + trimmed[j][i - 1]);
                dp[i] = min;
            }
            return dp[a.length];
        }
    }

    static class s2548{//Maximum Price to Fill a Bag
        public double maxPrice(int[][] items, int capacity) {
            Arrays.sort(items, (a, b) -> Double.compare(1.0 * b[0] / b[1], 1.0 * a[0] / a[1]));
            double r = 0;
            for (int i = 0; i < items.length && capacity > 0; i++) {
                int weight = Math.min(capacity, items[i][1]);
                r += 1.0 * weight / items[i][1] * items[i][0];
                capacity -= weight;
            }
            return capacity == 0 ? r : -1;
        }
    }
}
