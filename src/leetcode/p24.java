package leetcode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
public class p24{
    static class s2401{//Longest Nice Subarray
        public int longestNiceSubarray(int[] a){
            int r = 1, and = 0;
            for(int i = 0, j = 0; j < a.length; j++){
                while((and & a[j]) > 0)
                    and ^= a[i++];
                and |= a[j];
                r = Math.max(r, j - i + 1);
            }
            return r;
        }
    }

    static class s2404{//Most Frequent Even Element
        public int mostFrequentEven(int[] a){
            Map<Integer, Integer> fr = new TreeMap<>();
            int maxFr = 0;
            for(int n : a)
                if(n % 2 == 0){
                    fr.put(n, fr.getOrDefault(n, 0) + 1);
                    maxFr = Math.max(maxFr, fr.get(n));
                }
            final int max = maxFr;
            return fr.keySet().stream().filter(n -> fr.get(n) == max).findFirst().orElse(-1);
        }
    }

    static class s2405{//Optimal Partition of String
        public int partitionString(String s){
            int r = 0;
            for(int i = 0, j; i < s.length(); i = j){
                boolean[] used = new boolean[26];
                for(j = i; j < s.length() && !used[s.charAt(j) - 'a']; j++)
                    used[s.charAt(j) - 'a'] = true;
                r++;
            }
            return r;
        }
    }

    static class s2408{//Design SQL
        class SQL{
            Map<String, Integer> id = new HashMap<>();
            Map<String, Map<Integer, List<String>>> data = new HashMap<>();

            public SQL(List<String> names, List<Integer> columns){
                names.forEach(name -> data.put(name, new HashMap<>()));
                names.forEach(name -> id.put(name, 1));
            }

            public void insertRow(String name, List<String> row){
                data.get(name).put(id.get(name), row);
                id.put(name, id.get(name) + 1);
            }

            public void deleteRow(String name, int rowId){}

            public String selectCell(String name, int rowId, int columnId){
                return data.get(name).get(rowId).get(columnId - 1);
            }
        }
    }

    static class s2409{//Count Days Spent Together
        public int countDaysTogether(String a1s, String l1s, String a2s, String l2s) {
            int[] days = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
            int a1 = c(a1s, days), l1 = c(l1s, days), a2 = c(a2s, days), l2 = c(l2s, days), r = 0;
            for (int d = 1; d <= Arrays.stream(days).sum(); d++)
                if (a1 <= d && d <= l1 && a2 <= d && d <= l2)
                    r++;
            return r;
        }

        int c(String s, int[] days) {
            int r = 0, m = Integer.parseInt(s.substring(0, 2)) - 1, d = Integer.parseInt(s.substring(3, 5));
            for (int month = 0; month < m; month++)
                r += days[month];
            return r + d;
        }
    }

    static class s2410{//Maximum Matching of Players With Trainers
        public int matchPlayersAndTrainers(int[] players, int[] trainers){
            Arrays.sort(players);
            Arrays.sort(trainers);
            int r = 0;
            for(int i = 0, j = 0; i < players.length && j < trainers.length; )
                if(players[i] <= trainers[j]){
                    r++;
                    i++;
                    j++;
                }else j++;
            return r;
        }
    }

    static class s2413{//Smallest Even Multiple
        public int smallestEvenMultiple(int n){return n % 2 == 0 ? n : n * 2;}
    }

    static class s2414{//Length of the Longest Alphabetical Continuous Substring
        public int longestContinuousSubstring(String s){
            int r = 1;
            for(int i = 1, conseq = 1; i < s.length(); i++)
                if(s.charAt(i - 1) + 1 == s.charAt(i))
                    r = Math.max(r, ++conseq);
                else conseq = 1;
            return r;
        }
    }

    static class s2415{//Reverse Odd Levels of Binary Tree
        public TreeNode reverseOddLevels(TreeNode root){
            Queue<TreeNode> q = new LinkedList<>();
            q.offer(root);
            for(int level = 0; !q.isEmpty(); level++){
                if(level % 2 == 1){
                    int vals[] = q.stream().mapToInt(node -> node.val).toArray(), i = vals.length - 1;
                    for(TreeNode node : q)
                        node.val = vals[i--];
                }
                for(int size = q.size(); size > 0; size--){
                    TreeNode node = q.poll();
                    if(node.left != null){
                        q.offer(node.left);
                        q.offer(node.right);
                    }
                }
            }
            return root;
        }
    }

