package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class p37 {
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

    static class s3745{//Maximize Expression of Three Elements
        public int maximizeExpressionOfThree(int[] a) {
            Arrays.sort(a);
            return a[a.length - 1] + a[a.length - 2] - a[0];
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
