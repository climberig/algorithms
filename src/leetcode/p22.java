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
}
