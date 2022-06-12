package leetcode;
import java.util.Arrays;
public class p23{
    static class s2303{//Calculate Amount Paid in Taxes
        public double calculateTax(int[][] brackets, int income){
            double r = 0;
            for(int i = 0, amount = 0; i < brackets.length && amount < income; i++){
                int dollars = Math.min(income - amount, brackets[i][0] - amount);
                r += dollars * brackets[i][1] / 100.0;
                amount += dollars;
            }
            return r;
        }
    }

    static class s2304{//Minimum Path Cost in a Grid
        public int minPathCost(int[][] g, int[][] cost){
            int[] r = g[0];
            for(int i = 1; i < g.length; i++){
                int[] next = new int[g[0].length];
                Arrays.fill(next, Integer.MAX_VALUE);
                for(int j = 0; j < g[0].length; j++)
                    for(int k = 0; k < g[0].length; k++)
                        next[k] = Math.min(next[k], r[j] + g[i][k] + cost[g[i - 1][j]][k]);
                r = next;
            }
            return Arrays.stream(r).min().getAsInt();
        }
    }
}
