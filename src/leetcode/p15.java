package leetcode;
import java.util.*;
import java.util.stream.*;

public class p15{
    static class s1503{//Last Moment Before All Ants Fall Out of a Plank
        public int getLastMoment(int n, int[] left, int[] right){
            int r = 0;
            for(int p : left)
                r = Math.max(r, p);
            for(int p : right)
                r = Math.max(r, n - p);
            return r;
        }
    }

    static class s1508{//Range Sum of Sorted Subarray Sums
        public int rangeSum(int[] a, int n, int left, int right){
            int[] cs = new int[n + 1];
            for(int i = 0; i < n; i++)
                cs[i + 1] = cs[i] + a[i];
            List<Integer> sums = new ArrayList<>(n * (n + 1) / 2);
            for(int i = 0; i < n; i++)
                for(int j = 0; j <= i; j++)
                    sums.add(cs[i + 1] - cs[j]);
            Collections.sort(sums);
            long r = 0;
            for(int i = left - 1; i < right; i++)
                r += sums.get(i);
            return (int) (r % 1_000_000_007);
        }
    }

    static class s1513{//Number of Substrings With Only 1s
        public int numSub(String s){
            int r = 0;
            for(int i = 0, count = 0; i < s.length(); i++){
                count = s.charAt(i) == '1' ? count + 1 : 0;
                r = (r + count) % 1_000_000_007;
            }
            return r;
        }
    }

    static class s1516{//Move Sub-Tree of N-Ary Tree
        public Node moveSubTree(Node root, Node p, Node q){
            if(q.children.contains(p))
                return root;
            Node[] parents = new Node[2];
            findParents(root, p, q, parents);
            if(inSubtree(p, q)){
                parents[1].children.remove(q);
                q.children.add(p);
                if(parents[0] == null)
                    return q;
                int index = parents[0].children.indexOf(p);
                parents[0].children.set(index, q);
            }else{
                q.children.add(p);
                parents[0].children.remove(p);
            }
            return root;
        }

        void findParents(Node node, Node p, Node q, Node[] parents){
            if(node != null)
                for(Node next : node.children){
                    if(next == p)
                        parents[0] = node;
                    if(next == q)
                        parents[1] = node;
                    findParents(next, p, q, parents);
                }
        }

        boolean inSubtree(Node node, Node parent){
            if(node == null)
                return false;
            if(node == parent)
                return true;
            for(Node next : node.children)
                if(inSubtree(next, parent))
                    return true;
            return false;
        }

        class Node{
            public int val;
            public List<Node> children = new ArrayList<>();

            public Node(){}

            public Node(int _val){val = _val;}

            public Node(int _val, ArrayList<Node> _children){
                val = _val;
                children = _children;
            }
        }
    }

    static class s1519{
        public int[] countSubTrees(int n, int[][] edges, String labels){
            List<List<Integer>> g = new ArrayList<>(n);
            IntStream.range(0, n).forEach(v -> g.add(new ArrayList<>()));
            for(int[] e : edges){
                g.get(e[0]).add(e[1]);
                g.get(e[1]).add(e[0]);
            }
            int[] r = new int[n];
            boolean[] seen = new boolean[n];
            seen[0] = true;
            traverse(0, g, labels, r, seen);
            return r;
        }

        int[] traverse(int u, List<List<Integer>> g, String labels, int[] r, boolean[] seen){
            int[] f = new int[26];
            for(Integer v : g.get(u))
                if(!seen[v]){
                    seen[v] = true;
                    int[] c = traverse(v, g, labels, r, seen);
                    for(int i = 0; i < f.length; i++)
                        f[i] += c[i];
                }
            r[u] = ++f[labels.charAt(u) - 'a'];
            return f;
        }
    }

    static class s1526{//Minimum Number of Increments on Subarrays to Form a Target Array
        public int minNumberOperations(int[] target){
            int r = target[0];
            for(int i = 1; i < target.length; i++)
                r += Math.max(0, target[i] - target[i - 1]);
            return r;
        }
    }

    static class s1528{//Shuffle String
        public String restoreString(String s, int[] idx){
            char[] a = new char[s.length()];
            for(int i = 0; i < s.length(); i++)
                a[idx[i]] = s.charAt(i);
            return new String(a);
        }
    }

    static class s1536{//Minimum Swaps to Arrange a Binary Grid
        public int minSwaps(int[][] g){
            int zeros[] = new int[g.length], r = 0;
            for(int row = 0, count = 0; row < g.length; row++, count = 0){
                for(int col = g[0].length - 1; col >= 0 && g[row][col] == 0; col--, count++) ;
                zeros[row] = count;
            }
            for(int i = 0, need = g[0].length - 1, j; i < zeros.length; i++, need--){
                for(j = i; j < zeros.length && zeros[j] < need; j++) ;
                if(j == zeros.length)
                    return -1;
                for(; i < j; j--, r++){
                    int t = zeros[j - 1];
                    zeros[j - 1] = zeros[j];
                    zeros[j] = t;
                }
            }
            return r;
        }
    }

