package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class a {
    public List<String> commonChars(String[] words) {
        int[] common = new int[26];
        Arrays.fill(common, Integer.MAX_VALUE);
        for (String w : words) {
            int[] counts = new int[26];
            w.chars().forEach(c -> counts[c - 'a']++);
            for (int i = 0; i < common.length; i++)
                common[i] = Math.min(common[i], counts[i]);
        }
        List<String> r = new ArrayList<>();
        for (char c = 'a'; c <= 'z'; c++)
            while (common[c - 'a']-- > 0)
                r.add(Character.toString(c));
        return r;
    }
}
