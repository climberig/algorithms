package leetcode;
import java.util.*;
import java.util.stream.*;

public class p16{
    static class s1644{//Lowest Common Ancestor of a Binary Tree II
        int count = 0;

        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q){
            TreeNode lca = lca(root, p, q);
            return count == 2 ? lca : null;
        }

        TreeNode lca(TreeNode root, TreeNode p, TreeNode q){
            if(root == null)
                return null;
            TreeNode L = lca(root.left, p, q), R = lca(root.right, p, q);
            if(root == p || root == q){
                count++;
                return root;
            }
            return L == null ? R : R == null ? L : root;
        }
    }

    static class s1653{//Minimum Deletions to Make String Balanced
        public int minimumDeletions(String s){
            int[] dp = new int[s.length() + 1];//min chars to remove to make s[0:i] valid
            for(int i = 0, bCount = 0; i < s.length(); i++)
                if(s.charAt(i) == 'b'){
                    dp[i + 1] = dp[i];
                    bCount++;
                }else dp[i + 1] = Math.min(dp[i] + 1, bCount);
            return dp[s.length()];
        }
    }

    static class s1654{//Minimum Jumps to Reach Home
        public int minimumJumps(int[] forbidden, int a, int b, int x){
            Set<Integer> seen = Arrays.stream(forbidden).boxed().collect(Collectors.toSet()), notAllowed = new HashSet<>(seen);
            Queue<int[]> q = new LinkedList<>();
            for(seen.add(0), q.add(new int[]{0, 1, 0}); !q.isEmpty(); ){
                int[] p = q.poll();
                if(p[0] == x)
                    return p[2];
                if(seen.add(p[0] + a) && p[0] <= 2000 + b)
                    q.add(new int[]{p[0] + a, 1, p[2] + 1});
                if(p[1] == 1 && p[0] - b > 0 && !notAllowed.contains(p[0] - b))
                    q.add(new int[]{p[0] - b, -1, p[2] + 1});
            }
            return -1;
        }
    }

    static class s1664{//Ways to Make a Fair Array
        public int waysToMakeFair(int[] a){
            int r = 0, L[] = new int[2], R[] = new int[2];
            for(int i = 0; i < a.length; i++)
                R[i % 2] += a[i];
            for(int i = 0; i < a.length; i++){
                R[i % 2] -= a[i];
                if(L[0] + R[1] == L[1] + R[0])
                    r++;
                L[i % 2] += a[i];
            }
            return r;
        }
    }

    static class s1666{//Change the Root of a Binary Tree
        Node originRoot;

        public Node flipBinaryTree(Node root, Node leaf){
            originRoot = root;
            return flip(leaf, null);
        }

        Node flip(Node node, Node newParent){
            Node oldParent = node.parent;
            node.parent = newParent;
            if(node.left == newParent)
                node.left = null;
            if(node.right == newParent)
                node.right = null;
            if(node == originRoot)
                return node;
            if(node.left != null)
                node.right = node.left;
            node.left = flip(oldParent, node);
            return node;
        }

        class Node{
            int val;
            Node left, right, parent;
        }
    }

    static class s1670{//Design Front Middle Back Queue
        class FrontMiddleBackQueue{
            LinkedList<Integer> front = new LinkedList<>(), back = new LinkedList<>();

            public void pushFront(int val){
                ifFrontBigger();
                front.addFirst(val);
            }

            public void pushMiddle(int val){
                ifFrontBigger();
                front.addLast(val);
            }

            public void pushBack(int val){
                back.addLast(val);
                ifBackBigger();
            }

            public int popFront(){
                if(front.isEmpty())
                    return -1;
                int val = front.removeFirst();
                ifBackBigger();
                return val;
            }

            public int popMiddle(){
                if(front.isEmpty())
                    return -1;
                int val = front.removeLast();
                ifBackBigger();
                return val;
            }

            public int popBack(){
                if(front.isEmpty())
                    return -1;
                ifFrontBigger();
                return back.removeLast();
            }

            void ifBackBigger(){
                if(back.size() > front.size())
                    front.addLast(back.pollFirst());
            }

            void ifFrontBigger(){
                if(front.size() > back.size())
                    back.addFirst(front.removeLast());
            }
        }
    }

    static class s1673{//Find the Most Competitive Subsequence
        public int[] mostCompetitive(int[] a, int k){
            Stack<Integer> s = new Stack<>();
            for(int i = 0; i < a.length; i++){
                while(!s.isEmpty() && a[i] < s.peek() && a.length - i + s.size() > k)
                    s.pop();
                if(s.size() < k)
                    s.push(a[i]);
            }
            return s.stream().mapToInt(i -> i).toArray();
        }
    }

    static class s1679{//Max Number of K-Sum Pairs
        public int maxOperations(int[] a, int k){
            int r = 0;
            Arrays.sort(a);
            for(int i = 0, j = a.length - 1; i < j; )
                if(a[i] + a[j] == k){
                    i++;
                    j--;
                    r++;
                }else if(a[i] + a[j] < k)
                    i++;
                else j--;
            return r;
        }
    }

    static class s1680{//Concatenation of Consecutive Binary Numbers
        public int concatenatedBinary(int n){
            long r = 0;
            for(int i = 1; i <= n; i++){
                String b = Integer.toBinaryString(i);
                r = ((r << b.length()) + i) % 1_000_000_007;
            }
            return (int) r;
        }
    }

    static class s1682{// Longest Palindromic Subsequence II
        public int longestPalindromeSubseq(String s){
            return longest(s.toCharArray(), 0, s.length() - 1, 0, new Integer[s.length()][s.length()][27]);
        }

        int longest(char[] a, int lo, int hi, int last, Integer[][][] dp){
            if(lo + 1 > hi)
                return 0;
            if(dp[lo][hi][last] != null)
                return dp[lo][hi][last];
            if(a[lo] == a[hi] && a[lo] - 'a' + 1 != last)
                return dp[lo][hi][last] = 2 + longest(a, lo + 1, hi - 1, a[lo] - 'a' + 1, dp);
            return dp[lo][hi][last] = Math.max(longest(a, lo + 1, hi, last, dp), longest(a, lo, hi - 1, last, dp));
        }
    }

    static class s1690{//Stone Game VII
        public int stoneGameVII(int[] stones){
            return dfs(Arrays.stream(stones).sum(), 0, stones.length - 1, stones, new Integer[stones.length][stones.length]);
        }

        int dfs(int sum, int i, int j, int[] a, Integer[][] dp){
            if(i == j)
                return 0;
            if(dp[i][j] != null)
                return dp[i][j];
            return dp[i][j] = Math.max(sum - a[i] - dfs(sum - a[i], i + 1, j, a, dp), sum - a[j] - dfs(sum - a[j], i, j - 1, a, dp));
        }
    }
}
