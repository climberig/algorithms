package leetcode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    static class s1416{//Restore The Array
        public int numberOfArrays(String s, int k){
            return dfs(s, k, 0, new Integer[s.length()]);
        }

        int dfs(String s, int k, int i, Integer[] dp){
            if(i == s.length())
                return 1;
            if(s.charAt(i) == '0')
                return 0;
            if(dp[i] != null)
                return dp[i];
            int r = 0;
            long n = 0;
            for(int j = i; j < s.length() && n <= k; j++){
                n = n * 10 + s.charAt(j) - '0';
                if(n <= k)
                    r = (r + dfs(s, k, j + 1, dp)) % 1_000_000_007;
            }
            return dp[i] = r;
        }
    }

    static class s1426{//Counting Elements
        /**
         * Given an integer array arr, count how many elements x there are, such that x + 1 is also in arr. If there are duplicates in arr,
         * count them separately.
         */
        public int countElements(int[] a){
            int f[] = new int[1_001];
            Arrays.stream(a).forEach(n -> f[n]++);
            return IntStream.range(1, f.length).filter(i -> f[i] > 0).map(i -> f[i - 1]).sum();
        }
    }

    static class s1427{//Perform String Shifts
        public String stringShift(String s, int[][] shifts){
            int shift = 0;
            for(int[] sh : shifts)
                shift += sh[0] == 0 ? sh[1] : -sh[1];
            shift = (s.length() + shift % s.length()) % s.length();
            return s.substring(shift) + s.substring(0, shift);
        }
    }

    static class s1432{// Max Difference You Can Get From Changing an Integer
        public int maxDiff(int num){
            String s = String.valueOf(num);
            StringBuilder hi = new StringBuilder(s.length());
            for(int i = 0; i < s.length(); i++)
                if(s.charAt(i) != '9'){
                    hi.append('9');
                    for(int j = i + 1; j < s.length(); j++)
                        if(s.charAt(i) == s.charAt(j))
                            hi.append('9');
                        else hi.append(s.charAt(j));
                    break;
                }else hi.append(s.charAt(i));
            int i = 0;
            if(s.charAt(0) != '1')
                s = s.replace(s.charAt(0), '1');
            else{
                for(; i < s.length() && (s.charAt(i) == '1' || s.charAt(i) == '0'); i++) ;
                if(i < s.length())
                    s = s.replace(s.charAt(i), '0');
            }
            return Integer.parseInt(hi.toString()) - Integer.parseInt(s);
        }
    }

    static class s1449{//Form Largest Integer With Digits That Add up to Target
        Map<Integer, String> dp = new HashMap<>();
        public String largestNumber(int[] cost, int target){
            if(target == 0)
                return "";
            if(dp.containsKey(target))
                return dp.get(target);
            String r = "0";
            for(int d = 9; d >= 1; d--)
                if(target - cost[d - 1] >= 0){
                    String s = largestNumber(cost, target - cost[d - 1]);
                    if(!s.equals("0") && (r.equals("0") || s.length() + 1 > r.length()))
                        r = d + s;
                }
            dp.put(target, r);
            return r;
        }
    }

    static class s1458{//Max Dot Product of Two Subsequences
        public int maxDotProduct(int[] a1, int[] a2){
            int n = a1.length, m = a2.length, dp[][] = new int[n][m];
            for(int i = 0; i < n; i++)
                for(int j = 0; j < m; j++){
                    dp[i][j] = a1[i] * a2[j] + (i > 0 && j > 0 ? Math.max(dp[i - 1][j - 1], 0) : 0);
                    if(i > 0)
                        dp[i][j] = Math.max(dp[i][j], dp[i - 1][j]);
                    if(j > 0)
                        dp[i][j] = Math.max(dp[i][j], dp[i][j - 1]);
                }
            return dp[n - 1][m - 1];
        }
    }

    static class s1461{//Check If a String Contains All Binary Codes of Size K
        public boolean hasAllCodes(String s, int k){
            Set<String> codes = new HashSet<>();
            for(int i = k; i <= s.length() && codes.size() < 1 << k; i++)
                codes.add(s.substring(i - k, i));
            return codes.size() == 1 << k;
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

    static class s1469{//Find All The Lonely Nodes
        /**
         * In a binary tree, a lonely node is a node that is the only child of its parent node.
         * The root of the tree is not lonely because it does not have a parent node.
         */
        public List<Integer> getLonelyNodes(TreeNode root){
            List<Integer> r = new ArrayList<>();
            dfs(root, false, r);
            return r;
        }

        void dfs(TreeNode node, boolean lonely, List<Integer> r){
            if(node != null){
                if(lonely)
                    r.add(node.val);
                dfs(node.left, node.right == null, r);
                dfs(node.right, node.left == null, r);
            }
        }
    }

    static class s1477{//Find Two Non-overlapping Sub-arrays Each With Target Sum
        public int minSumOfLengths(int[] a, int target){
            Map<Integer, Integer> m1 = new HashMap<>(), m2 = new HashMap<>();
            m1.put(0, -1);
            int minLen = Integer.MAX_VALUE, min[] = new int[a.length], r = Integer.MAX_VALUE;
            for(int i = 0, sum = 0; i < a.length; i++){
                sum += a[i];
                if(m1.containsKey(sum - target))
                    minLen = Math.min(minLen, i - m1.get(sum - target));
                min[i] = minLen;
                m1.put(sum, i);
            }
            m2.put(0, a.length);
            for(int i = a.length - 1, sum = 0; i > 0; i--){
                sum += a[i];
                if(m2.containsKey(sum - target) && min[i - 1] != Integer.MAX_VALUE)
                    r = Math.min(r, min[i - 1] + m2.get(sum - target) - i);
                m2.put(sum, i);
            }
            return r == Integer.MAX_VALUE ? -1 : r;
        }
    }

    static class s1478{//Allocate Mailboxes
        public int minDistance(int[] houses, int k){
            Arrays.sort(houses);
            int r = dfs(houses, 0, k, new Integer[houses.length + 1][k + 1]);
            return r == Integer.MAX_VALUE ? -1 : r;
        }

        int dfs(int[] houses, int start, int k, Integer[][] dp){
            if(k == 0 || start >= houses.length)
                return Integer.MAX_VALUE;
            if(k == 1)
                return distance(houses, start, houses.length - 1);
            if(dp[start][k] != null)
                return dp[start][k];
            int r = Integer.MAX_VALUE;
            for(int i = start; i <= houses.length - k; i++){
                int d1 = distance(houses, start, i), r1 = dfs(houses, i + 1, k - 1, dp);
                if(d1 < Integer.MAX_VALUE && r1 < Integer.MAX_VALUE)
                    r = Integer.min(r, d1 + r1);
            }
            return dp[start][k] = r;
        }

        int distance(int[] houses, int lo, int hi){
            int m = houses[lo + (hi - lo) / 2], r = 0;
            for(int i = lo; i <= hi; i++)
                r += Math.abs(houses[i] - m);
            return r;
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
        public int minimumTime(String s){
            int r = s.length();
            for(int i = 0, dp = 0; i < s.length(); i++){
                dp = Math.min((s.charAt(i) - '0') * 2 + dp, i + 1);
                r = Math.min(r, dp + s.length() - i - 1);
            }
            return r;
        }
    }
}
