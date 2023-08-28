package leetcode;
import java.util.Collections;
import java.util.List;
public class p28{
    static class s2824{//Count Pairs Whose Sum is Less than Target
        public int countPairs(List<Integer> a, int target) {
            Collections.sort(a);
            int r = 0, i = 0;
            for (int j = a.size() - 1; j >= 0; j--) {
                for (; i < a.size() && a.get(i) + a.get(j) < target; i++) ;
                r += Math.min(i, j);
            }
            return r;
        }
    }
}