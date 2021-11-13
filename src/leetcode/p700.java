package leetcode;

import java.util.*;
import java.util.stream.*;

public class p700{
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

    static class s723{//Candy Crush TODO: submit next time!
        public int[][] candyCrush(int[][] b){
            Set<Integer> s = new HashSet<>();
            for(int i = 0; i < b.length; i++)
                for(int j = 0; j < b[0].length; j++){
                    if(b[i][j] > 0 && j - 2 >= 0 && b[i][j - 1] == b[i][j] && b[i][j - 2] == b[i][j])
                        s.add(51 * i + j);
                    if(b[i][j] > 0 && i - 2 >= 0 && b[i - 1][j] == b[i][j] && b[i - 2][j] == b[i][j])
                        s.add(51 * i + j);
                }
            s.forEach(p -> b[p / 51][p % 51] = 0);
            for(int c = 0; c < b[0].length; c++){
                int bot = b.length - 1;
                for(int j = b.length - 1; j >= 0; j--)
                    if(b[j][c] > 0)
                        b[bot++][c] = b[j][c];
                for(; bot >= 0; b[bot++][c] = 0) ;
            }
            return s.isEmpty() ? b : candyCrush(b);
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
}
