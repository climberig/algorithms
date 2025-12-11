package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class p37 {
    static class s3726{//Remove Zeros in Decimal Representation
        public long removeZeros(long n){
            long r = 0;
            for(long m = 1; n > 0; n /= 10){
                long d = n % 10;
                if(d != 0){
                    r = d * m + r;
                    m *= 10;
                }
            }
            return r;
        }
    }

    static class s3731{//Find Missing Elements
        public List<Integer> findMissingElements(int[] a){
            TreeSet<Integer> s = Arrays.stream(a).boxed().collect(Collectors.toCollection(TreeSet::new));
            List<Integer> r = new ArrayList<>();
            for(int i = s.first() + 1; i < s.last(); i++)
                if(!s.contains(i))
                    r.add(i);
            return r;
        }
    }

    static class s3740{//Minimum Distance Between Three Equal Elements I
        public int minimumDistance(int[] a){
            int minDist = Integer.MAX_VALUE;
            for(int i = 0, j, k; i < a.length; i++){
                for(j = i + 1; j < a.length && a[i] != a[j]; j++) ;
                for(k = j + 1; k < a.length && a[i] != a[k]; k++) ;
                if(j < a.length && k < a.length)
                    minDist = Math.min(minDist, j - i + k - i + k - j);
            }
            return minDist == Integer.MAX_VALUE ? -1 : minDist;
        }
    }

    static class s3745{//Maximize Expression of Three Elements
        public int maximizeExpressionOfThree(int[] a) {
            Arrays.sort(a);
            return a[a.length - 1] + a[a.length - 2] - a[0];
        }
    }

    static class s3769{//Sort Integers by Binary Reflection
        public int[] sortByReflection(int[] a){
            return Arrays.stream(a).boxed().sorted((n1, n2) -> {
                int b1 = binReflection(n1), b2 = binReflection(n2);
                return b1 != b2 ? b1 - b2 : n1 - n2;
            }).mapToInt(n -> n).toArray();
        }

        int binReflection(int n){
            String s = Integer.toBinaryString(n);
            return Integer.parseInt(new StringBuilder(s).reverse().toString(), 2);
        }
    }

    static class s3754 {//Concatenate Non-Zero Digits and Multiply by Sum I
        public long sumAndMultiply(int n) {
            int x = 0, sum = 0;
            for (int m = 1; n > 0; n /= 10) {
                int d = n % 10;
                if (d > 0) {
                    x = m * d + x;
                    sum += d;
                    m *= 10;
                }
            }
            return 1L * x * sum;
        }
    }
}
