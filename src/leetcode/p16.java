package leetcode;
import java.util.*;
import java.util.stream.*;

public class p16{
    static class s1601{//Maximum Number of Achievable Transfer Requests
        int r = 0;
        public int maximumRequests(int n, int[][] requests){
            bt(requests, 0, new int[n], 0);
            return r;
        }

        void bt(int[][] requests, int idx, int[] count, int n){
            if(idx == requests.length){
                if(Arrays.stream(count).allMatch(c -> c == 0))
                    r = Math.max(r, n);
            }else{
                count[requests[idx][0]]++;
                count[requests[idx][1]]--;
                bt(requests, idx + 1, count, n + 1);
                count[requests[idx][0]]--;
                count[requests[idx][1]]++;
                bt(requests, idx + 1, count, n);
            }
        }
    }

    static class s1604{//Alert Using Same Key-Card Three or More Times in a One Hour Period
        public List<String> alertNames(String[] keyName, String[] keyTime){
            Map<String, List<Integer>> m = new TreeMap<>();
            for(int i = 0; i < keyName.length; i++){
                m.putIfAbsent(keyName[i], new ArrayList<>());
                String[] p = keyTime[i].split(":");
                m.get(keyName[i]).add(Integer.parseInt(p[0]) * 60 + Integer.parseInt(p[1]));
            }
            List<String> r = new ArrayList<>();
            for(String name : m.keySet()){
                List<Integer> times = m.get(name);
                Collections.sort(times);
                for(int i = 2; i < times.size(); i++)
                    if(times.get(i - 2) >= times.get(i) - 60){
                        r.add(name);
                        break;
                    }
            }
            return r;
        }
    }

    static class s1620{//Coordinate With Maximum Network Quality
        public int[] bestCoordinate(int[][] towers, int radius){
            double bestQ = -1;
            int[] r = null;
            for(int x = 0; x <= 50; x++)
                for(int y = 0; y <= 50; y++){
                    double q = 0;
                    for(int[] t : towers){
                        int x1 = t[0], y1 = t[1], q1 = t[2];
                        double d = (x - x1) * (x - x1) + (y - y1) * (y - y1);
                        if(d <= radius * radius)
                            q += Math.floor(1.0 * q1 / (1 + Math.sqrt(d)));
                    }
                    if(q > bestQ){
                        bestQ = q;
                        r = new int[]{x, y};
                    }
                }
            return r;
        }
    }

    static class s1621{//Number of Sets of K Non-Overlapping Line Segments
        public int numberOfSets(int n, int k){
            return count(0, k, 1, new Integer[n + 1][k + 1][2], n);
        }
        int count(int i, int k, int isStart, Integer[][][] dp, int n){
            if(dp[i][k][isStart] != null)
                return dp[i][k][isStart];
            if(k == 0)
                return 1;
            if(i == n)
                return 0;
            int r = count(i + 1, k, isStart, dp, n);
            if(isStart == 1)
                r += count(i + 1, k, 0, dp, n);
            else r += count(i, k - 1, 1, dp, n);
            return dp[i][k][isStart] = r % 1_000_000_007;
        }
    }

    static class s1626{//Best Team With No Conflicts
        public int bestTeamScore(int[] scores, int[] ages){
            int[][] players = new int[ages.length][2];
            for(int i = 0; i < ages.length; i++)
                players[i] = new int[]{ages[i], scores[i]};
            Arrays.sort(players, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
            int dp[] = new int[ages.length], r = players[0][1];
            for(int i = 0; i < ages.length; i++){
                dp[i] = players[i][1];
                for(int j = 0; j < i; j++)
                    if(players[j][1] <= players[i][1])
                        dp[i] = Math.max(dp[i], players[i][1] + dp[j]);
                r = Math.max(dp[i], r);
            }
            return r;
        }
    }

    static class s1629{//Slowest Key
        public char slowestKey(int[] time, String keys){
            int slowest = time[0], idx = 0;
            for(int i = 1; i < keys.length(); i++)
                if(time[i] - time[i - 1] > slowest || (time[i] - time[i - 1] == slowest && keys.charAt(i) > keys.charAt(idx))){
                    slowest = time[i] - time[i - 1];
                    idx = i;
                }
            return keys.charAt(idx);
        }
    }

