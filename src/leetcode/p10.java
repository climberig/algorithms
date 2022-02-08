package leetcode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class p10{
    static class s1002{//Find Common Characters
        public List<String> commonChars(String[] words){
            int[] common = new int[26];
            Arrays.fill(common, Integer.MAX_VALUE);
            for(String w : words){
                int[] counts = new int[26];
                w.chars().forEach(c -> counts[c - 'a']++);
                for(int i = 0; i < common.length; i++)
                    common[i] = Math.min(common[i], counts[i]);
            }
            List<String> r = new ArrayList<>();
            for(char c = 'a'; c <= 'z'; c++)
                while(common[c - 'a']-- > 0)
                    r.add(Character.toString(c));
            return r;
        }
    }
    static class s1013{//Partition Array Into Three Parts With Equal Sum
        public boolean canThreePartsEqualSum(int[] a){
            int sum = Arrays.stream(a).sum(), count = 0;
            if(sum % 3 != 0)
                return false;
            for(int i = 0, currSum = 0, target = sum / 3; i < a.length; i++){
                currSum += a[i];
                if(currSum == target){
                    currSum = 0;
                    count++;
                }
            }
            return count >= 3;
        }
    }

    static class s1015{//Smallest Integer Divisible by K
        public int smallestRepunitDivByK(int k){
            if(!Set.of(1, 3, 7, 9).contains(k % 10))
                return -1;
            Set<Integer> remainders = new HashSet<>();
            for(int len = 1, mod = 0; mod <= k; len++){
                mod = (mod * 10 + 1) % k;
                if(mod == 0)
                    return len;
                if(!remainders.add(mod))
                    return -1;
            }
            return -1;
        }
    }

    static class s1016{//Binary String With Substrings Representing 1 To N
        public boolean queryString(String s, int n){
            for(int i = n; i > n / 2; i--)
                if(!s.contains(Integer.toBinaryString(i)))
                    return false;
            return true;
        }
    }

    static class s1022{//Sum of Root To Leaf Binary Numbers
        public int sumRootToLeaf(TreeNode root){
            return sum(root, 0);
        }

        int sum(TreeNode node, int sum){
            if(node == null)
                return 0;
            if(node.left == null && node.right == null)
                return 2 * sum + node.val;
            return sum(node.left, 2 * sum + node.val) + sum(node.right, 2 * sum + node.val);
        }
    }

    static class s1031{//Maximum Sum of Two Non-Overlapping Subarrays
        public int maxSumTwoNoOverlap(int[] a, int L, int R){
            int[] cs = new int[a.length + 1];
            for(int i = 0; i < a.length; i++)
                cs[i + 1] = cs[i] + a[i];
            return Math.max(maxSum(cs, L, R), maxSum(cs, R, L));
        }

        int maxSum(int[] cs, int L, int R){
            int r = 0;
            for(int i = L + R, maxL = 0; i < cs.length; i++){
                maxL = Math.max(maxL, cs[i - R] - cs[i - R - L]);
                r = Math.max(r, maxL + cs[i] - cs[i - R]);
            }
            return r;
        }
    }

    static class s1034{//Coloring A Border
        public int[][] colorBorder(int[][] g, int r0, int c0, int color){
            List<int[]> border = new ArrayList<>();
            dfs(g, r0, c0, g[r0][c0], border, new boolean[g.length][g[0].length], new int[]{-1, 0, 1, 0, -1});
            border.forEach(c -> g[c[0]][c[1]] = color);
            return g;
        }

        void dfs(int[][] g, int x, int y, int color, List<int[]> r, boolean[][] seen, int[] dirs){
            if(0 <= x && x < g.length && 0 <= y && y < g[0].length && g[x][y] == color && !seen[x][y]){
                seen[x][y] = true;
                for(int d = 1; d < dirs.length; d++){
                    int xx = x + dirs[d - 1], yy = y + dirs[d];
                    if(xx < 0 || xx >= g.length || yy < 0 || yy >= g[0].length || g[xx][yy] != color){
                        r.add(new int[]{x, y});
                        break;
                    }
                }
                dfs(g, x + 1, y, color, r, seen, dirs);
                dfs(g, x - 1, y, color, r, seen, dirs);
                dfs(g, x, y + 1, color, r, seen, dirs);
                dfs(g, x, y - 1, color, r, seen, dirs);
            }
        }
    }

    static class s1036{//Escape a Large Maze
        public boolean isEscapePossible(int[][] blocked, int[] src, int[] target){
            Set<String> s = Arrays.stream(blocked).map(b -> b[0] + "," + b[1]).collect(Collectors.toSet());
            return bfs(src, target, new HashSet<>(s)) && bfs(target, src, new HashSet<>(s));
        }

        boolean bfs(int[] src, int[] target, Set<String> seen){
            int dirs[] = {-1, 0, 1, 0, -1}, blockedSize = seen.size();
            seen.add(src[0] + "," + src[1]);
            Queue<int[]> q = new LinkedList<>();
            for(q.offer(src); !q.isEmpty() && seen.size() < 20_000 + blockedSize; )
                for(int i = 1, sq[] = q.poll(); i < dirs.length; i++){
                    int x = sq[0] + dirs[i - 1], y = sq[1] + dirs[i];
                    if(0 <= x && x < 1_000_000 && 0 <= y && y < 1_000_000 && seen.add(x + "," + y)){
                        if(x == target[0] && y == target[1])
                            return true;
                        else q.offer(new int[]{x, y});
                    }
                }
            return seen.size() >= 20_000 + blockedSize;
        }
    }

    static class s1039{//Minimum Score Triangulation of Polygon
        public int minScoreTriangulation(int[] a){
            return score(a, 0, a.length - 1, new Integer[a.length][a.length]);
        }

        int score(int[] a, int i, int j, Integer[][] dp){
            if((j - i + 1) < 3)
                return 0;
            if(dp[i][j] != null)
                return dp[i][j];
            int min = Integer.MAX_VALUE;
            for(int k = i + 1; k < j; k++)
                min = Math.min(min, score(a, i, k, dp) + a[i] * a[k] * a[j] + score(a, k, j, dp));
            return dp[i][j] = min;
        }
    }

    static class s1042{//Flower Planting With No Adjacent
        public int[] gardenNoAdj(int n, int[][] paths){
            List<List<Integer>> g = new ArrayList<>(n);
            IntStream.range(0, n).forEach(i -> g.add(new ArrayList<>(3)));
            for(int[] p : paths){
                g.get(p[0] - 1).add(p[1] - 1);
                g.get(p[1] - 1).add(p[0] - 1);
            }
            int[] r = new int[n];
            for(int u = 0; u < n; u++){
                int[] colors = new int[5];
                g.get(u).forEach(v -> colors[r[v]] = 1);
                for(int c = 4; c >= 1; c--)
                    if(colors[c] == 0)
                        r[u] = c;
            }
            return r;
        }
    }

    static class s1049{//Last Stone Weight II
        public int lastStoneWeightII(int[] stones){
            boolean[] dp = new boolean[1501];
            dp[0] = true;
            int sum = 0;
            for(int stone : stones){
                sum += stone;
                for(int i = Math.min(sum, 1500); i >= stone; i--)
                    dp[i] |= dp[i - stone];
            }
            for(int i = sum / 2; i >= 0; i--)
                if(dp[i])
                    return sum - i - i;
            return 0;
        }
    }

    static class s1053{//Previous Permutation With One Swap
        public int[] prevPermOpt1(int[] a){
            int i = a.length - 2, j;
            for(; i >= 0 && a[i] <= a[i + 1]; i--) ;
            if(i >= 0){
                for(j = i + 1; j < a.length && a[i] > a[j]; j++) ;
                for(j = j - 1; a[j - 1] == a[j]; j--) ;
                int t = a[i];
                a[i] = a[j];
                a[j] = t;
            }
            return a;
        }
    }

    static class s1056{//Confusing Number
        public boolean confusingNumber(int num){
            int r = 0;
            Map<Integer, Integer> m = Map.of(0, 0, 1, 1, 6, 9, 8, 8, 9, 6);
            for(int n = num; n > 0; n /= 10)
                if(!m.containsKey(n % 10))
                    return false;
                else r = 10 * r + m.get(n % 10);
            return r != num;
        }
    }

    static class s1058{//Minimize Rounding Error to Meet Target
        public String minimizeError(String[] prices, int target){
            double r = 0;
            PriorityQueue<double[]> q = new PriorityQueue<>(Comparator.comparingDouble(a -> a[1]));
            for(String price : prices){
                double f = Double.parseDouble(price), floor = Math.floor(f), ceil = Math.ceil(f);
                if(floor != ceil)
                    q.offer(new double[]{f, ceil - f});
                r += f - floor;
                target -= floor;
            }
            if(target < 0 || target > q.size())
                return "-1";
            while(target-- > 0){
                double p[] = q.poll(), f = p[0], d = p[1];
                r = r - (f - Math.floor(f)) + d;
            }
            return String.format("%.3f", r);
        }
    }

    static class s1065{//Index Pairs of a String
        /**
         * Given a string text and an array of strings words, return an array of all index pairs [i, j]
         * so that the substring text[i...j] is in words. Return the pairs [i, j] in sorted order
         * (i.e., sort them by their first coordinate, and in case of ties sort them by their second coordinate).
         */
        public int[][] indexPairs(String text, String[] words){
            Node trie = new Node();
            for(String w : words){
                Node node = trie;
                for(char c : w.toCharArray()){
                    if(node.nodes[c - 'a'] == null)
                        node.nodes[c - 'a'] = new Node();
                    node = node.nodes[c - 'a'];
                }
                node.isWord = true;
            }
            List<int[]> r = new ArrayList<>();
            char[] s = text.toCharArray();
            for(int i = 0; i < text.length(); i++){
                Node node = trie;
                for(int j = i; j < s.length && node != null; j++){
                    node = node.nodes[s[j] - 'a'];
                    if(node != null && node.isWord)
                        r.add(new int[]{i, j});
                }
            }
            return r.toArray(new int[0][]);

        }

        class Node{
            Node[] nodes = new Node[26];
            boolean isWord;
        }
    }

    static class s1072{//Flip Columns For Maximum Number of Equal Rows
        public int maxEqualRowsAfterFlips(int[][] m){
            Map<String, Integer> counts = new HashMap<>();
            for(int[] row : m){
                String s1 = Arrays.stream(row).mapToObj(n -> String.valueOf(1 - n)).collect(Collectors.joining(""));
                String s2 = Arrays.stream(row).mapToObj(String::valueOf).collect(Collectors.joining(""));
                counts.put(s1, counts.getOrDefault(s1, 0) + 1);
                counts.put(s2, counts.getOrDefault(s2, 0) + 1);
            }
            return counts.values().stream().mapToInt(n -> n).max().getAsInt();
        }
    }

    static class s1086{//High Five
        public int[][] highFive(int[][] items){
            TreeMap<Integer, PriorityQueue<Integer>> m = new TreeMap<>();
            for(int[] item : items){
                m.putIfAbsent(item[0], new PriorityQueue<>());
                PriorityQueue<Integer> q = m.get(item[0]);
                q.offer(item[1]);
                if(q.size() > 5)
                    q.poll();
            }
            int r[][] = new int[m.size()][2], i = 0;
            for(Integer id : m.keySet())
                r[i++] = new int[]{id, m.get(id).stream().mapToInt(n -> n).sum() / 5};
            return r;
        }
    }

    static class s1099{//Two Sum Less Than K
        public int twoSumLessThanK(int[] a, int k){
            Arrays.sort(a);
            int r = -1;
            for(int i = 0, j = a.length - 1; i < j; ){
                int sum = a[i] + a[j];
                if(sum < k){
                    r = Math.max(sum, r);
                    i++;
                }else j--;
            }
            return r;
        }
    }
}
