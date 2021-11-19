package leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
public class a{
    public int findLHS(int[] a){
        Map<Integer, Integer> m = new HashMap<>();
        Arrays.stream(a).forEach(n -> m.put(n, m.getOrDefault(n, 0) + 1));
        int r = 0;
        for(Integer n : m.keySet())
            r = Math.max(r, m.get(n) + m.getOrDefault(n + 1, -m.get(n)));
        return r;
    }
}