    static class s1631{//Path With Minimum Effort
        public int minimumEffortPath(int[][] h){
            int n = h.length, m = h[0].length, dp[][] = new int[n][m], dirs[] = {-1, 0, 1, 0, -1};
            Arrays.stream(dp).forEach(r -> Arrays.fill(r, Integer.MAX_VALUE));
            Queue<int[]> q = new LinkedList<>();
            for(dp[0][0] = 0, q.add(new int[]{0, 0}); !q.isEmpty(); )
                for(int d = 1, p[] = q.poll(); d < dirs.length; d++){
                    int r = p[0] + dirs[d - 1], c = p[1] + dirs[d];
                    if(0 <= r && r < n && 0 <= c && c < m){
                        int effort = Math.max(Math.abs(h[r][c] - h[p[0]][p[1]]), dp[p[0]][p[1]]);
                        if(effort < dp[r][c]){
                            dp[r][c] = effort;
                            q.add(new int[]{r, c});
                        }
                    }
                }
            return dp[n - 1][m - 1];
        }
    }

    static class s1641{//Count Sorted Vowel Strings
        public int countVowelStrings(int n){
            int a = 1, e = 1, i = 1, o = 1, u = 1;
            while(--n > 0){
                a = (a + e + i + o + u); // a, e, i, o, u -> aa, ae, ai, ao, au
                e = (e + i + o + u); // e, i, o, u -> ee, ei, eo, eu
                i = (i + o + u); // i, o, u -> ii, io, iu
                o = (o + u); // o, u -> oo, ou
            }
            return a + e + i + o + u;
        }
    }

    static class s1644{//Lowest Common Ancestor of a Binary Tree II
        int count = 0;

        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q){
            TreeNode lca = lca(root, p, q);
            return count == 2 ? lca : null;
        }

        TreeNode lca(TreeNode root, TreeNode p, TreeNode q){
            if(root == null)
                return null;
            TreeNode L = lca(root.left, p, q), R = lca(root.right, p, q);
            if(root == p || root == q){
                count++;
                return root;
            }
            return L == null ? R : R == null ? L : root;
        }
    }

    static class s1653{//Minimum Deletions to Make String Balanced
        public int minimumDeletions(String s){
            int[] dp = new int[s.length() + 1];//min chars to remove to make s[0:i] valid
            for(int i = 0, bCount = 0; i < s.length(); i++)
                if(s.charAt(i) == 'b'){
                    dp[i + 1] = dp[i];
                    bCount++;
                }else dp[i + 1] = Math.min(dp[i] + 1, bCount);
            return dp[s.length()];
        }
    }

    static class s1654{//Minimum Jumps to Reach Home
        public int minimumJumps(int[] forbidden, int a, int b, int x){
            Set<Integer> seen = Arrays.stream(forbidden).boxed().collect(Collectors.toSet()), notAllowed = new HashSet<>(seen);
            Queue<int[]> q = new LinkedList<>();
            for(seen.add(0), q.add(new int[]{0, 1, 0}); !q.isEmpty(); ){
                int[] p = q.poll();
                if(p[0] == x)
                    return p[2];
                if(seen.add(p[0] + a) && p[0] <= 2000 + b)
                    q.add(new int[]{p[0] + a, 1, p[2] + 1});
                if(p[1] == 1 && p[0] - b > 0 && !notAllowed.contains(p[0] - b))
                    q.add(new int[]{p[0] - b, -1, p[2] + 1});
            }
            return -1;
        }
    }

    static class s1655{//Distribute Repeating Integers
        public boolean canDistribute(int[] a, int[] quantity){
            Map<Integer, Integer> m = new HashMap<>();
            Arrays.stream(a).forEach(n -> m.put(n, m.getOrDefault(n, 0) + 1));
            int count[] = new int[m.size()], i = 0;
            for(int val : m.values())
                count[i++] = val;
            Arrays.sort(quantity);
            return canAssign(quantity.length - 1, count, quantity);
        }

        boolean canAssign(int idx, int[] counts, int[] quantity){
            if(idx < 0)
                return true;
            for(int i = 0; i < counts.length; i++)
                if(counts[i] >= quantity[idx]){
                    counts[i] -= quantity[idx];
                    if(canAssign(idx - 1, counts, quantity))
                        return true;
                    counts[i] += quantity[idx];
                }

            return false;
        }
    }

