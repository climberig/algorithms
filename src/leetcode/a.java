package leetcode;

public class a{
    static class s2087{//Minimum Cost Homecoming of a Robot in a Grid
        public int minCost(int[] start, int[] home, int[] rowCosts, int[] colCosts){
            int cost = 0;
            for(int r = start[0] + 1; r <= home[0]; cost += rowCosts[r++]) ;
            for(int c = start[1] + 1; c <= home[1]; cost += colCosts[c++]) ;
            for(int r = start[0] - 1; r >= home[0]; cost += rowCosts[r--]) ;
            for(int c = start[1] - 1; c >= home[1]; cost += colCosts[c--]) ;
            return cost;
        }
    }
}
