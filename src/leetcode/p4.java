package leetcode;
import leetcode.NTree.Node;

import java.util.*;
import java.util.stream.IntStream;

public class p4{
    static class s401{//Binary Watch
        public List<String> readBinaryWatch(int turnedOn){
            List<String> r = new ArrayList<>();
            for(int h = 0; h <= 11; h++)
                for(int m = 0; m <= 59; m++)
                    if(Integer.bitCount(h) + Integer.bitCount(m) == turnedOn)
                        r.add(String.format("%d:%02d", h, m));
            return r;
        }
    }

    static class s404{//Sum of Left Leaves
        public int sumOfLeftLeaves(TreeNode root){return sum(root, false);}

        int sum(TreeNode node, boolean left){
            if(node != null && node.left == null && node.right == null && left)
                return node.val;
            return node != null ? sum(node.left, true) + sum(node.right, false) : 0;
        }
    }

    static class s405{//Convert a Number to Hexadecimal
        public String toHex(int n){
            if(n == 0)
                return "0";
            String s = "0123456789abcdef";
            StringBuilder r = new StringBuilder();
            for(; n != 0; n >>>= 4)
                r.insert(0, s.charAt(n & 15));
            return r.toString();
        }
    }

    static class s408{//Valid Word Abbreviation, tricky test case "a", "01" should return false
        public boolean validWordAbbreviation(String word, String abbr){
            int i = 0, j = 0;
            for(char[] w = word.toCharArray(), a = abbr.toCharArray(); i < w.length && j < a.length; )
                if(w[i] == a[j]){
                    ++i;
                    ++j;
                }else{
                    if(a[j] <= '0' || a[j] > '9')
                        return false;
                    int n = 0;
                    while(j < a.length && Character.isDigit(a[j]))
                        n = 10 * n + a[j++] - '0';
                    i += n;
                }
            return i == word.length() && j == abbr.length();
        }
    }

    static class s409{//Longest Palindrome
        public int longestPalindrome(String s){
            BitSet bs = new BitSet();
            for(byte b : s.getBytes())
                bs.flip(b);
            return bs.cardinality() <= 1 ? s.length() : s.length() - bs.cardinality() + 1;
        }
    }

    static class s412{//Fizz Buzz
        public List<String> fizzBuzz(int n){
            List<String> r = new ArrayList<>(n);
            for(int i = 1, fizz = 3, buzz = 5; i <= n; i++)
                if(i == fizz && i == buzz){
                    fizz += 3;
                    buzz += 5;
                    r.add("FizzBuzz");
                }else if(i == fizz){
                    fizz += 3;
                    r.add("Fizz");
                }else if(i == buzz){
                    buzz += 5;
                    r.add("Buzz");
                }else r.add(String.valueOf(i));
            return r;
        }
    }

    static class s414{//Third Maximum Number
        public int thirdMax(int[] a){
            PriorityQueue<Integer> q = new PriorityQueue<>();
            Arrays.stream(a).filter(n -> !q.contains(n)).forEach(n -> {
                q.add(n);
                if(q.size() > 3)
                    q.poll();
            });
            if(q.size() == 2)
                q.poll();
            return q.poll();
        }
    }

    static class s415{//Add Strings
        public String addStrings(String n1, String n2){
            StringBuilder r = new StringBuilder();
            for(int i1 = n1.length() - 1, i2 = n2.length() - 1, carry = 0; i1 >= 0 || i2 >= 0 || carry == 1; i1--, i2--){
                int n = (i1 >= 0 ? n1.charAt(i1) - '0' : 0) + (i2 >= 0 ? n2.charAt(i2) - '0' : 0) + carry;
                r.append(n % 10);
                carry = n / 10;
            }
            return r.reverse().toString();
        }
    }

