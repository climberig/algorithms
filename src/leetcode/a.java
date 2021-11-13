package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class a {
    public String[] findRestaurant(String[] list1, String[] list2) {
        int minSum = Integer.MAX_VALUE;
        Map<String, Integer> list1m = IntStream.range(0, list1.length).boxed().collect(Collectors.toMap(i -> list1[i], i -> i));
        Map<Integer, List<String>> r = new HashMap<>();
        for (int i = 0; i < list2.length; i++)
            if (list1m.containsKey(list2[i])) {
                int sum = list1m.get(list2[i]) + i;
                r.computeIfAbsent(sum, l -> new ArrayList<>()).add(list2[i]);
                minSum = Math.min(minSum, sum);
            }
        return r.get(minSum).toArray(new String[0]);
    }
}
