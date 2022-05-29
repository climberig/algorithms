package leetcode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
public class p22{
    static class s2200{
        public List<Integer> findKDistantIndices(int[] a, int key, int k){
            List<Integer> r = new ArrayList<>();
            for(int i = 0, prev = 0; i < a.length; i++)
                if(a[i] == key){
                    for(int j = Math.max(i - k, prev); j < a.length && j <= i + k; j++)
                        r.add(j);
                    prev = i + k + 1;
                }
            return r;
        }
    }

    static class s2201{//Count Artifacts That Can Be Extracted
        public int digArtifacts(int n, int[][] artifacts, int[][] dig){
            Set<Integer> s = Arrays.stream(dig).map(d -> d[0] * 1000 + d[1]).collect(Collectors.toSet());
            return (int) Arrays.stream(artifacts).filter(a -> uncovered(a[0], a[1], a[2], a[3], s)).count();
        }

        boolean uncovered(int r1, int c1, int r2, int c2, Set<Integer> s){
            for(int i = r1; i <= r2; i++)
                for(int j = c1; j <= c2; j++)
                    if(!s.contains(1000 * i + j))
                        return false;
            return true;
        }
    }

    static class s2202{//Maximize the Topmost Element After K Moves
        public int maximumTop(int[] a, int k){
            if(a.length == 1 && k % 2 == 1)
                return -1;
            if(k > a.length)
                return Arrays.stream(a).max().getAsInt();
            PriorityQueue<Integer> q = new PriorityQueue<>(Comparator.reverseOrder());
            IntStream.range(0, k - 1).forEach(i -> q.offer(a[i]));
            int r = !q.isEmpty() ? q.poll() : -1;
            return k < a.length ? Math.max(r, a[k]) : r;
        }
    }

    static class s2203{//Minimum Weighted Subgraph With the Required Paths
        public long minimumWeight(int n, int[][] edges, int src1, int src2, int dest){
            List<Map<Integer, Integer>> g = IntStream.range(0, n).mapToObj(i -> new HashMap<Integer, Integer>()).collect(Collectors.toList());
            List<Map<Integer, Integer>> g1 = IntStream.range(0, n).mapToObj(i -> new HashMap<Integer, Integer>()).collect(Collectors.toList());
            for(int[] e : edges){
                g.get(e[0]).put(e[1], Math.min(e[2], g.get(e[0]).getOrDefault(e[1], Integer.MAX_VALUE)));
                g1.get(e[1]).put(e[0], Math.min(e[2], g1.get(e[1]).getOrDefault(e[0], Integer.MAX_VALUE)));
            }
            long a[] = dfs(src1, g), b[] = dfs(src2, g), c[] = dfs(dest, g1), r = Long.MAX_VALUE;
            for(int i = 0; i < n; i++)
                if(a[i] != Long.MAX_VALUE && b[i] != Long.MAX_VALUE && c[i] != Long.MAX_VALUE)
                    r = Math.min(r, a[i] + b[i] + c[i]);
            return r == Long.MAX_VALUE ? -1 : r;
        }

        long[] dfs(int start, List<Map<Integer, Integer>> g){
            long[] dist = new long[g.size()];
            Arrays.fill(dist, Long.MAX_VALUE);
            PriorityQueue<long[]> q = new PriorityQueue<>(Comparator.comparingLong(a -> a[0]));
            dist[start] = 0;
            for(q.offer(new long[]{0, start}); !q.isEmpty(); ){
                long p[] = q.poll(), d = p[0], u = p[1];
                Map<Integer, Integer> adj = g.get((int) u);
                for(int v : adj.keySet())
                    if(d + adj.get(v) < dist[v]){
                        dist[v] = d + adj.get(v);
                        q.offer(new long[]{dist[v], v});
                    }
            }
            return dist;
        }
    }

    static class s2204{//Distance to a Cycle in Undirected Graph
        public int[] distanceToCycle(int n, int[][] edges){
            int[] degree = new int[n], r = new int[n];
            List<List<Integer>> g = IntStream.range(0, n).mapToObj(i -> new ArrayList<Integer>()).collect(Collectors.toList());
            for(int[] e : edges){
                degree[e[0]]++;
                degree[e[1]]++;
                g.get(e[0]).add(e[1]);
                g.get(e[1]).add(e[0]);
            }
            Queue<Integer> q = IntStream.range(0, n).filter(u -> degree[u] == 1).boxed().collect(Collectors.toCollection(LinkedList::new));
            while(!q.isEmpty()){
                degree[q.peek()]--;
                g.get(q.poll()).stream().filter(u -> degree[u] > 0 && --degree[u] == 1).forEach(q::add);
            }
            boolean[] seen = new boolean[n];
            IntStream.range(0, n).filter(u -> degree[u] > 0).forEach(u -> {
                seen[u] = true;
                q.offer(u);
            });
            for(int d = 1; !q.isEmpty(); d++)
                for(int size = q.size(); size > 0; size--)
                    for(Integer v : g.get(q.poll()))
                        if(!seen[v]){
                            seen[v] = true;
                            r[v] = d;
                            q.offer(v);
                        }
            return r;
        }
    }

    static class s2206{//Divide Array Into Equal Pairs
        public boolean divideArray(int[] a){
            int[] f = new int[501];
            Arrays.stream(a).forEach(n -> f[n]++);
            return Arrays.stream(f).allMatch(n -> n % 2 == 0);
        }
    }

