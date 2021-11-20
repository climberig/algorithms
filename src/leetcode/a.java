package leetcode;

public class a{
    public int findPoisonedDuration(int[] timeSeries, int duration){
        int r = duration;
        for(int i = 0; i < timeSeries.length - 1; i++)
            r += Math.min(timeSeries[i + 1] - timeSeries[i], duration);
        return r;
    }
}