    static class s1658{//Minimum Operations to Reduce X to Zero
        public int minOperations(int[] a, int x){
            int target = Arrays.stream(a).sum() - x, sum = 0, r = Integer.MIN_VALUE;
            if(target == 0)
                return a.length;
            Map<Integer, Integer> m = new HashMap<>();
            m.put(0, -1);
            for(int i = 0; i < a.length; i++){
                sum += a[i];
                if(m.containsKey(sum - target))
                    r = Math.max(r, i - m.get(sum - target));
                m.put(sum, i);
            }
            return r == Integer.MIN_VALUE ? -1 : a.length - r;
        }
    }

    static class s1664{//Ways to Make a Fair Array
        public int waysToMakeFair(int[] a){
            int r = 0, L[] = new int[2], R[] = new int[2];
            for(int i = 0; i < a.length; i++)
                R[i % 2] += a[i];
            for(int i = 0; i < a.length; i++){
                R[i % 2] -= a[i];
                if(L[0] + R[1] == L[1] + R[0])
                    r++;
                L[i % 2] += a[i];
            }
            return r;
        }
    }

    static class s1665{//Minimum Initial Energy to Finish Tasks
        public int minimumEffort(int[][] tasks){
            Arrays.sort(tasks, (a, b) -> Integer.compare(b[1] - b[0], a[1] - a[0]));
            int r = 0, left = 0;
            for(int[] task : tasks){
                int need = Math.max(0, task[1] - left);
                r += need;
                left = Math.max(task[1], left) - task[0];
            }
            return r;
        }
    }

    static class s1666{//Change the Root of a Binary Tree
        Node originRoot;

        public Node flipBinaryTree(Node root, Node leaf){
            originRoot = root;
            return flip(leaf, null);
        }

        Node flip(Node node, Node newParent){
            Node oldParent = node.parent;
            node.parent = newParent;
            if(node.left == newParent)
                node.left = null;
            if(node.right == newParent)
                node.right = null;
            if(node == originRoot)
                return node;
            if(node.left != null)
                node.right = node.left;
            node.left = flip(oldParent, node);
            return node;
        }

        class Node{
            int val;
            Node left, right, parent;
        }
    }

    static class s1670{//Design Front Middle Back Queue
        class FrontMiddleBackQueue{
            LinkedList<Integer> front = new LinkedList<>(), back = new LinkedList<>();

            public void pushFront(int val){
                ifFrontBigger();
                front.addFirst(val);
            }

            public void pushMiddle(int val){
                ifFrontBigger();
                front.addLast(val);
            }

            public void pushBack(int val){
                back.addLast(val);
                ifBackBigger();
            }

            public int popFront(){
                if(front.isEmpty())
                    return -1;
                int val = front.removeFirst();
                ifBackBigger();
                return val;
            }

            public int popMiddle(){
                if(front.isEmpty())
                    return -1;
                int val = front.removeLast();
                ifBackBigger();
                return val;
            }

            public int popBack(){
                if(front.isEmpty())
                    return -1;
                ifFrontBigger();
                return back.removeLast();
            }

            void ifBackBigger(){
                if(back.size() > front.size())
                    front.addLast(back.pollFirst());
            }

            void ifFrontBigger(){
                if(front.size() > back.size())
                    back.addFirst(front.removeLast());
            }
        }
    }

    static class s1673{//Find the Most Competitive Subsequence
        public int[] mostCompetitive(int[] a, int k){
            Stack<Integer> s = new Stack<>();
            for(int i = 0; i < a.length; i++){
                while(!s.isEmpty() && a[i] < s.peek() && a.length - i + s.size() > k)
                    s.pop();
                if(s.size() < k)
                    s.push(a[i]);
            }
            return s.stream().mapToInt(i -> i).toArray();
        }
    }

    static class s1675{//Minimize Deviation in Array
        public int minimumDeviation(int[] a){
            TreeSet<Integer> s = Arrays.stream(a).mapToObj(n -> n % 2 == 0 ? n : 2 * n).collect(Collectors.toCollection(TreeSet::new));
            int r = Integer.MAX_VALUE;
            for(; s.last() % 2 == 0; r = Math.min(r, s.last() - s.first()))
                s.add(s.pollLast() / 2);
            return Math.min(r, s.last() - s.first());
        }
    }

    static class s1679{//Max Number of K-Sum Pairs
        public int maxOperations(int[] a, int k){
            int r = 0;
            Arrays.sort(a);
            for(int i = 0, j = a.length - 1; i < j; )
                if(a[i] + a[j] == k){
                    i++;
                    j--;
                    r++;
                }else if(a[i] + a[j] < k)
                    i++;
                else j--;
            return r;
        }
    }

