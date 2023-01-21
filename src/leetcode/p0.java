package leetcode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class p0{
    static class s1{//Two Sum
        public int[] twoSum(int[] a, int t){
            int i = 0;
            Map<Integer, Integer> m = new HashMap<>();
            for(; i < a.length && !m.containsKey(t - a[i]); i++)
                m.put(a[i], i);
            return new int[]{m.get(t - a[i]), i};
        }
    }

    static class s3{//Longest Substring Without Repeating Characters
        public int lengthOfLongestSubstring(String str){
            int r = 0;
            boolean[] f = new boolean[256];
            for(int i = 0, j = 0; i < str.length(); i++){
                while(f[str.charAt(i)])
                    f[str.charAt(j++)] = false;
                f[str.charAt(i)] = true;
                r = Math.max(r, i - j + 1);
            }
            return r;
        }
    }

    static class s5{//Longest Palindromic Substring
        public String longestPalindrome(String s){
            int[] r = {0, 1};
            for(int i = 0; i < s.length(); i++){
                extend(s, i - 1, i + 1, r);
                extend(s, i - 1, i, r);
            }
            return s.substring(r[0], r[1]);
        }

        void extend(String s, int lo, int hi, int[] r){
            for(; 0 <= lo && hi < s.length() && s.charAt(lo) == s.charAt(hi); lo--, hi++) ;
            if(hi - lo - 1 > r[1] - r[0]){
                r[0] = lo + 1;
                r[1] = hi;
            }
        }
    }

    static class s11{//Container With Most Water
        public int maxArea(int[] heights){
            int r = 0;
            for(int lo = 0, hi = heights.length - 1; lo < hi; ){
                int minHeight = Math.min(heights[lo], heights[hi]);
                r = Math.max(r, (hi - lo) * minHeight);
                for(; heights[lo] <= minHeight && lo < hi; lo++) ;
                for(; heights[hi] <= minHeight && lo < hi; hi--) ;
            }
            return r;
        }
    }

    static class s17{//Letter Combinations of a Phone Number
        public List<String> letterCombinations(String digits){
            String[] m = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
            LinkedList<String> r = new LinkedList<>();
            r.add("");
            for(char d : digits.toCharArray())
                for(int size = r.size(); size > 0; size--){
                    String s = r.poll();
                    m[d - '0'].chars().forEach(c -> r.add(s + (char) c));
                }
            return r.size() == 1 ? Collections.emptyList() : r;
        }
    }

    static class s31{//Next Permutation
        public void nextPermutation(int[] a){
            int i = a.length - 1;
            for(; i > 0 && a[i - 1] >= a[i]; i--) ;
            if(i > 0){
                int j = a.length - 1;
                for(; i <= j && a[i - 1] >= a[j]; j--) ;
                swap(a, i - 1, j);
            }
            for(int j = a.length - 1; i < j; i++, j--)
                swap(a, i, j);
        }

        void swap(int[] a, int i, int j){
            int t = a[i];
            a[i] = a[j];
            a[j] = t;
        }
    }

    static class s32{//Longest Valid Parentheses
        public int longestValidParentheses(String str){
            boolean[] valid = new boolean[str.length()];
            Stack<Integer> s = new Stack<>();
            for(int i = 0; i < str.length(); i++)
                if(str.charAt(i) == '(')
                    s.push(i);
                else if(!s.isEmpty())
                    valid[s.pop()] = valid[i] = true;
            int r = 0, len = 0;
            for(boolean v : valid){
                len = v ? len + 1 : 0;
                r = Math.max(r, len);
            }
            return r;
        }
    }

    static class s47{//Permutations II
        public List<List<Integer>> permuteUnique(int[] a){
            List<List<Integer>> r = new LinkedList<>();
            Arrays.sort(a);
            dfs(a, new boolean[a.length], new LinkedList<>(), r);
            return r;
        }

        void dfs(int[] a, boolean[] used, LinkedList<Integer> curr, List<List<Integer>> r){
            if(curr.size() == a.length)
                r.add(new ArrayList<>(curr));
            else for(int i = 0; i < a.length; i++)
                if(!used[i]){
                    if(i > 0 && !used[i - 1] && a[i - 1] == a[i])
                        continue;
                    used[i] = true;
                    curr.add(a[i]);
                    dfs(a, used, curr, r);
                    curr.remove(curr.size() - 1);
                    used[i] = false;
                }
        }
    }

    static class s51{//N-Queens
        public List<List<String>> solveNQueens(int n){
            char[][] b = new char[n][n];
            IntStream.range(0, n).forEach(r -> Arrays.fill(b[r], '.'));
            List<List<String>> r = new ArrayList<>();
            place(b, 0, n, r);
            return r;
        }

        void place(char[][] b, int col, int n, List<List<String>> r){
            if(col == n)
                r.add(IntStream.range(0, n).mapToObj(i -> new String(b[i])).collect(Collectors.toList()));
            else for(int row = 0; row < n; row++)
                if(check(row, col, b)){
                    b[row][col] = 'Q';
                    place(b, col + 1, n, r);
                    b[row][col] = '.';
                }
        }

        boolean check(int row, int col, char[][] b){
            for(int r = 0; r < b.length; r++)
                for(int c = 0; c < col; c++)
                    if(b[r][c] == 'Q' && (r == row || row + c == col + r || row + col == c + r))
                        return false;
            return true;
        }
    }

    static class s52{//N-Queens II
        int r = 0;

        public int totalNQueens(int n){
            place(0, n, new char[n][n]);
            return r;
        }

        void place(int col, int n, char[][] b){
            if(col == n)
                r++;
            else for(int row = 0; row < n; row++)
                if(check(row, col, b)){
                    b[row][col] = 'q';
                    place(col + 1, n, b);
                    b[row][col] = '.';
                }
        }

        boolean check(int row, int col, char[][] b){
            for(int r = 0; r < b.length; r++)
                for(int c = 0; c < col; c++)
                    if(b[r][c] == 'q' && (r == row || r + c == row + col || row + c == col + r))
                        return false;
            return true;
        }
    }

    static class s53{//Maximum Subarray
        public int maxSubArray(int[] a){
            int r = a[0], sum = a[0];
            for(int i = 1; i < a.length; i++){
                sum = Math.max(a[i], sum + a[i]);
                r = Math.max(r, sum);
            }
            return r;
        }
    }

    static class s59{//Spiral Matrix II
        public int[][] generateMatrix(int n){
            int[][] m = new int[n][n];
            for(int r1 = 0, c1 = -1, r2 = n - 1, c2 = n - 1, v = 1; r1 <= r2 && c1 <= c2; ){
                for(int c = ++c1; c <= c2 && r1 <= r2; c++)
                    m[r1][c] = v++;
                for(int r = ++r1; r <= r2 && c1 <= c2; r++)
                    m[r][c2] = v++;
                for(int c = --c2; c >= c1 && r1 <= r2; c--)
                    m[r2][c] = v++;
                for(int r = --r2; r >= r1 && c1 <= c2; r--)
                    m[r][c1] = v++;
            }
            return m;
        }
    }

    static class s62{//Unique Paths
        public int uniquePaths(int m, int n){
            int[][] g = new int[m][n];
            IntStream.range(0, n).forEach(i -> g[0][i] = 1);
            IntStream.range(0, m).forEach(i -> g[i][0] = 1);
            for(int i = 1; i < m; i++)
                for(int j = 1; j < n; j++)
                    g[i][j] = g[i - 1][j] + g[i][j - 1];
            return g[m - 1][n - 1];
        }
    }

    static class s63{//Unique Paths II
        public int uniquePathsWithObstacles(int[][] g){
            int[][] dp = new int[g.length][g[0].length];
            dp[0][0] = 1 - g[0][0];
            for(int i = 1; i < g[0].length; i++)
                dp[0][i] = g[0][i] == 1 ? 0 : dp[0][i - 1];
            for(int i = 1; i < g.length; i++)
                dp[i][0] = g[i][0] == 1 ? 0 : dp[i - 1][0];
            for(int i = 1; i < g.length; i++)
                for(int j = 1; j < g[0].length; j++)
                    dp[i][j] = g[i][j] == 1 ? 0 : dp[i - 1][j] + dp[i][j - 1];
            return dp[g.length - 1][g[0].length - 1];
        }
    }

    static class s69{//Sqrt(x)
        public int mySqrt(int x){
            if(x <= 1)
                return x;
            long lo = 1, hi = x / 2, r = 0;
            while(lo <= hi){
                long mid = (lo + hi) / 2;
                if(mid * mid == x)
                    return (int) mid;
                if(mid * mid < x){
                    r = mid;
                    lo = mid + 1;
                }else hi = mid - 1;
            }
            return (int) r;
        }
    }

    static class s88{//Merge Sorted Array
        public void merge(int[] a1, int m, int[] a2, int n){
            for(int i1 = m - 1, i2 = n - 1, j = a1.length - 1; j >= 0; j--)
                if(i1 >= 0 && i2 >= 0){
                    if(a1[i1] >= a2[i2])
                        a1[j] = a1[i1--];
                    else a1[j] = a2[i2--];
                }else if(i1 >= 0)
                    a1[j] = a1[i1--];
                else a1[j] = a2[i2--];
        }
    }

    static class s93{//Restore IP Addresses
        public List<String> restoreIpAddresses(String s) {
            if (s.length() < 4 || s.length() > 12)
                return Collections.emptyList();
            List<String> r = new ArrayList<>();
            dfs(s, new LinkedList<>(), r);
            return r;
        }

        void dfs(String s, LinkedList<String> parts, List<String> r) {
            if (s.isEmpty()) {
                if (parts.size() == 4)
                    r.add(String.join(".", parts));
            } else for (int i = 1; i <= (s.charAt(0) == '0' ? 1 : 3) && i <= s.length(); i++) {
                Integer p = Integer.parseInt(s.substring(0, i));
                if (p <= 255) {
                    parts.add(String.valueOf(p));
                    dfs(s.substring(i), parts, r);
                    parts.removeLast();
                }
            }
        }
    }

    static class s99{//Recover Binary Search Tree
        TreeNode a, b, pre = new TreeNode(Integer.MIN_VALUE);

        public void recoverTree(TreeNode root){
            traverse(root);
            int t = a.val;
            a.val = b.val;
            b.val = t;
        }

        void traverse(TreeNode node){
            if(node != null){
                traverse(node.left);
                if(a == null && pre.val > node.val)
                    a = pre;
                if(a != null && pre.val > node.val)
                    b = node;
                pre = node;
                traverse(node.right);
            }
        }
    }
}

