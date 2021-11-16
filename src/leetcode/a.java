package leetcode;

import java.util.HashMap;
import java.util.Map;
public class a{
    public int[] twoSum(int[] a, int t){
        Map<Integer, Integer> m = new HashMap<>();
        for(int i = 0; i < a.length; i++)
            if(m.containsKey(t - a[i]))
                return new int[]{m.get(t - a[i]), i};
            else m.put(a[i], i);
        return null;
    }
}
