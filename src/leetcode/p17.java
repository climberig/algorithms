package leetcode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class p17{
    static class s1702{//Maximum Binary String After Change
        public String maximumBinaryString(String binary){
            char[] a = binary.toCharArray();
            int i = -1, zeroes = 0;
            for(int j = a.length - 1; j >= 0; j--)
                if(a[j] == '0'){
                    zeroes++;
                    i = j;
                }
            if(i < 0)
                return binary;
            Arrays.fill(a, '1');
            a[i + zeroes - 1] = '0';
            return new String(a);
        }
    }

    static class s1712{//Ways to Split Array Into Three Subarrays
        public int waysToSplit(int[] a){
            int n = a.length, r = 0;
            for(int i = 1; i < n; ++i)
                a[i] += a[i - 1];
            for(int i = 0, j = 0, k = 0; i < n - 2; i++){
                while(j <= i || (j < n - 1 && a[j] < a[i] * 2))
                    j++;
                while(k < j || (k < n - 1 && a[k] - a[i] <= a[n - 1] - a[k]))
                    k++;
                r = (r + k - j) % 1_000_000_007;
            }
            return r;
        }
    }

    static class s1717{//Maximum Score From Removing Substrings
        public int maximumGain(String s, int x, int y){
            StringBuilder b = new StringBuilder(s);
            if(x > y)
                return rm(b, 'a', 'b', x) + rm(b, 'b', 'a', y);
            return rm(b, 'b', 'a', y) + rm(b, 'a', 'b', x);
        }

        int rm(StringBuilder s, char a, char b, int score){
            int i = 0, r = 0;
            for(int j = 0; j < s.length(); j++){
                s.setCharAt(i++, s.charAt(j));
                if(i > 1 && s.charAt(i - 2) == a && s.charAt(i - 1) == b){
                    i -= 2;
                    r += score;
                }
            }
            s.setLength(i);
            return r;
        }
    }

    static class s1718{//Construct the Lexicographically Largest Valid Sequence
        public int[] constructDistancedSequence(int n){
            int[] r = new int[2 * n - 1];
            construct(0, r, new boolean[n + 1], n);
            return r;
        }

        boolean construct(int i, int[] a, boolean[] seen, int n){
            if(i == a.length)
                return true;
            if(a[i] != 0)
                return construct(i + 1, a, seen, n);
            for(int k = n; k > 0; k--)
                if(!seen[k] && (k == 1 || i + k < a.length && a[i + k] == 0)){
                    a[i] = k;
                    if(k != 1)
                        a[i + k] = k;
                    seen[k] = true;
                    if(construct(i + 1, a, seen, n))
                        return true;
                    a[i] = 0;
                    if(k != 1)
                        a[i + k] = 0;
                    seen[k] = false;
                }
            return false;
        }
    }

    static class s1721{//Swapping Nodes in a Linked List
        public ListNode swapNodes(ListNode head, int k){
            ListNode first = head, second;
            int len = 1, i = 1;
            for(ListNode node = head; node != null; len++, node = node.next)
                if(len == k)
                    first = node;
            for(second = head; i < len - k; second = second.next, i++) ;
            int t = first.val;
            first.val = second.val;
            second.val = t;
            return head;
        }
    }

    static class s1722{//Minimize Hamming Distance After Swap Operations
        public int minimumHammingDistance(int[] src, int[] tgt, int[][] swaps){
            List<List<Integer>> g = IntStream.range(0, src.length).mapToObj(i -> new ArrayList<Integer>()).collect(Collectors.toList());
            for(int[] s : swaps){
                g.get(s[0]).add(s[1]);
                g.get(s[1]).add(s[0]);
            }
            int r = 0;
            boolean[] seen = new boolean[src.length];
            for(int i = 0; i < src.length; i++)
                if(!seen[i]){
                    List<Integer> indices = new ArrayList<>();
                    dfs(i, seen, g, indices);
                    Map<Integer, Integer> m = new HashMap<>();
                    for(Integer j : indices)
                        m.put(src[j], m.getOrDefault(src[j], 0) + 1);
                    for(Integer j : indices)
                        if(m.getOrDefault(tgt[j], 0) > 0)
                            m.put(tgt[j], m.get(tgt[j]) - 1);
                        else r++;
                }
            return r;
        }

        void dfs(int i, boolean[] seen, List<List<Integer>> g, List<Integer> indices){
            seen[i] = true;
            indices.add(i);
            for(Integer j : g.get(i))
                if(!seen[j])
                    dfs(j, seen, g, indices);
        }
    }

    static class s1727{//Largest Submatrix With Rearrangements
        public int largestSubmatrix(int[][] m){
            for(int c = 0; c < m[0].length; c++)
                for(int r = 1; r < m.length; r++)
                    m[r][c] += m[r][c] == 0 ? 0 : m[r - 1][c];
            int r = 0;
            for(int[] row : m){
                Arrays.sort(row);
                for(int j = 1; j <= m[0].length; j++)
                    r = Math.max(r, j * row[m[0].length - j]);
            }
            return r;
        }
    }

    static class s1730{//Shortest Path to Get Food
        public int getFood(char[][] g){
            Queue<int[]> q = new LinkedList<>();
            for(int i = 0; i < g.length; i++)
                for(int j = 0; j < g[0].length; j++)
                    if(g[i][j] == '*'){
                        q.add(new int[]{i, j});
                        break;
                    }
            for(int dist = 1, dirs[] = {-1, 0, 1, 0, -1}; !q.isEmpty(); dist++)
                for(int size = q.size(); size > 0; size--){
                    int p[] = q.poll(), x = p[0], y = p[1];
                    for(int d = 1; d < dirs.length; d++){
                        int nx = x + dirs[d - 1], ny = y + dirs[d];
                        if(0 <= nx && nx < g.length && 0 <= ny && ny < g[0].length && g[nx][ny] != 'X'){
                            if(g[nx][ny] == '#')
                                return dist;
                            g[nx][ny] = 'X';
                            q.offer(new int[]{nx, ny});
                        }
                    }
                }
            return -1;
        }
    }

    static class s1733{//Minimum Number of People to Teach
        public int minimumTeachings(int n, int[][] languages, int[][] friendships){
            List<Set<Integer>> m = IntStream.range(0, languages.length + 1).mapToObj(f -> new HashSet<Integer>()).collect(Collectors.toList());
            for(int i = 0; i < languages.length; i++)
                for(int lang : languages[i])
                    m.get(i + 1).add(lang);
            boolean[] alreadyCan = new boolean[friendships.length];
            for(int j = 1; j <= n; j++)
                for(int i = 0; i < friendships.length; i++)
                    if(m.get(friendships[i][0]).contains(j) && m.get(friendships[i][1]).contains(j))
                        alreadyCan[i] = true;
            int r = Integer.MAX_VALUE;
            for(int i = 1; i <= n; i++){
                Set<Integer> teachSet = new HashSet<>();
                for(int j = 0; j < friendships.length; j++)
                    if(!alreadyCan[j]){
                        if(!m.get(friendships[j][0]).contains(i))
                            teachSet.add(friendships[j][0]);
                        if(!m.get(friendships[j][1]).contains(i))
                            teachSet.add(friendships[j][1]);
                    }
                r = Math.min(teachSet.size(), r);
            }
            return r;
        }
    }

    static class s1734{//Decode XORed Permutation
        public int[] decode(int[] encoded){
            int allXor = 0, xor = 0, r[] = new int[encoded.length + 1];
            for(int i = 1; i <= encoded.length + 1; i++)
                allXor ^= i;
            for(int e : encoded){
                xor ^= e;
                allXor ^= xor;
            }
            r[0] = allXor;
            for(int i = 1; i < r.length; i++)
                r[i] = encoded[i - 1] ^ r[i - 1];
            return r;
        }
    }

    static class s1737{//Change Minimum Characters to Satisfy One of Three Conditions
        public int minCharacters(String a, String b){
            int m = a.length(), n = b.length(), r = m + n;
            int[] c1 = new int[26], c2 = new int[26];
            a.chars().forEach(c -> c1[c - 'a']++);
            b.chars().forEach(c -> c2[c - 'a']++);
            for(int i = 0; i < 26; ++i){
                r = Math.min(r, m + n - c1[i] - c2[i]); // condition 3
                if(i > 0){
                    c1[i] += c1[i - 1];
                    c2[i] += c2[i - 1];
                }
                if(i < 25){
                    r = Math.min(r, m - c1[i] + c2[i]); // condition 1
                    r = Math.min(r, n - c2[i] + c1[i]); // condition 2
                }
            }
            return r;
        }
    }

    static class s1738{//Find Kth Largest XOR Coordinate Value
        public int kthLargestValue(int[][] mat, int k){
            int n = mat.length, m = mat[0].length, pre[][] = new int[n + 1][m + 1], r[] = new int[n * m];
            for(int i = 0; i < n; i++)
                for(int j = 0; j < m; j++)
                    r[i * m + j] = pre[i + 1][j + 1] = pre[i + 1][j] ^ pre[i][j + 1] ^ pre[i][j] ^ mat[i][j];
            Arrays.sort(r);
            return r[r.length - k];
        }
    }

    static class s1744{//Can You Eat Your Favorite Candy on Your Favorite Day?
        public boolean[] canEat(int[] counts, int[][] queries){
            long[] cs = new long[counts.length + 1];
            boolean[] r = new boolean[queries.length];
            for(int i = 0; i < counts.length; i++)
                cs[i + 1] = cs[i] + counts[i];
            for(int i = 0; i < r.length; i++){
                int type = queries[i][0], day = queries[i][1], dailyCap = queries[i][2];
                r[i] = cs[type] / dailyCap <= day && day <= cs[type + 1] - 1;
            }
            return r;
        }
    }

    static class s1745{//Palindrome Partitioning IV
        public boolean checkPartitioning(String s){
            char[] a = s.toCharArray();
            boolean[][] dp = new boolean[a.length][a.length];
            for(int i = a.length - 1; i >= 0; i--)
                for(int j = i; j < a.length; j++)
                    dp[i][j] = a[i] == a[j] && (i + 1 > j - 1 || dp[i + 1][j - 1]);
            for(int i = 1; i < a.length - 1; i++)
                for(int j = i; j < a.length - 1; j++)
                    if(dp[0][i - 1] && dp[i][j] && dp[j + 1][a.length - 1])
                        return true;
            return false;
        }
    }

    static class s1749{//Maximum Absolute Sum of Any Subarray
        public int maxAbsoluteSum(int[] a){
            int r = 0, maxSum = 0, minSum = 0;
            for(int n : a){
                maxSum = Math.max(0, maxSum + n);
                minSum = Math.min(0, minSum + n);
                r = Math.max(r, Math.max(maxSum, -minSum));
            }
            return r;
        }
    }

    static class s1750{//Minimum Length of String After Deleting Similar Ends
        public int minimumLength(String s){
            int i = 0, j = s.length() - 1;
            while(i < j && s.charAt(i) == s.charAt(j)){
                char c = s.charAt(i);
                for(; i <= j && s.charAt(i) == c; i++) ;
                for(; i <= j && s.charAt(j) == c; j--) ;
            }
            return j - i + 1;
        }
    }

    static class s1751{//Maximum Number of Events That Can Be Attended II
        public int maxValue(int[][] events, int k){
            Arrays.sort(events, Comparator.comparingInt(a -> a[0]));
            return maxVal(0, k, 0, events, new HashMap<>());
        }

        int maxVal(int i, int k, int endTime, int[][] events, Map<String, Integer> dp){
            if(k == 0 || i == events.length)
                return 0;
            String key = k + "_" + endTime;
            if(dp.containsKey(key))
                return dp.get(key);
            int max = maxVal(i + 1, k, endTime, events, dp);
            if(endTime < events[i][0])
                max = Math.max(max, maxVal(i + 1, k - 1, events[i][1], events, dp) + events[i][2]);
            dp.put(key, max);
            return max;
        }
    }

    static class s1755{//Closest Subsequence Sum
        public int minAbsDifference(int[] a, int goal){
            List<Integer> sums = sums(Arrays.copyOfRange(a, 0, a.length / 2));
            List<Integer> sortedSums = sums(Arrays.copyOfRange(a, a.length / 2, a.length)).stream().sorted().collect(Collectors.toList());
            int r = Integer.MAX_VALUE;
            for(Integer sum : sums){
                int i = Collections.binarySearch(sortedSums, goal - sum);
                if(i >= 0)
                    return 0;
                i = Math.min(-(i + 1), sortedSums.size() - 1);
                r = Math.min(r, Math.abs(sum + sortedSums.get(i) - goal));
                if(i + 1 < sortedSums.size())
                    r = Math.min(r, Math.abs(sum + sortedSums.get(i + 1) - goal));
                if(i - 1 >= 0)
                    r = Math.min(r, Math.abs(sum + sortedSums.get(i - 1) - goal));
            }
            return r;
        }

        List<Integer> sums(int[] a){
            Set<Integer> r = new HashSet<>();
            dfs(0, 0, a, r);
            return new ArrayList<>(r);
        }

        void dfs(int i, int currSum, int[] a, Set<Integer> r){
            r.add(currSum);
            if(i < a.length){
                dfs(i + 1, currSum, a, r);
                dfs(i + 1, currSum + a[i], a, r);
            }
        }
    }

    static class s1756{//Design Most Recently Used Queue
        class MRUQueue{
            int[] q;

            public MRUQueue(int n){
                q = IntStream.range(1, n + 1).toArray();
            }

            public int fetch(int k){
                int val = q[k - 1];
                for(int i = k; i < q.length; i++)
                    q[i - 1] = q[i];
                q[q.length - 1] = val;
                return val;
            }
        }
    }

    static class s1760{//Minimum Limit of Balls in a Bag
        public int minimumSize(int[] bags, int k){
            int lo = 1, hi = 1_000_000_000;
            while(lo < hi){
                int size = (lo + hi) / 2, ops = 0;
                for(int bag : bags)
                    ops += (bag - 1) / size;
                if(ops > k)//the size is too small
                    lo = size + 1;
                else hi = size;
            }
            return lo;
        }
    }

    static class s1762{//Buildings With an Ocean View
        public int[] findBuildings(int[] heights){
            LinkedList<Integer> r = new LinkedList<>();
            for(int i = heights.length - 1, max = 0; i >= 0; i--)
                if(heights[i] > max){
                    r.addFirst(i);
                    max = heights[i];
                }
            return r.stream().mapToInt(n -> n).toArray();
        }
    }

    static class s1764{//Form Array by Concatenating Subarrays of Another Array
        public boolean canChoose(int[][] groups, int[] a){
            return can(0, groups, a, 0);
        }

        boolean can(int gIdx, int[][] groups, int[] a, int aIdx){
            if(gIdx == groups.length)
                return true;
            for(int i = aIdx; i <= a.length - groups[gIdx].length; i++)
                if(same(a, i, groups[gIdx]) && can(gIdx + 1, groups, a, i + groups[gIdx].length))
                    return true;
            return false;
        }

        boolean same(int[] a, int start, int[] g){
            for(int i = start, j = 0; j < g.length; i++, j++)
                if(a[i] != g[j])
                    return false;
            return true;
        }
    }

    static class s1770{//Maximum Score from Performing Multiplication Operations
        public int maximumScore(int[] a, int[] m){
            return max(a, m, 0, a.length - 1, 0, new Integer[m.length][m.length]);
        }

        int max(int[] a, int[] m, int L, int R, int i, Integer[][] dp){
            if(i == m.length)
                return 0;
            if(dp[L][a.length - R - 1] == null)
                dp[L][a.length - R - 1] = Math.max(a[L] * m[i] + max(a, m, L + 1, R, i + 1, dp), a[R] * m[i] + max(a, m, L, R - 1, i + 1, dp));
            return dp[L][a.length - R - 1];
        }
    }

    static class s1775{//Equal Sum Arrays With Minimum Number of Operations
        public int minOperations(int[] a1, int[] a2){
            int s1 = IntStream.of(a1).sum(), s2 = IntStream.of(a2).sum(), r = 0;
            if(s1 > s2)
                return minOperations(a2, a1);
            int[] cnt = new int[6];
            IntStream.of(a1).forEach(n -> ++cnt[6 - n]);
            IntStream.of(a2).forEach(n -> ++cnt[n - 1]);
            for(int i = cnt.length - 1; s1 < s2 && i >= 0; )
                if(cnt[i] > 0){
                    s1 += i;
                    cnt[i]--;
                    r++;
                }else i--;
            return s1 < s2 ? -1 : r;
        }
    }

    static class s1778{//Shortest Path in a Hidden Grid
        public int findShortestPath(GridMaster m){
            char[] dirs = {'U', 'L', 'D', 'R'};
            int distance = 1, N = 500, moves[][] = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}}, g[][] = new int[2 * N + 1][2 * N + 1];
            dfs(g, N, N, m, moves, dirs);
            Queue<int[]> q = new LinkedList<>();
            for(g[N][N] = -1, q.offer(new int[]{N, N}); !q.isEmpty(); distance++)
                for(int size = q.size(); size > 0; size--){
                    int rc[] = q.poll(), r = rc[0], c = rc[1];
                    for(int[] move : moves){
                        int nr = r + move[0], nc = c + move[1];
                        if(g[nr][nc] > 0){
                            if(g[nr][nc] == 2)
                                return distance;
                            g[nr][nc] = -1;
                            q.offer(new int[]{nr, nc});
                        }
                    }
                }
            return -1;
        }

        void dfs(int[][] g, int r, int c, GridMaster m, int[][] moves, char[] dirs){
            if(g[r][c] == 0){
                g[r][c] = m.isTarget() ? 2 : 1;
                for(int i = 0; i < dirs.length; i++)
                    if(m.canMove(dirs[i])){
                        m.move(dirs[i]);
                        dfs(g, r + moves[i][0], c + moves[i][1], m, moves, dirs);
                        m.move(dirs[(i + 2) % 4]);
                    }else g[r + moves[i][0]][c + moves[i][1]] = -1;
            }
        }

        interface GridMaster{
            boolean canMove(char direction);

            void move(char direction);

            boolean isTarget();
        }
    }

    static class s1781{//Sum of Beauty of All Substrings
        public int beautySum(String s){
            int r = 0;
            char[] a = s.toCharArray();
            for(int i = 0; i < s.length(); i++)
                for(int j = i, fr[] = new int[26]; j < s.length(); j++){
                    fr[a[j] - 'a']++;
                    r += beauty(fr);
                }
            return r;
        }

        int beauty(int[] fr){
            int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
            for(int f : fr)
                if(f > 0){
                    min = Math.min(min, f);
                    max = Math.max(max, f);
                }
            return max - min;
        }
    }

    static class s1786{//Number of Restricted Paths From First to Last Node
        public int countRestrictedPaths(int n, int[][] edges){
            int[] dist = new int[n + 1];
            Arrays.fill(dist, Integer.MAX_VALUE);
            List<List<int[]>> g = IntStream.range(0, n + 1).mapToObj(i -> new ArrayList<int[]>()).collect(Collectors.toList());
            for(int[] e : edges){
                g.get(e[0]).add(new int[]{e[1], e[2]});
                g.get(e[1]).add(new int[]{e[0], e[2]});
            }
            PriorityQueue<int[]> q = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
            for(dist[n] = 0, q.add(new int[]{n, 0}); !q.isEmpty(); ){
                int p[] = q.poll(), u = p[0], d = p[1];
                for(int[] to : g.get(u))
                    if(d + to[1] < dist[to[0]])
                        q.add(new int[]{to[0], dist[to[0]] = d + to[1]});
            }
            return dfs(1, n, dist, g, new Integer[n + 1]);
        }

        int dfs(int u, int n, int[] d, List<List<int[]>> g, Integer[] dp){
            if(u == n)
                return 1;
            if(dp[u] != null)
                return dp[u];
            int r = 0;
            for(int[] v : g.get(u))
                if(d[v[0]] < d[u])
                    r = (r + dfs(v[0], n, d, g, dp)) % 1_000_000_007;
            return dp[u] = r;
        }
    }

    static class s1793{//Maximum Score of a Good Subarray
        public int maximumScore(int[] a, int k){
            int r = a[k], min = a[k], lo = k, hi = k;
            while(lo > 0 || hi < a.length - 1){
                if(lo == 0)
                    hi++;
                else if(hi == a.length - 1)
                    lo--;
                else if(a[lo - 1] < a[hi + 1])
                    hi++;
                else lo--;
                min = Math.min(min, Math.min(a[lo], a[hi]));
                r = Math.max(r, min * (hi - lo + 1));
            }
            return r;
        }
    }

    static class s1798{//Maximum Number of Consecutive Values You Can Make
        public int getMaximumConsecutive(int[] coins){
            Arrays.sort(coins);
            int r = 1;
            for(int coin : coins){
                if(coin > r)
                    break;
                r += coin;
            }
            return r;
        }
    }
}
