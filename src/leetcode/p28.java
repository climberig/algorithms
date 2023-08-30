package leetcode;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
public class p28{
    static class s2810{//Faulty Keyboard
        public String finalString(String s) {
            StringBuilder r = new StringBuilder();
            for (char c : s.toCharArray())
                if (c == 'i')
                    r.reverse();
                else r.append(c);
            return r.toString();
        }
    }

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

    static class s2828{//Check if a String Is an Acronym of Words
        public boolean isAcronym(List<String> words, String s) {
            return words.stream().map(w -> w.charAt(0) + "").collect(Collectors.joining("")).equals(s);
        }
    }
}