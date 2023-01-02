package leetcode;
import leetcode.NTree.Node;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class p5{
    static class s500{//Keyboard Row
        public String[] findWords(String[] words) {
            List<String> r = new ArrayList<>(), rows = Arrays.asList("qwertyuiop", "asdfghjkl", "zxcvbnm");
            for (String w : words)
                for (String row : rows)
                    if (w.toLowerCase().chars().allMatch(c -> row.indexOf(c) >= 0))
                        r.add(w);
            return r.toArray(new String[0]);
        }
    }

    static class s507{//Perfect Number
        public boolean checkPerfectNumber(int n) {
            int sum = 1;
            for (int d = 2; d <= Math.sqrt(n); d++)
                if (n % d == 0)
                    sum = sum + n / d + d;
            return n > 1 && n == sum;
        }
    }

    static class s509{//Fibonacci Number
        public int fib(int n) {
            int a = 0, b = 1;
            while (n-- > 0) {
                int c = a + b;
                a = b;
                b = c;
            }
            return a;
        }
    }

    static class s520{//Detect Capital
        public boolean detectCapitalUse(String word) {
            long capCount = word.chars().filter(Character::isUpperCase).count();
            return capCount == 0 || capCount == word.length() || (capCount == 1 && Character.isUpperCase(word.charAt(0)));
        }

        public boolean detectCapitalUse1(String word) {
            return word.matches("[A-Z]+|[a-z]+|[A-Z][a-z]+");
        }
    }

    static class s521{//Longest Uncommon Subsequence I
        public int findLUSlength(String a, String b) {
            return a.equals(b) ? -1 : Math.max(a.length(), b.length());
        }
    }

    static class s522{//Longest Uncommon Subsequence II
        public int findLUSlength(String[] strs) {
            int r = -1;
            for (int i = 0; i < strs.length; i++) {
                final int k = i;
                if (IntStream.range(0, strs.length).filter(j -> k != j).noneMatch(j -> subsequence(strs[k], strs[j])))
                    r = Math.max(r, strs[i].length());
            }
            return r;
        }

        boolean subsequence(String s1, String s2) {
            int i = 0;
            for (int j = 0; j < s2.length() && i < s1.length(); j++)
                if (s1.charAt(i) == s2.charAt(j))
                    i++;
            return i == s1.length();
        }
    }

    static class s530{//Minimum Absolute Difference in BST
        Integer min = Integer.MAX_VALUE, prev = null;

        public int getMinimumDifference(TreeNode root) {
            if (root == null)
                return min;
            getMinimumDifference(root.left);
            if (prev != null)
                min = Math.min(min, root.val - prev);
            prev = root.val;
            getMinimumDifference(root.right);
            return min;
        }
    }

    static class s531{//Lonely Pixel I
        public int findLonelyPixel(char[][] picture) {
            int[] rows = new int[picture.length], cols = new int[picture[0].length];
            for (int i = 0; i < picture.length; i++)
                for (int j = 0; j < picture[0].length; j++)
                    if (picture[i][j] == 'B') {
                        rows[i]++;
                        cols[j]++;
                    }
            int r = 0;
            for (int i = 0; i < picture.length; i++)
                for (int j = 0; j < picture[0].length; j++)
                    if (picture[i][j] == 'B' && rows[i] == 1 && cols[j] == 1)
                        r++;
            return r;
        }
    }

    static class s535{//Encode and Decode TinyURL
        public class Codec{
            Map<String, String> index = new HashMap<>(), revIndex = new HashMap<>();
            static String BASE_HOST = "http://tinyurl.com/", CHAR_SET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

            public String encode(String longUrl) {
                String shortUrl = revIndex.get(longUrl);
                while (shortUrl == null) {
                    StringBuilder b = new StringBuilder();
                    for (int i = 0; i < 6; i++)
                        b.append(CHAR_SET.charAt(new Random().nextInt(CHAR_SET.length())));
                    if (!index.containsKey(b.toString())) {
                        shortUrl = b.toString();
                        index.put(shortUrl, longUrl);
                        revIndex.put(longUrl, shortUrl);
                    }
                }
                return BASE_HOST + shortUrl;
            }

            public String decode(String shortUrl) {
                return index.get(shortUrl.replace(BASE_HOST, ""));
            }
        }
    }

    static class s538{//Convert BST to Greater Tree
        int sum = 0;
        public TreeNode convertBST(TreeNode root) {
            if (root == null)
                return null;
            convertBST(root.right);
            root.val += sum;
            sum = root.val;
            convertBST(root.left);
            return root;
        }
    }

    static class s541{//Reverse String II
        public String reverseStr(String s, int k) {
            char[] a = s.toCharArray();
            for (int i = 0; i < s.length(); i += 2 * k)
                for (int lo = i, hi = Math.min(a.length - 1, i + k - 1); lo < hi; lo++, hi--) {
                    char t = a[lo];
                    a[lo] = a[hi];
                    a[hi] = t;
                }
            return new String(a);
        }
    }

    static class s542{//01 Matrix
        public int[][] updateMatrix(int[][] m) {
            Queue<int[]> q = new LinkedList<>();
            for (int i = 0; i < m.length; i++)
                for (int j = 0; j < m[0].length; j++)
                    if (m[i][j] == 0)
                        q.offer(new int[]{i, j});
                    else m[i][j] = -1;
            for (int[] dirs = {-1, 0, 1, 0, -1}; !q.isEmpty(); ) {
                int p[] = q.poll(), x = p[0], y = p[1];
                for (int d = 1; d < dirs.length; d++) {
                    int nx = x + dirs[d - 1], ny = y + dirs[d];
                    if (0 <= nx && nx < m.length && 0 <= ny && ny < m[0].length && m[nx][ny] == -1) {
                        m[nx][ny] = 1 + m[x][y];
                        q.offer(new int[]{nx, ny});
                    }
                }
            }
            return m;
        }
    }

    static class s543{//Diameter of Binary Tree
        int r = 0;
        public int diameterOfBinaryTree(TreeNode root) {
            depth(root);
            return r;
        }

        int depth(TreeNode node) {
            if (node == null)
                return 0;
            int left = depth(node.left), right = depth(node.right);
            r = Math.max(r, left + right);
            return 1 + Math.max(left, right);
        }
    }

    static class s559{//Maximum Depth of N-ary Tree
        public int maxDepth(Node root) {
            if (root == null)
                return 0;
            int r = 0;
            for (Node node : root.children)
                r = Math.max(r, maxDepth(node));
            return r + 1;
        }
    }

    static class s561{//Array Partition I
        public int arrayPairSum(int[] a) {
            Arrays.sort(a);
            int sum = 0;
            for (int i = 0; i < a.length; i += 2)
                sum += a[i];
            return sum;
        }
    }

    static class s563{//Binary Tree Tilt
        int r = 0;
        public int findTilt(TreeNode root) {
            traverse(root);
            return r;
        }

        int traverse(TreeNode node) {
            if (node == null)
                return 0;
            int left = traverse(node.left), right = traverse(node.right);
            r += Math.abs(left - right);
            return left + right + node.val;
        }
    }

    static class s565{//Array Nesting
        public int arrayNesting(int[] a) {
            int r = 0;
            boolean[] seen = new boolean[a.length];
            for (int i = 0; i < a.length; i++)
                if (!seen[i]) {
                    int len = 0;
                    for (int j = i; !seen[j]; len++, j = a[j])
                        seen[j] = true;
                    r = Math.max(r, len);
                }
            return r;
        }
    }

    static class s566{//Reshape the Matrix
        public int[][] matrixReshape(int[][] a, int r, int c) {
            if (r * c != a.length * a[0].length)
                return a;
            int res[][] = new int[r][c], m = a[0].length;
            for (int i = 0; i < r * c; i++)
                res[i / c][i % c] = a[i / m][i % m];
            return res;
        }
    }

    static class s572{//Subtree of Another Tree
        public boolean isSubtree(TreeNode root, TreeNode sub) {
            if (root == null)
                return false;
            return sub(root, sub) || isSubtree(root.left, sub) || isSubtree(root.right, sub);
        }

        boolean sub(TreeNode root, TreeNode sub) {
            if (root == null && sub == null)
                return true;
            if (root == null || sub == null)
                return false;
            return root.val == sub.val && sub(root.left, sub.left) && sub(root.right, sub.right);
        }
    }

    static class s575{//Distribute Candies
        public int distributeCandies(int[] candyType) {
            return Math.min(candyType.length / 2, (int) Arrays.stream(candyType).distinct().count());
        }
    }

    static class s581{//Shortest Unsorted Continuous Subarray
        public int findUnsortedSubarray(int[] a) {
            int[] max = new int[a.length], min = new int[a.length];
            max[0] = a[0];
            min[a.length - 1] = a[a.length - 1];
            for (int i = 1, j = a.length - 2; i < a.length; i++, j--) {
                max[i] = Math.max(max[i - 1], a[i]);
                min[j] = Math.min(min[j + 1], a[j]);
            }
            int lo = 0, hi = a.length - 1;
            for (; lo <= hi && a[lo] == min[lo]; lo++) ;
            for (; lo <= hi && a[hi] == max[hi]; hi--) ;
            return hi - lo + 1;
        }
    }

    static class s582{//Kill Process
        /**
         * You have n processes forming a rooted tree structure. You are given two integer arrays pid and ppid, where pid[i] is
         * the ID of the ith process and ppid[i] is the ID of the ith process's parent process. Each process has only one parent
         * process but may have multiple children processes. Only one process has ppid[i] = 0, which means this process has no
         * parent process (the root of the tree). When a process is killed, all of its children processes will also be killed.
         * Given an integer kill representing the ID of a process you want to kill, return a list of the IDs of the processes
         * that will be killed. You may return the answer in any order.
         */
        public List<Integer> killProcess(List<Integer> pid, List<Integer> ppid, int kill) {
            Map<Integer, List<Integer>> g = new HashMap<>();
            for (int i = 0; i < pid.size(); i++) {
                g.putIfAbsent(ppid.get(i), new ArrayList<>());
                g.get(ppid.get(i)).add(pid.get(i));
            }
            List<Integer> r = new ArrayList<>();
            dfs(kill, g, r);
            return r;
        }

        void dfs(int pid, Map<Integer, List<Integer>> g, List<Integer> r) {
            r.add(pid);
            if (g.containsKey(pid))
                for (Integer child : g.get(pid))
                    dfs(child, g, r);
        }

        public List<Integer> killProcess1(List<Integer> pid, List<Integer> ppid, int kill) {
            Map<Integer, List<Integer>> g = new HashMap<>();
            for (int i = 0; i < pid.size(); i++) {
                g.putIfAbsent(ppid.get(i), new ArrayList<>());
                g.get(ppid.get(i)).add(pid.get(i));
            }
            Queue<Integer> q = new LinkedList<>();
            List<Integer> r = new ArrayList<>();
            for (q.add(kill); !q.isEmpty(); ) {
                Integer u = q.poll();
                r.add(u);
                if (g.containsKey(u))
                    g.get(u).forEach(q::offer);
            }
            return r;
        }
    }

    static class s583{//Delete Operation for Two Strings
        public int minDistance(String a, String b) {
            int[][] dp = new int[a.length() + 1][b.length() + 1];
            for (int i = 0; i < a.length(); i++)
                for (int j = 0; j < b.length(); j++)
                    if (a.charAt(i) == b.charAt(j))
                        dp[i + 1][j + 1] = 1 + dp[i][j];
                    else dp[i + 1][j + 1] = Math.max(dp[i + 1][j], dp[i][j + 1]);
            return a.length() + b.length() - 2 * dp[a.length()][b.length()];
        }
    }

    static class s594{//Longest Harmonious Subsequence
        public int findLHS(int[] a) {
            Map<Integer, Integer> m = new HashMap<>();
            Arrays.stream(a).forEach(n -> m.put(n, m.getOrDefault(n, 0) + 1));
            int r = 0;
            for (Integer n : m.keySet())
                r = Math.max(r, m.get(n) + m.getOrDefault(n + 1, -m.get(n)));
            return r;
        }
    }

    static class s598{//Range Addition II
        public int maxCount(int m, int n, int[][] ops) {
            for (int[] op : ops) {
                m = Math.min(m, op[0]);
                n = Math.min(n, op[1]);
            }
            return m * n;
        }
    }

    static class s599{//Minimum Index Sum of Two Lists
        public String[] findRestaurant(String[] list1, String[] list2) {
            int minSum = Integer.MAX_VALUE;
            Map<String, Integer> list1m = IntStream.range(0, list1.length).boxed().collect(Collectors.toMap(i -> list1[i], i -> i));
            Map<Integer, List<String>> r = new HashMap<>();
            for (int i = 0; i < list2.length; i++)
                if (list1m.containsKey(list2[i])) {
                    int sum = list1m.get(list2[i]) + i;
                    r.computeIfAbsent(sum, l -> new ArrayList<>()).add(list2[i]);
                    minSum = Math.min(minSum, sum);
                }
            return r.get(minSum).toArray(new String[0]);
        }
    }
}
