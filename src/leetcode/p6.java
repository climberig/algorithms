package leetcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class p6{
    static class s604{//Design Compressed String Iterator
        class StringIterator{
            char a[], c;
            int i, n;

            public StringIterator(String s){a = s.toCharArray();}

            public char next(){
                if(n == 0){
                    if(i == a.length)
                        return ' ';
                    c = a[i++];
                    while(i < a.length && Character.isDigit(a[i]))
                        n = 10 * n + a[i++] - '0';
                }
                n--;
                return c;
            }

            public boolean hasNext(){return n > 0 || i < a.length;}
        }
    }

    static class s605{//Can Place Flowers
        public boolean canPlaceFlowers(int[] a, int n){
            for(int i = 0; i < a.length && n > 0; i++)
                if(a[i] == 0 && (i == 0 || a[i - 1] == 0) && (i == a.length - 1 || a[i + 1] == 0)){
                    i++;
                    n--;
                }
            return n == 0;
        }
    }

    static class s606{//Construct String from Binary Tree
        public String tree2str(TreeNode node){
            if(node == null)
                return "";
            String left = tree2str(node.left), right = tree2str(node.right), r = node.val + "";
            if(node.right != null)
                r += "(" + left + ")(" + right + ")";
            else if(node.left != null)
                r += "(" + left + ")";
            return r;
        }
    }

    static class s617{//Merge Two Binary Trees
        public TreeNode mergeTrees(TreeNode r1, TreeNode r2){
            if(r1 != null && r2 != null)
                return new TreeNode(r1.val + r2.val, mergeTrees(r1.left, r2.left), mergeTrees(r1.right, r2.right));
            return r1 != null ? new TreeNode(r1.val, r1.left, r1.right) : r2 != null ? new TreeNode(r2.val, r2.left, r2.right) : null;
        }
    }

    static class s628{//Maximum Product of Three Numbers
        public int maximumProduct(int[] a){
            Arrays.sort(a);
            int last = a.length - 1;
            return Math.max(a[last] * a[last - 1] * a[last - 2], a[last] * a[0] * a[1]);
        }
    }

    static class s654{//Maximum Binary Tree
        public TreeNode constructMaximumBinaryTree(int[] a){return maxBt(a, 0, a.length - 1);}

        TreeNode maxBt(int[] a, int lo, int hi){
            if(lo > hi)
                return null;
            int maxIdx = lo;
            for(int i = lo + 1; i <= hi; i++)
                if(a[maxIdx] < a[i])
                    maxIdx = i;
            return new TreeNode(a[maxIdx], maxBt(a, lo, maxIdx - 1), maxBt(a, maxIdx + 1, hi));
        }
    }

    static class s657{//Robot Return to Origin
        public boolean judgeCircle(String moves){
            int x = 0, y = 0;
            for(int i = 0; i < moves.length(); i++)
                switch(moves.charAt(i)){
                    case 'U' -> y++;
                    case 'D' -> y--;
                    case 'L' -> x--;
                    case 'R' -> x++;
                }
            return x == 0 && y == 0;
        }
    }

    static class s684{//Redundant Connection
        public int[] findRedundantConnection(int[][] edges){
            int[] uf = IntStream.range(0, edges.length + 1).toArray();
            for(int[] e : edges){
                int p1 = find(e[0], uf), p2 = find(e[1], uf);
                if(p1 != p2)
                    uf[p1] = p2;
                else return e;
            }
            return null;
        }

        int find(int i, int[] uf){
            return i == uf[i] ? i : find(uf[i], uf);
        }
    }

    static class s694{//Number of Distinct Islands
        public int numDistinctIslands(int[][] g){
            Set<String> s = new HashSet<>();
            for(int i = 0; i < g.length; i++)
                for(int j = 0; j < g[0].length; j++)
                    if(g[i][j] == 1)
                        s.add(dfs(i, j, g, '.'));
            return s.size();
        }

        String dfs(int i, int j, int[][] g, char d){
            StringBuilder r = new StringBuilder().append(d);
            if(0 <= i && i < g.length && 0 <= j && j < g[0].length && g[i][j] == 1){
                g[i][j] = 0;
                r.append(dfs(i + 1, j, g, 'd')).append(dfs(i - 1, j, g, 'u'));
                r.append(dfs(i, j + 1, g, 'r')).append(dfs(i, j - 1, g, 'l'));
            }
            return r.toString();
        }
    }

    static class s695{//Max Area of Island
        public int maxAreaOfIsland(int[][] g){
            int r = 0;
            for(int i = 0; i < g.length; i++)
                for(int j = 0; j < g[0].length; j++)
                    if(g[i][j] == 1)
                        r = Math.max(r, area(i, j, g));
            return r;
        }

        int area(int i, int j, int[][] g){
            int r = 0;
            if(0 <= i && i < g.length && 0 <= j && j < g[0].length && g[i][j] == 1){
                g[i][j] = 0;
                r = 1 + area(i - 1, j, g) + area(i + 1, j, g) + area(i, j - 1, g) + area(i, j + 1, g);
            }
            return r;
        }
    }
}