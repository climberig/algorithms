package leetcode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
public class p28{
    static class s2810{//Faulty Keyboard
        public String finalString(String s) {
            StringBuilder r = new StringBuilder();
            for (char c : s.toCharArray())
                if (c == 'i')
                    r.reverse();
                else r.append(c);
            return r.toString();
        }
    }

    static class s2815{//Max Pair Sum in an Array
        public int maxSum(int[] a) {
            List<PriorityQueue<Integer>> ll = IntStream.range(0, 10).mapToObj(i -> new PriorityQueue<Integer>(Comparator.reverseOrder())).toList();
            Arrays.stream(a).forEach(n -> ll.get(maxDigit(n)).offer(n));
            return ll.stream().filter(q -> q.size() > 1).mapToInt(q -> q.poll() + q.poll()).max().orElse(-1);
        }

        int maxDigit(int n) {
            int d = 0;
            for (; n > 0; n /= 10)
                d = Math.max(d, n % 10);
            return d;
        }
    }

    static class s2824{//Count Pairs Whose Sum is Less than Target
        public int countPairs(List<Integer> a, int target) {
            Collections.sort(a);
            int r = 0, i = 0;
            for (int j = a.size() - 1; j >= 0; j--) {
                for (; i < a.size() && a.get(i) + a.get(j) < target; i++) ;
                r += Math.min(i, j);
            }
            return r;
        }
    }

    static class s2828{//Check if a String Is an Acronym of Words
        public boolean isAcronym(List<String> words, String s) {
            return words.stream().map(w -> w.charAt(0) + "").collect(Collectors.joining("")).equals(s);
        }
    }

    static class s2829{//Determine the Minimum Sum of a k-avoiding Array
        public int minimumSum(int n, int k) {
            int r = 0, i = 1;
            for (; i - 1 + i < k && n > 0; n--, i++)
                r += i;
            for (i = k; n > 0; i++, n--)
                r += i;
            return r;
        }
    }

    static class s2833{//Furthest Point From Origin
        public int furthestDistanceFromOrigin(String moves) {
            int r = 0, l = 0;
            for (char c : moves.toCharArray())
                switch (c) {
                    case 'R' -> r++;
                    case 'L' -> l++;
                }
            return Math.abs(r - l) + moves.length() - r - l;
        }
    }

    static class s2834{//Find the Minimum Possible Sum of a Beautiful Array
        public int minimumPossibleSum(int n, int target) {
            long t = Math.min((target + 2) / 2 - 1, n);
            long r = ((1L + t) * t / 2) % 1_000_000_007;
            n -= t;
            if (n > 0)
                r = (r + (((long) target + target + n - 1L) * n / 2)) % 1_000_000_007;
            return (int) r;
        }
    }

    static class s2839{//Check if Strings Can be Made Equal With Operations I
        public boolean canBeEqual(String s1, String s2) {
            return s(s1, 0).equals(s(s2, 0)) && s(s1, 1).equals(s(s2, 1));
        }

        Set<Character> s(String s, int i) {
            return new TreeSet<>(Arrays.asList(s.charAt(i), s.charAt(i + 2)));
        }
    }

    static class s2843{//Count Symmetric Integers
        public int countSymmetricIntegers(int low, int high) {
            int r = 0;
            for (int n = low; n <= high; n++) {
                String s = String.valueOf(n);
                if (s.length() % 2 == 0) {
                    int sum = 0;
                    for (int i = 0; i < s.length() / 2; sum += s.charAt(i++) - '0') ;
                    for (int i = s.length() / 2; i < s.length(); sum -= s.charAt(i++) - '0') ;
                    r += sum == 0 ? 1 : 0;
                }
            }
            return r;
        }
    }
}
