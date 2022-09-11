package leetcode;
import java.util.Map;
import java.util.TreeMap;
public class p24{
    static class s2404{//Most Frequent Even Element
        public int mostFrequentEven(int[] a){
            Map<Integer, Integer> fr = new TreeMap<>();
            int maxFr = 0;
            for(int n : a)
                if(n % 2 == 0){
                    fr.put(n, fr.getOrDefault(n, 0) + 1);
                    maxFr = Math.max(maxFr, fr.get(n));
                }
            final int max = maxFr;
            return fr.keySet().stream().filter(n -> fr.get(n) == max).findFirst().orElse(-1);
        }
    }
}
