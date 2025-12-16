package leetcode;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class p37 {
    static class s3707{//Equal Score Substrings
        public boolean scoreBalance(String s){
            int sum = 0;
            for(int i = 0; i < s.length(); i++)
                sum += s.charAt(i) - 'a' + 1;
            for(int i = 0, left = 0; i < s.length(); i++){
                left += s.charAt(i) - 'a' + 1;
                if(left == sum - left)
                    return true;
            }
            return false;
        }
    }

    static class s3712{//Sum of Elements With Frequency Divisible by K
        public int sumDivisibleByK(int[] a, int k){
            int f[] = new int[101], r = 0;
            Arrays.stream(a).forEach(n -> f[n]++);
            for(int i = 0; i < f.length; i++)
                if(f[i] % k == 0)
                    r += i * f[i];
            return r;
        }
    }

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

    static class s3736{//Minimum Moves to Equal Array Elements III
        public int minMoves(int[] a){
            return Arrays.stream(a).max().getAsInt() * a.length - Arrays.stream(a).sum();
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

    static class s3741{//Minimum Distance Between Three Equal Elements II
        public int minimumDistance(int[] a){
            int r = Integer.MAX_VALUE;
            Map<Integer, List<Integer>> m = new HashMap<>();
            for(int i = 0; i < a.length; i++){
                m.putIfAbsent(a[i], new ArrayList<>());
                m.get(a[i]).add(i);
            }
            for(List<Integer> list : m.values())
                for(int i = 2; i < list.size(); i++)
                    r = Math.min(r, 2 * (list.get(i) - list.get(i - 2)));
            return r == Integer.MAX_VALUE ? -1 : r;
        }
    }

    static class s3745{//Maximize Expression of Three Elements
        public int maximizeExpressionOfThree(int[] a) {
            Arrays.sort(a);
            return a[a.length - 1] + a[a.length - 2] - a[0];
        }
    }

    static class s3750{//Minimum Number of Flips to Reverse Binary String
        public int minimumFlips(int n){
            String s = Integer.toBinaryString(n);
            return (int) IntStream.range(0, s.length())
                    .filter(i -> s.charAt(i) != s.charAt(s.length() - i - 1))
                    .count();
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

    static class s3774{//Absolute Difference Between Maximum and Minimum K Elements
        public int absDifference(int[] a, int k){
            Arrays.sort(a);
            int r = 0;
            for(int i = 0; i < k; i++)
                r += a[a.length - 1 - i] - a[i];
            return r;
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