    static class s2418{//Sort the People
        public String[] sortPeople(String[] names, int[] heights){
            int[][] m = new int[heights.length][2];
            for(int i = 0; i < heights.length; i++)
                m[i] = new int[]{heights[i], i};
            Arrays.sort(m, (a, b) -> b[0] - a[0]);
            return IntStream.range(0, names.length).mapToObj(i -> names[m[i][1]]).toArray(String[]::new);
        }
    }

    static class s2419{//Longest Subarray With Maximum Bitwise AND
        public int longestSubarray(int[] a){
            int max = Arrays.stream(a).max().getAsInt(), r = 1;
            for(int i = 1, count = 1; i < a.length; i++)
                if(a[i - 1] == max && a[i] == max)
                    r = Math.max(r, ++count);
                else count = 1;
            return r;
        }
    }

    static class s2422{//Merge Operations to Turn Array Into a Palindrome
        public int minimumOperations(int[] a){
            int r = 0, left = a[0], right = a[a.length - 1];
            for(int i = 0, j = a.length - 1; i < j; )
                if(left == right){
                    left = a[++i];
                    right = a[--j];
                }else if(left < right){
                    left += a[++i];
                    r++;
                }else{
                    right += a[--j];
                    r++;
                }
            return r;
        }
    }

    static class s2423{//Remove Letter To Equalize Frequency
        public boolean equalFrequency(String word){
            int[] f = new int[26];
            word.chars().forEach(c -> f[c - 'a']++);
            TreeMap<Integer, Integer> m = new TreeMap<>();
            Arrays.stream(f).filter(c -> c > 0).forEach(c -> m.put(c, m.getOrDefault(c, 0) + 1));
            if(m.size() == 1 && (m.firstKey() == 1 || m.firstEntry().getValue() == 1))
                return true;
            if(m.size() != 2)
                return false;
            return Math.abs(m.firstKey() - m.lastKey()) == 1 && (m.firstEntry().getValue() == 1 || m.lastEntry().getValue() == 1);
        }
    }

    static class s2424{//Longest Uploaded Prefix
        class LUPrefix{
            Set<Integer> s = new HashSet<>();
            int max = 0;

            public LUPrefix(int n){}

            public void upload(int video){
                s.add(video);
                while(s.contains(max + 1))
                    max++;
            }

            public int longest(){return max;}
        }
    }

    static class s2425{//Bitwise XOR of All Pairings
        public int xorAllNums(int[] a1, int[] a2) {
            if (a1.length % 2 == 0 && a2.length % 2 == 0)
                return 0;
            if (a1.length % 2 == 0)
                return xor(a1);
            if (a2.length % 2 == 0)
                return xor(a2);
            return xor(a1) ^ xor(a2);
        }
        int xor(int[] a) {
            int r = 0;
            for (int n : a)
                r ^= n;
            return r;
        }
    }

    static class s2428{//Maximum Sum of an Hourglass
        public int maxSum(int[][] g){
            int r = 0;
            for(int i = 0; i + 3 <= g.length; i++)
                for(int j = 0; j + 3 <= g[0].length; j++)
                    r = Math.max(r, g[i][j] + g[i][j + 1] + g[i][j + 2] + g[i + 1][j + 1] + g[i + 2][j] + g[i + 2][j + 1] + g[i + 2][j + 2]);
            return r;
        }
    }

    static class s2431{//Maximize Total Tastiness of Purchased Fruits
        public int maxTastiness(int[] price, int[] tastiness, int maxAmount, int maxCoupons) {
            int[][][] dp = new int[price.length + 1][maxAmount + 1][maxCoupons + 1];
            for (int i = 1; i <= price.length; i++)
                for (int amount = 0; amount <= maxAmount; amount++)
                    for (int coupon = 0; coupon <= maxCoupons; coupon++) {
                        dp[i][amount][coupon] = dp[i - 1][amount][coupon];
                        if (amount >= price[i - 1])
                            dp[i][amount][coupon] = Math.max(dp[i][amount][coupon], tastiness[i - 1] + dp[i - 1][amount - price[i - 1]][coupon]);
                        if (coupon > 0 && amount >= price[i - 1] / 2)
                            dp[i][amount][coupon] = Math.max(dp[i][amount][coupon], tastiness[i - 1] + dp[i - 1][amount - price[i - 1] / 2][coupon - 1]);
                    }
            return dp[price.length][maxAmount][maxCoupons];
        }
    }

