package leetcode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class p21{
    static class s2100{//Find Good Days to Rob the Bank
        public List<Integer> goodDaysToRobBank(int[] sec, int time){
            boolean[] good = new boolean[sec.length];
            for(int i = sec.length - 2, count = 0; i >= 0; i--)
                good[i] = (count = sec[i] <= sec[i + 1] ? count + 1 : 0) >= time;
            List<Integer> r = new ArrayList<>();
            for(int i = 1, count = 0; i < sec.length; i++)
                if((count = sec[i - 1] >= sec[i] ? count + 1 : 0) >= time && good[i])
                    r.add(i);
            if(time == 0)
                r.addAll(Arrays.asList(0, sec.length - 1));
            return r;
        }
    }

    static class s2101{//Detonate the Maximum Bombs
        public int maximumDetonation(int[][] bombs){
            List<List<Integer>> g = IntStream.range(0, bombs.length).mapToObj(i -> new ArrayList<Integer>()).collect(Collectors.toList());
            for(int i = 0; i < bombs.length; i++)
                for(int j = 0; j < bombs.length; j++)
                    if(i != j && detonates(i, j, bombs))
                        g.get(i).add(j);
            int r = 0;
            for(int i = 0; i < bombs.length; i++)
                r = Math.max(dfs(i, g, new boolean[bombs.length]), r);
            return r;
        }

        int dfs(int u, List<List<Integer>> g, boolean[] seen){
            seen[u] = true;
            return 1 + g.get(u).stream().filter(v -> !seen[v]).mapToInt(v -> dfs(v, g, seen)).sum();
        }

        boolean detonates(int i, int j, int[][] bombs){//true if i bomb detonates j bomb
            long x = bombs[j][0] - bombs[i][0], y = bombs[j][1] - bombs[i][1];
            return x * x + y * y <= (long) bombs[i][2] * bombs[i][2];
        }
    }

    static class s2103{//Rings and Rods
        public int countPoints(String rings){
            String[] rods = new String[10];
            Arrays.fill(rods, "");
            for(int i = 0; i < rings.length(); i += 2){
                int rod = rings.charAt(i + 1) - '0';
                if(rods[rod].indexOf(rings.charAt(i)) == -1)
                    rods[rod] += rings.charAt(i);
            }
            return (int) Arrays.stream(rods).filter(s -> s.length() == 3).count();
        }
    }

    static class s2104{//Sum of Subarray Ranges
        public long subArrayRanges(int[] a){
            long r = 0;
            for(int i = 0; i < a.length; i++)
                for(int j = i, min = a[j], max = a[j]; j < a.length; j++){
                    max = Math.max(a[j], max);
                    min = Math.min(a[j], min);
                    r += max - min;
                }
            return r;
        }
    }

    static class s2105{//Watering Plants II
        public int minimumRefill(int[] plants, int capA, int capB){
            int r = 0;
            for(int i = 0, j = plants.length - 1, canA = capA, canB = capB; i <= j; canA -= plants[i++], canB -= plants[j--]){
                if(i == j)
                    return Math.max(canA, canB) >= plants[i] ? r : r + 1;
                if(canA < plants[i]){
                    canA = capA;
                    r++;
                }
                if(canB < plants[j]){
                    canB = capB;
                    r++;
                }
            }
            return r;
        }
    }

    static class s2106{//Maximum Fruits Harvested After at Most K Steps
        public int maxTotalFruits(int[][] fruits, int start, int k){
            int[] cs = new int[2 * 100_000 + 2];
            for(int i = 1, j = 0; i < cs.length; i++)
                if(j < fruits.length && fruits[j][0] == i - 1)
                    cs[i] = cs[i - 1] + fruits[j++][1];
                else cs[i] = cs[i - 1];
            int r = 0;
            for(int i = start + 1; i > 0 && (start + 1 - i) * 2 <= k; i--){
                int j = Math.max(0, k - 2 * (start + 1 - i));
                int p = Math.min(start + j + 1, cs.length - 1);
                r = Math.max(r, cs[p] - cs[i - 1]);
            }
            for(int i = start + 1; i < cs.length && (i - (start + 1)) * 2 <= k; i++){
                int j = Math.max(0, k - 2 * (i - (start + 1)));
                int p = Math.max(start + 1 - j, 1);
                r = Math.max(r, cs[i] - cs[p - 1]);
            }
            return r;
        }
    }

    static class s2107{//Number of Unique Flavors After Sharing K Candies
        public int shareCandies(int[] candies, int k){
            int r = 0;
            Map<Integer, Integer> flavors = new HashMap<>();
            Arrays.stream(candies).forEach(c -> flavors.put(c, flavors.getOrDefault(c, 0) + 1));
            for(int i = 0; i < candies.length; i++){
                if(i >= k){
                    r = Math.max(r, flavors.size());
                    flavors.put(candies[i - k], flavors.getOrDefault(candies[i - k], 0) + 1);
                }
                flavors.put(candies[i], flavors.get(candies[i]) - 1);
                if(flavors.get(candies[i]) == 0)
                    flavors.remove(candies[i]);
            }
            return Math.max(r, flavors.size());
        }
    }

    static class s2108{//Find First Palindromic String in the Array
        public String firstPalindrome(String[] words){
            return Arrays.stream(words).filter(w -> isPali(w.toCharArray())).findFirst().orElse("");
        }

        boolean isPali(char[] a){
            for(int i = 0, j = a.length - 1; i < j; i++, j--)
                if(a[i] != a[j])
                    return false;
            return true;
        }
    }

    static class s2109{//Adding Spaces to a String
        public String addSpaces(String s, int[] spaces){
            StringBuilder r = new StringBuilder();
            for(int i = 0, j = 0; i < s.length(); r.append(s.charAt(i++)))
                if(j < spaces.length && i == spaces[j]){
                    r.append(" ");
                    j++;
                }
            return r.toString();
        }
    }

    static class s2110{//Number of Smooth Descent Periods of a Stock
        public long getDescentPeriods(int[] prices){
            long r = 1, periodLen = 1;
            for(int i = 1; i < prices.length; i++){
                periodLen = prices[i - 1] - 1 == prices[i] ? periodLen + 1 : 1;
                r += periodLen;
            }
            return r;
        }
    }

    static class s2113{//Elements in Array After Removing and Replacing Elements
        public int[] elementInNums(int[] a, int[][] queries){
            int r[] = new int[queries.length], i = 0;
            for(int[] q : queries){
                int t = q[0] % (a.length * 2), j = q[1];
                int from = t <= a.length ? t : 0;
                int to = t > a.length ? t - a.length : a.length - 1;
                r[i++] = from + j <= to ? a[from + j] : -1;
            }
            return r;
        }
    }

    static class s2120{//Execution of All Suffix Instructions Staying in a Grid
        public int[] executeInstructions(int n, int[] startPos, String s){
            int[] r = new int[s.length()];
            for(int i = 0; i < s.length(); i++)
                r[i] = count(i, n, startPos[0], startPos[1], s);
            return r;
        }
        int count(int si, int n, int x, int y, String s){
            if(si == s.length())
                return 0;
            switch(s.charAt(si)){
                case 'U' -> x -= 1;
                case 'D' -> x += 1;
                case 'R' -> y += 1;
                case 'L' -> y -= 1;
            }
            return 0 <= x && x < n && 0 <= y && y < n ? 1 + count(si + 1, n, x, y, s) : 0;
        }
    }

    static class s2121{//Intervals Between Identical Elements
        public long[] getDistances(int[] a){
            long[] r = new long[a.length];
            sum(a, 0, 1, r);
            sum(a, a.length - 1, -1, r);
            return r;
        }

        void sum(int[] a, int start, int increment, long[] r){
            long[] count = new long[100_001], sum = new long[100_001];
            for(int i = start, j = 0; 0 <= i && i < r.length; i += increment, j++){
                r[i] += count[a[i]] * j - sum[a[i]];
                count[a[i]]++;
                sum[a[i]] += j;
            }
        }
    }

    static class s2122{//Recover the Original Array
        public int[] recoverArray(int[] a){
            Arrays.sort(a);
            for(int i = 1; i < a.length; i++){
                int k = a[i] - a[0];
                if(k > 0 && k % 2 == 0){
                    Map<Integer, Integer> freq = new HashMap<>();
                    Arrays.stream(a).forEach(n -> freq.put(n, freq.getOrDefault(n, 0) + 1));
                    int[] r = new int[a.length / 2];
                    if(dfs(a, k, r, freq))
                        return r;
                }
            }
            return null;
        }

        boolean dfs(int[] a, int k, int[] r, Map<Integer, Integer> freq){
            int i = 0;
            for(int n : a){
                if(freq.get(n) > 0){
                    if(freq.getOrDefault(n + k, 0) <= 0)
                        return false;
                    freq.put(n, freq.get(n) - 1);
                    freq.put(n + k, freq.get(n + k) - 1);
                    r[i++] = n + k / 2;
                }
            }
            return true;
        }
    }

    static class s2124{//Check if All A's Appears Before All B's
        public boolean checkString(String s){
            int i = 0, j = s.length() - 1;
            for(; i < s.length() && s.charAt(i) != 'b'; i++) ;
            for(; j >= 0 && s.charAt(j) != 'a'; j--) ;
            return i - 1 == j;
        }
    }

    static class s2125{//Number of Laser Beams in a Bank
        public int numberOfBeams(String[] bank){
            int r = 0, prev = 0;
            for(String b : bank){
                int ones = 0;
                for(char c : b.toCharArray())
                    ones += c == '1' ? 1 : 0;
                if(ones > 0){
                    r += prev * ones;
                    prev = ones;
                }
            }
            return r;
        }
    }

    static class s2126{//Destroying Asteroids
        public boolean asteroidsDestroyed(int mass, int[] asteroids){
            Arrays.sort(asteroids);
            long m = mass;
            for(int asteroid : asteroids)
                if(m < asteroid)
                    return false;
                else m += asteroid;
            return true;
        }
    }

    static class s2128{//Remove All Ones With Row and Column Flips
        public boolean removeOnes(int[][] g){
            int[] flipped = Arrays.stream(g[0]).map(n -> 1 - n).toArray();
            return IntStream.range(1, g.length).allMatch(r -> Arrays.equals(g[r], g[0]) || Arrays.equals(g[r], flipped));
        }
    }

    static class s2129{//Capitalize the Title
        public String capitalizeTitle(String title){
            return Arrays.stream(title.split(" "))
                    .map(w -> w.length() <= 2 ? w.toLowerCase() : Character.toUpperCase(w.charAt(0)) + w.toLowerCase().substring(1))
                    .collect(Collectors.joining(" "));
        }
    }

    static class s2130{//Maximum Twin Sum of a Linked List
        public int pairSum(ListNode head){
            List<Integer> vals = new ArrayList<>();
            for(; head != null; head = head.next)
                vals.add(head.val);
            int sum = 0;
            for(int lo = 0, hi = vals.size() - 1; lo < hi; lo++, hi--)
                sum = Math.max(vals.get(lo) + vals.get(hi), sum);
            return sum;
        }
    }

    static class s2131{//Longest Palindrome by Concatenating Two Letter Words
        public int longestPalindrome(String[] words){
            Map<String, Integer> m = new HashMap<>();
            int r = 0, doubleLetter = 0;
            for(String w : words)
                m.put(w, m.getOrDefault(w, 0) + 1);
            for(char a = 'a'; a <= 'z'; a++){
                int aaCount = m.getOrDefault("" + a + a, 0);
                if(doubleLetter == 0 && aaCount % 2 == 1)
                    doubleLetter = 2;
                r += (aaCount / 2) * 2 * 2;
                for(char b = (char) (a + 1); b <= 'z'; b++)
                    r += Math.min(m.getOrDefault("" + a + b, 0), m.getOrDefault("" + b + a, 0)) * 4;
            }
            return r + doubleLetter;
        }
    }

    static class s2133{//Check if Every Row and Column Contains All Numbers
        public boolean checkValid(int[][] m){
            for(int i = 0; i < m.length; i++){
                int x1 = 0, x2 = 0;
                for(int j = 0; j < m.length; j++){
                    x1 ^= (j + 1) ^ m[i][j];
                    x2 ^= (j + 1) ^ m[j][i];
                }
                if(x1 + x2 != 0)
                    return false;
            }
            return true;
        }
    }

    static class s2134{//Minimum Swaps to Group All 1's Together II
        public int minSwaps(int[] a){
            int len = Arrays.stream(a).sum(), ones = 0, r = Integer.MAX_VALUE;
            for(int i = 0; i < len - 1; i++)
                ones += a[i];
            if(ones == 0)
                return 0;
            for(int lo = 0, hi = len - 1; lo < a.length; lo++, hi = (hi + 1) % a.length){
                ones += a[hi];
                r = Math.min(len - ones, r);
                ones -= a[lo];
            }
            return r;
        }
    }

    static class s2135{//Count Words Obtained After Adding a Letter
        public int wordCount(String[] startWords, String[] targetWords){
            Set<String> s = Arrays.stream(startWords).map(this::sorted).collect(Collectors.toSet());
            int r = 0;
            for(String w : targetWords){
                String sorted = sorted(w);
                for(int i = 0; i < w.length(); i++)
                    if(s.contains(sorted.substring(0, i) + sorted.substring(i + 1))){
                        r++;
                        break;
                    }
            }
            return r;
        }

        String sorted(String w){
            char[] a = w.toCharArray();
            Arrays.sort(a);
            return new String(a);
        }
    }

    static class s2136{//Earliest Possible Day of Full Bloom
        public int earliestFullBloom(int[] plantTime, int[] growTime){
            int[][] pg = new int[plantTime.length][2];
            for(int i = 0; i < plantTime.length; i++)
                pg[i] = new int[]{plantTime[i], growTime[i]};
            Arrays.sort(pg, (a, b) -> b[1] - a[1]);
            int r = 0, time = 0;
            for(int[] a : pg){
                int plant = a[0], grow = a[1];
                time += plant;
                r = Math.max(r, time + grow);
            }
            return r;
        }
    }

    static class s2137{//Pour Water Between Buckets to Make Water Levels Equal
        /**
         * You have n buckets each containing some gallons of water in it, represented by a 0-indexed integer array buckets,
         * where the ith bucket contains buckets[i] gallons of water. You are also given an integer loss. You want to make
         * the amount of water in each bucket equal. You can pour any amount of water from one bucket to another bucket
         * (not necessarily an integer). However, every time you pour k gallons of water, you spill loss percent of k.
         * Return the maximum amount of water in each bucket after making the amount of water equal. Answers within 10-5 of
         * the actual answer will be accepted.
         */
        public double equalizeWater(int[] buckets, int loss){
            double lo = 0, hi = 100_000, r = 0, retainPercent = (100.0 - loss) / 100;
            while(hi - lo > 0.00001){
                double mid = (lo + hi) / 2.0, need = 0, have = 0;
                for(int b : buckets)
                    if(b >= mid)
                        have += b - mid;
                    else need += mid - b;
                if(need <= have * retainPercent){
                    r = mid;
                    lo = mid;
                }else hi = mid;
            }
            return r;
        }
    }

    static class s2138{//Divide a String Into Groups of Size k
        public String[] divideString(String s, int k, char fill){
            String[] r = new String[s.length() / k + (s.length() % k > 0 ? 1 : 0)];
            for(int i = 0, j = 0; i < s.length(); i += k, j++){
                String group = s.substring(i, Math.min(i + k, s.length()));
                while(group.length() < k)
                    group += fill;
                r[j] = group;
            }
            return r;
        }
    }

    static class s2139{//Minimum Moves to Reach Target Score
        public int minMoves(int target, int doubles){
            int r = 0;
            for(; target > 1 && doubles > 0; r++)
                if(target % 2 == 0){
                    target /= 2;
                    doubles--;
                }else target--;
            return r + target - 1;
        }
    }

    static class s2140{//Solving Questions With Brainpower
        public long mostPoints(int[][] questions){
            long[] dp = new long[questions.length + 1];
            for(int i = questions.length - 1; i >= 0; i--){
                int j = Math.min(i + questions[i][1] + 1, dp.length - 1);
                dp[i] = Math.max(dp[i + 1], questions[i][0] + dp[j]);
            }
            return dp[0];
        }
    }

    static class s2141{//Maximum Running Time of N Computers
        public long maxRunTime(int n, int[] batteries){
            long lo = 0, hi = Arrays.stream(batteries).mapToLong(i -> i).sum() / n;
            while(lo < hi){
                long mid = (lo + hi + 1) / 2, minutes = 0;
                for(int b : batteries)
                    minutes += Math.min(mid, b);
                if(minutes >= n * mid)
                    lo = mid;
                else hi = mid - 1;
            }
            return lo;
        }
    }

    static class s2143{//Choose Numbers From Two Arrays in Range
        public int countSubranges(int[] a1, int[] a2){
            Integer[][] dp = new Integer[a1.length][10_001];
            long r = 0;
            for(int i = 0; i < a1.length; i++)
                r = (r + count(i + 1, a1[i], a1, a2, dp) + count(i + 1, -a2[i], a1, a2, dp)) % 1_000_000_007;
            return (int) r;
        }
        int count(int i, int sum, int[] a1, int[] a2, Integer[][] dp){
            if(i == a1.length)
                return sum == 0 ? 1 : 0;
            int key = sum + 5_000;
            if(key < 0 || key > 10_000)
                return 0;
            if(dp[i][key] != null)
                return dp[i][key];
            return dp[i][key] = ((sum == 0 ? 1 : 0) + count(i + 1, sum + a1[i], a1, a2, dp) + count(i + 1, sum - a2[i], a1, a2, dp)) % 1_000_000_007;
        }
    }

    static class s2144{//Minimum Cost of Buying Candies With Discount
        public int minimumCost(int[] cost){
            Arrays.sort(cost);
            int r = 0;
            for(int i = cost.length - 1; i >= 0; i -= 3){
                r += cost[i];
                if(i - 1 >= 0)
                    r += cost[i - 1];
            }
            return r;
        }
    }

    static class s2145{//Count the Hidden Sequences
        public int numberOfArrays(int[] differences, int lower, int upper){
            long min = 0, max = 0, val = 0;
            for(int diff : differences){
                val = val + diff;
                min = Math.min(min, val);
                max = Math.max(max, val);
            }
            max -= min - lower;
            return (int) Math.max(0, upper - max + 1);
        }
    }

    static class s2146{//K Highest Ranked Items Within a Price Range
        public List<List<Integer>> highestRankedKItems(int[][] g, int[] pricing, int[] start, int k){
            int low = pricing[0], high = pricing[1], dirs[] = {-1, 0, 1, 0, -1};
            PriorityQueue<int[]> q = new PriorityQueue<>((a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] != b[1] ? a[1] - b[1] : a[2] != b[2] ? a[2] - b[2] : a[3] - b[3]);//distance, price, row, col
            List<List<Integer>> r = new ArrayList<>(k);
            for(q.add(new int[]{0, g[start[0]][start[1]], start[0], start[1]}), g[start[0]][start[1]] = 0; !q.isEmpty() && r.size() < k; ){
                int p[] = q.poll(), dist = p[0], price = p[1], x = p[2], y = p[3];
                if(low <= price && price <= high)
                    r.add(Arrays.asList(x, y));
                for(int d = 1; d < dirs.length; d++){
                    int nx = x + dirs[d - 1], ny = y + dirs[d];
                    if(0 <= nx && nx < g.length && 0 <= ny && ny < g[0].length && g[nx][ny] > 0){
                        q.offer(new int[]{dist + 1, g[nx][ny], nx, ny});
                        g[nx][ny] = 0;
                    }
                }
            }
            return r;
        }
    }

    static class s2147{//Number of Ways to Divide a Long Corridor
        public int numberOfWays(String corridor){
            long r = 1, mod = 1_000_000_007;
            for(int i = 0; i < corridor.length(); ){
                int seats = 0, j = i;
                for(; j < corridor.length() && seats < 2; j++)
                    if(corridor.charAt(j) == 'S')
                        seats++;
                for(i = j; i < corridor.length() && corridor.charAt(i) != 'S'; i++) ;
                if(seats < 2)
                    return 0;
                if(i < corridor.length())
                    r = r * (i - j + 1) % mod;
            }
            return (int) r;
        }
    }

    static class s2148{//Count Elements With Strictly Smaller and Greater Elements
        public int countElements(int[] a){
            int min = Arrays.stream(a).min().getAsInt(), max = Arrays.stream(a).max().getAsInt();
            return (int) Arrays.stream(a).filter(n -> min < n && n < max).count();
        }
    }

    static class s2149{//Rearrange Array Elements by Sign
        public int[] rearrangeArray(int[] a){
            int r[] = new int[a.length], i = 0, j = 1;
            for(int n : a)
                if(n > 0){
                    r[i] = n;
                    i += 2;
                }else{
                    r[j] = n;
                    j += 2;
                }
            return r;
        }
    }

    static class s2150{//Find All Lonely Numbers in the Array
        public List<Integer> findLonely(int[] a){
            Map<Integer, Integer> m = new HashMap<>();
            Arrays.stream(a).forEach(n -> m.put(n, m.getOrDefault(n, 0) + 1));
            List<Integer> r = new ArrayList<>();
            for(int n : a)
                if(m.get(n) + m.getOrDefault(n - 1, 0) + m.getOrDefault(n + 1, 0) == 1)
                    r.add(n);
            return r;
        }
    }

    static class s2154{//Keep Multiplying Found Values by Two
        public int findFinalValue(int[] a, int original){
            Set<Integer> s = Arrays.stream(a).boxed().collect(Collectors.toSet());
            for(; s.contains(original); original *= 2) ;
            return original;
        }
    }

    static class s2155{//All Divisions With the Highest Score of a Binary Array
        public List<Integer> maxScoreIndices(int[] a){
            List<Integer> r = new ArrayList<>();
            int score = Arrays.stream(a).sum(), maxScore = score;
            for(int i = 0; i <= a.length; i++){
                if(score > maxScore){
                    maxScore = score;
                    r.clear();
                }
                if(score == maxScore)
                    r.add(i);
                if(i < a.length)
                    score += 1 - 2 * a[i];
            }
            return r;
        }
    }

    static class s2156{//Find Substring With Given Hash Value
        public String subStrHash(String s, int p, int mod, int k, int hasVal){
            long poly = 0, pk = 1;
            int r = 0, n = s.length();
            for(int i = n - 1; i >= 0; i--){
                poly = (poly * p + s.charAt(i) - 'a' + 1) % mod;
                if(i + k >= n)
                    pk = p * pk % mod;
                else poly = (poly - (s.charAt(i + k) - 'a' + 1) * pk % mod + mod) % mod;
                if(poly == hasVal)
                    r = i;
            }
            return s.substring(r, r + k);
        }
    }

    static class s2158{//Amount of New Area Painted Each Day
        public int[] amountPainted(int[][] paint){
            int end = Arrays.stream(paint).mapToInt(p -> p[1]).max().getAsInt(), r[] = new int[paint.length];
            List<List<int[]>> line = new ArrayList<>(end + 1);
            for(int i = 0; i <= end; i++)
                line.add(new ArrayList<>());
            for(int i = 0; i < paint.length; i++){
                line.get(paint[i][0]).add(new int[]{i, 1});
                line.get(paint[i][1]).add(new int[]{i, -1});
            }
            TreeSet<Integer> s = new TreeSet<>();
            for(List<int[]> point : line){
                for(int[] layer : point)
                    if(layer[1] == 1)
                        s.add(layer[0]);
                    else s.remove(layer[0]);
                if(!s.isEmpty())
                    r[s.first()]++;
            }
            return r;
        }
    }

    static class s2160{//Minimum Sum of Four Digit Number After Splitting Digits
        public int minimumSum(int n){
            int[] d = new int[4];
            for(int i = 0; n > 0; n /= 10, i++)
                d[i] = n % 10;
            Arrays.sort(d);
            return 10 * (d[0] + d[2]) + d[1] + d[3];
        }
    }

    static class s2161{//Partition Array According to Given Pivot
        public int[] pivotArray(int[] a, int pivot){
            int r[] = new int[a.length], lo = 0, hi = a.length - 1;
            for(int i = 0, j = a.length - 1; i < a.length; i++, j--){
                if(a[i] < pivot)
                    r[lo++] = a[i];
                if(a[j] > pivot)
                    r[hi--] = a[j];
            }
            Arrays.fill(r, lo, hi + 1, pivot);
            return r;
        }
    }

    static class s2162{//Minimum Cost to Set Cooking Time
        public int minCostSetTime(int startAt, int moveCost, int pushCost, int targetSeconds){
            int min = 0, sec = targetSeconds, r = Integer.MAX_VALUE;
            for(; sec >= 0 && min <= 99; min += 1, sec -= 60)
                if(sec <= 99)
                    r = Math.min(minCost(min, sec, startAt, moveCost, pushCost), r);
            return r;
        }

        int minCost(int min, int sec, int prev, int moveCost, int pushCost){
            String s = min > 0 && sec < 10 ? min + "0" + sec : String.valueOf(min) + sec;
            int i = 0, cost = 0;
            for(; s.charAt(i) == '0'; i++) ;
            for(; i < s.length(); i++)
                if(s.charAt(i) - '0' == prev)
                    cost += pushCost;
                else{
                    cost += pushCost + moveCost;
                    prev = s.charAt(i) - '0';
                }
            return cost;
        }
    }

    static class s2163{//Minimum Difference in Sums After Removal of Elements
        public long minimumDifference(int[] a){
            long s1 = 0, s2 = 0, dp[] = new long[a.length], r = Long.MAX_VALUE, n = a.length / 3;
            PriorityQueue<Integer> q1 = new PriorityQueue<>(Comparator.reverseOrder()), q2 = new PriorityQueue<>();
            for(int i = 0; i < 2 * n; i++){
                q1.offer(a[i]);
                s1 += a[i];
                if(q1.size() > n)
                    s1 -= q1.poll();
                if(q1.size() == n)
                    dp[i] = s1;
            }
            for(int i = a.length - 1; i >= n; i--){
                q2.offer(a[i]);
                s2 += a[i];
                if(q2.size() > n)
                    s2 -= q2.poll();
                if(q2.size() == n)
                    r = Math.min(r, dp[i - 1] - s2);
            }
            return r;
        }
    }

    static class s2164{//Sort Even and Odd Indices Independently
        public int[] sortEvenOdd(int[] a){
            var qq = Arrays.asList(new PriorityQueue<Integer>(), new PriorityQueue<Integer>(Comparator.reverseOrder()));
            IntStream.range(0, a.length).forEach(i -> qq.get(i % 2).offer(a[i]));
            return IntStream.range(0, a.length).map(i -> qq.get(i % 2).poll()).toArray();
        }
    }

    static class s2165{//Smallest Value of the Rearranged Number
        public long smallestNumber(long n){
            int[] fr = new int[10];
            for(long m = Math.abs(n); m != 0; m /= 10)
                fr[(int) (m % 10)]++;
            return n >= 0 ? create(0, fr) : -create(fr.length - 1, fr);
        }

        long create(int i, int[] fr){
            long r = 0;
            for(int j = 1; i == 0 && j < fr.length; j++)
                if(fr[j] > 0){
                    r = j;
                    fr[j]--;
                    break;
                }
            for(int inc = i == 0 ? 1 : -1; 0 <= i && i < fr.length; i += inc)
                while(fr[i]-- > 0)
                    r = r * 10 + i;
            return r;
        }

        public long smallestNumber1(long n){
            List<Integer> digits = new ArrayList<>();
            for(long m = Math.abs(n); m > 0; m /= 10)
                digits.add((int) (m % 10));
            Collections.sort(digits, n >= 0 ? Comparator.naturalOrder() : Comparator.reverseOrder());
            for(int i = 0; n > 0 && digits.get(0) == 0 && i < digits.size(); i++)
                if(digits.get(i) > 0){
                    digits.set(0, digits.get(i));
                    digits.set(i, 0);
                }
            long r = 0;
            for(Integer d : digits)
                r = 10 * r + d;
            return r * (n >= 0 ? 1 : -1);
        }
    }

    static class s2166{//Design Bitset
        class Bitset{
            int b[], flipped = 0, count = 0;

            public Bitset(int size){b = new int[size];}

            public void fix(int idx){
                if(b[idx] == flipped){
                    b[idx] = 1 - flipped;
                    count++;
                }
            }

            public void unfix(int idx){
                if(b[idx] == 1 - flipped){
                    b[idx] = flipped;
                    count--;
                }
            }

            public void flip(){
                flipped ^= 1;
                count = b.length - count;
            }

            public boolean all(){return b.length == count;}

            public boolean one(){return count > 0;}

            public int count(){return count;}

            public String toString(){
                return Arrays.stream(b).mapToObj(v -> String.valueOf(v ^ flipped)).collect(Collectors.joining());
            }
        }
    }

    static class s2168{
        /* Given a digit string s, return the number of unique substrings of s where every digit appears the same number of times */
        public int equalDigitFrequency(String s){
            Set<Integer> ss = new HashSet<>();
            for(int i = 0; i < s.length(); i++){
                int count[] = new int[10], uniq = 0, maxCount = 0, rollingHash = 0;
                for(int j = i; j < s.length(); j++){
                    int digit = s.charAt(j) - '0';
                    rollingHash = (rollingHash * 11 + digit + 1) % 1_000_000_007;
                    uniq += count[digit]++ == 0 ? 1 : 0;
                    maxCount = Math.max(count[digit], maxCount);
                    if(maxCount * uniq == j - i + 1)
                        ss.add(rollingHash);
                }
            }
            return ss.size();
        }

        public int equalDigitFrequency1(String s){
            Node trie = new Node();
            int r = 0;
            for(int i = 0; i < s.length(); i++){
                Node node = trie;
                int count[] = new int[10], uniq = 0, maxCount = 0;
                for(int j = i; j < s.length(); j++){
                    int digit = s.charAt(j) - '0';
                    uniq += count[digit]++ == 0 ? 1 : 0;
                    maxCount = Math.max(maxCount, count[digit]);
                    if(node.nodes[digit] == null)
                        node.nodes[digit] = new Node();
                    node = node.nodes[digit];
                    if(uniq * maxCount == j - i + 1 && !node.seen){
                        r++;
                        node.seen = true;
                    }
                }
            }
            return r;
        }

        class Node{
            Node[] nodes = new Node[10];
            boolean seen;
        }
    }

    static class s2169{//Count Operations to Obtain Zero
        public int countOperations(int n1, int n2){
            int r = 0;
            for(; n1 > 0 && n2 > 0; r++)
                if(n1 >= n2)
                    n1 -= n2;
                else n2 -= n1;
            return r;
        }
    }

    static class s2170{//Minimum Operations to Make the Array Alternating
        public int minimumOperations(int[] a){
            int f[][] = new int[100_001][2], r = 0, odd = 0, even = 0;
            IntStream.range(0, a.length).forEach(i -> f[a[i]][i & 1]++);
            for(int i = 1; i < f.length; i++){
                r = Math.max(r, Math.max(f[i][0] + odd, f[i][1] + even));
                even = Math.max(even, f[i][0]);
                odd = Math.max(odd, f[i][1]);
            }
            return a.length - r;
        }
    }

    static class s2171{//Removing Minimum Number of Magic Beans
        public long minimumRemoval(int[] beans){
            Arrays.sort(beans);
            long max = 0;
            for(int i = 0, n = beans.length; i < beans.length; i++)
                max = Math.max(max, (long) beans[i] * (n - i));
            return Arrays.stream(beans).mapToLong(i -> i).sum() - max;
        }
    }

    static class s2174{//Remove All Ones With Row and Column Flips II
        public int removeOnes(int[][] g){
            int min = Integer.MAX_VALUE;
            for(int i = 0; i < g.length; i++)
                for(int j = 0; j < g[0].length; j++)
                    if(g[i][j] == 1){
                        List<int[]> flip = new ArrayList<>();
                        for(int r = 0; r < g.length; r++)
                            if(g[r][j] == 1){
                                flip.add(new int[]{r, j});
                                g[r][j] = 0;
                            }
                        for(int c = 0; c < g[0].length; c++)
                            if(g[i][c] == 1){
                                flip.add(new int[]{i, c});
                                g[i][c] = 0;
                            }
                        min = Math.min(min, 1 + removeOnes(g));
                        flip.forEach(f -> g[f[0]][f[1]] = 1);//backtrack
                    }
            return min < Integer.MAX_VALUE ? min : 0;
        }
    }

    static class s2176{//Count Equal and Divisible Pairs in an Array
        public int countPairs(int[] a, int k){
            int r = 0;
            for(int i = 0; i < a.length; i++)
                for(int j = i + 1; j < a.length; j++)
                    if(a[i] == a[j] && i * j % k == 0)
                        r++;
            return r;
        }
    }

    static class s2177{//Find Three Consecutive Integers That Sum to a Given Number
        public long[] sumOfThree(long n){
            if(n % 3 != 0)
                return new long[0];
            return new long[]{n / 3 - 1, n / 3, n / 3 + 1};
        }
    }

    static class s2178{//Maximum Split of Positive Even Integers
        public List<Long> maximumEvenSplit(long sum){
            List<Long> r = new ArrayList<>();
            if(sum % 2 == 0){
                for(long i = 2; sum > 2 * i; i += 2){
                    r.add(i);
                    sum -= i;
                }
                if(sum > 0)
                    r.add(sum);
            }
            return r;
        }
    }

    static class s2180{//Count Integers With Even Digit Sum
        public int countEven(int n){
            int r = 0;
            for(int sum = 0; n > 0; n--, sum = 0){
                for(int m = n; m > 0; m /= 10)
                    sum += m % 10;
                r += sum % 2 == 0 ? 1 : 0;
            }
            return r;
        }
    }

    static class s2181{//Merge Nodes in Between Zeros
        public ListNode mergeNodes(ListNode head){
            ListNode dummy = new ListNode(), sumNode = dummy, node = head.next;
            for(int sum = 0; node != null; node = node.next)
                if(node.val == 0){
                    sumNode.next = new ListNode(sum);
                    sumNode = sumNode.next;
                    sum = 0;
                }else sum += node.val;
            return dummy.next;
        }
    }
}
