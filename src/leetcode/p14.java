package leetcode;

import java.util.*;
import java.util.stream.*;

public class p14{
    static class s1400{//Construct K Palindrome Strings
        public boolean canConstruct(String s, int k){
            int odd = 0, count[] = new int[26];
            for(int i = 0; i < s.length(); i++){
                count[s.charAt(i) - 'a'] ^= 1;
                odd += count[s.charAt(i) - 'a'] > 0 ? 1 : -1;
            }
            return odd <= k && k <= s.length();
        }
    }

    static class s1405{//Longest Happy String
        public String longestDiverseString(int a, int b, int c){
            return gen(a, b, c, "a", "b", "c");
        }

        String gen(int a, int b, int c, String s1, String s2, String s3){
            if(a < b)
                return gen(b, a, c, s2, s1, s3);
            if(b < c)
                return gen(a, c, b, s1, s3, s2);
            if(b == 0)
                return s1.repeat(Math.min(2, a));
            int aUse = Math.min(2, a), bUse = a - aUse >= b ? 1 : 0;
            return s1.repeat(aUse) + s2.repeat(bUse) + gen(a - aUse, b - bUse, c, s1, s2, s3);
        }
    }

    static class s1415{//The k-th Lexicographical String of All Happy Strings of Length n
        public String getHappyString(int n, int k){
            Queue<String> q = new LinkedList<>();
            for(q.add(""); !q.isEmpty(); ){
                String s = q.poll();
                if(s.length() < n){
                    for(char c = 'a', prevC = !s.isEmpty() ? s.charAt(s.length() - 1) : ' '; c <= 'c'; c++)
                        if(c != prevC)
                            q.offer(s + c);
                }else if(s.length() == n && --k == 0)
                    return s;
            }
            return "";
        }
    }

    static class s1462{//Course Schedule IV
        public List<Boolean> checkIfPrerequisite(int n, int[][] prerequisites, int[][] queries){
            List<List<Integer>> g = IntStream.range(0, n).mapToObj(i -> new ArrayList<Integer>()).collect(Collectors.toList());
            Arrays.stream(prerequisites).forEach(p -> g.get(p[1]).add(p[0]));
            boolean[][] pre = new boolean[n][n];
            IntStream.range(0, n).forEach(u -> dfs(u, new boolean[n], g, pre[u]));
            return Arrays.stream(queries).map(q -> pre[q[1]][q[0]]).collect(Collectors.toList());
        }

        void dfs(int u, boolean[] seen, List<List<Integer>> g, boolean[] r){
            r[u] = seen[u] = true;
            g.get(u).stream().filter(v -> !seen[v]).forEach(v -> dfs(v, seen, g, r));
        }
    }

    static class s1463{//Cherry Pickup II
        public int cherryPickup(int[][] g){
            int m = g.length, n = g[0].length, dirs[] = {-1, 0, 1, 0, -1};
            return dfs(g, m, n, 0, 0, n - 1, new Integer[m][n][n], dirs);
        }

        int dfs(int[][] grid, int m, int n, int r, int c1, int c2, Integer[][][] dp, int[] dirs){
            if(r == m)
                return 0;
            if(dp[r][c1][c2] != null)
                return dp[r][c1][c2];
            int max = 0;
            for(int i = -1; i <= 1; i++)
                for(int j = -1; j <= 1; j++){
                    int nc1 = c1 + i, nc2 = c2 + j;
                    if(0 <= nc1 && nc1 < n && 0 <= nc2 && nc2 < n)
                        max = Math.max(max, dfs(grid, m, n, r + 1, nc1, nc2, dp, dirs));
                }
            int cherries = c1 == c2 ? grid[r][c1] : grid[r][c1] + grid[r][c2];
            return dp[r][c1][c2] = max + cherries;
        }
    }

    static class s1477{//Find Two Non-overlapping Sub-arrays Each With Target Sum
        public boolean hasAllCodes(String s, int k){
            Set<String> codes = new HashSet<>();
            for(int i = k; i <= s.length() && codes.size() < 1 << k; i++)
                codes.add(s.substring(i - k, i));
            return codes.size() == 1 << k;
        }
    }
}