    static class s2207{//Maximize Number of Subsequences in a String
        public long maximumSubsequenceCount(String text, String pattern){
            char c1 = pattern.charAt(0), c2 = pattern.charAt(1);
            return Math.max(count(c1, c2, text, 0, 1), count(c2, c1, text, text.length() - 1, -1));
        }

        long count(char c1, char c2, String text, int start, int step){
            long r = 0, c1Count = 0;
            for(int i = start; 0 <= i && i < text.length(); i += step){
                c1Count += text.charAt(i) == c1 ? 1 : 0;
                r += text.charAt(i) == c2 ? c1Count : 0;
            }
            return r + (c1 != c2 ? c1Count : 0);
        }
    }

    static class s2208{//Minimum Operations to Halve Array Sum
        public int halveArray(int[] a){
            double originSum = Arrays.stream(a).asLongStream().sum(), sum = originSum;
            PriorityQueue<Double> q = new PriorityQueue<>(Comparator.reverseOrder());
            Arrays.stream(a).forEach(n -> q.offer(1.0 * n));
            int r = 0;
            while(sum * 2 > originSum){
                Double n = q.poll();
                sum = sum - n + n / 2;
                q.offer(n / 2);
                r++;
            }
            return r;
        }
    }

    static class s2209{//Minimum White Tiles After Covering With Carpets
        public int minimumWhiteTiles(String floor, int nCarpets, int carpetLen){
            int[][] dp = new int[floor.length() + 1][nCarpets + 1];
            for(int i = 1; i <= floor.length(); i++)
                for(int c = 0; c <= nCarpets; c++){
                    int skip = dp[i - 1][c] + floor.charAt(i - 1) - '0';
                    int cover = c > 0 ? dp[Math.max(0, i - carpetLen)][c - 1] : floor.length();
                    dp[i][c] = Math.min(skip, cover);
                }
            return dp[floor.length()][nCarpets];
        }
    }

    static class s2210{//Count Hills and Valleys in an Array
        public int countHillValley(int[] a){
            int r = 0, left = a[0];
            for(int i = 1; i < a.length - 1; i++)
                if(left < a[i] && a[i] > a[i + 1] || left > a[i] && a[i] < a[i + 1]){
                    r++;
                    left = a[i];
                }
            return r;
        }
    }

    static class s2211{//Count Collisions on a Road
        public int countCollisions(String dir){
            int r = 0, i = 0, right = 0;
            for(; i < dir.length() && dir.charAt(i) == 'L'; i++) ;
            for(; i < dir.length(); i++)
                if(dir.charAt(i) == 'R')
                    right++;
                else{
                    r += dir.charAt(i) == 'S' ? right : right + 1;
                    right = 0;
                }
            return r;
        }
    }

    static class s2212{//Maximum Points in an Archery Competition
        int r[] = new int[12], maxPoints = 0;
        public int[] maximumBobPoints(int numArrows, int[] aliceArrows){
            bt(0, numArrows, aliceArrows, new int[aliceArrows.length], 0);
            return r;
        }

        void bt(int i, int numArrows, int[] aliceArrows, int[] bobArrows, int points){
            if(i >= aliceArrows.length && points > maxPoints){
                maxPoints = points;
                r = bobArrows.clone();
                r[0] += numArrows;
            }else if(i < aliceArrows.length){
                if(numArrows > aliceArrows[i]){
                    bobArrows[i] = aliceArrows[i] + 1;
                    bt(i + 1, numArrows - bobArrows[i], aliceArrows, bobArrows, points + i);
                    bobArrows[i] = 0;
                }
                bt(i + 1, numArrows, aliceArrows, bobArrows, points);
            }
        }
    }

    static class s2214{//Minimum Health to Beat Game
        public long minimumHealth(int[] damage, int armor){
            int maxProtect = Math.min(Arrays.stream(damage).max().getAsInt(), armor);
            return Arrays.stream(damage).asLongStream().sum() + 1 - maxProtect;
        }
    }

    static class s2215{//Find the Difference of Two Arrays
        public List<List<Integer>> findDifference(int[] a1, int[] a2){
            Set<Integer> s1 = Arrays.stream(a1).boxed().collect(Collectors.toSet());
            Set<Integer> s2 = Arrays.stream(a2).filter(n -> !s1.contains(n)).boxed().collect(Collectors.toSet());
            Arrays.stream(a2).forEach(s1::remove);
            return Arrays.asList(new ArrayList<>(s1), new ArrayList<>(s2));
        }
    }

    static class s2216{//Minimum Deletions to Make Array Beautiful
        public int minDeletion(int[] a){
            int r = 0, i = 0;
            for(; i < a.length - 1; i++)
                if(a[i] == a[i + 1])
                    r++;
                else i++;
            return r + a.length - i;
        }
    }

    static class s2217{//Find Palindrome With Fixed Length
        public long[] kthPalindrome(int[] queries, int intLength){
            long r[] = new long[queries.length], start = (long) Math.pow(10, (intLength + 1) / 2 - 1);
            for(int i = 0; i < queries.length; i++){
                String left = String.valueOf(start + queries[i] - 1);
                String right = intLength % 2 == 1 ? left.substring(0, left.length() - 1) : left;
                String s = left + new StringBuilder(right).reverse();
                r[i] = s.length() != intLength ? -1 : Long.parseLong(s);
            }
            return r;
        }
    }

