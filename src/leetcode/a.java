package leetcode;

public class a{
    static class s2091{//Removing Minimum and Maximum From Array
        public int minimumDeletions(int[] a){
            int minIdx = 0, maxIdx = 0;
            for(int i = 1; i < a.length; i++){
                if(a[minIdx] > a[i])
                    minIdx = i;
                if(a[maxIdx] < a[i])
                    maxIdx = i;
            }
            int left = Math.min(minIdx, maxIdx), right = Math.max(minIdx, maxIdx);
            int cutBoth = left + 1 + a.length - right;
            int cutLeft = 1 + right;
            int cutRight = a.length - left;
            return Math.min(cutBoth, Math.min(cutLeft, cutRight));
        }
    }
}
