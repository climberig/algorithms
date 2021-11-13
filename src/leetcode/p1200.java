package leetcode;

import java.util.*;
import java.util.stream.*;

public class p1200{
    static class s1202{//Smallest String With Swaps
        public String smallestStringWithSwaps(String s, List<List<Integer>> pairs){
            List<List<Integer>> g = IntStream.range(0, s.length()).mapToObj(i -> new ArrayList<Integer>()).collect(Collectors.toList());
            for(List<Integer> p : pairs){
                g.get(p.get(0)).add(p.get(1));
                g.get(p.get(1)).add(p.get(0));
            }
            boolean[] seen = new boolean[s.length()];
            char[] r = new char[s.length()];
            for(int i = 0; i < s.length(); i++)
                if(!seen[i]){
                    List<Integer> indices = new ArrayList<>();
                    dfs(i, seen, indices, s, g);
                    Collections.sort(indices);
                    List<Character> chars = indices.stream().map(s::charAt).sorted().collect(Collectors.toList());
                    for(int j = 0; j < chars.size(); j++)
                        r[indices.get(j)] = chars.get(j);
                }
            return new String(r);
        }

        void dfs(int i, boolean[] seen, List<Integer> indices, String s, List<List<Integer>> g){
            seen[i] = true;
            indices.add(i);
            for(Integer j : g.get(i))
                if(!seen[j])
                    dfs(j, seen, indices, s, g);
        }
    }

    static class s1208{//Get Equal Substrings Within Budget
        public int equalSubstring(String s, String t, int maxCost){
            int r = 0;
            for(int i = 0, j = 0; j < s.length(); j++){
                maxCost -= Math.abs(s.charAt(j) - t.charAt(j));
                while(maxCost < 0)
                    maxCost += Math.abs(s.charAt(i) - t.charAt(i++));
                r = Math.max(r, j - i + 1);
            }
            return r;
        }
    }

    static class s1248{//Count Number of Nice Subarrays
        public int numberOfSubarrays(int[] a, int k){
            int r = 0;
            for(int i = 0, j = 0, count = 0; j < a.length; j++){
                if(a[j] % 2 == 1){
                    k--;
                    count = 0;
                }
                while(k == 0){
                    k += a[i++] % 2;
                    count++;
                }
                r += count;
            }
            return r;
        }
    }

    static class s1273{//Delete Tree Nodes
        public int deleteTreeNodes(int size, int[] parents, int[] vals){
            List<List<Integer>> g = new ArrayList<>(size);
            IntStream.range(0, size).forEach(i -> g.add(new ArrayList<>()));
            for(int i = 1; i < parents.length; i++)
                g.get(parents[i]).add(i);
            return dfs(g, vals, 0)[1];
        }

        int[] dfs(List<List<Integer>> g, int[] vals, int node){
            int sum = vals[node], count = 1;
            for(Integer child : g.get(node)){
                int[] r = dfs(g, vals, child);
                sum += r[0];
                count += r[1];
            }
            if(sum == 0)
                count = 0;
            return new int[]{sum, count};
        }
    }

    static class s1284{//Minimum Number of Flips to Convert Binary Matrix to Zero Matrix
        public int minFlips(int[][] m){
            Set<String> seen = new HashSet<>();
            Queue<int[][]> q = new LinkedList<>();
            int flips = 0;
            for(q.add(m), seen.add(str(m)); !q.isEmpty(); flips++)
                for(int size = q.size(); size > 0; size--){
                    int[][] p = q.poll();
                    if(Arrays.stream(p).map(row -> Arrays.stream(row).sum()).allMatch(s -> s == 0))
                        return flips;
                    for(int i = 0; i < m.length; i++)
                        for(int j = 0; j < m[0].length; j++){
                            int[][] s = new int[p.length][p[0].length];
                            for(int k = 0; k < p.length; k++)
                                s[k] = p[k].clone();
                            flip(i, j, s);
                            flip(i + 1, j, s);
                            flip(i - 1, j, s);
                            flip(i, j + 1, s);
                            flip(i, j - 1, s);
                            if(seen.add(str(s)))
                                q.offer(s);
                        }
                }
            return -1;
        }

        void flip(int i, int j, int[][] s){
            if(0 <= i && i < s.length && 0 <= j && j < s[0].length)
                s[i][j] = 1 - s[i][j];
        }

        String str(int[][] m){return Arrays.stream(m).map(Arrays::toString).collect(Collectors.joining(""));}
    }

    static class s1289{//Minimum Falling Path Sum II
        public int minFallingPathSum(int[][] a){
            for(int r = a.length - 2; r >= 0; r--)
                for(int c = 0; c < a.length; c++){
                    int min = Integer.MAX_VALUE;
                    for(int i = 0; i < a.length; i++)
                        if(i != c)
                            min = Math.min(min, a[r + 1][i]);
                    a[r][c] += min;
                }
            return Arrays.stream(a[0]).min().getAsInt();
        }
    }
}
