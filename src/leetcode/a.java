package leetcode;

public class a{
    static class s665{//Non-decreasing Array
        public boolean checkPossibility(int[] a){
            int k = -1;
            for(int i = 0; i < a.length - 1; i++)
                if(a[i] > a[i + 1]){
                    if(k != -1)
                        return false;
                    k = i;
                }
            if(k <= 0 || k == a.length - 2)
                return true;
            return a[k - 1] <= a[k + 1] || a[k] <= a[k + 2];
        }
    }
}
