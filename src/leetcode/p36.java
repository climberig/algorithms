package leetcode;

public class p36{
    static class s3674{//Minimum Operations to Equalize Array
        public int minOperations(int[] a){
            for(int i = 1; i < a.length; i++)
                if(a[0] != a[i])
                    return 1;
            return 0;
        }
    }
}
