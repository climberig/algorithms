package leetcode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
public class p23{
    static class s2303{//Calculate Amount Paid in Taxes
        public double calculateTax(int[][] brackets, int income){
            double r = 0;
            for(int i = 0, amount = 0; i < brackets.length && amount < income; i++){
                int dollars = Math.min(income - amount, brackets[i][0] - amount);
                r += dollars * brackets[i][1] / 100.0;
                amount += dollars;
            }
            return r;
        }
    }

    static class s2304{//Minimum Path Cost in a Grid
        public int minPathCost(int[][] g, int[][] cost){
            int[] r = g[0];
            for(int i = 1; i < g.length; i++){
                int[] next = new int[g[0].length];
                Arrays.fill(next, Integer.MAX_VALUE);
                for(int j = 0; j < g[0].length; j++)
                    for(int k = 0; k < g[0].length; k++)
                        next[k] = Math.min(next[k], r[j] + g[i][k] + cost[g[i - 1][j]][k]);
                r = next;
            }
            return Arrays.stream(r).min().getAsInt();
        }
    }

    static class s2305{//Fair Distribution of Cookies
        int r = Integer.MAX_VALUE;
        public int distributeCookies(int[] cookies, int k){
            bt(0, cookies, new int[k]);
            return r;
        }

        void bt(int i, int[] cookies, int[] kids){
            if(i == cookies.length){
                int max = 0;
                for(int c : kids)
                    max = Math.max(max, c);
                r = Math.min(r, max);
            }else for(int j = 0; j < kids.length; j++){
                kids[j] += cookies[i];
                bt(i + 1, cookies, kids);
                kids[j] -= cookies[i];
            }
        }
    }

    static class s2309{//Greatest English Letter in Upper and Lower Case
        public String greatestLetter(String str){
            Set<Character> s = new HashSet<>();
            for(char c : str.toCharArray())
                s.add(c);
            for(char c = 'Z'; c >= 'A'; c--)
                if(s.contains(c) && s.contains(Character.toLowerCase(c)))
                    return c + "";
            return "";
        }
    }

    static class s2315{//Count Asterisks
        public int countAsterisks(String s){
            int r = 0, count = 0;
            for(int i = 0; i < s.length(); i++)
                if(s.charAt(i) == '*' && count % 2 == 0)
                    r++;
                else if(s.charAt(i) == '|')
                    count++;
            return r;
        }
    }

    static class s2319{//Check if Matrix Is X-Matrix
        public boolean checkXMatrix(int[][] g){
            for(int i = 0; i < g.length; i++)
                for(int j = 0; j < g.length; j++){
                    boolean diag = i - j == 0 || i + j == g.length - 1;
                    if(diag && g[i][j] == 0)
                        return false;
                    else if(!diag && g[i][j] != 0)
                        return false;
                }
            return true;
        }
    }

    static class s2320{//Count Number of Ways to Place Houses
        public int countHousePlacements(int n){
            long x = 1, o = 1, total = x + o;
            for(int i = 2; i <= n; i++){
                x = o;
                o = total;
                total = (x + o) % 1_000_000_007;
            }
            return (int) ((total * total) % 1_000_000_007);
        }
    }

    static class s2325{//Decode the Message
        public String decodeMessage(String key, String message){
            Map<Character, Character> m = new HashMap<>();
            m.put(' ', ' ');
            char to = 'a';
            for(char from : key.toCharArray())
                if(!m.containsKey(from))
                    m.put(from, to++);
            return message.chars().mapToObj(c -> m.get((char) c) + "").collect(Collectors.joining(""));
        }
    }

    static class s2326{//Spiral Matrix IV
        public int[][] spiralMatrix(int m, int n, ListNode head) {
            int[][] res = new int[m][n];
            Arrays.stream(res).forEach(row -> Arrays.fill(row, -1));
            int r = 0, c = 0, ri = 0, ci = 1;
            for (; head != null; head = head.next, r += ri, c += ci) {
                res[r][c] = head.val;
                if (res[(r + ri + m) % m][(c + ci + n) % n] != -1) {
                    int t = ri;
                    ri = ci;
                    ci = -t;
                }
            }
            return res;
        }
    }

