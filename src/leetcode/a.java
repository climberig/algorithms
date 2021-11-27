package leetcode;

import java.util.HashMap;
import java.util.Map;
public class a{
    public static void main(String[] args){
        System.out.println(new a().subarraysWithMoreZerosThanOnes(new int[]{0, 1, 1, 0, 1}));
    }
    public int subarraysWithMoreZerosThanOnes(int[] a){
        Map<Integer, Integer> m = new HashMap<>();
        m.put(0, 1);
        int r = 0, cnt = 0, sum = 0;
        for(int n : a){
            if(n == 1){
                // any subarray that has prefix sum equals (sum - 1) will become new valid subarray
                cnt += m.getOrDefault(sum, 0);
                sum++;
            }else{
                sum--;
                // any subarray that has prefix sum equals sum will become invalid
                cnt -= m.getOrDefault(sum, 0);
            }
            r = (r + cnt) % 1_000_000_007;
            m.put(sum, m.getOrDefault(sum, 0) + 1);
        }
        return r;
    }
}
