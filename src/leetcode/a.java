package leetcode;

import java.util.Arrays;
public class a{
    static class s2088{//Count Fertile Pyramids in a Land
        public int countPyramids(int[][] g){
            return count(inverse(g)) + count(g);
        }

        int count(int[][] g){
            int r = 0;
            for(int i = 1; i < g.length; i++)
                for(int j = 1; j < g[0].length - 1; j++)
                    if(g[i][j] >= 1 && g[i - 1][j] >= 1){
                        g[i][j] = Math.min(g[i - 1][j - 1], g[i - 1][j + 1]) + 1;
                        r += g[i][j] - 1;
                    }
            return r;
        }
        int[][] inverse(int[][] g){
            int[][] copy = new int[g.length][g[0].length];
            for(int i = 0; i < g.length; i++)
                copy[g.length - i - 1] = Arrays.copyOf(g[i], g[i].length);
            return copy;
        }
    }
}
