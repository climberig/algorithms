package leetcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.IntStream;

public class p36{
    static class s3663{//Find The Least Frequent Digit
        public int getLeastFrequentDigit(int n){
            int[] f = new int[10];
            for(; n > 0; n /= 10)
                f[n % 10]++;
            int minFreq = Arrays.stream(f).filter(d -> d > 0).min().getAsInt();
            return IntStream.range(0, 10).filter(d -> f[d] == minFreq).findFirst().getAsInt();
        }
    }

    static class s3668{//Restore Finishing Order
        public int[] recoverOrder(int[] order, int[] friends){
            int r[] = new int[friends.length], j = 0;
            for(int person : order)
                for(int friend : friends)
                    if(person == friend)
                        r[j++] = friend;
            return r;
        }
    }

    static class s3674{//Minimum Operations to Equalize Array
        public int minOperations(int[] a){
            for(int i = 1; i < a.length; i++)
                if(a[0] != a[i])
                    return 1;
            return 0;
        }
    }

    static class s3678{//Smallest Absent Positive Greater Than Average
        public int smallestAbsent(int[] a){
            Set<Integer> s = new HashSet<>();
            int sum = 0;
            for(int n : a){
                sum += n;
                s.add(n);
            }
            for(int avr = sum / a.length, n = avr; ; n++)
                if(n > avr && n > 0 && !s.contains(n))
                    return n;
        }
    }

    static class s3683{//Earliest Time to Finish One Task
        public int earliestTime(int[][] tasks){
            int r = Integer.MAX_VALUE;
            for(int[] t : tasks)
                r = Math.min(r, t[0] + t[1]);
            return r;
        }
    }

    static class s3697{//Compute Decimal Representation
        public int[] decimalRepresentation(int n){
            LinkedList<Integer> r = new LinkedList<>();
            for(int m = 1; n > 0; n /= 10, m *= 10){
                int d = n % 10;
                if(d != 0)
                    r.addFirst(d * m);
            }
            return r.stream().mapToInt(i -> i).toArray();
        }
    }
}
