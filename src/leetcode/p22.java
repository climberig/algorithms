package leetcode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
public class p22{
    static class s2200{
        public List<Integer> findKDistantIndices(int[] a, int key, int k){
            List<Integer> r = new ArrayList<>();
            for(int i = 0, prev = 0; i < a.length; i++)
                if(a[i] == key){
                    for(int j = Math.max(i - k, prev); j < a.length && j <= i + k; j++)
                        r.add(j);
                    prev = i + k + 1;
                }
            return r;
        }
    }

    static class s2201{//Count Artifacts That Can Be Extracted
        public int digArtifacts(int n, int[][] artifacts, int[][] dig){
            Set<Integer> s = Arrays.stream(dig).map(d -> d[0] * 1000 + d[1]).collect(Collectors.toSet());
            return (int) Arrays.stream(artifacts).filter(a -> uncovered(a[0], a[1], a[2], a[3], s)).count();
        }

        boolean uncovered(int r1, int c1, int r2, int c2, Set<Integer> s){
            for(int i = r1; i <= r2; i++)
                for(int j = c1; j <= c2; j++)
                    if(!s.contains(1000 * i + j))
                        return false;
            return true;
        }
    }

    static class s2202{//Maximize the Topmost Element After K Moves
        public int maximumTop(int[] a, int k){
            if(a.length == 1 && k % 2 == 1)
                return -1;
            if(k > a.length)
                return Arrays.stream(a).max().getAsInt();
            PriorityQueue<Integer> q = new PriorityQueue<>(Comparator.reverseOrder());
            IntStream.range(0, k - 1).forEach(i -> q.offer(a[i]));
            int r = !q.isEmpty() ? q.poll() : -1;
            return k < a.length ? Math.max(r, a[k]) : r;
        }
    }

    static class s2203{//Minimum Weighted Subgraph With the Required Paths
        public long minimumWeight(int n, int[][] edges, int src1, int src2, int dest){
            List<Map<Integer, Integer>> g = IntStream.range(0, n).mapToObj(i -> new HashMap<Integer, Integer>()).collect(Collectors.toList());
            List<Map<Integer, Integer>> g1 = IntStream.range(0, n).mapToObj(i -> new HashMap<Integer, Integer>()).collect(Collectors.toList());
            for(int[] e : edges){
                g.get(e[0]).put(e[1], Math.min(e[2], g.get(e[0]).getOrDefault(e[1], Integer.MAX_VALUE)));
                g1.get(e[1]).put(e[0], Math.min(e[2], g1.get(e[1]).getOrDefault(e[0], Integer.MAX_VALUE)));
            }
            long a[] = dfs(src1, g), b[] = dfs(src2, g), c[] = dfs(dest, g1), r = Long.MAX_VALUE;
            for(int i = 0; i < n; i++)
                if(a[i] != Long.MAX_VALUE && b[i] != Long.MAX_VALUE && c[i] != Long.MAX_VALUE)
                    r = Math.min(r, a[i] + b[i] + c[i]);
            return r == Long.MAX_VALUE ? -1 : r;
        }

        long[] dfs(int start, List<Map<Integer, Integer>> g){
            long[] dist = new long[g.size()];
            Arrays.fill(dist, Long.MAX_VALUE);
            PriorityQueue<long[]> q = new PriorityQueue<>(Comparator.comparingLong(a -> a[0]));
            dist[start] = 0;
            for(q.offer(new long[]{0, start}); !q.isEmpty(); ){
                long p[] = q.poll(), d = p[0], u = p[1];
                Map<Integer, Integer> adj = g.get((int) u);
                for(int v : adj.keySet())
                    if(d + adj.get(v) < dist[v]){
                        dist[v] = d + adj.get(v);
                        q.offer(new long[]{dist[v], v});
                    }
            }
            return dist;
        }
    }

    static class s2204{//Distance to a Cycle in Undirected Graph
        public int[] distanceToCycle(int n, int[][] edges){
            int[] degree = new int[n], r = new int[n];
            List<List<Integer>> g = IntStream.range(0, n).mapToObj(i -> new ArrayList<Integer>()).collect(Collectors.toList());
            for(int[] e : edges){
                degree[e[0]]++;
                degree[e[1]]++;
                g.get(e[0]).add(e[1]);
                g.get(e[1]).add(e[0]);
            }
            Queue<Integer> q = IntStream.range(0, n).filter(u -> degree[u] == 1).boxed().collect(Collectors.toCollection(LinkedList::new));
            while(!q.isEmpty()){
                degree[q.peek()]--;
                g.get(q.poll()).stream().filter(u -> degree[u] > 0 && --degree[u] == 1).forEach(q::add);
            }
            boolean[] seen = new boolean[n];
            IntStream.range(0, n).filter(u -> degree[u] > 0).forEach(u -> {
                seen[u] = true;
                q.offer(u);
            });
            for(int d = 1; !q.isEmpty(); d++)
                for(int size = q.size(); size > 0; size--)
                    for(Integer v : g.get(q.poll()))
                        if(!seen[v]){
                            seen[v] = true;
                            r[v] = d;
                            q.offer(v);
                        }
            return r;
        }
    }

    static class s2206{//Divide Array Into Equal Pairs
        public boolean divideArray(int[] a){
            int[] f = new int[501];
            Arrays.stream(a).forEach(n -> f[n]++);
            return Arrays.stream(f).allMatch(n -> n % 2 == 0);
        }
    }
}