    static class s1680{//Concatenation of Consecutive Binary Numbers
        public int concatenatedBinary(int n){
            long r = 0;
            for(int i = 1; i <= n; i++){
                String b = Integer.toBinaryString(i);
                r = ((r << b.length()) + i) % 1_000_000_007;
            }
            return (int) r;
        }
    }

    static class s1682{// Longest Palindromic Subsequence II
        public int longestPalindromeSubseq(String s){
            return longest(s.toCharArray(), 0, s.length() - 1, 0, new Integer[s.length()][s.length()][27]);
        }

        int longest(char[] a, int lo, int hi, int last, Integer[][][] dp){
            if(lo + 1 > hi)
                return 0;
            if(dp[lo][hi][last] != null)
                return dp[lo][hi][last];
            if(a[lo] == a[hi] && a[lo] - 'a' + 1 != last)
                return dp[lo][hi][last] = 2 + longest(a, lo + 1, hi - 1, a[lo] - 'a' + 1, dp);
            return dp[lo][hi][last] = Math.max(longest(a, lo + 1, hi, last, dp), longest(a, lo, hi - 1, last, dp));
        }
    }

    static class s1686{//Stone Game VI
        public int stoneGameVI(int[] aliceValues, int[] bobValues){
            int[][] vals = new int[aliceValues.length][2];
            for(int i = 0; i < vals.length; i++)
                vals[i] = new int[]{aliceValues[i], bobValues[i]};
            Arrays.sort(vals, (a, b) -> b[0] + b[1] - a[0] - a[1]);
            int diff = 0;
            for(int i = 0; i < vals.length; ){
                diff += vals[i++][0];
                if(i < vals.length)
                    diff -= vals[i++][1];
            }
            return (int) Math.signum(diff);
        }
    }

    static class s1690{//Stone Game VII
        public int stoneGameVII(int[] stones){
            return dfs(Arrays.stream(stones).sum(), 0, stones.length - 1, stones, new Integer[stones.length][stones.length]);
        }

        int dfs(int sum, int i, int j, int[] a, Integer[][] dp){
            if(i == j)
                return 0;
            if(dp[i][j] != null)
                return dp[i][j];
            return dp[i][j] = Math.max(sum - a[i] - dfs(sum - a[i], i + 1, j, a, dp), sum - a[j] - dfs(sum - a[j], i, j - 1, a, dp));
        }
    }

    static class s1691{//Maximum Height by Stacking Cuboids
        public int maxHeight(int[][] a){
            Arrays.stream(a).forEach(Arrays::sort);
            Arrays.sort(a, (x, y) -> x[0] != y[0] ? y[0] - x[0] : x[1] != y[1] ? y[1] - x[1] : y[2] - x[2]);
            int r = 0, dp[] = new int[a.length];
            for(int i = 0; i < a.length; i++){
                dp[i] = a[i][2];
                for(int j = 0; j < i; j++)
                    if(a[j][0] >= a[i][0] && a[j][1] >= a[i][1] && a[j][2] >= a[i][2])
                        dp[i] = Math.max(dp[i], dp[j] + a[i][2]);
                r = Math.max(r, dp[i]);
            }
            return r;
        }
    }

    static class s1692{//Count Ways to Distribute Candies
        public int waysToDistribute(int n, int k){
            long[][] dp = new long[k + 1][n + 1];
            IntStream.range(0, k + 1).forEach(i -> dp[i][i] = 1);
            for(int i = 1; i <= k; i++)
                for(int j = i + 1; j <= n; j++)
                    dp[i][j] = (i * dp[i][j - 1] + dp[i - 1][j - 1]) % 1_000_000_007;
            return (int) dp[k][n];
        }
    }

    static class s1696{//Jump Game VI
        public int maxResult(int[] a, int k){
            Deque<Integer> q = new ArrayDeque<>();
            q.offer(0);
            for(int i = 1; i < a.length; i++){
                a[i] += a[q.peekFirst()];
                while(!q.isEmpty() && a[q.peekLast()] <= a[i])
                    q.pollLast();
                q.offerLast(i);
                if(i - q.peekFirst() >= k)
                    q.pollFirst();
            }
            return a[a.length - 1];
        }
    }
}
