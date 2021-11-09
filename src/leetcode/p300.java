package leetcode;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class p300 {
    static class s303 {//Range Sum Query - Immutable
        class NumArray {
            final int[] cs;

            public NumArray(int[] a) {
                cs = new int[a.length + 1];
                for (int i = 0; i < a.length; i++)
                    cs[i + 1] = cs[i] + a[i];
            }

            public int sumRange(int left, int right) {return cs[right + 1] - cs[left];}
        }
    }

    static class s314 {//Binary Tree Vertical Order Traversal
        public List<List<Integer>> verticalOrder(TreeNode root) {
            Map<Integer, List<Integer>> m = new TreeMap<>();
            Queue<Pair<TreeNode, Integer>> q = new LinkedList<>();
            for (q.offer(new Pair<>(root, 0)); root != null && !q.isEmpty(); ) {
                Pair<TreeNode, Integer> p = q.poll();
                TreeNode node = p.getKey();
                m.computeIfAbsent(p.getValue(), l -> new ArrayList<>()).add(node.val);
                if (node.left != null)
                    q.offer(new Pair<>(node.left, p.getValue() - 1));
                if (node.right != null)
                    q.offer(new Pair<>(node.right, p.getValue() + 1));
            }
            return new ArrayList<>(m.values());
        }
    }

    static class s320 {//Generalized Abbreviation
        public List<String> generateAbbreviations(String word) {
            List<String> r = new ArrayList<>((int) Math.pow(2, word.length()));
            bt(word.toCharArray(), 0, 0, "", r);
            return r;
        }

        void bt(char[] a, int i, int count, String s, List<String> r) {
            if (i < a.length) {
                bt(a, i + 1, 0, s + (count > 0 ? count : "") + a[i], r);
                bt(a, i + 1, count + 1, s, r);
            } else r.add(s + (count > 0 ? count : ""));
        }
    }

    static class s322 {//Coin Change
        public int coinChange(int[] coins, int amount) {
            int[] dp = new int[amount + 1];
            Arrays.fill(dp, amount + 1);
            dp[0] = 0;
            for (int i = 1; i < dp.length; i++)
                for (int coin : coins)
                    if (coin <= i)
                        dp[i] = Math.min(dp[i], dp[i - coin] + 1);
            return dp[amount] <= amount ? dp[amount] : -1;
        }
    }

    static class s323 {//Number of Connected Components in an Undirected Graph
        public int countComponents(int n, int[][] edges) {
            int[] uf = IntStream.range(0, n).toArray();
            for (int[] e : edges) {
                int x = find(e[0], uf), y = find(e[1], uf);
                if (x != y) {
                    uf[x] = y;
                    n--;
                }
            }
            return n;
        }

        int find(int i, int[] uf) {return i == uf[i] ? i : (uf[i] = find(uf[i], uf));}
    }

    static class s339 {//Nested List Weight Sum
        public int depthSum(List<NestedInteger> nestedList) {
            int r = 0, level = 1;
            for (var q = new LinkedList<>(nestedList); !q.isEmpty(); level++)
                for (int size = q.size(); size > 0; size--)
                    if (q.peek().isInteger())
                        r += q.poll().getInteger() * level;
                    else q.addAll(q.poll().getList());
            return r;
        }
    }

    static class s341 {//Flatten Nested List Iterator
        public class NestedIterator implements Iterator<Integer> {
            Stack<ListIterator<NestedInteger>> s = new Stack<>();

            public NestedIterator(List<NestedInteger> nestedList) {
                s.push(nestedList.listIterator());
                hasNext();
            }

            @Override
            public Integer next() {
                hasNext();
                return s.peek().next().getInteger();
            }

            @Override
            public boolean hasNext() {
                while (!s.empty())
                    if (s.peek().hasNext()) {
                        NestedInteger ni = s.peek().next();
                        if (ni.isInteger())
                            return s.peek().previous() == ni;//rolls back the iterator!
                        s.push(ni.getList().listIterator());
                    } else s.pop();
                return false;
            }
        }
    }

    static class s342 {//Power of Four
        public boolean isPowerOfFour(int n) {
            return n > 0 && (n & (n - 1)) == 0 && (0x55555555 & n) != 0;
        }
    }

    static class s344 {//Reverse String
        public void reverseString(char[] s) {
            for (int i = 0, j = s.length - 1; i < j; i++, j--) {
                char t = s[i];
                s[i] = s[j];
                s[j] = t;
            }
        }
    }

    static class s345 {//Reverse Vowels of a String
        public String reverseVowels(String s) {
            Set<Character> vowels = Set.of('a', 'A', 'e', 'E', 'i', 'I', 'o', 'O', 'u', 'U');
            char[] a = s.toCharArray();
            for (int i = 0, j = a.length - 1; i < j; i++, j--) {
                for (; i < j && !vowels.contains(a[i]); i++) ;
                for (; i < j && !vowels.contains(a[j]); j--) ;
                if (i < j) {
                    char t = a[i];
                    a[i] = a[j];
                    a[j] = t;
                }
            }
            return new String(a);
        }
    }

    static class s346 {//Moving Average from Data Stream
        class MovingAverage {
            final LinkedList<Integer> list = new LinkedList<>();
            int size, sum;

            public MovingAverage(int size) {this.size = size;}

            public double next(int val) {
                list.addLast(val);
                sum += val;
                if (list.size() > size)
                    sum -= list.removeFirst();
                return sum * 1.0 / list.size();
            }
        }
    }

    static class s347 {//Top K Frequent Elements
        public int[] topKFrequent(int[] a, int k) {
            Map<Integer, Integer> fr = new HashMap<>();
            Arrays.stream(a).forEach(n -> fr.put(n, fr.getOrDefault(n, 0) + 1));
            List<List<Integer>> buckets = IntStream.range(0, a.length + 1).mapToObj(n -> new ArrayList<Integer>()).collect(Collectors.toList());
            fr.keySet().forEach(n -> buckets.get(fr.get(n)).add(n));
            int[] r = new int[k];
            for (int i = buckets.size() - 1, j = 0; j < k; i--)
                for (Integer n : buckets.get(i))
                    r[j++] = n;
            return r;
        }
    }

    static class s348 {//Design Tic-Tac-Toe
        class TicTacToe {
            int rows[], cols[], d1, d2;

            public TicTacToe(int n) {
                rows = new int[n];
                cols = new int[n];
            }

            public int move(int row, int col, int player) {
                int v = player == 1 ? 1 : -1, N = rows.length;
                rows[row] += v;
                cols[col] += v;
                d1 += row == col ? v : 0;
                d2 += row + col + 1 == N ? v : 0;
                return won(N, rows[row], cols[col], d1, d2) ? player : 0;
            }

            boolean won(int N, int... vals) {return Arrays.stream(vals).anyMatch(v -> Math.abs(v) == N);}
        }
    }

    static class s349 {//Intersection of Two Arrays
        public int[] intersection(int[] a1, int[] a2) {
            boolean[] a = new boolean[1_001];
            Arrays.stream(a1).forEach(n -> a[n] = true);
            List<Integer> r = new ArrayList<>();
            Arrays.stream(a2).filter(n -> a[n]).forEach(n -> {
                a[n] = false;
                r.add(n);
            });
            return r.stream().mapToInt(i -> i).toArray();
        }
    }

    static class s350 {//Intersection of Two Arrays II
        public int[] intersect(int[] a1, int[] a2) {
            int[] c1 = new int[1_001], c2 = new int[1_001];
            Arrays.stream(a1).forEach(n -> c1[n]++);
            Arrays.stream(a2).forEach(n -> c2[n]++);
            int[] r = new int[IntStream.range(0, c1.length).map(i -> Math.min(c1[i], c2[i])).sum()];
            for (int n = 0, i = 0; n < c1.length; n++)
                for (int j = 0; j < Math.min(c1[n], c2[n]); j++)
                    r[i++] = n;
            return r;
        }
    }

    static class s353 {//Design Snake Game
        class SnakeGame {
            Set<String> taken = new HashSet<>();
            Deque<int[]> snake = new LinkedList<>();
            int foodIdx = 0, width, height, food[][];
            Map<String, int[]> dirs = Map.of("U", new int[]{-1, 0}, "D", new int[]{1, 0}, "L", new int[]{0, -1}, "R", new int[]{0, 1});

            public SnakeGame(int width, int height, int[][] food) {
                this.width = width;
                this.height = height;
                this.food = food;
                taken.add("0,0");
                snake.add(new int[]{0, 0});
            }

            public int move(String dir) {
                int p[] = snake.peek(), x = p[0] + dirs.get(dir)[0], y = p[1] + dirs.get(dir)[1];
                if (!(foodIdx < food.length && food[foodIdx][0] == x && food[foodIdx][1] == y)) {
                    int[] rm = snake.removeLast();
                    taken.remove(rm[0] + "," + rm[1]);
                } else foodIdx++;
                if (x < 0 || x == height || y < 0 || y == width || !taken.add(x + "," + y))
                    return -1;
                snake.addFirst(new int[]{x, y});
                return snake.size() - 1;
            }
        }
    }

    static class s367 {//Valid Perfect Square
        public boolean isPerfectSquare(int n) {
            for (int i = 1; n > 0; i += 2)
                n -= i;
            return n == 0;
        }
    }

    static class s379 {//Design Phone Directory
        class PhoneDirectory {
            final Set<Integer> s = new HashSet<>();

            public PhoneDirectory(int maxNumbers) {IntStream.range(0, maxNumbers).forEach(s::add);}

            public int get() {
                if (s.isEmpty())
                    return -1;
                Integer n = s.iterator().next();
                s.remove(n);
                return n;
            }

            public boolean check(int number) {return s.contains(number);}

            public void release(int number) {s.add(number);}
        }
    }

    static class s387 {
        public int firstUniqChar(String s) {
            int[] f = new int[26];
            s.chars().forEach(c -> f[c - 'a']++);
            return IntStream.range(0, s.length()).filter(i -> f[s.charAt(i) - 'a'] == 1).findFirst().orElse(-1);
        }
    }

    static class s392 {//Is Subsequence
        public boolean isSubsequence(String s, String t) {
            for (int i = 0, j = 0; i < s.length() && j < t.length(); j++)
                if (s.charAt(i) == t.charAt(j) && ++i == s.length())
                    return true;
            return s.isEmpty();
        }
    }
}
