package leetcode;
import java.util.*;
public class p24{
    static class s2401{//Longest Nice Subarray
        public int longestNiceSubarray(int[] a){
            int r = 1, and = 0;
            for(int i = 0, j = 0; j < a.length; j++){
                while((and & a[j]) > 0)
                    and ^= a[i++];
                and |= a[j];
                r = Math.max(r, j - i + 1);
            }
            return r;
        }
    }

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

    static class s2410{//Maximum Matching of Players With Trainers
        public int matchPlayersAndTrainers(int[] players, int[] trainers){
            Arrays.sort(players);
            Arrays.sort(trainers);
            int r = 0;
            for(int i = 0, j = 0; i < players.length && j < trainers.length; )
                if(players[i] <= trainers[j]){
                    r++;
                    i++;
                    j++;
                }else j++;
            return r;
        }
    }

    static class s2413{//Smallest Even Multiple
        public int smallestEvenMultiple(int n){return n % 2 == 0 ? n : n * 2;}
    }

    static class s2414{//Length of the Longest Alphabetical Continuous Substring
        public int longestContinuousSubstring(String s){
            int r = 1;
            for(int i = 1, conseq = 1; i < s.length(); i++)
                if(s.charAt(i - 1) + 1 == s.charAt(i))
                    r = Math.max(r, ++conseq);
                else conseq = 1;
            return r;
        }
    }
}
