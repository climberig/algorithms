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

    static class s1411{//Number of Ways to Paint N × 3 Grid
        public int numOfWays(int n){
            long a121 = 6, a123 = 6, b121, b123, mod = 1_000_000_007;
            for(int i = 1; i < n; ++i){
                b121 = a121 * 3 + a123 * 2;
                b123 = a121 * 2 + a123 * 2;
                a121 = b121 % mod;
                a123 = b123 % mod;
            }
            return (int) ((a121 + a123) % mod);
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

    static class s1426{//Counting Elements
        public int countElements(int[] a){
            int f[] = new int[1_001];
            Arrays.stream(a).forEach(n -> f[n]++);
            return IntStream.range(1, f.length).filter(i -> f[i] > 0).map(i -> f[i - 1]).sum();
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

    static class s1465{//Maximum Area of a Piece of Cake After Horizontal and Vertical Cuts
        public int maxArea(int h, int w, int[] horizontalCuts, int[] verticalCuts){
            Arrays.sort(horizontalCuts);
            Arrays.sort(verticalCuts);
            int maxH = Math.max(horizontalCuts[0], h - horizontalCuts[horizontalCuts.length - 1]);
            int maxV = Math.max(verticalCuts[0], w - verticalCuts[verticalCuts.length - 1]);
            for(int i = 1; i < horizontalCuts.length; i++)
                maxH = Math.max(maxH, horizontalCuts[i] - horizontalCuts[i - 1]);
            for(int i = 1; i < verticalCuts.length; i++)
                maxV = Math.max(maxV, verticalCuts[i] - verticalCuts[i - 1]);
            return (int) (((long) maxH * maxV) % 1_000_000_007);
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

    static class s1482{//Minimum Number of Days to Make m Bouquets
        public int minDays(int[] bloomDays, int m, int k){
            int lo = 1, hi = 1_000_000_000, r = 0;
            while(lo <= hi){
                int mid = (lo + hi) / 2, n = nOfBouquets(mid, bloomDays, k);
                if(n >= m){
                    r = mid;
                    hi = mid - 1;
                }else lo = mid + 1;
            }
            return r > 0 ? r : -1;
        }

        int nOfBouquets(int maxDay, int[] bloomDays, int k){
            int adjacent = 0, bouquets = 0;
            for(int day : bloomDays)
                if(day > maxDay){
                    bouquets += adjacent / k;
                    adjacent = 0;
                }else adjacent++;
            return bouquets + adjacent / k;
        }
    }

    static class s1497{//Check If Array Pairs Are Divisible by k
        public boolean canArrange(int[] a, int k){
            int[] counts = new int[k];
            for(int n : a){
                n %= k;
                n += n < 0 ? k : 0;
                counts[n]++;
            }
            if(counts[0] % 2 != 0)
                return false;
            for(int i = 1; i <= k / 2; i++)
                if(counts[i] != counts[k - i])
                    return false;
            return true;
        }
    }

    static class s1498{//Number of Subsequences That Satisfy the Given Sum Condition
        public int numSubseq(int[] a, int target){
            Arrays.sort(a);
            int r = 0, n = a.length, lo = 0, hi = n - 1, mod = 1_000_000_007, pow[] = new int[n];
            pow[0] = 1;
            for(int i = 1; i < n; ++i)
                pow[i] = pow[i - 1] * 2 % mod;
            while(lo <= hi)
                if(a[lo] + a[hi] > target)
                    hi--;
                else r = (r + pow[hi - lo++]) % mod;
            return r;
        }
    }
}
