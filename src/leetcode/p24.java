package leetcode;
import java.util.HashMap;
import java.util.List;
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

    static class s2405{//Optimal Partition of String
        public int partitionString(String s){
            int r = 0;
            for(int i = 0, j; i < s.length(); i = j){
                boolean[] used = new boolean[26];
                for(j = i; j < s.length() && !used[s.charAt(j) - 'a']; j++)
                    used[s.charAt(j) - 'a'] = true;
                r++;
            }
            return r;
        }
    }

    static class s2408{//Design SQL
        class SQL{
            Map<String, Integer> id = new HashMap<>();
            Map<String, Map<Integer, List<String>>> data = new HashMap<>();

            public SQL(List<String> names, List<Integer> columns){
                names.forEach(name -> data.put(name, new HashMap<>()));
                names.forEach(name -> id.put(name, 1));
            }

            public void insertRow(String name, List<String> row){
                data.get(name).put(id.get(name), row);
                id.put(name, id.get(name) + 1);
            }

            public void deleteRow(String name, int rowId){}

            public String selectCell(String name, int rowId, int columnId){
                return data.get(name).get(rowId).get(columnId - 1);
            }
        }
    }
}