    static class s2330{//Valid Palindrome IV
        public boolean makePalindrome(String s) {
            int r = 0;
            for (int i = 0, j = s.length() - 1; i < j && r <= 2; i++, j--)
                if (s.charAt(i) != s.charAt(j))
                    r++;
            return r <= 2;
        }
    }

    static class s2331{//Evaluate Boolean Binary Tree
        public boolean evaluateTree(TreeNode node){
            if(node.val < 2)
                return node.val == 1;
            boolean left = evaluateTree(node.left), right = evaluateTree(node.right);
            return node.val == 2 ? left | right : left & right;
        }
    }

    static class s2335{//Minimum Amount of Time to Fill Cups
        public int fillCups(int[] amount){
            PriorityQueue<Integer> q = new PriorityQueue<>();
            Arrays.stream(amount).filter(a -> a > 0).forEach(a -> q.offer(-a));
            int r = 0;
            for(; q.size() > 1; r++){
                Integer a = q.poll(), b = q.poll();
                if(++a != 0)
                    q.offer(a);
                if(++b != 0)
                    q.offer(b);
            }
            return q.isEmpty() ? r : r - q.poll();
        }
    }

    static class s2336{//Smallest Number in Infinite Set
        class SmallestInfiniteSet{
            TreeSet<Integer> s = new TreeSet<>();

            public SmallestInfiniteSet(){IntStream.range(1, 1_001).forEach(n -> s.add(n));}

            public int popSmallest(){return s.pollFirst();}

            public void addBack(int n){s.add(n);}
        }
    }

    static class s2340{//Minimum Adjacent Swaps to Make a Valid Array
        public int minimumSwaps(int[] a){
            int minIdx = a.length - 1, maxIdx = 0;
            for(int i = 0; i < a.length; i++)
                if(a[i] >= a[maxIdx])
                    maxIdx = i;
            for(int i = a.length - 1; i >= 0; i--)
                if(a[i] <= a[minIdx])
                    minIdx = i;
            return minIdx + a.length - 1 - maxIdx - (minIdx > maxIdx ? 1 : 0);
        }
    }

    static class s2341{//Maximum Number of Pairs in Array
        public int[] numberOfPairs(int[] a){
            int[] r = new int[2], f = new int[101];
            Arrays.stream(a).forEach(n -> f[n]++);
            for(int n : f){
                r[0] += n / 2;
                r[1] += n % 2;
            }
            return r;
        }
    }

    static class s2342{//Max Sum of a Pair With Equal Sum of Digits
        public int maximumSum(int[] a){
            Map<Integer, Integer> m = new HashMap<>();
            int r = -1;
            for(int n : a){
                int sum = 0;
                for(int k = n; k > 0; k /= 10)
                    sum += k % 10;
                if(m.containsKey(sum)){
                    r = Math.max(r, n + m.get(sum));
                    m.put(sum, Math.max(m.get(sum), n));
                }else m.put(sum, n);
            }
            return r;
        }
    }

    static class s2347{//Best Poker Hand
        public String bestHand(int[] ranks, char[] suits){
            Arrays.sort(suits);
            if(suits[0] == suits[4])
                return "Flush";
            int[] f = new int[14];
            Arrays.stream(ranks).forEach(r -> f[r]++);
            int maxRank = Arrays.stream(f).max().getAsInt();
            if(maxRank >= 3)
                return "Three of a Kind";
            if(maxRank == 2)
                return "Pair";
            return "High Card";
        }
    }

    static class s2348{//Number of Zero-Filled Subarrays
        public long zeroFilledSubarray(int[] a){
            long r = 0;
            for(int i = 0, f = 0; i < a.length; i++)
                if(a[i] == 0)
                    r += ++f;
                else f = 0;
            return r;
        }
    }

    static class s2349{//Design a Number Container System
        class NumberContainers{
            Map<Integer, TreeSet<Integer>> nToIds = new HashMap<>();
            Map<Integer, Integer> idxToN = new HashMap<>();

            public void change(int idx, int n){
                if(idxToN.containsKey(idx)){
                    Integer prev = idxToN.get(idx);
                    nToIds.get(prev).remove(idx);
                }
                idxToN.put(idx, n);
                nToIds.putIfAbsent(n, new TreeSet<>());
                nToIds.get(n).add(idx);
            }

