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

    static class s2519{//Count the Number of K-Big Indices
        public int kBigIndices(int[] a, int k) {
            boolean[] big = new boolean[a.length];
            Queue<Integer> q = new PriorityQueue<>(Collections.reverseOrder());
            for (int i = 0; i < a.length; i++) {
                if (q.size() == k && q.peek() < a[i])
                    big[i] = true;
                q.add(a[i]);
                if (q.size() > k)
                    q.poll();
            }
            int r = 0;
            q.clear();
            for (int i = a.length - 1; i >= 0; i--) {
                if (q.size() == k && q.peek() < a[i] && big[i])
                    r++;
                q.add(a[i]);
                if (q.size() > k)
                    q.poll();
            }
            return r;
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

    static class s2551{//Put Marbles in Bags
        public long putMarbles(int[] a, int k) {
            long cuts[] = new long[a.length - 1], r = 0;
            for (int i = 0; i < cuts.length; i++)
                cuts[i] = a[i] + a[i + 1];
            Arrays.sort(cuts);
            for (int i = 0; i < k - 1; i++)
                r += cuts[a.length - 1 - 1 - i] - cuts[i];
            return r;
        }
    }

    static class s2553{//Separate the Digits in an Array
        public int[] separateDigits(int[] a) {
            List<Integer> r = new ArrayList<>();
            for (int n : a)
                for (char c : String.valueOf(n).toCharArray())
                    r.add(c - '0');
            return r.stream().mapToInt(i -> i).toArray();
        }
    }

    static class s2554{//Maximum Number of Integers to Choose From a Range I
        public int maxCount(int[] banned, int n, int maxSum) {
            int r = 0;
            Set<Integer> s = Arrays.stream(banned).boxed().collect(Collectors.toSet());
            for (int i = 1; i <= n && maxSum > 0; i++)
                if (!s.contains(i)) {
                    maxSum -= i;
                    if (maxSum >= 0)
                        r++;
                }
            return r;
        }
    }

    static class s2555{//Maximize Win From Two Segments
        public int maximizeWin(int[] prizePositions, int k) {
            int dp[] = new int[prizePositions.length], r = 1;
            for (int i = prizePositions.length - 1, j = prizePositions.length - 1, max = 0; i >= 0; i--) {
                if (prizePositions[j] - prizePositions[i] <= k)
                    max = Math.max(max, j - i + 1);
                else while (prizePositions[j] - prizePositions[i] > k)
                    j--;
                dp[i] = max;
            }
            for (int i = 0, j = 0, max = 0; j < prizePositions.length - 1; j++) {
                if (prizePositions[j] - prizePositions[i] <= k)
                    max = Math.max(max, j - i + 1);
                else while (prizePositions[j] - prizePositions[i] > k)
                    i++;
                r = Math.max(r, max + dp[j + 1]);
            }
            return r;
        }
    }

    static class s2556{//Disconnect Path in a Binary Matrix by at Most One Flip
        public boolean isPossibleToCutPath(int[][] g) {
            if (!possible(g, 0, 0))
                return true;
            g[0][0] = 1;
            return !possible(g, 0, 0);
        }

        boolean possible(int[][] g, int i, int j) {
            if (i == g.length - 1 && j == g[0].length - 1)
                return true;
            if (i >= g.length || j >= g[0].length || g[i][j] == 0)
                return false;
            g[i][j] = 0;
            return possible(g, i + 1, j) || possible(g, i, j + 1);
        }
    }

    static class s2557{//Maximum Number of Integers to Choose From a Range II
        public int maxCount(int[] banned, int n, long maxSum) {
            Set<Integer> s = Arrays.stream(banned).boxed().collect(Collectors.toCollection(TreeSet::new));
            s.add(n + 1);
            int r = 0, a = 1;
            for (int hi : s) {
                int b = hi - 1;
                if (a <= b) {
                    long sum = (a + b) * (b - a + 1L) / 2;
                    if (maxSum >= sum) {
                        maxSum -= sum;
                        r += b - a + 1;
                    } else {
                        r += (Math.sqrt((2L * a - 1) * (2L * a - 1) + 8L * maxSum) - 2 * a + 1) / 2;
                        maxSum = 0;
                    }
                }
                a = hi + 1;
                if (maxSum == 0)
                    break;
            }
            return r;
        }
    }

    static class s2558{//Take Gifts From the Richest Pile
        public long pickGifts(int[] gifts, int k) {
            PriorityQueue<Integer> q = new PriorityQueue<>(Comparator.reverseOrder());
            Arrays.stream(gifts).forEach(q::offer);
            while (k-- > 0)
                q.offer((int) Math.sqrt(q.poll()));
            return q.stream().mapToLong(n -> n).sum();
        }
    }

    static class s2559{//Count Vowel Strings in Ranges
        public int[] vowelStrings(String[] words, int[][] queries) {
            Set<Character> vowels = Set.of('a', 'e', 'i', 'o', 'u');
            int[] cs = new int[words.length + 1];
            for (int i = 0; i < words.length; i++) {
                char c1 = words[i].charAt(0), c2 = words[i].charAt(words[i].length() - 1);
                cs[i + 1] = cs[i] + (vowels.contains(c1) && vowels.contains(c2) ? 1 : 0);
            }
            int[] r = new int[queries.length];
            for (int i = 0; i < queries.length; i++)
                r[i] = cs[queries[i][1] + 1] - cs[queries[i][0]];
            return r;
        }
    }

    static class s2560{//House Robber IV
        public int minCapability(int[] a, int k) {
            int lo = 1, hi = 1_000_000_000, r = hi;
            while (lo <= hi) {
                int mid = (hi + lo) / 2;
                if (possible(mid, k, a)) {
                    r = mid;
                    hi = mid - 1;
                } else lo = mid + 1;
            }
            return r;
        }
        boolean possible(int cap, int k, int[] a) {
            int r = 0;
            for (int i = 0; i < a.length; i++)
                if (a[i] <= cap) {
                    r++;
                    i++;
                }
            return r >= k;
        }
    }

    static class s2562{//Find the Array Concatenation Value
        public long findTheArrayConcVal(int[] a) {
            long r = 0;
            for (int i = 0, j = a.length - 1; i <= j; i++, j--)
                if (i == j)
                    r += a[i];
                else r += Integer.parseInt(String.valueOf(a[i]) + a[j]);
            return r;
        }
    }

    static class s2563{//Count the Number of Fair Pairs
        public long countFairPairs(int[] a, int lower, int upper) {
            Arrays.sort(a);
            return countLessOrEqual(a, upper) - countLessOrEqual(a, lower - 1);
        }

        long countLessOrEqual(int[] a, int val) {
            long r = 0;
            for (int i = 0, j = a.length - 1; i < j; i++) {
                for (; i < j && a[i] + a[j] > val; j--) ;
                r += j - i;
            }
            return r;
        }
    }

    static class s2566{//Maximum Difference by Remapping a Digit
        public int minMaxDifference(int n) {
            String s = String.valueOf(n);
            return max(s) - min(s);
        }
        int min(String s) {
            for (int i = 0; i < s.length(); i++)
                if (s.charAt(i) != '0')
                    return Integer.parseInt(s.replaceAll(s.charAt(i) + "", "0"));
            return Integer.parseInt(s);
        }
        int max(String s) {
            for (int i = 0; i < s.length(); i++)
                if (s.charAt(i) != '9')
                    return Integer.parseInt(s.replaceAll(s.charAt(i) + "", "9"));
            return Integer.parseInt(s);
        }
    }

    static class s2567{//Minimum Score by Changing Two Elements
        public int minimizeSum(int[] a) {
            int last = a.length - 1;
            Arrays.sort(a);
            return Math.min(Math.min(a[last] - a[2], a[last - 2] - a[0]), a[last - 1] - a[1]);
        }
    }

    static class s2568{//Minimum Impossible OR
        public int minImpossibleOR(int[] a) {
            Set<Integer> s = Arrays.stream(a).boxed().collect(Collectors.toSet());
            int r = 1;
            for (; s.contains(r); r *= 2) ;
            return r;
        }
    }

    static class s2570{//Merge Two 2D Arrays by Summing Values
        public int[][] mergeArrays(int[][] a1, int[][] a2) {
            List<int[]> r = new ArrayList<>();
            int i = 0, j = 0;
            while (i < a1.length && j < a2.length)
                if (a1[i][0] == a2[j][0])
                    r.add(new int[]{a1[i][0], a1[i++][1] + a2[j++][1]});
                else if (a1[i][0] < a2[j][0])
                    r.add(a1[i++]);
                else r.add(a2[j++]);
            while (i < a1.length)
                r.add(a1[i++]);
            while (j < a2.length)
                r.add(a2[j++]);
            return r.toArray(new int[0][]);
        }

        public int[][] mergeArrays1(int[][] a1, int[][] a2) {
            Map<Integer, Integer> m = new TreeMap<>();
            Arrays.stream(a1).forEach(n -> m.put(n[0], n[1]));
            Arrays.stream(a2).forEach(n -> m.put(n[0], m.getOrDefault(n[0], 0) + n[1]));
            return m.keySet().stream().map(k -> new int[]{k, m.get(k)}).toList().toArray(new int[0][]);
        }
    }

    static class s2571{//Minimum Operations to Reduce an Integer to 0
        public int minOperations(int n) {
            int r = 0;
            while (n > 0)
                if ((n & 3) == 3) {
                    n++;
                    r++;
                } else {
                    r += n & 1;
                    n >>= 1;
                }
            return r;
        }
    }

    static class s2574{//Left and Right Sum Differences
        public int[] leftRigthDifference(int[] a) {
            int left = 0, right = Arrays.stream(a).sum(), r[] = new int[a.length];
            for (int i = 0; i < a.length; i++) {
                right -= a[i];
                r[i] = Math.abs(right - left);
                left += a[i];
            }
            return r;
        }
    }

    static class s2575{//Find the Divisibility Array of a String
        public int[] divisibilityArray(String word, int m) {
            int r[] = new int[word.length()];
            long d = 0;
            for (int i = 0; i < word.length(); i++) {
                d = (10 * d + (word.charAt(i) - '0')) % m;
                r[i] = d % m == 0 ? 1 : 0;
            }
            return r;
        }
    }

    static class s2576{//Find the Maximum Number of Marked Indices
        public int maxNumOfMarkedIndices(int[] a) {
            Arrays.sort(a);
            int i = 0;
            for (int j = (a.length + 1) / 2; j < a.length; j++)
                if (2 * a[i] <= a[j])
                    i++;
            return i * 2;
        }
    }

    static class s2577{//Minimum Time to Visit a Cell In a Grid
        public int minimumTime(int[][] g) {
            if (g[0][1] > 1 && g[1][0] > 1)
                return -1;
            int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            boolean[][] seen = new boolean[g.length][g[0].length];
            PriorityQueue<int[]> q = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
            for (q.offer(new int[]{g[0][0], 0, 0}); !q.isEmpty(); ) {
                int p[] = q.poll(), t = p[0], x = p[1], y = p[2];
                if (x == g.length - 1 && y == g[0].length - 1)
                    return t;
                if (!seen[x][y]) {
                    seen[x][y] = true;
                    for (int[] d : dirs) {
                        int nx = x + d[0], ny = y + d[1];
                        if (0 <= nx && nx < g.length && 0 <= ny && ny < g[0].length && !seen[nx][ny]) {
                            int wait = ((g[nx][ny] - t) % 2 == 0) ? 1 : 0;
                            q.offer(new int[]{Math.max(g[nx][ny] + wait, t + 1), nx, ny});
                        }
                    }
                }
            }
            return -1;
        }
    }

    static class s2578{//Split With Minimum Sum
        public int splitNum(int n) {
            PriorityQueue<Integer> q = new PriorityQueue<>();
            int n1 = 0, n2 = 0;
            for (; n > 0; n /= 10)
                q.offer(n % 10);
            while (!q.isEmpty()) {
                n1 = 10 * n1 + q.poll();
                if (!q.isEmpty())
                    n2 = 10 * n2 + q.poll();
            }
            return n1 + n2;
        }
    }

    static class s2579{//Count Total Number of Colored Cells
        public long coloredCells(int n) {
            long s = 0;
            for (int i = 1; i <= 2 * n - 1; i += 2)
                s += i;
            return 2 * s - 2L * n + 1;
        }
    }

    static class s2580{//Count Ways to Group Overlapping Ranges
        public int countWays(int[][] ranges) {
            Arrays.sort(ranges, Comparator.comparingInt(a -> a[0]));
            long r = 2, end = ranges[0][1];
            for (int[] range : ranges) {
                if (end < range[0])
                    r = (r * 2L) % 1_000_000_007;
                end = Math.max(end, range[1]);
            }
            return (int) r;
        }
    }

    static class s2582{//Pass the Pillow
        public int passThePillow(int n, int time) {
            int d = 2 * (n - 1);
            if (time >= d)
                time = time % d;
            if (1 + time <= n)
                return 1 + time;
            time -= n - 1;
            return n - time;
        }
    }   

    static class s2583{//Kth Largest Sum in a Binary Tree
        public long kthLargestLevelSum(TreeNode root, int k) {
            Queue<TreeNode> q = new LinkedList<>();
            List<Long> sums = new ArrayList<>();
            for (q.offer(root); !q.isEmpty(); ) {
                long sum = 0;
                for (int size = q.size(); size > 0; size--) {
                    TreeNode node = q.poll();
                    sum += node.val;
                    if (node.left != null)
                        q.offer(node.left);
                    if (node.right != null)
                        q.offer(node.right);
                }
                sums.add(sum);
            }
            Collections.sort(sums);
            return sums.size() >= k ? sums.get(sums.size() - k) : -1;
        }
    }

    static class s2585{//Number of Ways to Earn Points
        public int waysToReachTarget(int target, int[][] types) {
            return ways(0, types, target, new Integer[target + 1][types.length]);
        }

        int ways(int i, int[][] types, int target, Integer[][] dp) {
            if (target == 0)
                return 1;
            if (i >= types.length)
                return 0;
            if (dp[target][i] != null)
                return dp[target][i];
            int r = 0;
            for (int n = 0; n <= Math.min(target, types[i][0]) && n * types[i][1] <= target; n++)
                r = (r + ways(i + 1, types, target - n * types[i][1], dp)) % 1_000_000_007;
            dp[target][i] = r;
            return r;
        }
    }

    static class s2587{//Rearrange Array to Maximize Prefix Score
        public int maxScore(int[] a) {
            Arrays.sort(a);
            int r = 0;
            long s = 0;
            for (int i = a.length - 1; i >= 0; i--) {
                s += a[i];
                if (s > 0)
                    r++;
                else break;
            }
            return r;
        }
    }

    static class s2588{//Count the Number of Beautiful Subarrays
        public long beautifulSubarrays(int[] a) {
            long r = 0, pre = 0;
            Map<Long, Long> m = new HashMap<>();
            m.put(0L, 1L);
            for (int n : a) {
                pre ^= n;
                long v = m.getOrDefault(pre, 0L);
                r += v;
                m.put(pre, v + 1);
            }
            return r;
        }
    }

    static class s2591{//Distribute Money to Maximum Children
        public int distMoney(int money, int children) {
            money -= children;
            int r = -1;
            for (int n = 0; n <= children; n++) {
                int moneyLeft = money - 7 * n;
                if (moneyLeft < 0)
                    return r;
                if (n + 1 == children && moneyLeft == 3 || n == children && moneyLeft != 0)
                    continue;
                r = n;
            }
            return r;
        }
    }

    static class s2592{//Maximize Greatness of an Array
        public int maximizeGreatness(int[] a) {
            Arrays.sort(a);
            int r = 0;
            for (int i = a.length - 1, j = i; i >= 0; i--)
                if (a[i] < a[j]) {
                    r++;
                    j--;
                }
            return r;
        }
    }

    static class s2593{//Find Score of an Array After Marking All Elements
        public long findScore(int[] a) {
            PriorityQueue<int[]> q = new PriorityQueue<>((x, y) -> x[0] != y[0] ? x[0] - y[0] : x[1] - y[1]);
            for (int i = 0; i < a.length; i++)
                q.offer(new int[]{a[i], i});
            boolean[] used = new boolean[a.length];
            long r = 0;
            while (!q.isEmpty()) {
                int p[] = q.poll(), val = p[0], i = p[1];
                if (!used[i]) {
                    used[i] = true;
                    r += val;
                    if (i > 0)
                        used[i - 1] = true;
                    if (i < a.length - 1)
                        used[i + 1] = true;
                }
            }
            return r;
        }
    }

    static class s2594{//Minimum Time to Repair Cars
        public long repairCars(int[] ranks, int cars) {
            long lo = 1, hi = 1L * ranks[0] * cars * cars, r = hi;
            while (lo <= hi) {
                long mid = (lo + hi) / 2;
                if (cars(ranks, mid) >= cars) {
                    r = mid;
                    hi = mid - 1;
                } else lo = mid + 1;
            }
            return r;
        }
        long cars(int[] ranks, long time) {
            long r = 0;
            for (int rank : ranks)
                r += (long) Math.sqrt(time * 1.0 / rank);
            return r;
        }
    }

    static class s2595{//Number of Even and Odd Bits
        public int[] evenOddBit(int n) {
            int[] r = new int[2];
            for (int i = 0; n > 0; n /= 2, i++)
                r[i % 2] += n & 1;
            return r;
        }
    }

    static class s2596{//Check Knight Tour Configuration
        public boolean checkValidGrid(int[][] g) {
            if (g[0][0] != 0)
                return false;
            int n = g.length;
            int[][] dir = {{-2, -1}, {-2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}, {2, -1}, {2, 1}};
            for (int x = 0, y = 0, i = 1; i < n * n; i++) {
                boolean found = false;
                for (int[] d : dir) {
                    int nx = x + d[0], ny = y + d[1];
                    if (0 <= nx && nx < n && 0 <= ny && ny < n && g[nx][ny] == i) {
                        x = nx;
                        y = ny;
                        found = true;
                        break;
                    }
                }
                if (!found)
                    return false;
            }
            return true;
        }
    }
}