    static class s2218{//Maximum Value of K Coins From Piles
        public int maxValueOfCoins(List<List<Integer>> piles, int k){
            return maxVal(0, piles, k, new Integer[piles.size() + 1][k + 1]);
        }
        int maxVal(int pileIdx, List<List<Integer>> piles, int k, Integer[][] dp){
            if(pileIdx >= piles.size())
                return 0;
            if(dp[pileIdx][k] != null)
                return dp[pileIdx][k];
            int r = maxVal(pileIdx + 1, piles, k, dp);
            for(int i = 0, curr = 0; i < piles.get(pileIdx).size() && i < k; i++){
                curr += piles.get(pileIdx).get(i);
                r = Math.max(r, curr + maxVal(pileIdx + 1, piles, k - i - 1, dp));
            }
            return dp[pileIdx][k] = r;
        }
    }

    static class s2219{//Maximum Sum Score of Array
        public long maximumSumScore(int[] a){
            long r = Long.MIN_VALUE, left = 0, right = Arrays.stream(a).asLongStream().sum();
            for(int n : a){
                left += n;
                r = Math.max(r, Math.max(left, right));
                right -= n;
            }
            return r;
        }
    }

    static class s2220{//Minimum Bit Flips to Convert Number
        public int minBitFlips(int start, int goal){
            return Integer.bitCount(start ^ goal);
        }
    }

    static class s2221{//Find Triangular Sum of an Array
        public int triangularSum(int[] a){
            for(int len = a.length - 1; len > 0; len--){
                int[] b = new int[len];
                for(int i = 0; i < b.length; i++)
                    b[i] = (a[i] + a[i + 1]) % 10;
                a = b;
            }
            return a[0];
        }
    }

    static class s2222{//Number of Ways to Select Buildings
        public long numberOfWays(String s){
            int n = s.length(), all1 = s.chars().map(c -> c - '0').sum(), all0 = s.length() - all1;
            long r = 0;
            for(int i = 0, ones = 0, zero = 0; i < s.length(); i++)
                if(s.charAt(i) == '0'){
                    r += (long) ones * (all1 - ones);
                    zero++;
                }else{
                    r += (long) zero * (all0 - zero);
                    ones++;
                }
            return r;
        }
    }

    static class s2223{//Sum of Scores of Built Strings
        public long sumScores(String s){
            char[] a = s.toCharArray();
            int n = a.length, x = 0, y = 0, z[] = new int[n];
            long r = n;
            for(int i = 1; i < n; i++){
                z[i] = Math.max(0, Math.min(z[i - x], y - i + 1));
                while(i + z[i] < n && a[z[i]] == a[i + z[i]]){
                    x = i;
                    y = i + z[i];
                    z[i]++;
                }
                r += z[i];
            }
            return r;
        }
    }

    static class s2224{//Minimum Number of Operations to Convert Time
        public int convertTime(String from, String to){
            int diff = min(to) - min(from), ops[] = {60, 15, 5, 1}, r = 0;
            for(int i = 0; i < ops.length && diff > 0; diff = diff % ops[i++])
                r += diff / ops[i];
            return r;
        }

        int min(String time){
            String[] t = time.split(":");
            return Integer.parseInt(t[0]) * 60 + Integer.parseInt(t[1]);
        }
    }

    static class s2225{//Find Players With Zero or One Losses
        public List<List<Integer>> findWinners(int[][] matches){
            Map<Integer, Integer> losses = new TreeMap<>();
            for(int[] m : matches){
                losses.put(m[0], losses.getOrDefault(m[0], 0));
                losses.put(m[1], losses.getOrDefault(m[1], 0) + 1);
            }
            List<List<Integer>> r = Arrays.asList(new ArrayList<>(), new ArrayList<>());
            for(Integer player : losses.keySet())
                if(losses.get(player) <= 1)
                    r.get(losses.get(player)).add(player);
            return r;
        }
    }

    static class s2226{//Maximum Candies Allocated to K Children
        public int maximumCandies(int[] candies, long k){
            int lo = 1, hi = 10_000_000, r = 0;
            while(lo <= hi){
                int amount = (lo + hi) / 2;
                long n = 0;
                for(int i = 0; i < candies.length && n < k; i++)
                    n += candies[i] / amount;
                if(n >= k){
                    r = amount;
                    lo = amount + 1;
                }else hi = amount - 1;
            }
            return r;
        }
    }

    static class s2227{//Encrypt and Decrypt Strings
        class Encrypter{
            Map<Character, String> enc = new HashMap<>();
            Map<String, Integer> counts = new HashMap<>();

            public Encrypter(char[] keys, String[] values, String[] dictionary){
                for(int i = 0; i < keys.length; i++)
                    enc.put(keys[i], values[i]);
                for(String word : dictionary){
                    String e = encrypt(word);
                    counts.put(e, counts.getOrDefault(e, 0) + 1);
                }
            }

            public String encrypt(String word){return word.chars().mapToObj(c -> enc.get((char) c)).collect(Collectors.joining());}

            public int decrypt(String word){return counts.getOrDefault(word, 0);}
        }
    }

    static class s2229{//Check if an Array Is Consecutive
        public boolean isConsecutive(int[] a){
            Arrays.sort(a);
            for(int i = 1; i < a.length; i++)
                if(a[i - 1] + 1 != a[i])
                    return false;
            return true;
        }
    }

    static class s2231{//Largest Number After Digit Swaps by Parity
        public int largestInteger(int n){
            char[] a = String.valueOf(n).toCharArray();
            for(int i = 0; i < a.length; i++)
                for(int j = i + 1; j < a.length; j++)
                    if(a[j] > a[i] && (a[j] - a[i]) % 2 == 0){
                        char t = a[i];
                        a[i] = a[j];
                        a[j] = t;
                    }
            return Integer.parseInt(new String(a));
        }
    }