    static class s2432{//The Employee That Worked on the Longest Task
        public int hardestWorker(int n, int[][] logs){
            int r = logs[0][0], maxTime = logs[0][1];
            for(int i = 1; i < logs.length; i++)
                if(logs[i][1] - logs[i - 1][1] == maxTime)
                    r = Math.min(r, logs[i][0]);
                else if(logs[i][1] - logs[i - 1][1] > maxTime){
                    maxTime = logs[i][1] - logs[i - 1][1];
                    r = logs[i][0];
                }
            return r;
        }
    }

    static class s2433{//Find The Original Array of Prefix Xor
        public int[] findArray(int[] pref){
            int[] r = new int[pref.length];
            r[0] = pref[0];
            for(int i = 1; i < r.length; i++)
                r[i] = pref[i] ^ pref[i - 1];
            return r;
        }
    }

    static class s2436{//Minimum Split Into Subarrays With GCD Greater Than One
        public int minimumSplits(int[] a){
            int r = 1, gcd = a[0];
            for(int n : a){
                gcd = gcd(n, gcd);
                if(gcd == 1){
                    r++;
                    gcd = n;
                }
            }
            return r;
        }

        int gcd(int a, int b){return b == 0 ? a : gcd(b, a % b);}
    }

    static class s2437{//Number of Valid Clock Times
        public int countTime(String time){
            if(!time.contains("?")){
                int h = Integer.parseInt(time.substring(0, 2));
                int m = Integer.parseInt(time.substring(3, 5));
                return 0 <= h && h <= 23 && 0 <= m && m < 60 ? 1 : 0;
            }else{
                int r = 0;
                for(int d = 0; d <= 9; d++)
                    r += countTime(time.replaceFirst("\\?", d + ""));
                return r;
            }
        }
    }

    static class s2442{//Count Number of Distinct Integers After Reverse Operations
        public int countDistinctIntegers(int[] a){
            Set<Integer> s = new HashSet<>();
            for(int n : a){
                s.add(n);
                s.add(reverse(n));
            }
            return s.size();
        }

        int reverse(int n){
            int r = 0;
            for(; n > 0; n /= 10)
                r = 10 * r + n % 10;
            return r;
        }
    }

    static class s2443{//Sum of Number and Its Reverse
        public boolean sumOfNumberAndReverse(int n) {
            for (int i = 0; i <= n; i++)
                if (i + reverse(i) == n)
                    return true;
            return false;
        }

        int reverse(int n) {
            int r = 0;
            for (; n > 0; n /= 10)
                r = 10 * r + n % 10;
            return r;
        }
    }

    static class s2445{//Number of Nodes With Value One
        public int numberOfNodes(int n, int[] qs){
            int[] a = new int[n + 1];
            for(int q : qs)
                a[q] ^= 1;
            return dfs(a, 1);
        }

        int dfs(int[] a, int u){
            int r = a[u];
            if(u * 2 < a.length){
                a[u * 2] ^= a[u];
                r += dfs(a, u * 2);
            }
            if(u * 2 + 1 < a.length){
                a[u * 2 + 1] ^= a[u];
                r += dfs(a, u * 2 + 1);
            }
            return r;
        }
    }

    static class s2446{//Determine if Two Events Have Conflict
        public boolean haveConflict(String[] t1, String[] t2){
            int s1 = toInt(t1[0]), e1 = toInt(t1[1]);
            int s2 = toInt(t2[0]), e2 = toInt(t2[1]);
            return !(s2 - e1 > 0) && !(s1 - e2 > 0);
        }

        int toInt(String t){
            return Integer.parseInt(t.substring(0, 2)) * 60 + Integer.parseInt(t.substring(3, 5));
        }
    }