            public int find(int n){
                TreeSet<Integer> ids = nToIds.get(n);
                return ids == null || ids.isEmpty() ? -1 : ids.first();
            }
        }
    }

    static class s2351{// First Letter to Appear Twice
        public char repeatedCharacter(String s){
            boolean[] appeared = new boolean[26];
            int i = 0;
            for(; i < s.length() && !appeared[s.charAt(i) - 'a']; i++)
                appeared[s.charAt(i) - 'a'] = true;
            return s.charAt(i);
        }
    }

    static class s2352{//Equal Row and Column Pairs
        public int equalPairs(int[][] g){
            int r = 0;
            for(int i = 0; i < g.length; i++)
                for(int j = 0; j < g.length; j++)
                    r += equals(g, i, j);
            return r;
        }
        int equals(int[][] g, int r, int c){
            for(int i = 0; i < g.length; i++)
                if(g[r][i] != g[i][c])
                    return 0;
            return 1;
        }
    }

    static class s2357{//Make Array Zero by Subtracting Equal Amounts
        public int minimumOperations(int[] a){
            return (int) Arrays.stream(a).filter(n -> n > 0).distinct().count();
        }
    }

    static class s2358{//Maximum Number of Groups Entering a Competition
        public int maximumGroups(int[] grades){
            return (int) ((Math.sqrt(1 + 8 * grades.length) - 1) / 2);
        }
    }

    static class s2363{//Merge Similar Items
        public List<List<Integer>> mergeSimilarItems(int[][] a1, int[][] a2){
            Map<Integer, Integer> m = new TreeMap<>();
            Arrays.stream(a1).forEach(a -> m.put(a[0], a[1]));
            Arrays.stream(a2).forEach(a -> m.put(a[0], m.getOrDefault(a[0], 0) + a[1]));
            return m.keySet().stream().map(v -> Arrays.asList(v, m.get(v))).collect(Collectors.toList());
        }
    }

    static class s2367{//Number of Arithmetic Triplets
        public int arithmeticTriplets(int[] a, int diff){
            int r = 0;
            Set<Integer> seen = new HashSet<>();
            for(int n : a){
                seen.add(n);
                if(seen.contains(n - diff) && seen.contains(n - 2 * diff))
                    r++;
            }
            return r;
        }
    }

    static class s2368{//Reachable Nodes With Restrictions
        public int reachableNodes(int n, int[][] edges, int[] restricted){
            List<List<Integer>> g = IntStream.range(0, n).mapToObj(i -> new ArrayList<Integer>()).collect(Collectors.toList());
            for(int[] e : edges){
                g.get(e[0]).add(e[1]);
                g.get(e[1]).add(e[0]);
            }
            Set<Integer> seen = Arrays.stream(restricted).boxed().collect(Collectors.toSet());
            Queue<Integer> q = new LinkedList<>();
            for(q.add(0), seen.add(0); !q.isEmpty(); )
                g.get(q.poll()).stream().filter(seen::add).forEach(q::add);
            return seen.size() - restricted.length;
        }
    }

    static class s2369{//Check if There is a Valid Partition For The Array
        public boolean validPartition(int[] n) {
            boolean[] dp = {true, false, n[0] == n[1], false};
            for (int i = 2; i < n.length; i++) {
                boolean two = n[i] == n[i - 1];
                boolean three = (two && n[i] == n[i - 2]) || (n[i] - 1 == n[i - 1] && n[i] - 2 == n[i - 2]);
                dp[(i + 1) % 4] = (two && dp[(i - 1) % 4]) || (three && dp[(i - 2) % 4]);
            }
            return dp[n.length % 4];
        }
    }

    static class s2373{//Largest Local Values in a Matrix
        public int[][] largestLocal(int[][] g){
            int[][] r = new int[g.length - 2][g.length - 2];
            for(int i = 0; i < g.length - 2; i++)
                for(int j = 0; j < g.length - 2; j++)
                    for(int i1 = 0; i1 < 3; i1++)
                        for(int j1 = 0; j1 < 3; j1++)
                            r[i][j] = Math.max(r[i][j], g[i + i1][j + j1]);
            return r;
        }
    }

    static class s2374{//Node With Highest Edge Score
        public int edgeScore(int[] edges){
            long scores[] = new long[edges.length];
            int r = 0;
            for(int i = 0; i < edges.length; i++)
                scores[edges[i]] += i;
            for(int i = 0; i < edges.length; i++)
                if(scores[i] > scores[r])
                    r = i;
            return r;
        }
    }