    static class s1538{//Guess the Majority in a Hidden Array
        public int guessMajority(ArrayReader reader){
            int n = reader.length(), groupA = 1, groupB = 0, idxA = -1, idxB = -1;
            int r0123 = reader.query(0, 1, 2, 3), r0124 = reader.query(0, 1, 2, 4);
            for(int i = 4; i < n; i++)
                if(reader.query(0, 1, 2, i) == r0123){
                    groupA++;
                    idxA = i;
                }else{
                    groupB++;
                    idxB = i;
                }
            int[][] rThree = new int[3][4];
            rThree[0] = new int[]{1, 2, 3, 4};
            rThree[1] = new int[]{0, 2, 3, 4};
            rThree[2] = new int[]{0, 1, 3, 4};
            for(int i = 0; i < 3; i++)
                if(reader.query(rThree[i][0], rThree[i][1], rThree[i][2], rThree[i][3]) == r0124){
                    groupA++;
                    idxA = i;
                }else{
                    groupB++;
                    idxB = i;
                }
            return groupA > groupB ? idxA : (groupA < groupB ? idxB : -1);
        }

        interface ArrayReader{
            int query(int a, int b, int c, int d);

            int length();
        }
    }

    static class s1545{//Find Kth Bit in Nth Binary String
        public char findKthBit(int n, int k){
            String s = "0";
            while(k > s.length())
                s = s + "1" + invertReverse(s);
            return s.charAt(k - 1);
        }

        String invertReverse(String s){
            StringBuilder r = new StringBuilder(s.length());
            for(int i = s.length() - 1; i >= 0; i--)
                r.append(s.charAt(i) == '0' ? '1' : '0');
            return r.toString();
        }
    }

    static class s1546{//Maximum Number of Non-Overlapping Subarrays With Sum Equals Target
        public int maxNonOverlapping(int[] a, int target){
            Map<Integer, Integer> dp = new HashMap<>();
            dp.put(0, 0);
            int sum = 0, r = 0;
            for(int n : a){
                sum += n;
                if(dp.containsKey(sum - target))
                    r = Math.max(r, 1 + dp.get(sum - target));
                dp.put(sum, r);
            }
            return r;
        }
    }

    static class s1547{//Minimum Cost to Cut a Stick
        public int minCost(int n, int[] cuts){
            int[] c = new int[cuts.length + 2];
            System.arraycopy(Arrays.stream(cuts).sorted().toArray(), 0, c, 1, cuts.length);
            c[c.length - 1] = n;
            return minCost(0, c.length - 1, c, new Integer[c.length][c.length]);
        }

        int minCost(int lo, int hi, int[] cuts, Integer[][] dp){
            if(lo + 1 >= hi)
                return 0;
            if(dp[lo][hi] != null)
                return dp[lo][hi];
            int r = Integer.MAX_VALUE;
            for(int i = lo + 1; i < hi; i++)
                r = Math.min(r, minCost(lo, i, cuts, dp) + minCost(i, hi, cuts, dp));
            return dp[lo][hi] = cuts[hi] - cuts[lo] + r;
        }
    }

    static class s1552{//Magnetic Force Between Two Balls
        public int maxDistance(int[] position, int m){
            Arrays.sort(position);
            int r = 1, lo = 1, hi = 1_000_000_000;
            while(lo <= hi){
                int force = (lo + hi) / 2;
                if(valid(force, position, m)){
                    r = force;
                    lo = force + 1;
                }else hi = force - 1;
            }
            return r;
        }

        boolean valid(int force, int[] position, int m){
            for(int i = 0, lastIdx = 0; i < position.length && m > 1; i++)
                if(position[i] - position[lastIdx] >= force){
                    lastIdx = i;
                    m--;
                }
            return m == 1;
        }
    }

    static class s1557{//Minimum Number of Vertices to Reach All Nodes
        public List<Integer> findSmallestSetOfVertices(int n, List<List<Integer>> edges){
            boolean[] seen = new boolean[n];
            edges.forEach(e -> seen[e.get(1)] = true);
            return IntStream.range(0, n).filter(i -> !seen[i]).boxed().collect(Collectors.toList());
        }
    }

    static class s1559{//Detect Cycles in 2D Grid
        public boolean containsCycle(char[][] g){
            for(int i = 0, dirs[] = {-1, 0, 1, 0, -1}; i < g.length; i++)
                for(int j = 0; j < g[0].length; j++)
                    if('a' <= g[i][j] && g[i][j] <= 'z' && hasCycle(i, j, i, j, g, g[i][j], dirs))
                        return true;
            return false;
        }

        boolean hasCycle(int i, int j, int prevI, int prevJ, char[][] g, char c, int[] dirs){
            g[i][j] = Character.toUpperCase(c);
            for(int d = 1; d < dirs.length; d++){
                int x = i + dirs[d - 1], y = j + dirs[d];
                if(0 <= x && x < g.length && 0 <= y && y < g[0].length && (x != prevI || y != prevJ))
                    if(g[x][y] == Character.toUpperCase(c))
                        return true;
                    else if(g[x][y] == c && hasCycle(x, y, i, j, g, c, dirs))
                        return true;
            }
            return false;
        }
    }

