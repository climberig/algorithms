package leetcode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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
}
