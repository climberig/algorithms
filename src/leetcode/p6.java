package leetcode;
import java.util.*;
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

    static class s616{//Add Bold Tag in String
        public String addBoldTag(String s, String[] words){
            boolean[] b = new boolean[s.length() + 1];
            for(String w : words)
                for(int i = s.indexOf(w); i != -1; i = s.indexOf(w, i + 1))
                    Arrays.fill(b, i, i + w.length(), true);
            StringBuilder r = new StringBuilder(b[0] ? "<b>" : "");
            for(int i = 0; i < b.length - 1; i++){
                r.append(s.charAt(i));
                r.append(b[i] && !b[i + 1] ? "</b>" : "");
                r.append(!b[i] && b[i + 1] ? "<b>" : "");
            }
            return r.toString();
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

    static class s637{//Average of Levels in Binary Tree
        public List<Double> averageOfLevels(TreeNode root){
            Queue<TreeNode> q = new LinkedList<>();
            List<Double> r = new ArrayList<>();
            for(q.add(root); !q.isEmpty(); ){
                int size = q.size();
                double sum = 0;
                for(int i = 0; i < size; i++){
                    TreeNode node = q.poll();
                    sum += node.val;
                    if(node.left != null)
                        q.offer(node.left);
                    if(node.right != null)
                        q.offer(node.right);
                }
                r.add(sum / size);
            }
            return r;
        }
    }

    static class s643{//Maximum Average Subarray I
        public double findMaxAverage(int[] a, int k){
            int sum = Arrays.stream(a, 0, k).sum(), maxSum = sum;
            for(int i = k; i < a.length; i++){
                sum += a[i] - a[i - k];
                maxSum = Math.max(maxSum, sum);
            }
            return maxSum * 1.0 / k;
        }
    }

    static class s645{//Set Mismatch
        public int[] findErrorNums(int[] a){
            int[] r = new int[2];
            for(int n : a)
                if(a[Math.abs(n) - 1] < 0)
                    r[0] = Math.abs(n);
                else a[Math.abs(n) - 1] *= -1;
            for(int i = 0; i < a.length; i++)
                if(a[i] > 0)
                    r[1] = i + 1;
            return r;
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

    static class s661{//Image Smoother
        public int[][] imageSmoother(int[][] img){
            int[][] r = new int[img.length][img[0].length];
            for(int i = 0; i < r.length; i++)
                for(int j = 0; j < r[0].length; j++){
                    int n = 0, sum = 0;
                    for(int x = -1; x <= 1; x++)
                        for(int y = -1; y <= 1; y++)
                            if(valid(i + x, j + y, img)){
                                sum += img[i + x][j + y];
                                n++;
                            }
                    r[i][j] = sum / n;
                }
            return r;
        }

        boolean valid(int x, int y, int[][] m){return 0 <= x && x < m.length && 0 <= y && y < m[0].length;}
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

    static class s693{//Binary Number with Alternating Bits
        public boolean hasAlternatingBits(int n){
            int diff = 0;
            for(int prev = -1; n > 0; n /= 2){
                int curr = n & 1;
                if(curr == prev)
                    return false;
                diff += curr == 1 ? 1 : -1;
                prev = curr;
            }
            return Math.abs(diff) <= 1;
        }

        boolean hasAlternatingBits2(int n){
        /*
        n =         1 0 1 0 1 0 1 0
        n >> 1      0 1 0 1 0 1 0 1
        n ^ n>>1    1 1 1 1 1 1 1 1
        n           1 1 1 1 1 1 1 1
        n + 1     1 0 0 0 0 0 0 0 0
        n & (n+1)   0 0 0 0 0 0 0 0
        */
            n = n ^ (n >> 1);
            return (n & n + 1) == 0;
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

    static class s696{//Count Binary Substrings
        public int countBinarySubstrings(String s){
            int r = 0, prevCount = 0, currCount = 1;
            for(int i = 1; i < s.length(); i++)
                if(s.charAt(i - 1) != s.charAt(i)){
                    r += Math.min(prevCount, currCount);
                    prevCount = currCount;
                    currCount = 1;
                }else currCount++;
            return r + Math.min(currCount, prevCount);
        }
    }

    static class s697{//Degree of an Array
        public int findShortestSubArray(int[] a){
            int maxFreq = 0, freq[] = new int[50_000], first[] = new int[50_000], r = Integer.MAX_VALUE;
            for(int i = 0; i < a.length; i++){
                if(first[a[i]] == 0)
                    first[a[i]] = i + 1;
                if(++freq[a[i]] >= maxFreq){
                    if(freq[a[i]] > maxFreq)
                        r = i - first[a[i]] + 2;
                    else r = Math.min(r, i - first[a[i]] + 2);
                    maxFreq = freq[a[i]];
                }
            }
            return r;
        }
    }
}
