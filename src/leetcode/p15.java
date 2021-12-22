package leetcode;

import java.util.*;
import java.util.stream.*;

public class p15{
    static class s1503{//Last Moment Before All Ants Fall Out of a Plank
        public int getLastMoment(int n, int[] left, int[] right){
            int r = 0;
            for(int p : left)
                r = Math.max(r, p);
            for(int p : right)
                r = Math.max(r, n - p);
            return r;
        }
    }

    static class s1513{//Number of Substrings With Only 1s
        public int numSub(String s){
            int r = 0;
            for(int i = 0, count = 0; i < s.length(); i++){
                count = s.charAt(i) == '1' ? count + 1 : 0;
                r = (r + count) % 1_000_000_007;
            }
            return r;
        }
    }

    static class s1516{//Move Sub-Tree of N-Ary Tree
        public Node moveSubTree(Node root, Node p, Node q){
            if(q.children.contains(p))
                return root;
            Node[] parents = new Node[2];
            findParents(root, p, q, parents);
            if(inSubtree(p, q)){
                parents[1].children.remove(q);
                q.children.add(p);
                if(parents[0] == null)
                    return q;
                int index = parents[0].children.indexOf(p);
                parents[0].children.set(index, q);
            }else{
                q.children.add(p);
                parents[0].children.remove(p);
            }
            return root;
        }

        void findParents(Node node, Node p, Node q, Node[] parents){
            if(node != null)
                for(Node next : node.children){
                    if(next == p)
                        parents[0] = node;
                    if(next == q)
                        parents[1] = node;
                    findParents(next, p, q, parents);
                }
        }

        boolean inSubtree(Node node, Node parent){
            if(node == null)
                return false;
            if(node == parent)
                return true;
            for(Node next : node.children)
                if(inSubtree(next, parent))
                    return true;
            return false;
        }

        class Node{
            public int val;
            public List<Node> children = new ArrayList<>();

            public Node(){}

            public Node(int _val){val = _val;}

            public Node(int _val, ArrayList<Node> _children){
                val = _val;
                children = _children;
            }
        }
    }

    static class s1519{
        public int[] countSubTrees(int n, int[][] edges, String labels){
            List<List<Integer>> g = new ArrayList<>(n);
            IntStream.range(0, n).forEach(v -> g.add(new ArrayList<>()));
            for(int[] e : edges){
                g.get(e[0]).add(e[1]);
                g.get(e[1]).add(e[0]);
            }
            int[] r = new int[n];
            boolean[] seen = new boolean[n];
            seen[0] = true;
            traverse(0, g, labels, r, seen);
            return r;
        }

        int[] traverse(int u, List<List<Integer>> g, String labels, int[] r, boolean[] seen){
            int[] f = new int[26];
            for(Integer v : g.get(u))
                if(!seen[v]){
                    seen[v] = true;
                    int[] c = traverse(v, g, labels, r, seen);
                    for(int i = 0; i < f.length; i++)
                        f[i] += c[i];
                }
            r[u] = ++f[labels.charAt(u) - 'a'];
            return f;
        }
    }

    static class s1526{//Minimum Number of Increments on Subarrays to Form a Target Array
        public int minNumberOperations(int[] target){
            int r = target[0];
            for(int i = 1; i < target.length; i++)
                r += Math.max(0, target[i] - target[i - 1]);
            return r;
        }
    }

    static class s1528{//Shuffle String
        public String restoreString(String s, int[] idx){
            char[] a = new char[s.length()];
            for(int i = 0; i < s.length(); i++)
                a[idx[i]] = s.charAt(i);
            return new String(a);
        }
    }

    static class s1536{//Minimum Swaps to Arrange a Binary Grid
        public int minSwaps(int[][] g){
            int zeros[] = new int[g.length], r = 0;
            for(int row = 0, count = 0; row < g.length; row++, count = 0){
                for(int col = g[0].length - 1; col >= 0 && g[row][col] == 0; col--, count++) ;
                zeros[row] = count;
            }
            for(int i = 0, need = g[0].length - 1, j; i < zeros.length; i++, need--){
                for(j = i; j < zeros.length && zeros[j] < need; j++) ;
                if(j == zeros.length)
                    return -1;
                for(; i < j; j--, r++){
                    int t = zeros[j - 1];
                    zeros[j - 1] = zeros[j];
                    zeros[j] = t;
                }
            }
            return r;
        }
    }

