package leetcode;
import java.util.*;
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

    static class s2107{//Number of Unique Flavors After Sharing K Candies
        public int shareCandies(int[] candies, int k){
            int r = 0;
            Map<Integer, Integer> flavors = new HashMap<>();
            Arrays.stream(candies).forEach(c -> flavors.put(c, flavors.getOrDefault(c, 0) + 1));
            for(int i = 0; i < candies.length; i++){
                if(i >= k){
                    r = Math.max(r, flavors.size());
                    flavors.put(candies[i - k], flavors.getOrDefault(candies[i - k], 0) + 1);
                }
                flavors.put(candies[i], flavors.get(candies[i]) - 1);
                if(flavors.get(candies[i]) == 0)
                    flavors.remove(candies[i]);
            }
            return Math.max(r, flavors.size());
        }
    }

    static class s2108{//Find First Palindromic String in the Array
        public String firstPalindrome(String[] words){
            return Arrays.stream(words).filter(w -> isPali(w.toCharArray())).findFirst().orElse("");
        }

        boolean isPali(char[] a){
            for(int i = 0, j = a.length - 1; i < j; i++, j--)
                if(a[i] != a[j])
                    return false;
            return true;
        }
    }

    static class s2109{//Adding Spaces to a String
        public String addSpaces(String s, int[] spaces){
            StringBuilder r = new StringBuilder();
            for(int i = 0, j = 0; i < s.length(); r.append(s.charAt(i++)))
                if(j < spaces.length && i == spaces[j]){
                    r.append(" ");
                    j++;
                }
            return r.toString();
        }
    }

    static class s2110{//Number of Smooth Descent Periods of a Stock
        public long getDescentPeriods(int[] prices){
            long r = 1, periodLen = 1;
            for(int i = 1; i < prices.length; i++){
                periodLen = prices[i - 1] - 1 == prices[i] ? periodLen + 1 : 1;
                r += periodLen;
            }
            return r;
        }
    }

    static class s2113{//Elements in Array After Removing and Replacing Elements
        public int[] elementInNums(int[] a, int[][] queries){
            int r[] = new int[queries.length], i = 0;
            for(int[] q : queries){
                int t = q[0] % (a.length * 2), j = q[1];
                int from = t <= a.length ? t : 0;
                int to = t > a.length ? t - a.length : a.length - 1;
                r[i++] = from + j <= to ? a[from + j] : -1;
            }
            return r;
        }
    }

    static class s2120{//Execution of All Suffix Instructions Staying in a Grid
        public int[] executeInstructions(int n, int[] startPos, String s){
            int[] r = new int[s.length()];
            for(int i = 0; i < s.length(); i++)
                r[i] = count(i, n, startPos[0], startPos[1], s);
            return r;
        }
        int count(int si, int n, int x, int y, String s){
            if(si == s.length())
                return 0;
            switch(s.charAt(si)){
                case 'U' -> x -= 1;
                case 'D' -> x += 1;
                case 'R' -> y += 1;
                case 'L' -> y -= 1;
            }
            return 0 <= x && x < n && 0 <= y && y < n ? 1 + count(si + 1, n, x, y, s) : 0;
        }
    }
}
