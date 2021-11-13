package leetcode;

import java.util.*;

public class p400 {
    static class s401 {//Binary Watch
        public List<String> readBinaryWatch(int turnedOn) {
            List<String> r = new ArrayList<>();
            for (int h = 0; h <= 11; h++)
                for (int m = 0; m <= 59; m++)
                    if (Integer.bitCount(h) + Integer.bitCount(m) == turnedOn)
                        r.add(String.format("%d:%02d", h, m));
            return r;
        }
    }

    static class s404 {//Sum of Left Leaves
        public int sumOfLeftLeaves(TreeNode root) {return sum(root, false);}

        int sum(TreeNode node, boolean left) {
            if (node != null && node.left == null && node.right == null && left)
                return node.val;
            return node != null ? sum(node.left, true) + sum(node.right, false) : 0;
        }
    }

    static class s408 {//Valid Word Abbreviation, tricky test case "a", "01" should return false
        public boolean validWordAbbreviation(String word, String abbr) {
            int i = 0, j = 0;
            for (char[] w = word.toCharArray(), a = abbr.toCharArray(); i < w.length && j < a.length; )
                if (w[i] == a[j]) {
                    ++i;
                    ++j;
                } else {
                    if (a[j] <= '0' || a[j] > '9')
                        return false;
                    int n = 0;
                    while (j < a.length && Character.isDigit(a[j]))
                        n = 10 * n + a[j++] - '0';
                    i += n;
                }
            return i == word.length() && j == abbr.length();
        }
    }

    static class s409 {//Longest Palindrome
        public int longestPalindrome(String s) {
            BitSet bs = new BitSet();
            for (byte b : s.getBytes())
                bs.flip(b);
            return bs.cardinality() <= 1 ? s.length() : s.length() - bs.cardinality() + 1;
        }
    }

    static class s412 {//Fizz Buzz
        public List<String> fizzBuzz(int n) {
            List<String> r = new ArrayList<>(n);
            for (int i = 1, fizz = 3, buzz = 5; i <= n; i++)
                if (i == fizz && i == buzz) {
                    fizz += 3;
                    buzz += 5;
                    r.add("FizzBuzz");
                } else if (i == fizz) {
                    fizz += 3;
                    r.add("Fizz");
                } else if (i == buzz) {
                    buzz += 5;
                    r.add("Buzz");
                } else r.add(String.valueOf(i));
            return r;
        }
    }

    static class s414 {//Third Maximum Number
        public int thirdMax(int[] a) {
            PriorityQueue<Integer> q = new PriorityQueue<>();
            Arrays.stream(a).filter(n -> !q.contains(n)).forEach(n -> {
                q.add(n);
                if (q.size() > 3)
                    q.poll();
            });
            if (q.size() == 2)
                q.poll();
            return q.poll();
        }
    }

    static class s415 {//Add Strings
        public String addStrings(String n1, String n2) {
            StringBuilder r = new StringBuilder();
            for (int i1 = n1.length() - 1, i2 = n2.length() - 1, carry = 0; i1 >= 0 || i2 >= 0 || carry == 1; i1--, i2--) {
                int n = (i1 >= 0 ? n1.charAt(i1) - '0' : 0) + (i2 >= 0 ? n2.charAt(i2) - '0' : 0) + carry;
                r.append(n % 10);
                carry = n / 10;
            }
            return r.reverse().toString();
        }
    }

    static class s422 {//Valid Word Square
        public boolean validWordSquare(List<String> words) {
            for (int i = 0, n = words.size(); i < n; i++)
                for (int j = 0; j < words.get(i).length(); j++)
                    if (j >= n || i >= words.get(j).length() || words.get(i).charAt(j) != words.get(j).charAt(i))
                        return false;
            return true;
        }
    }

    static class s434 {//Number of Segments in a String
        public int countSegments(String s) {
            s = s.trim();
            return s.isEmpty() ? 0 : s.split("\\s+").length;
        }
    }

    static class s441 {//Arranging Coins
        public int arrangeCoins(int n) {
            int r = 0;
            for (; n >= 0; n -= r)
                r++;
            return r - 1;
        }
    }

    static class s443 {//String Compression
        public int compress(char[] a) {
            int j = 0;
            for (int i = 1, count = 1; i <= a.length; i++)
                if (i == a.length || a[i - 1] != a[i]) {
                    a[j++] = a[i - 1];
                    if (count > 1)
                        for (char c : String.valueOf(count).toCharArray())
                            a[j++] = c;
                    count = 1;
                } else count++;
            return j;
        }
    }

    static class s461 {//Hamming Distance
        public int hammingDistance(int x, int y) {
            return Integer.bitCount(x ^ y);
        }
    }

    static class s463 {//Island Perimeter
        public int islandPerimeter(int[][] g) {
            int r = 0;
            for (int i = 0, n = g.length, m = g[0].length; i < g.length; i++)
                for (int j = 0; j < g[0].length; j++)
                    if (g[i][j] == 1) {
                        r += i == 0 || g[i - 1][j] == 0 ? 1 : 0;
                        r += j == 0 || g[i][j - 1] == 0 ? 1 : 0;
                        r += i == n - 1 || g[i + 1][j] == 0 ? 1 : 0;
                        r += j == m - 1 || g[i][j + 1] == 0 ? 1 : 0;
                    }
            return r;
        }
    }

    static class s482 {//License Key Formatting
        public String licenseKeyFormatting(String s, int k) {
            StringBuilder r = new StringBuilder();
            for (int i = s.length() - 1, len = 0; i >= 0; i--)
                if (s.charAt(i) != '-') {
                    if (len == k) {
                        r.append('-');
                        len = 0;
                    }
                    r.append(Character.toUpperCase(s.charAt(i)));
                    len++;
                }
            return r.reverse().toString();
        }
    }

    static class s485 {//Max Consecutive Ones
        public int findMaxConsecutiveOnes(int[] a) {
            int r = 0, count = 0;
            for (int n : a)
                if (n == 1)
                    r = Math.max(r, ++count);
                else count = 0;
            return r;
        }
    }
}