    static class s2447{//Number of Subarrays With GCD Equal to K
        public int subarrayGCD(int[] a, int k){
            int r = 0;
            for(int i = 0; i < a.length; i++)
                for(int j = i, gcd = a[j]; j < a.length && gcd % k == 0; j++){
                    gcd = gcd(a[j], gcd);
                    if(gcd == k)
                        r++;
                }
            return r;
        }

        int gcd(int a, int b){return b == 0 ? a : gcd(b, a % b);}
    }

    static class s2450{//Number of Distinct Binary Strings After Applying Operations
        public int countDistinctStrings(String s, int k){
            long r = 1;
            for(int i = 0; i <= s.length() - k; i++)
                r = (r * 2) % 1_000_000_007;
            return (int) r;
        }
    }

    static class s2452{//Words Within Two Edits of Dictionary
        public List<String> twoEditWords(String[] queries, String[] dictionary){
            List<String> r = new ArrayList<>();
            for(String q : queries)
                if(Arrays.stream(dictionary).anyMatch(w -> IntStream.range(0, w.length()).filter(i -> q.charAt(i) != w.charAt(i)).count() <= 2))
                    r.add(q);
            return r;
        }
    }

    static class s2453{//Destroy Sequential Targets
        public int destroyTargets(int[] a, int space) {
            Map<Integer, Integer> m = new HashMap<>();
            for (int n : a) {
                n = n % space;
                m.put(n, m.getOrDefault(n, 0) + 1);
            }
            int max = Collections.max(m.values());
            Arrays.sort(a);
            for (int n : a)
                if (m.get(n % space) == max)
                    return n;
            return 0;
        }
    }

    static class s2460{//Apply Operations to an Array
        public int[] applyOperations(int[] a){
            for(int i = 0; i < a.length - 1; i++)
                if(a[i] == a[i + 1]){
                    a[i] *= 2;
                    a[i + 1] = 0;
                }
            int i = 0;
            for(int j = 0; j < a.length; j++)
                if(a[j] != 0)
                    a[i++] = a[j];
            for(; i < a.length; i++)
                a[i] = 0;
            return a;
        }
    }

    static class s2461{//Maximum Sum of Distinct Subarrays With Length K
        public long maximumSubarraySum(int[] a, int k){
            long r = 0, sum = 0;
            Map<Integer, Integer> m = new HashMap<>();
            for(int i = 0; i < a.length; i++){
                m.put(a[i], m.getOrDefault(a[i], 0) + 1);
                sum += a[i];
                if(i >= k - 1){
                    if(m.size() == k)
                        r = Math.max(r, sum);
                    m.put(a[i - k + 1], m.get(a[i - k + 1]) - 1);
                    if(m.get(a[i - k + 1]) == 0)
                        m.remove(a[i - k + 1]);
                    sum -= a[i - k + 1];
                }
            }
            return r;
        }
    }

    static class s2464{//Minimum Subarrays in a Valid Split
        public int validSubarraySplit(int[] a) {
            int[] dp = new int[a.length + 1];
            Arrays.fill(dp, -1);
            dp[0] = 0;
            for (int i = 0; i < a.length; i++)
                for (int j = 0; j <= i; j++)
                    if (dp[j] != -1 && gcd(a[i], a[j]) > 1)
                        dp[i + 1] = dp[i + 1] != -1 ? Math.min(1 + dp[j], dp[i + 1]) : 1 + dp[j];
            return dp[a.length];
        }

        int gcd(int a, int b) {return b == 0 ? a : gcd(b, a % b);}
    }

    static class s2465{//Number of Distinct Averages
        public int distinctAverages(int[] a) {
            Set<Integer> s = new HashSet<>();
            Arrays.sort(a);
            for (int i = 0, j = a.length - 1; i < j; i++, j--)
                s.add(a[i] + a[j]);
            return s.size();
        }
    }