    static class s2375{//Construct Smallest Number From DI String
        public String smallestNumber(String pattern) {
            Queue<String> q = new LinkedList<>();
            for (char d = '1'; d <= '9'; d++)
                q.offer(d + "");
            for (int i = 0; i < pattern.length(); i++)
                for (int size = q.size(); size > 0; size--) {
                    String n = q.poll();
                    char d1 = n.charAt(n.length() - 1);
                    for (char d2 = '1'; d2 <= '9'; d2++) {
                        if (!n.contains(d2 + ""))
                            if (pattern.charAt(i) == 'I' && d1 < d2)
                                q.offer(n + d2);
                            else if (pattern.charAt(i) == 'D' && d1 > d2)
                                q.offer(n + d2);
                    }
                }
            return q.stream().sorted().toList().get(0);
        }
    }

    static class s2379{//Minimum Recolors to Get K Consecutive Black Blocks
        public int minimumRecolors(String blocks, int k){
            int r = k, b = 0;
            for(int i = 0; i < blocks.length(); i++){
                b += blocks.charAt(i) == 'B' ? 1 : 0;
                if(i >= k - 1){
                    r = Math.min(r, k - b);
                    b -= blocks.charAt(i - k + 1) == 'B' ? 1 : 0;
                }
            }
            return r;
        }
    }

    static class s2380{//Time Needed to Rearrange a Binary String
        public int secondsToRemoveOccurrences(String s){
            int r = 0;
            char[] a = s.toCharArray();
            while(true){
                int changes = 0;
                for(int i = 0; i < a.length - 1; i++)
                    if(a[i] == '0' && a[i + 1] == '1'){
                        a[i] = '1';
                        a[i + 1] = '0';
                        i += 1;
                        changes++;
                    }
                if(changes == 0)
                    break;
                r++;
            }
            return r;
        }
    }

    static class s2383{//Minimum Hours of Training to Win a Competition
        public int minNumberOfHours(int initialEnergy, int initialExperience, int[] energy, int[] experience){
            int r = 0;
            for(int i = 0; i < energy.length; i++){
                if(initialExperience <= experience[i]){
                    r += experience[i] - initialExperience + 1;
                    initialExperience += experience[i] - initialExperience + 1;
                }
                if(initialEnergy <= energy[i]){
                    r += energy[i] - initialEnergy + 1;
                    initialEnergy += energy[i] - initialEnergy + 1;
                }
                initialExperience += experience[i];
                initialEnergy -= energy[i];
            }
            return r;
        }
    }

    static class s2384{//Largest Palindromic Number
        public String largestPalindromic(String s){
            int fr[] = new int[10], a[] = new int[s.length()], lo = 0, hi = s.length() - 1;
            Arrays.fill(a, -1);
            s.chars().forEach(c -> fr[c - '0']++);
            for(int d = 9; d >= 0; d--)
                if(lo > 0 || d > 0)
                    for(; fr[d] > 1; fr[d] -= 2)
                        a[lo++] = a[hi--] = d;
            for(int d = 9; d >= 0; d--)
                if(fr[d] > 0){
                    a[lo] = d;
                    break;
                }
            StringBuilder r = new StringBuilder();
            for(int n : a)
                if(n > -1)
                    r.append(n);
            return r.toString();
        }
    }

    static class s2385{//Amount of Time for Binary Tree to Be Infected
        public int amountOfTime(TreeNode root, int start){
            Map<Integer, List<Integer>> g = new HashMap<>();
            populate(null, root, g);
            Set<Integer> seen = new HashSet<>();
            Queue<Integer> q = new LinkedList<>();
            int r = 0;
            for(q.add(start), seen.add(start); !q.isEmpty(); r++)
                for(int size = q.size(); size > 0; size--)
                    g.get(q.poll()).stream().filter(seen::add).forEach(q::offer);
            return r - 1;
        }

        void populate(TreeNode parent, TreeNode node, Map<Integer, List<Integer>> g){
            if(node != null){
                g.put(node.val, new ArrayList<>());
                if(parent != null){
                    g.get(node.val).add(parent.val);
                    g.get(parent.val).add(node.val);
                }
                populate(node, node.left, g);
                populate(node, node.right, g);
            }
        }
    }