    static class s417{//Pacific Atlantic Water Flow
        public List<List<Integer>> pacificAtlantic(int[][] g){
            boolean[][] pacific = new boolean[g.length][g[0].length], atlantic = new boolean[g.length][g[0].length];
            for(int i = 0; i < g.length; i++){
                dfs(0, i, 0, g, pacific);
                dfs(0, i, g[0].length - 1, g, atlantic);
            }
            for(int i = 0; i < g[0].length; i++){
                dfs(0, 0, i, g, pacific);
                dfs(0, g.length - 1, i, g, atlantic);
            }
            List<List<Integer>> r = new ArrayList<>();
            for(int i = 0; i < g.length; i++)
                for(int j = 0; j < g[0].length; j++)
                    if(pacific[i][j] && atlantic[i][j])
                        r.add(Arrays.asList(i, j));
            return r;
        }

        int dfs(int prevVal, int r, int c, int[][] g, boolean[][] flow){
            if(0 <= r && r < g.length && 0 <= c && c < g[0].length && !flow[r][c]){
                flow[r][c] = prevVal <= g[r][c];
                if(flow[r][c])
                    return dfs(g[r][c], r + 1, c, g, flow) + dfs(g[r][c], r - 1, c, g, flow) + dfs(g[r][c], r, c + 1, g, flow) + dfs(g[r][c], r, c - 1, g, flow);
            }
            return 0;
        }
    }

    static class s422{//Valid Word Square
        public boolean validWordSquare(List<String> words){
            for(int i = 0, n = words.size(); i < n; i++)
                for(int j = 0; j < words.get(i).length(); j++)
                    if(j >= n || i >= words.get(j).length() || words.get(i).charAt(j) != words.get(j).charAt(i))
                        return false;
            return true;
        }
    }

    static class s431{//Encode N-ary Tree to Binary Tree
        public TreeNode encode(Node root){
            if(root == null)
                return null;
            TreeNode r = new TreeNode(root.val);
            if(root.children.isEmpty())
                return r;
            r.left = encode(root.children.get(0));
            TreeNode node = r.left;
            for(int i = 1; i < root.children.size(); i++){
                node.right = encode(root.children.get(i));
                node = node.right;
            }
            return r;
        }

        public Node decode(TreeNode root){
            if(root == null)
                return null;
            List<Node> children = new ArrayList<>();
            for(TreeNode node = root.left; node != null; node = node.right)
                children.add(decode(node));
            return new Node(root.val, children);
        }
    }

    static class s434{//Number of Segments in a String
        public int countSegments(String s){
            s = s.trim();
            return s.isEmpty() ? 0 : s.split("\\s+").length;
        }
    }

    static class s441{//Arranging Coins
        public int arrangeCoins(int n){
            int r = 0;
            for(; n >= 0; n -= r)
                r++;
            return r - 1;
        }
    }

    static class s443{//String Compression
        public int compress(char[] a){
            int j = 0;
            for(int i = 1, count = 1; i <= a.length; i++)
                if(i == a.length || a[i - 1] != a[i]){
                    a[j++] = a[i - 1];
                    if(count > 1)
                        for(char c : String.valueOf(count).toCharArray())
                            a[j++] = c;
                    count = 1;
                }else count++;
            return j;
        }
    }

    static class s448{//Find All Numbers Disappeared in an Array
        public List<Integer> findDisappearedNumbers(int[] a){
            for(int i = 0; i < a.length; i++)
                while(a[i] != a[a[i] - 1]){
                    int t = a[i];
                    a[i] = a[t - 1];
                    a[t - 1] = t;
                }
            List<Integer> r = new ArrayList<>();
            for(int i = 0; i < a.length; i++)
                if(a[i] != i + 1)
                    r.add(i + 1);
            return r;
        }
    }

    static class s451{//Sort Characters By Frequency
        public String frequencySort(String s){
            int[] counts = new int[256];
            s.chars().forEach(c -> counts[c]++);
            PriorityQueue<Integer> q = new PriorityQueue<>((a, b) -> counts[b] - counts[a]);
            IntStream.range(0, counts.length).filter(i -> counts[i] > 0).forEach(q::offer);
            StringBuilder r = new StringBuilder();
            while(!q.isEmpty()){
                int c = q.poll();
                r.append(String.valueOf((char) c).repeat(counts[c]));
            }
            return r.toString();
        }
    }

