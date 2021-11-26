package leetcode;

import java.util.Map;
import java.util.TreeMap;
public class a{
    static class s2021{//Brightest Position on Street
        public int brightestPosition(int[][] lights){
            Map<Integer, Integer> m = new TreeMap<>();
            for(int[] l : lights){
                int left = l[0] - l[1], right = l[0] + l[1];
                m.put(left, m.getOrDefault(left, 0) + 1);
                m.put(right + 1, m.getOrDefault(right + 1, 0) - 1);
            }
            int maxLight = Integer.MIN_VALUE, sum = 0, minPosition = 0;
            for(Integer p : m.keySet()){
                sum += m.get(p);
                if(sum > maxLight){
                    maxLight = sum;
                    minPosition = p;
                }
            }
            return minPosition;
        }
    }
}
