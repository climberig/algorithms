package leetcode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class p21{
    static class s2100{//Find Good Days to Rob the Bank
        public List<Integer> goodDaysToRobBank(int[] sec, int time){
            boolean[] good = new boolean[sec.length];
            for(int i = sec.length - 2, count = 0; i >= 0; i--)
                good[i] = (count = sec[i] <= sec[i + 1] ? count + 1 : 0) >= time;
            List<Integer> r = new ArrayList<>();
            for(int i = 1, count = 0; i < sec.length; i++)
                if((count = sec[i - 1] >= sec[i] ? count + 1 : 0) >= time && good[i])
                    r.add(i);
            if(time == 0)
                r.addAll(Arrays.asList(0, sec.length - 1));
            return r;
        }
    }

    static class s2101{//Detonate the Maximum Bombs
        public int maximumDetonation(int[][] bombs){
            List<List<Integer>> g = IntStream.range(0, bombs.length).mapToObj(i -> new ArrayList<Integer>()).collect(Collectors.toList());
            for(int i = 0; i < bombs.length; i++)
                for(int j = 0; j < bombs.length; j++)
                    if(i != j && detonates(i, j, bombs))
                        g.get(i).add(j);
            int r = 0;
            for(int i = 0; i < bombs.length; i++)
                r = Math.max(dfs(i, g, new boolean[bombs.length]), r);
            return r;
        }

        int dfs(int u, List<List<Integer>> g, boolean[] seen){
            seen[u] = true;
            return 1 + g.get(u).stream().filter(v -> !seen[v]).mapToInt(v -> dfs(v, g, seen)).sum();
        }

        boolean detonates(int i, int j, int[][] bombs){//true if i bomb detonates j bomb
            long x = bombs[j][0] - bombs[i][0], y = bombs[j][1] - bombs[i][1];
            return x * x + y * y <= (long) bombs[i][2] * bombs[i][2];
        }
    }

    static class s2103{//Rings and Rods
        public int countPoints(String rings){
            String[] rods = new String[10];
            Arrays.fill(rods, "");
            for(int i = 0; i < rings.length(); i += 2){
                int rod = rings.charAt(i + 1) - '0';
                if(rods[rod].indexOf(rings.charAt(i)) == -1)
                    rods[rod] += rings.charAt(i);
            }
            return (int) Arrays.stream(rods).filter(s -> s.length() == 3).count();
        }
    }

    static class s2104{//Sum of Subarray Ranges
        public long subArrayRanges(int[] a){
            long r = 0;
            for(int i = 0; i < a.length; i++)
                for(int j = i, min = a[j], max = a[j]; j < a.length; j++){
                    max = Math.max(a[j], max);
                    min = Math.min(a[j], min);
                    r += max - min;
                }
            return r;
        }
    }

    static class s2105{//Watering Plants II
        public int minimumRefill(int[] plants, int capA, int capB){
            int r = 0;
            for(int i = 0, j = plants.length - 1, canA = capA, canB = capB; i <= j; canA -= plants[i++], canB -= plants[j--]){
                if(i == j)
                    return Math.max(canA, canB) >= plants[i] ? r : r + 1;
                if(canA < plants[i]){
                    canA = capA;
                    r++;
                }
                if(canB < plants[j]){
                    canB = capB;
                    r++;
                }
            }
            return r;
        }
    }
}