    static class s2466{//Count Ways To Build Good Strings
        public int countGoodStrings(int low, int high, int zero, int one) {
            int dp[] = new int[high + 1], r = 0, mod = 1_000_000_007;
            dp[0] = 1;
            for (int i = 1; i <= high; i++) {
                if (i >= zero)
                    dp[i] = (dp[i] + dp[i - zero]) % mod;
                if (i >= one)
                    dp[i] = (dp[i] + dp[i - one]) % mod;
                if (i >= low)
                    r = (r + dp[i]) % mod;
            }
            return r;
        }
    }

    static class s2470{//Number of Subarrays With LCM Equal to K
        public int subarrayLCM(int[] a, int k) {
            int r = 0;
            for (int i = 0, lcm = 1; i < a.length; i++, lcm = 1) {
                for (int j = i; j < a.length && lcm <= k; j++) {
                    lcm = lcm * a[j] / gcd(lcm, a[j]);
                    if (lcm == k)
                        r++;
                }
            }
            return r;
        }

        int gcd(int a, int b) {return b == 0 ? a : gcd(b, a % b);}
    }

    static class s2473{//Minimum Cost to Buy Apples
        public long[] minCost(int n, int[][] roads, int[] appleCost, int k) {
            List<ArrayList<int[]>> g = IntStream.range(0, n).mapToObj(i -> new ArrayList<int[]>()).toList();
            for (int[] road : roads) {
                int a = road[0] - 1, b = road[1] - 1, c = road[2];
                g.get(a).add(new int[]{b, c});
                g.get(b).add(new int[]{a, c});
            }
            return IntStream.range(0, n).mapToLong(i -> min(i, appleCost, k, n, g)).toArray();
        }

        long min(int u, int[] w, int k, int n, List<ArrayList<int[]>> g) {
            Queue<long[]> q = new PriorityQueue<>(Comparator.comparingLong(o -> o[0]));
            long dist[] = new long[n], r = Long.MAX_VALUE;
            Arrays.fill(dist, Long.MAX_VALUE);
            for (q.offer(new long[]{w[u], 0, u}); !q.isEmpty(); ) {
                long t[] = q.poll(), cost = t[0], path = t[1];
                int v = (int) t[2];
                if (cost < dist[v]) {
                    dist[v] = cost;
                    r = Math.min(r, cost);
                    for (int[] next : g.get(v)) {
                        int id = next[0], weight = next[1];
                        if ((path + weight) * (k + 1) < r)
                            q.offer(new long[]{(long) w[id] + (path + weight) * (k + 1), path + weight, id});
                    }
                }
            }
            return r;
        }
    }

    static class s2476{//Closest Nodes Queries in a Binary Search Tree
        public List<List<Integer>> closestNodes(TreeNode root, List<Integer> queries) {
            TreeSet<Integer> s = new TreeSet<>();
            inorder(root, s);
            return queries.stream().map(n -> Arrays.asList(val(s.floor(n)), val(s.ceiling(n)))).collect(Collectors.toList());
        }

        void inorder(TreeNode node, TreeSet<Integer> s) {
            if (node != null) {
                inorder(node.left, s);
                s.add(node.val);
                inorder(node.right, s);
            }
        }

        int val(Integer n) {return n != null ? n : -1;}
    }

    static class s2477{//Minimum Fuel Cost to Report to the Capital
        long r = 0;

        public long minimumFuelCost(int[][] roads, int seats) {
            List<List<Integer>> g = IntStream.range(0, roads.length + 1).mapToObj(i -> new ArrayList<Integer>()).collect(Collectors.toList());
            for (int[] road : roads) {
                g.get(road[0]).add(road[1]);
                g.get(road[1]).add(road[0]);
            }
            dfs(0, 0, g, seats);
            return r;
        }
        int dfs(int u, int parent, List<List<Integer>> g, int seats) {
            int people = 1;
            for (int v : g.get(u))
                if (v != parent)
                    people += dfs(v, u, g, seats);
            if (u != 0)
                r += (people + seats - 1) / seats;
            return people;
        }
    }

    static class s2482{//Difference Between Ones and Zeros in Row and Column
        public int[][] onesMinusZeros(int[][] g) {
            int[] rows = new int[g.length], cols = new int[g[0].length];
            for (int i = 0; i < g.length; i++)
                for (int j = 0; j < g[0].length; j++) {
                    rows[i] += g[i][j];
                    cols[j] += g[i][j];
                }
            int[][] r = new int[g.length][g[0].length];
            for (int i = 0; i < g.length; i++)
                for (int j = 0; j < g[0].length; j++)
                    r[i][j] = 2 * rows[i] + 2 * cols[j] - g.length - g[0].length;
            return r;
        }
    }

