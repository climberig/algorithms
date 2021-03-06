package three_sum;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Solution {
    public static void main(String[] args) {
        System.out.println(new Solution().threeSum(new int[]{-4, -1, -1, -1, 0, 1, 2, 2}));
    }

    public List<List<Integer>> threeSum(int[] a) {
        Arrays.sort(a);
        List<List<Integer>> r = new LinkedList<>();
        for (int i = 0; i < a.length - 2; i++)
            if (i == 0 || a[i - 1] != a[i]) {
                int lo = i + 1, hi = a.length - 1;
                while (lo < hi) {
                    int sum = a[i] + a[lo] + a[hi];
                    if (sum == 0) {
                        r.add(Arrays.asList(a[i], a[lo++], a[hi--]));
                        while (lo < hi && a[lo - 1] == a[lo]) lo++;
                        while (lo < hi && a[hi + 1] == a[hi]) hi--;
                    }else if (sum < 0)
                        lo++;
                    else hi--;
                }
            }
        return r;
    }
}
