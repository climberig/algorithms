package leetcode;

public class a{
    public static void main(String[] args){
        System.out.println(new a().minCost(new int[]{1, 0}, new int[]{2, 3}, new int[]{5, 4, 3}, new int[]{8, 2, 6, 7}));
    }
    public int minCost(int[] start, int[] home, int[] rowCosts, int[] colCosts){
        int cost = 0, rStep = (int) Math.signum(home[0] - start[0]), cStep = (int) Math.signum(home[1] - start[1]);
        for(int r = start[0] + rStep; r != home[0] + rStep; cost += rowCosts[r], r += rStep) ;
        for(int c = start[1] + cStep; c != home[1] + cStep; cost += colCosts[c], c += cStep) ;
        return cost;
    }
}