    static class s1570{//Dot Product of Two Sparse Vectors
        class SparseVector{
            final Map<Integer, Integer> m = new HashMap<>();

            SparseVector(int[] a){IntStream.range(0, a.length).filter(i -> a[i] > 0).forEach(i -> m.put(i, a[i]));}

            public int dotProduct(SparseVector v){
                return m.size() <= v.m.size() ? m.keySet().stream().mapToInt(i -> m.get(i) * v.m.getOrDefault(i, 0)).sum() : v.dotProduct(this);
            }
        }
    }

    static class s1574{//Shortest Subarray to be Removed to Make Array Sorted
        public int findLengthOfShortestSubarray(int[] a){
            int left = 0;
            for(; left + 1 < a.length && a[left] <= a[left + 1]; left++) ;
            if(left == a.length - 1)
                return 0;
            int right = a.length - 1;
            for(; right > left && a[right - 1] <= a[right]; right--) ;
            int r = Math.min(a.length - left - 1, right);
            for(int i = 0, j = right; i <= left && j < a.length; )
                if(a[j] >= a[i]){
                    r = Math.min(r, j - i - 1);
                    i++;
                }else j++;
            return r;
        }
    }

    static class s1577{//Number of Ways Where Square of Number Is Equal to Product of Two Numbers
        public int numTriplets(int[] a1, int[] a2){
            return count(a1, a2) + count(a2, a1);
        }

        int count(int[] a1, int[] a2){
            Map<Long, Integer> squares = new HashMap<>();
            for(int n : a2){
                long s = (long) n * n;
                squares.put(s, squares.getOrDefault(s, 0) + 1);
            }
            int r = 0;
            for(int i = 0; i < a1.length; i++)
                for(int j = i + 1; j < a1.length; j++)
                    r += squares.getOrDefault((long) a1[i] * a1[j], 0);
            return r;
        }
    }

    static class s1589{//Maximum Sum Obtained of Any Permutation
        public int maxSumRangeQuery(int[] a, int[][] requests){
            int[] count = new int[a.length];
            for(var r : requests){
                count[r[0]]++;
                if(r[1] + 1 < a.length)
                    count[r[1] + 1]--;
            }
            for(int i = 1; i < count.length; ++i)
                count[i] += count[i - 1];
            Arrays.sort(a);
            Arrays.sort(count);
            return (int) (IntStream.range(0, a.length).mapToLong(i -> (long) a[i] * count[i]).sum() % 1_000_000_007);
        }
    }

    static class s1590{//Make Sum Divisible by P
        public int minSubarray(int[] a, int p){
            int r = a.length, need = (int) (Arrays.stream(a).mapToLong(n -> n).sum() % p);
            Map<Integer, Integer> last = new HashMap<>();
            last.put(0, -1);
            for(int i = 0, sum = 0; i < a.length; ++i){
                sum = (sum + a[i]) % p;
                last.put(sum, i);
                int want = (sum - need + p) % p;
                r = Math.min(r, i - last.getOrDefault(want, -a.length));
            }
            return r < a.length ? r : -1;
        }
    }

    static class s1594{//Maximum Non Negative Product in a Matrix
        public int maxProductPath(int[][] g){
            int n = g.length, m = g[0].length;
            long[][] max = new long[n][m], min = new long[n][m];
            max[0][0] = min[0][0] = g[0][0];
            for(int r = 1; r < n; r++)
                min[r][0] = max[r][0] = min[r - 1][0] * g[r][0];
            for(int c = 1; c < m; c++)
                min[0][c] = max[0][c] = min[0][c - 1] * g[0][c];
            for(int i = 1; i < n; i++)
                for(int j = 1; j < m; j++){
                    long v1 = Math.min(g[i][j] * min[i - 1][j], g[i][j] * min[i][j - 1]);
                    long v2 = Math.max(g[i][j] * min[i - 1][j], g[i][j] * min[i][j - 1]);
                    long v3 = Math.max(g[i][j] * max[i - 1][j], g[i][j] * max[i][j - 1]);
                    long v4 = Math.min(g[i][j] * max[i - 1][j], g[i][j] * max[i][j - 1]);
                    min[i][j] = Math.min(v1, Math.min(v2, Math.min(v3, v4)));
                    max[i][j] = Math.max(v1, Math.max(v2, Math.max(v3, v4)));
                }
            return max[n - 1][m - 1] >= 0 ? (int) (max[n - 1][m - 1] % 1_000_000_007) : -1;
        }
    }

    static class s1599{//Maximum Profit of Operating a Centennial Wheel
        public int minOperationsMaxProfit(int[] customers, int ticketPrice, int cost){
            int profit = 0, maxProfit = 0, i = 0, rotation = 0, maxRotation = 0, waiting = 0;
            while(i < customers.length || waiting > 0){
                if(i < customers.length)
                    waiting += customers[i++];
                int board = Math.min(4, waiting);
                waiting -= board;
                profit += ticketPrice * board - cost;
                rotation++;
                if(profit > maxProfit){
                    maxProfit = profit;
                    maxRotation = rotation;
                }
            }
            return maxProfit > 0 ? maxRotation : -1;
        }
    }
}