    static class s453{//Minimum Moves to Equal Array Elements
        public int minMoves(int[] a){
            int min = Arrays.stream(a).min().getAsInt();
            return Arrays.stream(a).map(n -> n - min).sum();
        }
    }

    static class s455{//Assign Cookies
        public int findContentChildren(int[] g, int[] s){
            Arrays.sort(g);
            Arrays.sort(s);
            int i = 0;
            for(int j = 0; i < g.length && j < s.length; j++)
                if(s[j] >= g[i])
                    i++;
            return i;
        }
    }

    static class s459{//Repeated Substring Pattern
        public boolean repeatedSubstringPattern(String s){
            String ss = s + s;
            return ss.substring(1, ss.length() - 1).contains(s);
        }
    }

    static class s461{//Hamming Distance
        public int hammingDistance(int x, int y){
            return Integer.bitCount(x ^ y);
        }
    }

    static class s463{//Island Perimeter
        public int islandPerimeter(int[][] g){
            int r = 0;
            for(int i = 0, n = g.length, m = g[0].length; i < g.length; i++)
                for(int j = 0; j < g[0].length; j++)
                    if(g[i][j] == 1){
                        r += i == 0 || g[i - 1][j] == 0 ? 1 : 0;
                        r += j == 0 || g[i][j - 1] == 0 ? 1 : 0;
                        r += i == n - 1 || g[i + 1][j] == 0 ? 1 : 0;
                        r += j == m - 1 || g[i][j + 1] == 0 ? 1 : 0;
                    }
            return r;
        }
    }

    static class s482{//License Key Formatting
        public String licenseKeyFormatting(String s, int k){
            StringBuilder r = new StringBuilder();
            for(int i = s.length() - 1, len = 0; i >= 0; i--)
                if(s.charAt(i) != '-'){
                    if(len == k){
                        r.append('-');
                        len = 0;
                    }
                    r.append(Character.toUpperCase(s.charAt(i)));
                    len++;
                }
            return r.reverse().toString();
        }
    }

    static class s484{//Find Permutation
        public int[] findPermutation(String s){
            int lastUsed = 0, r[] = new int[s.length() + 1], k = 0;
            for(int i = 0; i <= s.length(); i++)
                if(i == s.length() || s.charAt(i) == 'I'){
                    for(int n = i + 1; n > lastUsed; n--)
                        r[k++] = n;
                    lastUsed = i + 1;
                }
            return r;
        }
    }

    static class s485{//Max Consecutive Ones
        public int findMaxConsecutiveOnes(int[] a){
            int r = 0, count = 0;
            for(int n : a)
                if(n == 1)
                    r = Math.max(r, ++count);
                else count = 0;
            return r;
        }
    }

    static class s492{//Construct the Rectangle
        public int[] constructRectangle(int area){
            for(int w = (int) Math.sqrt(area); w >= 1; w--)
                if(area % w == 0)
                    return new int[]{area / w, w};
            return null;
        }
    }

    static class s495{//Teemo Attacking
        public int findPoisonedDuration(int[] timeSeries, int duration){
            int r = duration;
            for(int i = 0; i < timeSeries.length - 1; i++)
                r += Math.min(timeSeries[i + 1] - timeSeries[i], duration);
            return r;
        }
    }

    static class s496{//Next Greater Element I
        public int[] nextGreaterElement(int[] a1, int[] a2){
            Map<Integer, Integer> m = new HashMap<>();
            Stack<Integer> s = new Stack<>();
            for(int n : a2){
                while(!s.isEmpty() && s.peek() < n)
                    m.put(s.pop(), n);
                s.push(n);
            }
            return Arrays.stream(a1).map(n -> m.getOrDefault(n, -1)).toArray();
        }
    }
}