    static class s2387{//Median of a Row Wise Sorted Matrix
        public int matrixMedian(int[][] g) {
            int lo = 1, hi = 1_000_000, k = g.length * g[0].length / 2 + 1;
            while (lo < hi) {
                int mid = (lo + hi + 1) / 2, sum = 0;
                for (int[] row : g) {
                    int idx = helper(row, mid);
                    sum += g[0].length - idx;
                }
                if (sum < k) hi = mid - 1;
                else lo = mid;
            }
            return hi;
        }

        int helper(int[] a, int val) {
            int lo = 0, hi = a.length - 1;
            while (lo < hi) {
                int mid = (lo + hi) / 2;
                if (a[mid] < val)
                    lo = mid + 1;
                else hi = mid;
            }
            return a[hi] >= val ? hi : hi + 1;
        }
    }

    static class s2389{//Longest Subsequence With Limited Sum
        public int[] answerQueries(int[] a, int[] queries){
            int[] r = new int[queries.length];
            Arrays.sort(a);
            for(int i = 0; i < queries.length; i++)
                for(int j = 0, sum = 0; j < a.length && sum + a[j] <= queries[i]; r[i]++, sum += a[j++]) ;
            return r;
        }
    }

    static class s2390{//Removing Stars From a String
        public String removeStars(String s){
            StringBuilder r = new StringBuilder();
            for(int i = 0; i < s.length(); i++)
                if(s.charAt(i) == '*')
                    r.deleteCharAt(r.length() - 1);
                else r.append(s.charAt(i));
            return r.toString();
        }
    }

    static class s2391{//Minimum Amount of Time to Collect Garbage
        public int garbageCollection(String[] garbage, int[] travel){
            int r = 0, m = 0, g = 0, p = 0;
            for(int i = 0; i < garbage.length; i++){
                int mCount = 0, gCount = 0, pCount = 0;
                for(char c : garbage[i].toCharArray())
                    switch(c){
                        case 'G' -> gCount++;
                        case 'P' -> pCount++;
                        case 'M' -> mCount++;
                    }
                if(mCount > 0){
                    r += mCount + m;
                    m = 0;
                }
                if(pCount > 0){
                    r += pCount + p;
                    p = 0;
                }
                if(gCount > 0){
                    r += gCount + g;
                    g = 0;
                }
                if(i < travel.length){
                    m += travel[i];
                    g += travel[i];
                    p += travel[i];
                }
            }
            return r;
        }
    }

    static class s2393{//Count Strictly Increasing Subarrays
        public long countSubarrays(int[] a){
            long r = 1;
            for(int i = 1, count = 1; i < a.length; i++)
                if(a[i - 1] >= a[i]){
                    r++;
                    count = 1;
                }else r += ++count;
            return r;
        }
    }

    static class s2395{//Find Subarrays With Equal Sum
        public boolean findSubarrays(int[] a){
            Set<Integer> sums = new HashSet<>();
            for(int i = 0; i < a.length - 1; i++)
                if(!sums.add(a[i] + a[i + 1]))
                    return true;
            return false;
        }
    }

    static class s2397{//Maximum Rows Covered by Columns
        public int maximumRows(int[][] m, int nSelect) {
            int r = 0;
            for (int i = 1; i < Math.pow(2, m[0].length); i++)
                if (Integer.bitCount(i) == nSelect) {
                    int mask = i;
                    r = Math.max(r, (int) Arrays.stream(m).filter(row -> covered(row, mask)).count());
                }
            return r;
        }
        boolean covered(int[] a, int mask) {
            for (int i = 0; i < a.length; i++)
                if (a[i] == 1 && (mask & (1 << i)) == 0)
                    return false;
            return true;
        }
    }

    static class s2399{//Check Distances Between Same Letters
        public boolean checkDistances(String s, int[] distance){
            int[] idx = new int[26];
            for(int i = 0; i < s.length(); i++)
                if(idx[s.charAt(i) - 'a'] > 0){
                    if(i - idx[s.charAt(i) - 'a'] != distance[s.charAt(i) - 'a'])
                        return false;
                }else idx[s.charAt(i) - 'a'] = i + 1;
            return true;
        }
    }
}
