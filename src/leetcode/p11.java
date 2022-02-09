package leetcode;
import java.util.*;
import java.util.stream.*;

public class p11{
    static class s1108{//Defanging an IP Address
        public String defangIPaddr(String address){return address.replace(".", "[.]");}
    }

    static class s1119{//Remove Vowels from a String
        public String removeVowels(String s){
            Set<Character> vowels = Set.of('a', 'e', 'i', 'o', 'u');
            StringBuilder r = new StringBuilder();
            for(char c : s.toCharArray())
                if(!vowels.contains(c))
                    r.append(c);
            return r.toString();
        }

        public String removeVowels1(String s){
            return s.replaceAll("[aeiou]", "");
        }
    }

    static class s1129{//Shortest Path with Alternating Colors
        public int[] shortestAlternatingPaths(int n, int[][] redEdges, int[][] blueEdges){
            int r[] = new int[n];
            Arrays.fill(r, Integer.MAX_VALUE);
            List<List<int[]>> g = IntStream.range(0, n).mapToObj(i -> new ArrayList<int[]>()).collect(Collectors.toList());
            Arrays.stream(redEdges).forEach(e -> g.get(e[0]).add(new int[]{e[1], 0, 0}));
            Arrays.stream(blueEdges).forEach(e -> g.get(e[0]).add(new int[]{e[1], 1, 0}));
            Queue<int[]> q = new LinkedList<>();
            boolean[][] seen = new boolean[n][2];
            for(q.add(new int[]{0, -1, 0}), Arrays.fill(seen[0], true); !q.isEmpty(); ){
                int[] u = q.poll();
                r[u[0]] = Math.min(r[u[0]], u[2]);
                for(int[] v : g.get(u[0]))
                    if(!seen[v[0]][v[1]] && u[1] != v[1]){
                        seen[v[0]][v[1]] = true;
                        q.offer(new int[]{v[0], v[1], u[2] + 1});
                    }
            }
            return Arrays.stream(r).map(v -> v == Integer.MAX_VALUE ? -1 : v).toArray();
        }
    }

    static class s1133{//Largest Unique Number
        public int largestUniqueNumber(int[] a){
            int[] counts = new int[1_001];
            Arrays.stream(a).forEach(n -> counts[n]++);
            for(int n = counts.length - 1; n >= 0; n--)
                if(counts[n] == 1)
                    return n;
            return -1;
        }
    }

    static class s1134{//Armstrong Number
        public boolean isArmstrong(int n){
            int pow = (int) Math.log10(n) + 1, r = 0;
            for(int m = n; m > 0; m /= 10)
                r += Math.pow(m % 10, pow);
            return r == n;
        }
    }

    static class s1140{//Stone Game II
        public int stoneGameII(int[] piles){
            int[] cs = Arrays.copyOf(piles, piles.length);
            for(int i = cs.length - 2; i >= 0; i--)
                cs[i] += cs[i + 1];
            return dfs(cs, 1, 0, new int[piles.length][piles.length]);
        }
        int dfs(int[] cs, int m, int idx, int[][] dp){
            if(idx + 2 * m >= cs.length)
                return cs[idx];
            if(dp[idx][m] > 0)
                return dp[idx][m];
            int r = 0, take;
            for(int i = 1; i <= 2 * m; i++){
                take = cs[idx] - cs[idx + i];
                r = Math.max(r, take + cs[idx + i] - dfs(cs, Math.max(i, m), idx + i, dp));
            }
            return dp[idx][m] = r;
        }
    }

    static class s1143{//Longest Common Subsequence
        public int longestCommonSubsequence(String text1, String text2){
            int[][] dp = new int[text1.length() + 1][text2.length() + 1];
            for(int i = 0; i < text1.length(); i++)
                for(int j = 0; j < text2.length(); j++)
                    if(text1.charAt(i) == text2.charAt(j))
                        dp[i + 1][j + 1] = 1 + dp[i][j];
                    else dp[i + 1][j + 1] = Math.max(dp[i + 1][j], dp[i][j + 1]);
            return dp[text1.length()][text2.length()];
        }
    }

    static class s1144{//Decrease Elements To Make Array Zigzag
        public int movesToMakeZigzag(int[] a){return Math.min(zig(a, 1), zig(a, -1));}

        int zig(int[] a, int sign){
            int moves = 0;
            for(int i = 1, prev = a[0]; i < a.length; i++, sign = -sign)
                if(sign > 0){
                    moves += prev >= a[i] ? prev - a[i] + 1 : 0;
                    prev = a[i];
                }else if(sign < 0){
                    int move = prev <= a[i] ? a[i] - prev + 1 : 0;
                    prev = a[i] - move;
                    moves += move;
                }
            return moves;
        }
    }