    static class s2232{//Minimize Result by Adding Parentheses to Expression
        public String minimizeResult(String expression){
            Function<String, Integer> toi = s -> s.isEmpty() ? 0 : Integer.parseInt(s);
            String p[] = expression.split("\\+"), a = p[0], b = p[1], s = "(" + expression + ")";
            for(int i = 0, minVal = Integer.MAX_VALUE; i < a.length(); i++){
                int n1 = toi.apply(a.substring(0, i)), n2 = toi.apply(a.substring(i));
                for(int j = b.length(); j >= 1; j--){
                    int m2 = toi.apply(b.substring(0, j)), m1 = toi.apply(b.substring(j));
                    int val = (n1 == 0 ? 1 : n1) * (n2 + m2) * (m1 == 0 ? 1 : m1);
                    if(val < minVal){
                        minVal = val;
                        s = (n1 > 0 ? n1 : "") + "(" + n2 + "+" + m2 + ")" + (m1 > 0 ? m1 : "");
                    }
                }
            }
            return s;
        }
    }

    static class s2233{//Maximum Product After K Increments
        public int maximumProduct(int[] a, int k){
            PriorityQueue<Integer> q = new PriorityQueue<>();
            Arrays.stream(a).forEach(q::offer);
            while(k-- > 0)
                q.offer(q.poll() + 1);
            long r = 1;
            for(Integer n : q)
                r = (r * n) % 1_000_000_007;
            return (int) r;
        }
    }

    static class s2237{//Count Positions on Street With Required Brightness
        public int meetRequirement(int n, int[][] lights, int[] reqs){
            int line[] = new int[n + 1], r = 0, sum = 0;
            for(int[] l : lights){
                line[Math.max(0, l[0] - l[1])]++;
                line[Math.min(n, l[0] + l[1] + 1)]--;
            }
            for(int i = 0; i < n; i++){
                sum += line[i];
                r += sum >= reqs[i] ? 1 : 0;
            }
            return r;
        }
    }

    static class s2239{//Find Closest Number to Zero
        public int findClosestNumber(int[] a){
            int minDiff = Integer.MAX_VALUE, r = 0;
            for(int n : a)
                if(Math.abs(n) == minDiff)
                    r = Math.max(r, n);
                else if(Math.abs(n) < minDiff){
                    minDiff = Math.abs(n);
                    r = n;
                }
            return r;
        }
    }

    static class s2240{//Number of Ways to Buy Pens and Pencils
        public long waysToBuyPensPencils(int total, int cost1, int cost2){
            long r = 0;
            for(int amount = 0; amount <= total; amount += cost1)
                r += 1 + (total - amount) / cost2;
            return r;
        }
    }

    static class s2241{//Design an ATM Machine
        class ATM{
            long[][] bank = {{0, 20}, {0, 50}, {0, 100}, {0, 200}, {0, 500}};

            public void deposit(int[] banknotesCount){
                for(int i = 0; i < banknotesCount.length; i++)
                    bank[i][0] += banknotesCount[i];
            }

            public int[] withdraw(int amount){
                int[] r = new int[bank.length];
                for(int i = bank.length - 1; i >= 0 && amount > 0; i--){
                    r[i] = (int) Math.min(amount / bank[i][1], bank[i][0]);
                    amount -= r[i] * bank[i][1];
                }
                if(amount > 0)
                    return new int[]{-1};
                IntStream.range(0, bank.length).forEach(i -> bank[i][0] -= r[i]);
                return r;
            }
        }
    }

    static class s2242{//Maximum Score of a Node Sequence
        public int maximumScore(int[] scores, int[][] edges){
            List<PriorityQueue<Integer>> g = new ArrayList<>();
            for(int i = 0; i < scores.length; i++)
                g.add(new PriorityQueue<>(Comparator.comparingInt(o -> scores[o])));
            for(int[] e : edges){
                addToQueue(g.get(e[0]), e[1]);
                addToQueue(g.get(e[1]), e[0]);
            }
            int r = -1;
            for(int[] e : edges)
                for(int x : g.get(e[0]))
                    for(int y : g.get(e[1]))
                        if(e[0] != y && e[1] != x && x != y)
                            r = Math.max(r, scores[e[0]] + scores[x] + scores[y] + scores[e[1]]);
            return r;
        }

        void addToQueue(PriorityQueue<Integer> q, int val){
            q.offer(val);
            if(q.size() > 3)
                q.poll();
        }
    }

    static class s2243{//Calculate Digit Sum of a String
        public String digitSum(String s, int k){
            if(s.length() <= k)
                return s;
            StringBuilder r = new StringBuilder();
            for(int i = 1, sum = 0; i <= s.length(); i++){
                sum += s.charAt(i - 1) - '0';
                if(i % k == 0 || i == s.length()){
                    r.append(sum);
                    sum = 0;
                }
            }
            return digitSum(r.toString(), k);
        }
    }

    static class s2244{//Minimum Rounds to Complete All Tasks
        public int minimumRounds(int[] tasks){
            Map<Integer, Integer> m = new HashMap<>();
            for(int t : tasks)
                m.put(t, m.getOrDefault(t, 0) + 1);
            int r = 0;
            for(Integer v : m.values()){
                if(v == 1)
                    return -1;
                r += (v + 2) / 3;
            }
            return r;
        }
    }

