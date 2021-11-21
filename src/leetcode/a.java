package leetcode;

import java.util.stream.IntStream;
public class a{
    public int maxDistance(int[] cs){
        return IntStream.range(0, cs.length).filter(i -> cs[i] != cs[cs.length - 1] || cs[cs.length - 1 - i] != cs[0]).map(i -> cs.length - 1 - i).findFirst().orElse(0);
    }
}
