package leetcode;

import java.util.*;
import java.util.stream.*;

public class p11{
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

    static class s1177{//Can Make Palindrome from Substring
        public List<Boolean> canMakePaliQueries(String s, int[][] queries){
            int[] f = new int[s.length() + 1];
            for(int i = 0; i < s.length(); i++)
                f[i + 1] = f[i] ^ (1 << (s.charAt(i) - 'a'));
            return Arrays.stream(queries).map(q -> Integer.bitCount(f[q[1] + 1] ^ f[q[0]]) / 2 <= q[2]).collect(Collectors.toList());
        }
    }
}