    static class s2245{//Maximum Trailing Zeros in a Cornered Path
        public int maxTrailingZeros(int[][] g){
            int n = g.length, m = g[0].length, r = 0;
            int[][] twos_h = new int[n + 1][m + 1], twos_v = new int[n + 1][m + 1];
            int[][] fives_h = new int[n + 1][m + 1], fives_v = new int[n + 1][m + 1];
            for(int i = 0; i < n; i++)
                for(int j = 0; j < m; j++){
                    int two = two(g[i][j]), five = five(g[i][j]);
                    twos_h[i + 1][j + 1] = two + twos_h[i + 1][j];
                    twos_v[i + 1][j + 1] = two + twos_v[i][j + 1];
                    fives_h[i + 1][j + 1] = five + fives_h[i + 1][j];
                    fives_v[i + 1][j + 1] = five + fives_v[i][j + 1];
                }
            for(int i = 0; i < n; i++)
                for(int j = 0; j < m; j++){
                    r = Math.max(r, Math.min(twos_h[i + 1][j + 1] + twos_v[i][j + 1], fives_h[i + 1][j + 1] + fives_v[i][j + 1]));
                    r = Math.max(r, Math.min(twos_h[i][m], fives_h[i][m]));
                    r = Math.max(r, Math.min(twos_h[n][j], fives_h[n][j]));
                    r = Math.max(r, Math.min(twos_h[i + 1][m] - twos_h[i + 1][j] + twos_v[i][j + 1], fives_h[i + 1][m] - fives_h[i + 1][j] + fives_v[i][j + 1]));
                    r = Math.max(r, Math.min(twos_h[i + 1][j + 1] + twos_v[n][j + 1] - twos_v[i + 1][j + 1], fives_h[i + 1][j + 1] + fives_v[n][j + 1] - fives_v[i + 1][j + 1]));
                    r = Math.max(r, Math.min(twos_h[i + 1][m] - twos_h[i + 1][j] + twos_v[n][j + 1] - twos_v[i + 1][j + 1], fives_h[i + 1][m] - fives_h[i + 1][j] + fives_v[n][j + 1] - fives_v[i + 1][j + 1]));
                }
            return r;
        }

        int two(int n){
            return n % 2 == 0 ? 1 + two(n / 2) : 0;
        }
        int five(int n){
            return n % 5 == 0 ? 1 + five(n / 5) : 0;
        }
    }

    static class s2246{//Longest Path With Different Adjacent Characters
        int r = 1;
        public int longestPath(int[] parent, String s){
            List<List<Integer>> g = IntStream.range(0, s.length()).mapToObj(v -> new ArrayList<Integer>()).collect(Collectors.toList());
            IntStream.range(1, s.length()).forEach(i -> g.get(parent[i]).add(i));
            dfs(0, g, s);
            return r;
        }

        int dfs(int u, List<List<Integer>> g, String s){
            Queue<Integer> q = new PriorityQueue<>();
            for(int v : g.get(u)){
                int len = dfs(v, g, s);
                if(s.charAt(u) != s.charAt(v))
                    q.offer(-len);
            }
            int a = q.isEmpty() ? 0 : -q.poll(), b = q.isEmpty() ? 0 : -q.poll();
            r = Math.max(r, a + b + 1);
            return a + 1;
        }
    }

    static class s2247{//Maximum Cost of Trip With K Highways
        public int maximumCost(int n, int[][] highways, int k){
            List<List<int[]>> g = IntStream.range(0, n).mapToObj(u -> new ArrayList<int[]>()).collect(Collectors.toList());
            for(int[] h : highways){
                g.get(h[0]).add(new int[]{h[1], h[2]});
                g.get(h[1]).add(new int[]{h[0], h[2]});
            }
            int[][] dp = new int[n][1 << n];
            return IntStream.range(0, n).map(u -> maxCost(u, g, k - 1, 1 << u, dp)).max().getAsInt();
        }

        int maxCost(int u, List<List<int[]>> g, int k, int mask, int[][] dp){
            if(k < 0)
                return 0;
            if(dp[u][mask] != 0)
                return dp[u][mask];
            int r = -1;
            for(int[] e : g.get(u)){
                int v = e[0], cost = e[1];
                if((mask & (1 << v)) == 0){
                    int rr = maxCost(v, g, k - 1, mask | (1 << v), dp);
                    if(rr != -1)
                        r = Math.max(r, cost + rr);
                }
            }
            return dp[u][mask] = r;
        }
    }

    static class s2248{//Intersection of Multiple Arrays
        public List<Integer> intersection(int[][] aa){
            int[] counts = new int[1_001];
            Arrays.stream(aa).forEach(a -> Arrays.stream(a).forEach(n -> counts[n]++));
            return IntStream.range(0, counts.length).filter(n -> counts[n] == aa.length).boxed().collect(Collectors.toList());
        }
    }

    static class s2249{//Count Lattice Points Inside a Circle
        public int countLatticePoints(int[][] circles){
            Set<Integer> s = new HashSet<>();
            for(int[] c : circles){
                int x = c[0], y = c[1], r = c[2];
                for(int a = x - r; a <= x + r; a++)
                    for(int b = y - r; b <= y + r; b++)
                        if((x - a) * (x - a) + (y - b) * (y - b) <= r * r)
                            s.add(301 * a + b);
            }
            return s.size();
        }
    }