    static class s1145{//Binary Tree Coloring Game
        public boolean btreeGameWinningMove(TreeNode root, int n, int x){
            int[] lr = new int[2];
            count(root, x, lr);
            return Math.max(n - lr[0] - lr[1] - 1, Math.max(lr[0], lr[1])) > n / 2;
        }

        int count(TreeNode node, int val, int[] lr){
            if(node == null)
                return 0;
            int left = count(node.left, val, lr), right = count(node.right, val, lr);
            if(node.val == val){
                lr[0] = left;
                lr[1] = right;
            }
            return 1 + left + right;
        }
    }

    static class s1147{//Longest Chunked Palindrome Decomposition
        public int longestDecomposition(String text){
            return longest(text.toCharArray(), 0, text.length() - 1);
        }

        int longest(char[] a, int left, int right){
            for(int i = 0; left + i < right - i; i++)
                if(match(a, left, right, i))
                    return 2 + longest(a, left + i + 1, right - i - 1);
            return left > right ? 0 : 1;
        }

        boolean match(char[] a, int left, int right, int i){
            for(int lo = left, hi = right - i; hi <= right; lo++, hi++)
                if(a[lo] != a[hi])
                    return false;
            return true;
        }
    }

    static class s1151{//Minimum Swaps to Group All 1's Together
        public int minSwaps(int[] a){
            int width = Arrays.stream(a).sum(), ones = Arrays.stream(a, 0, width).sum(), maxOnes = ones;
            for(int i = width; i < a.length; i++){
                ones += a[i] - a[i - width];
                maxOnes = Math.max(maxOnes, ones);
            }
            return width - maxOnes;
        }
    }

    static class s1155{//Number of Dice Rolls With Target Sum
        public int numRollsToTarget(int D, int F, int target){
            long[][] dp = new long[D + 1][target + 1];
            dp[0][0] = 1;
            for(int d = 1; d <= D; d++)
                for(int t = 0; t <= target; t++)
                    for(int f = 1; f <= F && f <= t; f++)
                        dp[d][t] = (dp[d][t] + dp[d - 1][t - f]) % 1_000_000_007;
            return (int) dp[D][target];
        }
    }

    static class s1165{//Single-Row Keyboard
        public int calculateTime(String keyboard, String word){
            int layout[] = new int[26], r = 0;
            for(int i = 0; i < keyboard.length(); i++)
                layout[keyboard.charAt(i) - 'a'] = i;
            for(int i = 0, j = 0; i < word.length(); i++){
                r += Math.abs(layout[word.charAt(i) - 'a'] - j);
                j = layout[word.charAt(i) - 'a'];
            }
            return r;
        }
    }

    static class s1177{//Can Make Palindrome from Substring
        public List<Boolean> canMakePaliQueries(String s, int[][] queries){
            int[] f = new int[s.length() + 1];
            for(int i = 0; i < s.length(); i++)
                f[i + 1] = f[i] ^ (1 << (s.charAt(i) - 'a'));
            return Arrays.stream(queries).map(q -> Integer.bitCount(f[q[1] + 1] ^ f[q[0]]) / 2 <= q[2]).collect(Collectors.toList());
        }
    }

    static class s1191{//K-Concatenation Maximum Sum
        public int kConcatenationMaxSum(int[] a, int k){
            long r = 0, sum = Arrays.stream(a).mapToLong(i -> i).sum(), right = max(a.length - 1, -1, a), left = max(0, 1, a);
            if(sum > 0)
                r = Math.max(sum * k, r);
            if(k > 2)
                r = Math.max(r, right + (k - 2) * sum + left);
            sum = 0;
            for(int n : a){
                sum = Math.max(n, sum + n);
                r = Math.max(r, sum);
            }
            if(k == 1)
                return (int) (r % 1_000_000_007);
            return (int) (Math.max(r, left + right) % 1_000_000_007);
        }

        long max(int start, int d, int[] a){
            long r = 0, sum = 0;
            for(int i = start; 0 <= i && i < a.length; i += d){
                sum += a[i];
                r = Math.max(r, sum);
            }
            return r;
        }
    }

    static class s1196{//How Many Apples Can You Put into the Basket
        public int maxNumberOfApples(int[] weight){
            Arrays.sort(weight);
            int i = 0;
            for(int cap = 5_000; i < weight.length && weight[i] <= cap; i++)
                cap -= weight[i];
            return i;
        }
    }

    static class s1199{//Minimum Time to Build Blocks
        public int minBuildTime(int[] blocks, int split){
            PriorityQueue<Integer> q = new PriorityQueue<>();
            Arrays.stream(blocks).forEach(q::offer);
            while(q.size() > 1){
                int a = q.poll(), b = q.poll();
                q.offer(split + b);
            }
            return q.poll();
        }
    }
}
