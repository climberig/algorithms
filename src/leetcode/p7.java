package leetcode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class p7{
    static class s700{//Search in a Binary Search Tree
        public TreeNode searchBST(TreeNode root, int val){
            while(root != null && root.val != val)
                root = val < root.val ? root.left : root.right;
            return root;
        }
    }

    static class s703{//Kth Largest Element in a Stream
        class KthLargest{
            PriorityQueue<Integer> q = new PriorityQueue<>();
            int k;

            public KthLargest(int k, int[] a){
                this.k = k;
                Arrays.stream(a).forEach(this::add);
            }

            public int add(int val){
                q.offer(val);
                if(q.size() > k)
                    q.poll();
                return q.peek();
            }
        }
    }

    static class s704{//Binary Search
        public int search(int[] a, int target){
            int lo = 0, hi = a.length - 1;
            while(lo <= hi){
                int i = (lo + hi) / 2;
                if(a[i] == target)
                    return i;
                if(a[i] < target)
                    lo = i + 1;
                else hi = i - 1;
            }
            return lo < a.length && a[lo] == target ? lo : -1;
        }
    }

    static class s717{//1-bit and 2-bit Characters
        public boolean isOneBitCharacter(int[] bits){
            int n = bits.length, i = 0;
            while(i < n - 1)
                i += bits[i] == 0 ? 1 : 2;
            return i == n - 1;
        }
    }

    static class s723{//Candy Crush
        public int[][] candyCrush(int[][] b){
            Set<Integer> s = new HashSet<>();
            for(int i = 0; i < b.length; i++)
                for(int j = 0; j < b[0].length; j++){
                    if(b[i][j] > 0 && j - 2 >= 0 && b[i][j - 1] == b[i][j] && b[i][j - 2] == b[i][j])
                        s.addAll(Arrays.asList(51 * i + j, 51 * i + j - 1, 51 * i + j - 2));
                    if(b[i][j] > 0 && i - 2 >= 0 && b[i - 1][j] == b[i][j] && b[i - 2][j] == b[i][j])
                        s.addAll(Arrays.asList(51 * i + j, 51 * (i - 1) + j, 51 * (i - 2) + j));
                }
            s.forEach(p -> b[p / 51][p % 51] = 0);
            for(int c = 0; c < b[0].length; c++){
                int bot = b.length - 1;
                for(int j = b.length - 1; j >= 0; j--)
                    if(b[j][c] > 0)
                        b[bot--][c] = b[j][c];
                for(; bot >= 0; b[bot--][c] = 0) ;
            }
            return s.isEmpty() ? b : candyCrush(b);
        }
    }
    static class s724{//Find Pivot Index
        public int pivotIndex(int[] a){
            int right = Arrays.stream(a).sum(), left = 0;
            for(int i = 0; i < a.length; i++){
                right -= a[i];
                if(left == right)
                    return i;
                left += a[i];
            }
            return -1;
        }
    }

    static class s728{//Self Dividing Numbers
        public List<Integer> selfDividingNumbers(int left, int right){
            return IntStream.rangeClosed(left, right).filter(this::selfDividing).boxed().collect(Collectors.toList());
        }
        boolean selfDividing(int n){
            for(int m = n; m > 0; m /= 10){
                int d = m % 10;
                if(d == 0 || n % d != 0)
                    return false;
            }
            return true;
        }
    }

    static class s733{//Flood Fill
        public int[][] floodFill(int[][] image, int sr, int sc, int newColor){
            if(image[sr][sc] == newColor)
                return image;
            int dirs[] = {-1, 0, 1, 0, -1}, oldColor = image[sr][sc];
            Queue<int[]> q = new LinkedList<>();
            for(q.add(new int[]{sr, sc}), image[sr][sc] = newColor; !q.isEmpty(); )
                for(int d = 1, p[] = q.poll(); d < dirs.length; d++){
                    int x = p[0] + dirs[d - 1], y = p[1] + dirs[d];
                    if(0 <= x && x < image.length && 0 <= y && y < image[0].length && image[x][y] == oldColor){
                        image[x][y] = newColor;
                        q.add(new int[]{x, y});
                    }
                }
            return image;
        }
    }

    static class s734{//Sentence Similarity
        public boolean areSentencesSimilar(String[] s1, String[] s2, List<List<String>> pairs){
            if(s1.length != s2.length)
                return false;
            Map<String, Set<String>> m = new HashMap<>();
            for(List<String> p : pairs){
                m.computeIfAbsent(p.get(0), s -> new HashSet<>()).add(p.get(1));
                m.computeIfAbsent(p.get(1), s -> new HashSet<>()).add(p.get(0));
            }
            for(int i = 0; i < s1.length; i++)
                if(!s1[i].equals(s2[i]) && !m.getOrDefault(s1[i], Collections.emptySet()).contains(s2[i]))
                    return false;
            return true;
        }
    }

    static class s743{//Network Delay Time
        public int networkDelayTime(int[][] times, int n, int k){
            List<List<int[]>> g = new ArrayList<>(n);
            IntStream.range(0, n).forEach(u -> g.add(new ArrayList<>()));
            for(int[] t : times)
                g.get(t[0] - 1).add(new int[]{t[1] - 1, t[2]});
            int[] received = new int[n];
            Arrays.fill(received, Integer.MAX_VALUE);
            received[k - 1] = 0;
            for(var q = new LinkedList<>(Arrays.asList(k - 1)); !q.isEmpty(); ){
                Integer u = q.poll();
                for(int[] a : g.get(u)){
                    int v = a[0], t = a[1];
                    if(received[u] + t < received[v]){
                        received[v] = received[u] + t;
                        q.offer(v);
                    }
                }
            }
            int r = Arrays.stream(received).max().getAsInt();
            return r == Integer.MAX_VALUE ? -1 : r;
        }
    }

    static class s746{//Min Cost Climbing Stairs
        public int minCostClimbingStairs(int[] cost){
            for(int i = 2; i < cost.length; i++)
                cost[i] += Math.min(cost[i - 2], cost[i - 1]);
            return Math.min(cost[cost.length - 1], cost[cost.length - 2]);
        }
    }

    static class s749{//Contain Virus
        public int containVirus(int[][] g){
            int[] dirs = {-1, 0, 1, 0, -1};
            PriorityQueue<Region> q = new PriorityQueue<>(Comparator.comparingInt(r -> -r.spread.size()));
            for(int i = 0; i < g.length; i++)
                for(int j = 0; j < g[0].length; j++)
                    if(g[i][j] == 1){
                        Region region = new Region();
                        dfs(i, j, g, region, dirs);
                        q.offer(region);
                    }
            if(q.isEmpty())
                return 0;
            Region biggestRegion = q.poll();
            biggestRegion.area.forEach(p -> g[p.getKey()][p.getValue()] = 2);
            for(Region region : q){
                region.area.forEach(p -> g[p.getKey()][p.getValue()] = 1);
                region.spread.forEach(p -> g[p.getKey()][p.getValue()] = 1);
            }
            return biggestRegion.walls + containVirus(g);
        }

        void dfs(int row, int col, int[][] g, Region region, int[] dirs){
            region.area.add(new Pair<>(row, col));
            g[row][col] = -1;
            for(int d = 1; d < dirs.length; d++){
                int x = row + dirs[d - 1], y = col + dirs[d];
                if(0 <= x && x < g.length && 0 <= y && y < g[0].length)
                    if(g[x][y] == 0){
                        region.spread.add(new Pair<>(x, y));
                        region.walls++;
                    }else if(g[x][y] == 1)
                        dfs(x, y, g, region, dirs);
            }
        }

        class Region{
            Set<Pair<Integer, Integer>> spread = new HashSet<>(), area = new HashSet<>();
            int walls;
        }
    }

    static class s758{//Bold Words in String
        public String boldWords(String[] words, String s){
            boolean[] bold = new boolean[s.length() + 1];
            for(String w : words)
                for(int i = s.indexOf(w); i != -1; i = s.indexOf(w, i + 1))
                    Arrays.fill(bold, i, i + w.length(), true);
            StringBuilder r = new StringBuilder(bold[0] ? "<b>" : "");
            for(int i = 0; i < bold.length - 1; i++){
                r.append(s.charAt(i));
                r.append(bold[i] && !bold[i + 1] ? "</b>" : "");
                r.append(!bold[i] && bold[i + 1] ? "<b>" : "");
            }
            return r.toString();
        }
    }

    static class s760{//Find Anagram Mapping
        /**
         * You are given two integer arrays nums1 and nums2 where nums2 is an anagram of nums1.
         * Both arrays may contain duplicates. Return an index mapping array mapping from nums1
         * to nums2 where mapping[i] = j means the ith element in nums1 appears in nums2 at index j.
         * If there are multiple answers, return any of them. An array a is an anagram of an array b
         * means b is made by randomizing the order of the elements in a.
         */
        public int[] anagramMappings(int[] a1, int[] a2){
            Map<Integer, Stack<Integer>> m = new HashMap<>();
            for(int i = 0; i < a2.length; i++)
                m.computeIfAbsent(a2[i], s -> new Stack<>()).add(i);
            int[] r = new int[a1.length];
            for(int i = 0; i < a1.length; i++)
                r[i] = m.get(a1[i]).pop();
            return r;
        }
    }

}