    static class s2250{//Count Number of Rectangles Containing Each Point
        public int[] countRectangles(int[][] rectangles, int[][] points){
            Arrays.sort(rectangles, Comparator.comparingInt(a -> a[0]));
            List<List<Integer>> heights = IntStream.range(0, 101).mapToObj(h -> new ArrayList<Integer>()).collect(Collectors.toList());
            for(int[] rec : rectangles)
                heights.get(rec[1]).add(rec[0]);
            int[] r = new int[points.length];
            for(int i = 0; i < points.length; i++)
                for(int h = points[i][1]; h < heights.size(); h++)
                    r[i] += bs(heights.get(h), points[i][0]);
            return r;
        }

        int bs(List<Integer> list, int val){
            int lo = 0, hi = list.size() - 1, r = list.size();
            while(lo <= hi){
                int mid = (lo + hi) / 2;
                if(list.get(mid) >= val){
                    r = mid;
                    hi = mid - 1;
                }else lo = mid + 1;
            }
            return list.size() - r;
        }
    }

    static class s2251{//Number of Flowers in Full Bloom
        public int[] fullBloomFlowers(int[][] flowers, int[] persons){
            Queue<int[]> q = new PriorityQueue<>((a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
            for(int i = 0; i < persons.length; i++)
                q.offer(new int[]{persons[i], 1, i});//time, event, idx
            for(int[] f : flowers){
                q.offer(new int[]{f[0], 0});
                q.offer(new int[]{f[1], 2});
            }
            int r[] = new int[persons.length], blooms = 0;
            while(!q.isEmpty()){
                int[] p = q.poll();
                switch(p[1]){
                    case 0 -> blooms++;
                    case 2 -> blooms--;
                    default -> r[p[2]] = blooms;
                }
            }
            return r;
        }
    }

    static class s2254{//Design Video Sharing Platform
        class VideoSharingPlatform{
            Map<Integer, Video> videos = new HashMap<>();
            PriorityQueue<Integer> available = new PriorityQueue<>();

            public VideoSharingPlatform(){
                IntStream.range(0, 100_000).forEach(n -> available.offer(n));
            }

            public int upload(String video){
                int id = available.poll();
                videos.put(id, new Video(video));
                return id;
            }

            public void remove(int videoId){
                if(videos.containsKey(videoId)){
                    videos.remove(videoId);
                    available.add(videoId);
                }
            }

            public String watch(int videoId, int startMinute, int endMinute){
                if(!videos.containsKey(videoId))
                    return "-1";
                Video video = videos.get(videoId);
                video.views++;
                return video.s.substring(startMinute, Math.min(endMinute + 1, video.s.length()));
            }

            public void like(int videoId){
                if(videos.containsKey(videoId))
                    videos.get(videoId).likes++;
            }

            public void dislike(int videoId){
                if(videos.containsKey(videoId))
                    videos.get(videoId).dislikes++;
            }

            public int[] getLikesAndDislikes(int videoId){
                Video v = videos.get(videoId);
                return v != null ? new int[]{v.likes, v.dislikes} : new int[]{-1};
            }

            public int getViews(int videoId){
                return videos.containsKey(videoId) ? videos.get(videoId).views : -1;
            }

            class Video{
                int likes, dislikes, views;
                String s;

                public Video(String s){this.s = s;}
            }
        }
    }

    static class s2255{//Count Prefixes of a Given String
        public int countPrefixes(String[] words, String s){
            return (int) Arrays.stream(words).filter(s::startsWith).count();
        }
    }

    static class s2256{//Minimum Average Difference
        public int minimumAverageDifference(int[] a){
            long left = 0, right = Arrays.stream(a).asLongStream().sum(), minAvr = Integer.MAX_VALUE;
            int r = 0;
            for(int i = 0; i < a.length; i++){
                left += a[i];
                right -= a[i];
                long v = Math.abs(left / (i + 1) - (i == a.length - 1 ? 0 : right / (a.length - i - 1)));
                if(v < minAvr){
                    minAvr = v;
                    r = i;
                }
            }
            return r;
        }
    }

    static class s2257{//Count Unguarded Cells in the Grid
        public int countUnguarded(int n, int m, int[][] guards, int[][] walls){
            List<TreeMap<Integer, Integer>> rows = new ArrayList<>(n), cols = new ArrayList<>(m);
            for(int i = 0; i < n; i++){
                rows.add(new TreeMap<>());
                rows.get(i).put(-1, 1);
                rows.get(i).put(m, 1);
            }
            for(int i = 0; i < m; i++){
                cols.add(new TreeMap<>());
                cols.get(i).put(-1, 1);
                cols.get(i).put(n, 1);
            }
            boolean[][] occupied = new boolean[n][m];
            for(int[] g : guards){
                rows.get(g[0]).put(g[1], 0);
                cols.get(g[1]).put(g[0], 0);
                occupied[g[0]][g[1]] = true;
            }
            for(int[] w : walls){
                rows.get(w[0]).put(w[1], 1);
                cols.get(w[1]).put(w[0], 1);
                occupied[w[0]][w[1]] = true;
            }
            int r = 0;
            for(int i = 0; i < n; i++)
                for(int j = 0; j < m; j++)
                    if(!occupied[i][j]){
                        TreeMap<Integer, Integer> m1 = rows.get(i), m2 = cols.get(j);
                        r += m1.lowerEntry(j).getValue() * m1.higherEntry(j).getValue() * m2.lowerEntry(i).getValue() * m2.higherEntry(i).getValue();
                    }
            return r;
        }
    }

    static class s2259{//Remove Digit From Number to Maximize Result
        public String removeDigit(String number, char digit){
            TreeSet<String> r = new TreeSet<>();
            for(int i = 0; i < number.length(); i++)
                if(number.charAt(i) == digit)
                    r.add(number.substring(0, i) + number.substring(i + 1));
            return r.last();
        }
    }

    static class s2260{//Minimum Consecutive Cards to Pick Up
        public int minimumCardPickup(int[] cards){
            int r = Integer.MAX_VALUE;
            Map<Integer, Integer> m = new HashMap<>();
            for(int i = 0; i < cards.length; i++){
                if(m.containsKey(cards[i]))
                    r = Math.min(r, i - m.get(cards[i]) + 1);
                m.put(cards[i], i);
            }
            return r == Integer.MAX_VALUE ? -1 : r;
        }
    }

    static class s2261{//K Divisible Elements Subarrays
        public int countDistinct(int[] a, int k, int p){
            int r = 0;
            Trie trie = new Trie();
            for(int i = 0; i < a.length; i++){
                int count = 0;
                Trie node = trie;
                for(int j = i; j < a.length && count <= k; j++){
                    if(a[j] % p == 0)
                        count++;
                    if(count <= k && node.nodes[a[j]] == null)
                        r++;
                    if(node.nodes[a[j]] == null)
                        node.nodes[a[j]] = new Trie();
                    node = node.nodes[a[j]];
                }
            }
            return r;
        }

        class Trie{
            Trie[] nodes = new Trie[201];
        }
    }

    static class s2262{// Total Appeal of A String
        public long appealSum(String s){
            long r = 0, cur = 0, prev[] = new long[26];
            for(int i = 0; i < s.length(); ++i){
                cur += i + 1 - prev[s.charAt(i) - 'a'];
                prev[s.charAt(i) - 'a'] = i + 1;
                r += cur;
            }
            return r;
        }
    }

    static class s2263{//Make Array Non-decreasing or Non-increasing
        public int convertArray(int[] a){
            return Math.min(minOps(a), minOps(reverse(a)));
        }

        int minOps(int[] a){
            int dp[] = new int[1_001];
            for(int n : a){
                int nextDp[] = new int[dp.length], min = dp[0];
                for(int d = 0; d < dp.length; d++){
                    min = Math.min(min, dp[d]);
                    nextDp[d] = min + Math.abs(n - d);
                }
                dp = nextDp;
            }
            return Arrays.stream(dp).min().getAsInt();
        }

        int[] reverse(int[] a){
            for(int i = 0, j = a.length - 1; i < j; i++, j--){
                int t = a[i];
                a[i] = a[j];
                a[j] = t;
            }
            return a;
        }
    }

    static class s2264{//Largest 3-Same-Digit Number in String
        public String largestGoodInteger(String n){
            for(int i = 9; i >= 0; i--)
                if(n.contains("" + i + i + i))
                    return "" + i + i + i;
            return "";
        }
    }

    static class s2265{//Count Nodes Equal to Average of Subtree
        int r = 0;
        public int averageOfSubtree(TreeNode root){
            tr(root);
            return r;
        }
        int[] tr(TreeNode node){
            if(node == null)
                return new int[]{0, 0};
            int[] left = tr(node.left), right = tr(node.right);
            int count = left[0] + right[0] + 1, sum = left[1] + right[1] + node.val;
            if(sum / count == node.val)
                r++;
            return new int[]{count, sum};
        }
    }

    static class s2266{//Count Number of Texts
        public int countTexts(String pressedKeys){
            int[] m = {0, 0, 3, 3, 3, 3, 3, 4, 3, 4};
            return count(0, pressedKeys.toCharArray(), m, new Integer[pressedKeys.length() + 1]);
        }
        int count(int i, char[] a, int[] m, Integer[] dp){
            if(i >= a.length)
                return 1;
            if(dp[i] != null)
                return dp[i];
            int r = 0;
            for(int j = 0; i + j < a.length && a[i] == a[i + j] && j < m[a[i] - '0']; j++)
                r = (r + count(i + j + 1, a, m, dp)) % 1_000_000_007;
            return dp[i] = r;
        }
    }

    static class s2268{//Minimum Number of Keypresses
        public int minimumKeypresses(String s){
            int f[] = new int[26], r = 0, n = f.length;
            s.chars().forEach(c -> f[c - 'a']++);
            Arrays.sort(f);
            for(int i = 0; i < n; i++)
                r += (i / 9 + 1) * f[n - 1 - i];
            return r;
        }
    }

    static class s2269{//Find the K-Beauty of a Number
        public int divisorSubstrings(int n, int k){
            int r = 0;
            String s = String.valueOf(n);
            for(int i = 0; i + k <= s.length(); i++){
                int m = Integer.parseInt(s.substring(i, i + k));
                if(m != 0 && n % m == 0)
                    r++;
            }
            return r;
        }
    }

    static class s2270{//Number of Ways to Split Array
        public int waysToSplitArray(int[] a){
            long right = Arrays.stream(a).asLongStream().sum(), left = 0;
            int r = 0;
            for(int i = 0; i < a.length - 1; i++){
                left += a[i];
                right -= a[i];
                r += left >= right ? 1 : 0;
            }
            return r;
        }
    }

    static class s2273{//Find Resultant Array After Removing Anagrams
        public List<String> removeAnagrams(String[] words){
            List<String> r = new ArrayList<>();
            for(int i = 0; i < words.length; i++){
                String ana = ana(words[i]);
                r.add(words[i]);
                for(; i + 1 < words.length && ana.equals(ana(words[i + 1])); i++) ;
            }
            return r;
        }

        String ana(String s){
            char[] a = s.toCharArray();
            Arrays.sort(a);
            return new String(a);
        }
    }

    static class s2274{//Maximum Consecutive Floors Without Special Floors
        public int maxConsecutive(int bottom, int top, int[] special){
            Arrays.sort(special);
            int r = Math.max(special[0] - bottom, top - special[special.length - 1]);
            for(int i = 1; i < special.length; i++)
                r = Math.max(r, special[i] - special[i - 1] - 1);
            return r == 1 ? 0 : r;
        }
    }

    static class s2275{//Largest Combination With Bitwise AND Greater Than Zero
        public int largestCombination(int[] a){
            int r = 0;
            for(int i = 0; i < 32; i++){
                int d = i;
                r = Math.max(r, Arrays.stream(a).map(n -> (n >> d) & 1).sum());
            }
            return r;
        }
    }

    static class s2276{//Count Integers in Intervals
        class CountIntervals{
            TreeMap<Integer, Integer> m = new TreeMap<>();
            int count = 0;

            public void add(int left, int right){
                int l = left, r = right;
                while(m.floorKey(r) != null && m.get(m.floorKey(r)) >= l){
                    int preL = m.floorKey(r), preR = m.get(preL);
                    count -= (preR - preL + 1);
                    m.remove(preL);
                    l = Math.min(l, preL);
                    r = Math.max(r, preR);
                }
                m.put(l, r);
                count += (r - l + 1);
            }

            public int count(){return count;}
        }
    }

    static class s2278{//Percentage of Letter in String
        public int percentageLetter(String s, char letter){
            return (int) s.chars().filter(c -> c == letter).count() * 100 / s.length();
        }
    }

    static class s2279{//Maximum Bags With Full Capacity of Rocks
        public int maximumBags(int[] capacity, int[] rocks, int extra){
            int r = 0, diff[] = IntStream.range(0, rocks.length).map(i -> capacity[i] - rocks[i]).sorted().toArray();
            for(int i = 0; i < rocks.length && extra - diff[i] >= 0; extra -= diff[i++])
                r++;
            return r;
        }
    }

    static class s2280{//Minimum Lines to Represent a Line Chart
        public int minimumLines(int[][] prices){
            Arrays.sort(prices, Comparator.comparingInt(a -> a[0]));
            int r = 1;
            for(int i = 2; i < prices.length; i++){
                int x1 = prices[i - 2][0], x2 = prices[i - 1][0], x3 = prices[i][0];
                int y1 = prices[i - 2][1], y2 = prices[i - 1][1], y3 = prices[i][1];
                if((y2 - y1) * (x3 - x1) != (y3 - y1) * (x2 - x1))
                    r++;
            }
            return prices.length == 1 ? 0 : r;
        }
    }

    static class s2282{//Number of People That Can Be Seen in a Grid
        public int[][] seePeople(int[][] h){
            int[][] r = new int[h.length][h[0].length];
            for(int i = 0; i < h.length; i++){
                Stack<Integer> s = new Stack<>();
                for(int j = h[0].length - 1; j >= 0; j--)
                    process(s, r, h, i, j);
            }
            for(int j = 0; j < h[0].length; j++){
                Stack<Integer> s = new Stack<>();
                for(int i = h.length - 1; i >= 0; i--)
                    process(s, r, h, i, j);
            }
            return r;
        }

        void process(Stack<Integer> s, int[][] r, int[][] h, int i, int j){
            for(; !s.isEmpty() && h[i][j] > s.peek(); s.pop())
                r[i][j]++;
            r[i][j] += !s.isEmpty() && h[i][j] <= s.peek() ? 1 : 0;
            if(s.isEmpty() || h[i][j] != s.peek())
                s.push(h[i][j]);
        }
    }

    static class s2283{//Check if Number Has Equal Digit Count and Digit Value
        public boolean digitCount(String n){
            int[] f = new int[10];
            n.chars().forEach(c -> f[c - '0']++);
            for(int i = 0; i < n.length(); i++)
                if(n.charAt(i) - '0' != f[i])
                    return false;
            return true;
        }
    }

    static class s2284{//Sender With Largest Word Count
        public String largestWordCount(String[] messages, String[] senders){
            Map<String, Integer> counts = new HashMap<>();
            for(int i = 0; i < messages.length; i++)
                counts.put(senders[i], counts.getOrDefault(senders[i], 0) + messages[i].split(" ").length);
            TreeMap<Integer, TreeSet<String>> m = new TreeMap<>();
            for(String sender : counts.keySet())
                m.computeIfAbsent(counts.get(sender), e -> new TreeSet<>()).add(sender);
            return m.lastEntry().getValue().last();
        }
    }

    static class s2285{//Maximum Total Importance of Roads
        public long maximumImportance(int n, int[][] roads){
            int f[] = new int[n], r = 0;
            for(int[] road : roads){
                f[road[0]]++;
                f[road[1]]++;
            }
            Arrays.sort(f);
            return IntStream.range(0, n).mapToLong(i -> f[i] * (i + 1L)).sum();
        }
    }

    static class s2287{//Rearrange Characters to Make Target String
        public int rearrangeCharacters(String s, String t){
            int[] sf = new int[26], tf = new int[26];
            s.chars().forEach(c -> sf[c - 'a']++);
            t.chars().forEach(c -> tf[c - 'a']++);
            return t.chars().map(c -> sf[c - 'a'] / tf[c - 'a']).min().getAsInt();
        }
    }
}
