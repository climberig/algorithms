package leetcode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
public class a{
    public int numberOfPaths(int n, int[][] corridors){
        List<Set<Integer>> g = IntStream.range(0, n + 1).mapToObj(i -> new HashSet<Integer>()).collect(Collectors.toList());
        for(int[] c : corridors){
            if(c[0] < c[1])
                g.get(c[0]).add(c[1]);
            else g.get(c[1]).add(c[0]);
        }
        int r = 0;
        for(int[] e : corridors){
            Set<Integer> s1 = g.get(e[0]), s2 = g.get(e[1]);
            for(Integer u : s1)
                if(s2.contains(u))
                    r++;
        }
        return r;
    }
}
