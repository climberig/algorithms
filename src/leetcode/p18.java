package leetcode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class p18{
    static class s1802{//Maximum Value at a Given Index in a Bounded Array
        public int maxValue(int n, int index, int maxSum){
            int lo = 1, hi = maxSum, r = 1;
            while(lo <= hi){
                int v = (lo + hi) / 2;
                long sum = sum(v - 1, index) + sum(v - 1, n - index - 1) + v;
                if(sum <= maxSum){
                    r = v;
                    lo = v + 1;
                }else hi = v - 1;
            }
            return r;
        }

        long sum(int v, int n){
            int m = v - n, v1 = Math.max(m + 1, 1), onesLen = m < 0 ? -m : 0;
            return 1L * onesLen + (1L * v1 + v) * Math.min(n, v) / 2;
        }
    }

    static class s1804{//Implement Trie II (Prefix Tree)
        class Trie{
            int k, n;//k - number of instances, n - number of all strings, k<=n
            Trie[] nodes = new Trie[26];

            public void insert(String word){
                Trie node = this;
                for(char c : word.toCharArray()){
                    if(node.nodes[c - 'a'] == null)
                        node.nodes[c - 'a'] = new Trie();
                    node = node.nodes[c - 'a'];
                    node.n++;
                }
                node.k++;
            }

            public int countWordsEqualTo(String word){return find(word).k;}

            public int countWordsStartingWith(String prefix){return find(prefix).n;}

            Trie find(String word){
                Trie node = this;
                for(int i = 0; i < word.length() && node != null; i++)
                    node = node.nodes[word.charAt(i) - 'a'];
                return node != null ? node : new Trie();
            }

            public void erase(String word){
                Trie node = this;
                for(int i = 0; i < word.length(); i++, node.n--)
                    node = node.nodes[word.charAt(i) - 'a'];
                node.k--;
            }
        }
    }

    static class s1810{
        public int findShortestPath(GridMaster master){
            int N = 100, g[][] = new int[2 * N + 1][2 * N + 1], moves[][] = new int[][]{{-1, 0}, {0, -1}, {1, 0}, {0, 1}}, target[] = new int[]{-N, -N};
            char[] dirs = new char[]{'U', 'L', 'D', 'R'};
            IntStream.range(0, g.length).forEach(i -> Arrays.fill(g[i], -1));
            g[N][N] = 0;
            dfs(master, N, N, g, target, dirs, moves);
            boolean[][] seen = new boolean[2 * N + 1][2 * N + 1];
            PriorityQueue<int[]> q = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));
            for(q.offer(new int[]{N, N, 0}); !q.isEmpty(); ){
                int[] p = q.poll();
                if(p[0] == target[0] && p[1] == target[1])
                    return p[2];
                if(!seen[p[0]][p[1]]){
                    seen[p[0]][p[1]] = true;
                    for(int[] move : moves){
                        int nr = p[0] + move[0], nc = p[1] + move[1];
                        if(0 <= nr && nr <= 2 * N && 0 <= nc && nc <= 2 * N && !seen[nr][nc] && g[nr][nc] != -1)
                            q.offer(new int[]{nr, nc, p[2] + g[nr][nc]});
                    }
                }
            }
            return -1;
        }

        void dfs(GridMaster m, int r, int c, int[][] g, int[] target, char[] dirs, int[][] moves){
            if(m.isTarget()){
                target[0] = r;
                target[1] = c;
            }
            for(int i = 0; i < 4; i++){
                int nr = r + moves[i][0], nc = c + moves[i][1];
                if(m.canMove(dirs[i]) && g[nr][nc] == -1){
                    int val = m.move(dirs[i]);
                    g[nr][nc] = val;
                    dfs(m, nr, nc, g, target, dirs, moves);
                    m.move(dirs[(i + 2) % 4]);
                }
            }
        }

        interface GridMaster{
            boolean canMove(char direction);

            int move(char direction);

            boolean isTarget();
        }
    }

    static class s1814{//Count Nice Pairs in an Array
        public int countNicePairs(int[] a){
            Map<Integer, Integer> m = new HashMap<>();
            long r = 0;
            for(int n : a){
                int rev = 0;
                for(int i = n; i > 0; i /= 10)
                    rev = rev * 10 + i % 10;
                r += m.getOrDefault(n - rev, 0);
                m.put(n - rev, m.getOrDefault(n - rev, 0) + 1);
            }
            return (int) (r % 1_000_000_007);
        }
    }

    static class s1818{//Minimum Absolute Sum Difference
        public int minAbsoluteSumDiff(int[] a1, int[] a2){
            int sorted[] = Arrays.stream(a1).sorted().toArray(), max = Integer.MIN_VALUE;
            long sumDiff = 0;
            for(int i = 0; i < a2.length; i++){
                int diff = Math.abs(a1[i] - a2[i]), idx = Arrays.binarySearch(sorted, a2[i]);
                sumDiff += diff;
                idx = idx < 0 ? ~idx : idx;
                if(idx < sorted.length)
                    max = Math.max(max, diff - Math.abs(sorted[idx] - a2[i]));
                if(idx > 0)
                    max = Math.max(max, diff - Math.abs(sorted[idx - 1] - a2[i]));
            }
            return (int) ((sumDiff - max) % 1_000_000_007);
        }
    }

    static class s1820{//Maximum Number of Accepted Invitations
        public int maximumInvitations(int[][] m){
            int pairedGirls[] = new int[m[0].length], r = 0;
            for(int boy = 0; boy < m.length; boy++)
                if(dfs(m, boy, new HashSet<>(), pairedGirls))
                    r++;
            return r;
        }

        boolean dfs(int[][] m, int boy, Set<Integer> seenGirls, int[] pairedGirls){
            for(int i = 0; i < m[0].length; i++)
                if(m[boy][i] == 1 && seenGirls.add(i) && (pairedGirls[i] == 0 || dfs(m, pairedGirls[i] - 1, seenGirls, pairedGirls))){
                    pairedGirls[i] = boy + 1;
                    return true;
                }
            return false;
        }
    }

    static class s1832{//Check if the Sentence Is Pangram
        public boolean checkIfPangram(String s){
            return s.chars().boxed().collect(Collectors.toSet()).size() == 26;
        }
    }

    static class s1838{//Frequency of the Most Frequent Element
        public int maxFrequency(int[] a, int k){
            Arrays.sort(a);
            int r = 0, i = 0, j = 0;
            for(long sum = 0; j < a.length; j++){
                sum += a[j];
                while(sum + k < (long) a[j] * (j - i + 1))
                    sum -= a[i++];
                r = Math.max(r, j - i + 1);
            }
            return r;
        }
    }

    static class s1846{//Maximum Element After Decreasing and Rearranging
        public int maximumElementAfterDecrementingAndRearranging(int[] a){
            Arrays.sort(a);
            int r = 0;
            for(int n : a)
                r = Math.min(r + 1, n);
            return r;
        }
    }

    static class s1850{//Minimum Adjacent Swaps to Reach the Kth Smallest Number
        public int getMinSwaps(String num, int k){
            char[] a = num.toCharArray(), b = a.clone();
            while(k-- > 0){
                int i = b.length - 2, j;
                for(; b[i] >= b[i + 1]; i--) ;
                for(j = i + 1; j < b.length && b[i] < b[j]; j++) ;
                swap(b, i, j - 1);
                for(int lo = i + 1, hi = b.length - 1; lo < hi; lo++, hi--)
                    swap(b, lo, hi);
            }
            int r = 0;
            for(int i = 0; i < a.length; i++)
                if(a[i] != b[i]){
                    int j = i;
                    for(; a[i] != b[j]; j++) ;
                    for(; j != i; j--, r++)
                        swap(b, j - 1, j);
                }
            return r;
        }

        void swap(char[] a, int i, int j){
            char t = a[i]; a[i] = a[j]; a[j] = t;
        }
    }

    static class s1856{//Maximum Subarray Min-Product
        public int maxSumMinProduct(int[] a){
            long cs[] = new long[a.length + 1], r = 0;
            for(int i = 0; i < a.length; i++)
                cs[i + 1] = cs[i] + a[i];
            Stack<Integer> s = new Stack<>();
            for(int i = 0; i <= a.length; i++){
                while(!s.isEmpty() && (i == a.length || a[s.peek()] > a[i])){
                    int j = s.pop();
                    r = Math.max(r, (cs[i] - cs[s.isEmpty() ? 0 : s.peek() + 1]) * a[j]);
                }
                s.push(i);
            }
            return (int) (r % 1_000_000_007);
        }
    }

    static class s1866{//Number of Ways to Rearrange Sticks With K Sticks Visible
        public int rearrangeSticks(int n, int k){
            return (int) arrange(n, k, new long[k + 1][n + 1]);
        }

        long arrange(int n, int k, long[][] dp){
            if(k == 0)
                return 0;
            if(k == n)
                return 1;
            if(dp[k][n] > 0)
                return dp[k][n];
            return dp[k][n] = (arrange(n - 1, k - 1, dp) + (n - 1) * arrange(n - 1, k, dp)) % 1_000_000_007;
        }
    }

    static class s1872{//Stone Game VIII
        public int stoneGameVIII(int[] stones){
            for(int i = 1; i < stones.length; i++)
                stones[i] += stones[i - 1];
            int r = stones[stones.length - 1];
            for(int i = stones.length - 2; i >= 1; i--)
                r = Math.max(r, stones[i] - r);
            return r;
        }
    }

    static class s1874{//Minimize Product Sum of Two Arrays
        public int minProductSum(int[] a, int[] b){
            Arrays.sort(a);
            Arrays.sort(b);
            int r = 0;
            for(int i = 0; i < a.length; i++)
                r += a[i] * b[a.length - 1 - i];
            return r;
        }
    }

    static class s1878{//Get Biggest Three Rhombus Sums in a Grid
        public int[] getBiggestThree(int[][] g){
            Set<Integer> sums = new HashSet<>();
            for(int i = 0; i < g.length; i++)
                for(int j = 0; j < g[0].length; j++)
                    for(int side = 0; side <= Math.min(g.length / 2, g[0].length / 2); side++){
                        int sum = 0;
                        if(side == 0)
                            sum = g[i][j];
                        else if(0 <= j - side && j + side < g[0].length && i + side + side < g.length){
                            int r, c;
                            for(r = i + 1, c = j + 1; c - j <= side; sum += g[r++][c++]) ;
                            for(c = c - 2; c >= j; sum += g[r++][c--]) ;
                            for(r = r - 2; j - c <= side; sum += g[r--][c--]) ;
                            for(c = c + 2; c <= j; sum += g[r--][c++]) ;
                        }
                        sums.add(sum);
                    }
            return sums.stream().sorted(Comparator.reverseOrder()).limit(3).mapToInt(s -> s).toArray();
        }
    }
}
