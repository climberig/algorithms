package leetcode;

public class a{
    static class s2086{//Minimum Number of Buckets Required to Collect Rainwater from Houses
        public int minimumBuckets(String street){
            int r = 0;
            char[] a = street.toCharArray();
            for(int i = 0; i < a.length; i++)
                if(a[i] == '.' && countCharAround(a, i, 'H') == 2){
                    r++;
                    a[i - 1] = a[i + 1] = 'h';
                }else if(a[i] == 'H' && countCharAround(a, i, '.') == 0)
                    return -1;
            for(char c : a)
                r += c == 'H' ? 1 : 0;
            return r;
        }

        int countCharAround(char[] a, int i, char c){
            return (i > 0 && a[i - 1] == c ? 1 : 0) + (i < a.length - 1 && a[i + 1] == c ? 1 : 0);
        }
    }
}