    static class s2485{//Find the Pivot Integer
        public int pivotInteger(int n) {
            for (int right = (n + 1) * n / 2, left = 0, i = 1; left < right; i++) {
                left += i;
                if (left == right)
                    return i;
                right -= i;
            }
            return -1;
        }
    }

    static class s2486{//Append Characters to String to Make Subsequence
        public int appendCharacters(String s, String t) {
            int i = 0, j = 0;
            for (; i < s.length() && j < t.length(); i++)
                if (s.charAt(i) == t.charAt(j))
                    j++;
            return t.length() - j;
        }
    }

    static class s2487{//Remove Nodes From Linked List
        public ListNode removeNodes(ListNode node) {
            if (node == null)
                return null;
            node.next = removeNodes(node.next);
            return node.next != null && node.val < node.next.val ? node.next : node;
        }
    }

    static class s2489{//Number of Substrings With Fixed Ratio
        public long fixedRatio(String s, int num1, int num2) {
            int zero = 0, one = 0;
            Map<Integer, Integer> dp = new HashMap<>();
            dp.put(0, 1);
            long r = 0;
            for (char c : s.toCharArray()) {
                if (c == '0')
                    zero++;
                else one++;
                int f = zero * num2 - one * num1;
                if (dp.containsKey(f))
                    r += dp.get(f);
                dp.put(f, dp.getOrDefault(f, 0) + 1);
            }
            return r;
        }
    }

    static class s2490{//Circular Sentence
        public boolean isCircularSentence(String s) {
            for (int i = 0; i < s.length(); i++)
                if (s.charAt(i) == ' ')
                    if (s.charAt(i - 1) != s.charAt(i + 1))
                        return false;
            return s.charAt(0) == s.charAt(s.length() - 1);
        }
    }

    static class s2491{//Divide Players Into Teams of Equal Skill
        public long dividePlayers(int[] skill) {
            Arrays.sort(skill);
            long r = 0, target = skill[0] + skill[skill.length - 1];
            for (int i = 0, j = skill.length - 1; i < j; i++, j--) {
                if (skill[i] + skill[j] != target)
                    return -1;
                r += (long) skill[i] * skill[j];
            }
            return r;
        }
    }

    static class s2495{//Number of Subarrays Having Even Product
        public long evenProduct(int[] a) {
            long count = 0, odd = 0;
            for (int n : a)
                if (n % 2 != 0)
                    odd += ++count;
                else count = 0;
            return (1L + a.length) * a.length / 2 - odd;
        }
    }

    static class s2496{//Maximum Value of a String in an Array
        public int maximumValue(String[] strs) {
            return (int) Arrays.stream(strs).mapToLong(this::val).max().getAsLong();
        }

        long val(String s) {
            long r = 0;
            for (int i = 0; i < s.length(); i++)
                if (Character.isDigit(s.charAt(i)))
                    r = r * 10 + (s.charAt(i) - '0');
                else return s.length();
            return r;
        }
    }

    static class s2497{//Maximum Star Sum of a Graph
        public int maxStarSum(int[] vals, int[][] edges, int k) {
            var ll = IntStream.range(0, vals.length).mapToObj(i -> new ArrayList<Integer>()).toList();
            for (int[] e : edges) {
                int u = e[0], v = e[1];
                if (vals[v] > 0)
                    ll.get(u).add(vals[v]);
                if (vals[u] > 0)
                    ll.get(v).add(vals[u]);
            }
            return IntStream.range(0, vals.length).map(i -> vals[i] + ll.get(i).stream().sorted(Comparator.reverseOrder()).limit(k).mapToInt(n -> n).sum()).max().getAsInt();
        }
    }

    static class s2498{//Frog Jump II
        public int maxJump(int[] a) {
            int r = a[1];
            for (int i = 2; i < a.length; ++i)
                r = Math.max(r, a[i] - a[i - 2]);
            return r;
        }
    }
}