    static class s1538{//Guess the Majority in a Hidden Array
        public int guessMajority(ArrayReader reader){
            int n = reader.length(), groupA = 1, groupB = 0, idxA = -1, idxB = -1;
            int r0123 = reader.query(0, 1, 2, 3), r0124 = reader.query(0, 1, 2, 4);
            for(int i = 4; i < n; i++)
                if(reader.query(0, 1, 2, i) == r0123){
                    groupA++;
                    idxA = i;
                }else{
                    groupB++;
                    idxB = i;
                }
            int[][] rThree = new int[3][4];
            rThree[0] = new int[]{1, 2, 3, 4};
            rThree[1] = new int[]{0, 2, 3, 4};
            rThree[2] = new int[]{0, 1, 3, 4};
            for(int i = 0; i < 3; i++)
                if(reader.query(rThree[i][0], rThree[i][1], rThree[i][2], rThree[i][3]) == r0124){
                    groupA++;
                    idxA = i;
                }else{
                    groupB++;
                    idxB = i;
                }
            return groupA > groupB ? idxA : (groupA < groupB ? idxB : -1);
        }

        interface ArrayReader{
            int query(int a, int b, int c, int d);

            int length();
        }
    }

    static class s1545{//Find Kth Bit in Nth Binary String
        public char findKthBit(int n, int k){
            String s = "0";
            while(k > s.length())
                s = s + "1" + invertReverse(s);
            return s.charAt(k - 1);
        }

        String invertReverse(String s){
            StringBuilder r = new StringBuilder(s.length());
            for(int i = s.length() - 1; i >= 0; i--)
                r.append(s.charAt(i) == '0' ? '1' : '0');
            return r.toString();
        }
    }

    static class s1552{//Magnetic Force Between Two Balls
        public int maxDistance(int[] position, int m){
            Arrays.sort(position);
            int r = 1, lo = 1, hi = 1_000_000_000;
            while(lo <= hi){
                int force = (lo + hi) / 2;
                if(valid(force, position, m)){
                    r = force;
                    lo = force + 1;
                }else hi = force - 1;
            }
            return r;
        }

        boolean valid(int force, int[] position, int m){
            for(int i = 0, lastIdx = 0; i < position.length && m > 1; i++)
                if(position[i] - position[lastIdx] >= force){
                    lastIdx = i;
                    m--;
                }
            return m == 1;
        }
    }

    static class s1557{//Minimum Number of Vertices to Reach All Nodes
        public List<Integer> findSmallestSetOfVertices(int n, List<List<Integer>> edges){
            boolean[] seen = new boolean[n];
            edges.forEach(e -> seen[e.get(1)] = true);
            return IntStream.range(0, n).filter(i -> !seen[i]).boxed().collect(Collectors.toList());
        }
    }

    static class s1559{//Detect Cycles in 2D Grid
        public boolean containsCycle(char[][] g){
            for(int i = 0, dirs[] = {-1, 0, 1, 0, -1}; i < g.length; i++)
                for(int j = 0; j < g[0].length; j++)
                    if('a' <= g[i][j] && g[i][j] <= 'z' && hasCycle(i, j, i, j, g, g[i][j], dirs))
                        return true;
            return false;
        }

        boolean hasCycle(int i, int j, int prevI, int prevJ, char[][] g, char c, int[] dirs){
            g[i][j] = Character.toUpperCase(c);
            for(int d = 1; d < dirs.length; d++){
                int x = i + dirs[d - 1], y = j + dirs[d];
                if(0 <= x && x < g.length && 0 <= y && y < g[0].length && (x != prevI || y != prevJ))
                    if(g[x][y] == Character.toUpperCase(c))
                        return true;
                    else if(g[x][y] == c && hasCycle(x, y, i, j, g, c, dirs))
                        return true;
            }
            return false;
        }
    }

    static class s1570{//Dot Product of Two Sparse Vectors
        class SparseVector{
            final Map<Integer, Integer> m = new HashMap<>();

            SparseVector(int[] a){IntStream.range(0, a.length).filter(i -> a[i] > 0).forEach(i -> m.put(i, a[i]));}

            public int dotProduct(SparseVector v){
                return m.size() <= v.m.size() ? m.keySet().stream().mapToInt(i -> m.get(i) * v.m.getOrDefault(i, 0)).sum() : v.dotProduct(this);
            }
        }
    }

    static class s1574{//Shortest Subarray to be Removed to Make Array Sorted
        public int findLengthOfShortestSubarray(int[] a){
            int left = 0;
            for(; left + 1 < a.length && a[left] <= a[left + 1]; left++) ;
            if(left == a.length - 1)
                return 0;
            int right = a.length - 1;
            for(; right > left && a[right - 1] <= a[right]; right--) ;
            int r = Math.min(a.length - left - 1, right);
            for(int i = 0, j = right; i <= left && j < a.length; )
                if(a[j] >= a[i]){
                    r = Math.min(r, j - i - 1);
                    i++;
                }else j++;
            return r;
        }
    }
}
