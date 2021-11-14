package leetcode;

import java.util.stream.IntStream;
public class a{
    public static void main(String[] args){
        System.out.println(new a().timeRequiredToBuy(new int[]{84, 49, 5, 24, 70, 77, 87, 8}, 3));
    }
    public int timeRequiredToBuy(int[] tickets, int k){
        return IntStream.range(0, tickets.length).map(i -> Math.min(tickets[i], i <= k ? tickets[k] : tickets[k] - 1)).sum();
    }
}
