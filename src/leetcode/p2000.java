package leetcode;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class p2000{
    static class s2001{//Number of Pairs of Interchangeable Rectangles
        public long interchangeableRectangles(int[][] rectangles){
            Map<String, Integer> m = new HashMap<>();
            long r = 0;
            for(int[] rec : rectangles){
                int gcd = gcd(rec[0], rec[1]);
                String f = rec[0] / gcd + "/" + rec[1] / gcd;
                r += m.getOrDefault(f, 0);
                m.put(f, m.getOrDefault(f, 0) + 1);
            }
            return r;
        }

        int gcd(int a, int b){return b == 0 ? a : gcd(b, a % b);}
    }

    static class s2002{//Maximum Product of the Length of Two Palindromic Subsequences
        public int maxProduct(String s){
            char[] a = s.toCharArray();
            int r = 0, len = (int) Math.pow(2, s.length());
            List<Integer> pMasks = IntStream.range(1, len).filter(n -> isPalindrome(mask(a, n))).boxed().collect(Collectors.toList());
            for(int i = 0; i < pMasks.size(); i++)
                for(int j = i + 1; j < pMasks.size(); j++)
                    if((pMasks.get(i) & pMasks.get(j)) == 0)
                        r = Math.max(r, Integer.bitCount(pMasks.get(i)) * Integer.bitCount(pMasks.get(j)));
            return r;
        }

        char[] mask(char[] a, int n){
            StringBuilder r = new StringBuilder();
            for(int i = a.length - 1; i >= 0; i--, n >>= 1)
                if((n & 1) == 1)
                    r.append(a[i]);
            return r.reverse().toString().toCharArray();
        }

        boolean isPalindrome(char[] a){
            for(int i = 0, j = a.length - 1; i < j; i++, j--)
                if(a[i] != a[j])
                    return false;
            return true;
        }
    }

    static class s2006{//Count Number of Pairs With Absolute Difference K
        public int countKDifference(int[] a, int k){
            int r = 0;
            for(int i = 0; i < a.length; i++)
                for(int j = i + 1; j < a.length; j++)
                    if(Math.abs(a[i] - a[j]) == k)
                        r++;
            return r;
        }
    }

    static class s2007{// Find Original Array From Doubled Array
        public int[] findOriginalArray(int[] changed){
            if(changed.length % 2 != 0)
                return new int[0];
            int[] r = new int[changed.length / 2], freq = new int[100_001];
            Arrays.stream(changed).forEach(n -> freq[n]++);
            for(int n = 0, i = 0; n < freq.length; n++)
                if(freq[n] > 0){
                    if(n * 2 >= freq.length || freq[n] > freq[2 * n])
                        return new int[0];
                    for(; freq[n] > 0; r[i++] = n, freq[n]--, freq[2 * n]--) ;
                }
            return r;
        }
    }

    static class s2008{//Maximum Earnings From Taxi
        public long maxTaxiEarnings(int n, int[][] rides){
            long[] dp = new long[n + 1];
            Arrays.sort(rides, Comparator.comparingInt(r -> r[1]));
            for(int i = 1, j = 0; i <= n; i++){
                while(j < rides.length && rides[j][1] == i)
                    dp[i] = Math.max(dp[i], rides[j][1] - rides[j][0] + rides[j][2] + dp[rides[j++][0]]);
                dp[i] = Math.max(dp[i], dp[i - 1]);
            }
            return dp[n];
        }

        public long maxTaxiEarnings1(int n, int[][] rides){
            long[] dp = new long[n + 1];
            Map<Integer, List<int[]>> m = new HashMap<>();
            Arrays.stream(rides).forEach(r -> m.computeIfAbsent(r[1], l -> new ArrayList<>()).add(r));
            for(int i = 1; i <= n; i++){
                for(int[] r : m.getOrDefault(i, Collections.emptyList()))
                    dp[i] = Math.max(dp[i], r[1] - r[0] + r[2] + dp[r[0]]);
                dp[i] = Math.max(dp[i], dp[i - 1]);
            }
            return dp[n];
        }
    }

    static class s2011{//Final Value of Variable After Performing Operations
        public int finalValueAfterOperations(String[] ops){
            return Arrays.stream(ops).mapToInt(op -> op.contains("+") ? 1 : -1).sum();
        }
    }

    static class s2012{//Sum of Beauty in the Array
        public int sumOfBeauties(int[] a){
            int r = 0, min[] = new int[a.length];
            min[a.length - 1] = a[a.length - 1];
            for(int i = a.length - 2; i >= 0; i--)
                min[i] = Math.min(a[i], min[i + 1]);
            for(int i = 1, max = a[0]; i < a.length - 1; max = Math.max(max, a[i++]))
                if(max < a[i] && a[i] < min[i + 1])
                    r += 2;
                else if(a[i - 1] < a[i] && a[i] < a[i + 1])
                    r += 1;
            return r;
        }
    }

    static class s2013{//Detect Squares
        class DetectSquares{
            int[][] counts = new int[1_001][1_001];
            List<int[]> points = new ArrayList<>();

            public void add(int[] point){
                counts[point[0]][point[1]] += 1;
                points.add(point);
            }

            public int count(int[] point){
                int x = point[0], y = point[1], r = 0;
                for(int[] p1 : points){
                    int x1 = p1[0], y1 = p1[1];
                    if(Math.abs(x - x1) != 0 && Math.abs(x - x1) == Math.abs(y - y1))
                        r += counts[x][y1] * counts[x1][y];
                }
                return r;
            }
        }
    }

    static class s2015{//Average Height of Buildings in Each Segment
        public int[][] averageHeightOfBuildings(int[][] buildings){
            Map<Integer, Integer> height = new TreeMap<>(), count = new TreeMap<>();
            for(int[] b : buildings){
                height.put(b[0], height.getOrDefault(b[0], 0) + b[2]);
                height.put(b[1], height.getOrDefault(b[1], 0) - b[2]);
                count.put(b[0], count.getOrDefault(b[0], 0) + 1);
                count.put(b[1], count.getOrDefault(b[1], 0) - 1);
            }
            LinkedList<int[]> r = new LinkedList<>();
            r.add(new int[]{-1, -1, 0});
            int sumH = 0, sumC = 0;
            for(Integer b : height.keySet()){
                sumH += height.get(b);
                sumC += count.get(b);
                if(sumC - count.get(b) != 0)
                    r.peekLast()[1] = b;
                if(sumC > 0){
                    int avr = sumH / sumC;
                    if((r.peekLast()[2] != avr && avr > 0) || sumC - count.get(b) == 0)
                        r.add(new int[]{b, b, avr});
                }
            }
            int a[][] = new int[r.size() - 1][3], i = 0;
            r.removeFirst();
            for(int[] v : r)
                a[i++] = v;
            return a;
        }
    }

    static class s2016{//Maximum Difference Between Increasing Elements
        public int maximumDifference(int[] a){
            int r = -1, i = 0;
            for(int j = 1; j < a.length; j++)
                if(a[i] < a[j])
                    r = Math.max(r, a[j] - a[i]);
                else i = j;
            return r;
        }
    }

    static class s2018{//Check if Word Can Be Placed In Crossword
        public boolean placeWordInCrossword(char[][] b, String word){
            char[] w = word.toCharArray(), rw = new StringBuilder(word).reverse().toString().toCharArray();
            for(int i = 0; i < b.length; i++)
                for(int j = 0; j < b[0].length; j++)
                    if(down(i, j, b, w) || down(i, j, b, rw) || right(i, j, b, w) || right(i, j, b, rw))
                        return true;
            return false;
        }

        boolean right(int r, int c, char[][] b, char[] w){
            if(c > 0 && b[r][c - 1] != '#' || c + w.length > b[0].length)
                return false;
            int i = 0;
            for(; i < w.length; i++)
                if(b[r][c + i] != w[i] && b[r][c + i] != ' ')
                    return false;
            return c + i == b[0].length || b[r][c + i] == '#';
        }

        boolean down(int r, int c, char[][] b, char[] w){
            if(r > 0 && b[r - 1][c] != '#' || r + w.length > b.length)
                return false;
            int i = 0;
            for(; i < w.length; i++)
                if(b[r + i][c] != w[i] && b[r + i][c] != ' ')
                    return false;
            return r + i == b.length || b[r + i][c] == '#';
        }
    }

    static class s2017{//Grid Game
        public long gridGame(int[][] g){
            long top = Arrays.stream(g[0]).asLongStream().sum(), bottom = 0, r = Long.MAX_VALUE;
            for(int i = 0; i < g[0].length; i++){
                top -= g[0][i];
                r = Math.min(r, Math.max(top, bottom));
                bottom += g[1][i];
            }
            return r;
        }
    }

    static class s2021{//Brightest Position on Street
        /**
         * A perfectly straight street is represented by a number line. The street has street lamp(s)
         * on it and is represented by a 2D integer array lights. Each lights[i] = [positioni, rangei]
         * indicates that there is a street lamp at position positioni that lights up the area from
         * [positioni - rangei, positioni + rangei] (inclusive). The brightness of a position p is defined
         * as the number of street lamp that light up the position p. Given lights, return the brightest
         * position on the street. If there are multiple brightest positions, return the smallest one.
         */
        public int brightestPosition(int[][] lights){
            Map<Integer, Integer> m = new TreeMap<>();
            for(int[] l : lights){
                int left = l[0] - l[1], right = l[0] + l[1];
                m.put(left, m.getOrDefault(left, 0) + 1);
                m.put(right + 1, m.getOrDefault(right + 1, 0) - 1);
            }
            int maxLight = Integer.MIN_VALUE, sum = 0, minPosition = 0;
            for(Integer p : m.keySet()){
                sum += m.get(p);
                if(sum > maxLight){
                    maxLight = sum;
                    minPosition = p;
                }
            }
            return minPosition;
        }
    }

    static class s2022{//Convert 1D Array Into 2D Array
        public int[][] construct2DArray(int[] origin, int n, int m){
            if(n * m != origin.length)
                return new int[0][];
            int[][] r = new int[n][m];
            for(int i = 0; i < origin.length; i++)
                r[i / m][i % m] = origin[i];
            return r;
        }
    }

    static class s2023{//Number of Pairs of Strings With Concatenation Equal to Target
        public int numOfPairs(String[] a, String target){
            int r = 0;
            Map<String, Integer> starts = new HashMap<>(), ends = new HashMap<>();
            for(String n : a){
                if(target.startsWith(n))
                    r += ends.getOrDefault(target.substring(n.length()), 0);
                if(target.endsWith(n))
                    r += starts.getOrDefault(target.substring(0, target.length() - n.length()), 0);
                if(target.startsWith(n))
                    starts.put(n, starts.getOrDefault(n, 0) + 1);
                if(target.endsWith(n))
                    ends.put(n, ends.getOrDefault(n, 0) + 1);
            }
            return r;
        }

        public int numOfPairs1(String[] a, String target){
            int r = 0;
            for(int i = 0; i < a.length; i++)
                for(int j = i + 1; j < a.length; j++){
                    if((a[i] + a[j]).equals(target))
                        r++;
                    if((a[j] + a[i]).equals(target))
                        r++;
                }
            return r;
        }
    }

    static class s2024{//Maximize the Confusion of an Exam
        public int maxConsecutiveAnswers(String s, int k){
            return Math.max(max(s, 'T', k), max(s, 'F', k));
        }

        int max(String s, char c, int k){
            int r = 0;
            for(int i = 0, j = 0; j < s.length(); j++){
                k -= s.charAt(j) != c ? 1 : 0;
                while(k < 0)
                    k += s.charAt(i++) != c ? 1 : 0;
                r = Math.max(r, j - i + 1);
            }
            return r;
        }
    }

    static class s2027{//Minimum Moves to Convert String
        public int minimumMoves(String s){
            int r = 0;
            for(int i = 0; i < s.length(); i++)
                if(s.charAt(i) == 'X'){
                    r++;
                    i += 2;
                }
            return r;
        }
    }

    static class s2028{ //Find Missing Observations
        public int[] missingRolls(int[] rolls, int mean, int n){
            int sum = mean * (rolls.length + n) - Arrays.stream(rolls).sum();
            if(sum < n || sum > 6 * n)
                return new int[0];
            int r[] = new int[n];
            Arrays.fill(r, sum / n);
            for(int i = 0; i < sum % n; i++)
                r[i]++;
            return r;
        }
    }

    static class s2032{
        public List<Integer> twoOutOfThree(int[] a1, int[] a2, int[] a3){
            int[][] a = {a1, a2, a3}, c = new int[3][101];
            for(int i = 0; i < a.length; i++)
                for(int n : a[i])
                    c[i][n] = 1;
            return IntStream.range(1, 101).filter(n -> c[0][n] + c[1][n] + c[2][n] >= 2).boxed().collect(Collectors.toList());
        }
    }

    static class s2033{//Minimum Operations to Make a Uni-Value Grid
        public int minOperations(int[][] g, int x){
            int a[] = new int[g.length * g[0].length];
            for(int r = 0, i = 0; r < g.length; r++)
                for(int c = 0; c < g[0].length; c++)
                    a[i++] = g[r][c];
            Arrays.sort(a);
            int m = a[a.length / 2], r = 0;
            for(int n : a)
                if(Math.abs(n - m) % x != 0)
                    return -1;
                else r += Math.abs(n - m) / x;
            return r;
        }
    }

    static class s2034{//Stock Price Fluctuation
        class StockPrice{
            TreeMap<Integer, Integer> timePrice = new TreeMap<>(), prices = new TreeMap<>();

            public void update(int timestamp, int price){
                if(timePrice.containsKey(timestamp)){
                    int oldPrice = timePrice.remove(timestamp);
                    prices.put(oldPrice, prices.get(oldPrice) - 1);
                    if(prices.get(oldPrice) == 0)
                        prices.remove(oldPrice);
                }
                timePrice.put(timestamp, price);
                prices.put(price, prices.getOrDefault(price, 0) + 1);
            }

            public int current(){return timePrice.lastEntry().getValue();}

            public int maximum(){return prices.lastKey();}

            public int minimum(){return prices.firstKey();}
        }
    }

    static class s2036{//Maximum Alternating Subarray Sum
        /**
         * A subarray of a 0-indexed integer array is a contiguous non-empty sequence of elements
         * within an array. The alternating subarray sum of a subarray that ranges from index i to
         * j (inclusive, 0 <= i <= j < nums.length) is nums[i] - nums[i+1] + nums[i+2] - ... +/- nums[j].
         * Given a 0-indexed integer array nums, return the maximum alternating subarray sum of any subarray of nums
         */
        public long maximumAlternatingSubarraySum(int[] a){
            long r = Integer.MIN_VALUE, lastMinus = Integer.MIN_VALUE, lastPlus = Integer.MIN_VALUE;
            for(int n : a){
                long plus = Math.max(n, lastMinus + n);
                lastMinus = lastPlus - n;
                lastPlus = plus;
                r = Math.max(r, Math.max(lastMinus, lastPlus));
            }
            return r;
        }
    }

    static class s2037{//Minimum Number of Moves to Seat Everyone
        public int minMovesToSeat(int[] seats, int[] students){
            Arrays.sort(seats);
            Arrays.sort(students);
            return IntStream.range(0, seats.length).map(i -> Math.abs(seats[i] - students[i])).sum();
        }
    }

    static class s2038{//Remove Colored Pieces if Both Neighbors are the Same Color
        public boolean winnerOfGame(String s){
            int a = 0, b = 0;
            for(int i = 1; i < s.length() - 1; i++)
                if(s.charAt(i) == s.charAt(i - 1) && s.charAt(i) == s.charAt(i + 1))
                    if(s.charAt(i) == 'A')
                        a++;
                    else b++;
            return a > b;
        }
    }

    static class s2039{//The Time When the Network Becomes Idle
        public int networkBecomesIdle(int[][] edges, int[] patience){
            List<List<Integer>> g = IntStream.range(0, patience.length).mapToObj(l -> new ArrayList<Integer>()).collect(Collectors.toList());
            for(int[] e : edges){
                g.get(e[0]).add(e[1]);
                g.get(e[1]).add(e[0]);
            }
            Queue<Integer> q = new LinkedList<>();
            boolean[] seen = new boolean[patience.length];
            int r = 0, time = 0;
            for(seen[0] = true, q.add(0); !q.isEmpty(); time++)
                for(int size = q.size(); size > 0; size--){
                    int u = q.poll(), roundTripTime = 2 * time, idle = roundTripTime + 1;
                    if(patience[u] < roundTripTime)
                        idle += roundTripTime - (roundTripTime % patience[u] == 0 ? patience[u] : roundTripTime % patience[u]);
                    r = Math.max(r, idle);
                    for(Integer v : g.get(u))
                        if(!seen[v]){
                            q.offer(v);
                            seen[v] = true;
                        }
                }
            return r;
        }
    }

    static class s2042{//Check if Numbers Are Ascending in a Sentence
        public boolean areNumbersAscending(String s){
            char[] a = s.toCharArray();
            for(int i = 0, prev = -1, n = 0; i < a.length; i++)
                if(Character.isDigit(a[i])){
                    while(i < a.length && Character.isDigit(a[i]))
                        n = 10 * n + a[i++] - '0';
                    if(prev >= n)
                        return false;
                    prev = n;
                    n = 0;
                }
            return true;
        }
    }

    static class s2043{//Simple Bank System
        class Bank{
            final long[] accounts;

            public Bank(long[] balance){accounts = balance;}

            public boolean transfer(int i1, int i2, long money){
                if(i2 <= accounts.length && withdraw(i1, money))
                    return deposit(i2, money);
                else return false;
            }

            public boolean deposit(int i, long money){
                if(i <= accounts.length){
                    accounts[i - 1] += money;
                    return true;
                }else return false;
            }

            public boolean withdraw(int i, long money){
                if(i <= accounts.length && accounts[i - 1] >= money){
                    accounts[i - 1] -= money;
                    return true;
                }else return false;
            }
        }
    }

    static class s2044{//Count Number of Maximum Bitwise-OR Subsets
        int r = 0;
        public int countMaxOrSubsets(int[] a){
            bt(0, a, 0, Arrays.stream(a).reduce((x, y) -> x | y).getAsInt());
            return r;
        }

        void bt(int i, int[] a, int or, int maxOr){
            if(i < a.length){
                bt(i + 1, a, or | a[i], maxOr);
                bt(i + 1, a, or, maxOr);
            }else r += or == maxOr ? 1 : 0;
        }
    }

    static class s2046{//Sort Linked List Already Sorted Using Absolute Values
        public ListNode sortLinkedList(ListNode head){
            ListNode node = head.next, newHead = head, prev = head;
            while(node != null)
                if(node.val < 0){
                    ListNode next = node.next;
                    prev.next = next;
                    newHead = new ListNode(node.val, newHead);
                    node = next;
                }else{
                    prev = node;
                    node = node.next;
                }
            return newHead;
        }
    }

    static class s2047{//Number of Valid Words in a Sentence
        public int countValidWords(String s){
            String words[] = s.trim().split("\\s+"), pMarks = ".,!";
            int r = 0;
            for(String w : words){
                char[] a = w.toCharArray();
                int end = pMarks.indexOf(a[a.length - 1]) >= 0 ? a.length - 2 : a.length - 1, hyphenIdx = w.indexOf('-');
                if(hyphenIdx >= 0){
                    if(valid(a, 0, hyphenIdx - 1, false) && valid(a, hyphenIdx + 1, end, false))
                        r++;
                }else if(valid(a, 0, end, true))
                    r++;
            }
            return r;
        }
        boolean valid(char[] a, int lo, int hi, boolean canBeEmpty){
            if(lo > hi)
                return canBeEmpty;
            for(int i = lo; i <= hi; i++)
                if(a[i] < 'a' || 'z' < a[i])
                    return false;
            return true;
        }

        public int countValidWords1(String sentence){
            String words[] = sentence.trim().split("\\s+"), regex = "([a-z]+(-?[a-z]+)?)?[!\\.,]?";
            return (int) Arrays.stream(words).filter(w -> w.matches(regex)).count();
        }
    }

    static class s2048{//Next Greater Numerically Balanced Number
        public int nextBeautifulNumber(int n){
            Queue<String> q = new LinkedList<>();
            for(q.add(""); !q.isEmpty(); ){
                String s = q.poll();
                int counts[] = new int[7], num = s.isEmpty() ? 0 : Integer.parseInt(s);
                s.chars().forEach(c -> counts[c - '0']++);
                if(num > n && balanced(counts))
                    return num;
                if(valid(counts))
                    for(int i = 1; i <= 6; i++)
                        if(counts[i] < i)
                            q.offer(s + i);
            }
            return 1;
        }

        boolean balanced(int[] counts){
            for(int i = 1; i < counts.length; i++)
                if(counts[i] > 0 && counts[i] != i)
                    return false;
            return true;
        }

        boolean valid(int[] counts){
            for(int i = 1; i < counts.length; i++)
                if(counts[i] > i)
                    return false;
            return true;
        }
    }

    static class s2049{
        public int countHighestScoreNodes(int[] parents){
            List<Node> nodes = IntStream.range(0, parents.length).mapToObj(Node::new).collect(Collectors.toList());
            for(int i = 1; i < parents.length; i++){
                Node p = nodes.get(parents[i]);
                if(p.left == null)
                    p.left = nodes.get(i);
                else p.right = nodes.get(i);
            }
            TreeMap<Long, Integer> m = new TreeMap<>();
            traverse(nodes.get(0), m, parents.length);
            return m.get(m.lastKey());
        }

        int traverse(Node node, TreeMap<Long, Integer> m, int n){
            if(node == null)
                return 0;
            int left = traverse(node.left, m, n), right = traverse(node.right, m, n);
            long score = (long) Math.max(left, 1) * Math.max(right, 1) * Math.max(n - left - right - 1, 1);
            m.put(score, m.getOrDefault(score, 0) + 1);
            return left + right + 1;
        }

        class Node{
            Node left, right;
            int val;

            Node(int val){
                this.val = val;
            }
        }
    }

    static class s2050{//Parallel Courses III
        public int minimumTime(int n, int[][] relations, int[] time){
            int[] degree = new int[n], dist = new int[n];
            List<List<Integer>> g = IntStream.range(0, n).mapToObj(i -> new ArrayList<Integer>()).collect(Collectors.toList());
            for(int[] r : relations){
                degree[r[0] - 1]++;
                g.get(r[1] - 1).add(r[0] - 1);
            }
            Queue<Integer> q = new LinkedList<>();
            for(int u = 0; u < n; u++)
                if(degree[u] == 0){
                    q.offer(u);
                    dist[u] = time[u];
                }
            while(!q.isEmpty()){
                int u = q.poll();
                for(Integer v : g.get(u)){
                    dist[v] = Math.max(dist[v], dist[u] + time[v]);
                    if(--degree[v] == 0)
                        q.offer(v);
                }
            }
            return Arrays.stream(dist).max().getAsInt();
        }
    }

    static class s2052{//Minimum Cost to Separate Sentence Into Rows
        /**
         * You are given a string sentence containing words separated by spaces, and an integer k. Your task is to
         * separate sentence into rows where the number of characters in each row is at most k. You may assume that
         * sentence does not begin or end with a space, and the words in sentence are separated by a single space.
         * You can split sentence into rows by inserting line breaks between words in sentence. A word cannot be
         * split between two rows. Each word must be used exactly once, and the word order cannot be rearranged.
         * Adjacent words in a row should be separated by a single space, and rows should not begin or end with spaces.
         * The cost of a row with length n is (k - n)2, and the total cost is the sum of the costs for all rows except
         * the last one. For example if sentence = "i love leetcode" and k = 12: Separating sentence into "i", "love",
         * and "leetcode" has a cost of (12 - 1)2 + (12 - 4)2 = 185. Separating sentence into "i love", and "leetcode"
         * has a cost of (12 - 6)2 = 36. Separating sentence into "i", and "love leetcode" is not possible because the
         * length of "love leetcode" is greater than k. Return the minimum possible total cost of separating sentence into rows.
         */
        Map<String, Integer> dp = new HashMap<>();

        public int minimumCost(String s, int k){
            if(s.length() <= k)
                return 0;
            if(dp.containsKey(s))
                return dp.get(s);
            int cost = Integer.MAX_VALUE;
            for(int i = 0; i < s.length() && i <= k; i++)
                if(s.charAt(i) == ' ')
                    cost = Math.min(cost, (k - i) * (k - i) + minimumCost(s.substring(i + 1), k));
            dp.put(s, cost);
            return cost;
        }
    }

    static class s2053{//Kth Distinct String in an Array
        public String kthDistinct(String[] a, int k){
            Map<String, Integer> m = new HashMap<>();
            Arrays.stream(a).forEach(s -> m.put(s, m.getOrDefault(s, 0) + 1));
            for(String s : a)
                if(m.get(s) == 1 && --k == 0)
                    return s;
            return "";
        }
    }

    static class s2054{//Two Best Non-Overlapping Events
        public int maxTwoEvents(int[][] events){
            Arrays.sort(events, Comparator.comparingInt(e -> e[1]));
            TreeMap<Integer, Integer> m = new TreeMap<>();
            int r = 0;
            for(int[] e : events){
                Map.Entry<Integer, Integer> last = m.floorEntry(e[0] - 1);
                r = Math.max(r, e[2] + (last != null ? last.getValue() : 0));
                last = m.lastEntry();
                m.put(e[1], Math.max(last != null ? last.getValue() : 0, e[2]));
            }
            return r;
        }
    }

    static class s2055{//Plates Between Candles
        public int[] platesBetweenCandles(String s, int[][] queries){
            TreeMap<Integer, Integer> m = new TreeMap<>();
            for(int i = 0, count = 0; i < s.length(); i++)
                if(s.charAt(i) == '|')
                    m.put(i, count);
                else count++;
            int[] r = new int[queries.length];
            for(int i = 0; i < queries.length; i++){
                Map.Entry<Integer, Integer> left = m.ceilingEntry(queries[i][0]), right = m.floorEntry(queries[i][1]);
                if(left != null && right != null)
                    r[i] = Math.max(0, right.getValue() - left.getValue());
            }
            return r;
        }
    }

    static class s2056{//Number of Valid Move Combinations On Chessboard
        public int countCombinations(String[] pieces, int[][] positions){
            List<List<int[]>> allTargets = IntStream.range(0, pieces.length).mapToObj(i -> new ArrayList<int[]>()).collect(Collectors.toList());
            for(int i = 0; i < pieces.length; i++){
                int p[] = positions[i], x = p[0], y = p[1];
                allTargets.get(i).add(p);//add init position as a target
                for(int r = 1; r <= 8; r++)
                    for(int c = 1; c <= 8; c++)
                        if(r != x || c != y){//all but not init position, it is already added
                            if(!pieces[i].equals("rook") && (r + c == x + y || r - c == x - y))//valid target for bishop and queen
                                allTargets.get(i).add(new int[]{r, c});
                            if(!pieces[i].equals("bishop") && (r == x || c == y))//valid target for rook and queen
                                allTargets.get(i).add(new int[]{r, c});
                        }
            }
            return count(0, positions, allTargets, new LinkedList<>());
        }

        int count(int idx, int[][] initPositions, List<List<int[]>> allTargets, LinkedList<int[]> targets){
            if(idx == initPositions.length)
                return valid(initPositions, targets) ? 1 : 0;
            int count = 0;
            for(int[] position : allTargets.get(idx)){
                targets.add(position);
                count += count(idx + 1, initPositions, allTargets, targets);
                targets.removeLast();
            }
            return count;
        }

        boolean valid(int[][] initPositions, List<int[]> targets){
            List<int[]> positions = Arrays.stream(initPositions).map(int[]::clone).collect(Collectors.toList());//deep copy init positions as we are going to move towards targets
            for(boolean keepMoving = true; keepMoving; ){
                keepMoving = false;
                Set<Integer> used = new HashSet<>();
                for(int i = 0; i < positions.size(); i++){
                    int verticalDirection = targets.get(i)[0] - positions.get(i)[0], horizontalDirection = targets.get(i)[1] - positions.get(i)[1];
                    positions.get(i)[0] += Integer.compare(verticalDirection, 0);
                    positions.get(i)[1] += Integer.compare(horizontalDirection, 0);
                    if(verticalDirection != 0 || horizontalDirection != 0)//target is not reached
                        keepMoving = true;
                    if(!used.add(13 * positions.get(i)[0] + positions.get(i)[1]))//collision with another piece
                        return false;
                }
            }
            return true;
        }
    }

    static class s2057{//Smallest Index With Equal Value
        public int smallestEqual(int[] a){
            for(int d1 = 0; d1 <= 9; d1++)
                for(int d2 = 0; d2 <= 9 && 10 * d1 + d2 < a.length; d2++)
                    if(d2 == a[d1 * 10 + d2])
                        return d1 * 10 + d2;
            return -1;
        }
    }

    static class s2058{//Find the Minimum and Maximum Number of Nodes Between Critical Points
        public int[] nodesBetweenCriticalPoints(ListNode node){
            int first = Integer.MAX_VALUE, last = 0, prevVal = node.val, minDist = Integer.MAX_VALUE;
            for(int i = 0; node.next != null; i++, prevVal = node.val, node = node.next)
                if((prevVal < node.val && node.val > node.next.val) || (prevVal > node.val && node.val < node.next.val)){
                    if(last != 0)
                        minDist = Math.min(minDist, i - last);
                    first = Math.min(first, i);
                    last = i;
                }
            return minDist == Integer.MAX_VALUE ? new int[]{-1, -1} : new int[]{minDist, last - first};
        }
    }

    static class s2059{//Minimum Operations to Convert Number
        public int minimumOperations(int[] a, int start, int goal){
            boolean[] seen = new boolean[1_001];
            Queue<Integer> q = new LinkedList<>(Arrays.asList(start));
            for(int r = 1; !q.isEmpty(); r++)
                for(int size = q.size(); size > 0; size--)
                    for(int n = q.poll(), i = 0; i < a.length; i++)
                        for(int x : new int[]{n + a[i], n - a[i], n ^ a[i]})
                            if(x == goal)
                                return r;
                            else if(0 <= x && x < seen.length && !seen[x]){
                                seen[x] = true;
                                q.offer(x);
                            }
            return -1;
        }
    }

    static class s2061{//Number of Spaces Cleaning Robot Cleaned
        /**
         * A room is represented by a 0-indexed 2D binary matrix room where a 0 represents
         * an empty space and a 1 represents a space with an object. The top left corner of
         * the room will be empty in all test cases. A cleaning robot starts in the top left
         * corner of the room and is facing right. The robot will continue heading straight
         * until it reaches the edge of the room, or it hits an object, after which it will
         * turn 90 degrees clockwise and repeat this process. The starting space and all spaces
         * that the robot visits are cleaned by it. Return the number of clean spaces in the room
         * if the robot runs indefinitely.
         */
        public int numberOfCleanRooms(int[][] room){
            int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}, seen = new int[room.length][room[0].length];
            for(int r = 0, c = 0, dirIdx = 0; (1 << dirIdx & seen[r][c]) == 0; ){
                seen[r][c] |= 1 << dirIdx;
                if(room[r][c] == 0)
                    room[r][c] = -1;
                int nr = r + dirs[dirIdx][0], nc = c + dirs[dirIdx][1];
                if(0 <= nr && nr < room.length && 0 <= nc && nc < room[0].length && room[nr][nc] != 1){
                    r = nr;
                    c = nc;
                }else dirIdx = (dirIdx + 1) % dirs.length;
            }
            return -Arrays.stream(room).mapToInt(row -> Arrays.stream(row).filter(n -> n < 0).sum()).sum();
        }
    }

    static class s2062{
        public int countVowelSubstrings(String word){//Count Vowel Substrings of a String
            Set<Character> vowels = Set.of('a', 'e', 'i', 'o', 'u'), curr = new HashSet<>();
            int r = 0;
            char[] w = word.toCharArray();
            for(int i = 0; i < w.length; i++, curr.clear())
                for(int j = i; j < w.length && vowels.contains(w[j]); j++){
                    curr.add(w[j]);
                    r += curr.size() == vowels.size() ? 1 : 0;
                }
            return r;
        }
    }

    static class s2063{//Vowels of All Substrings
        public long countVowels(String word){
            long r = 0;
            for(int i = 0; i < word.length(); i++)
                if("aeiou".indexOf(word.charAt(i)) >= 0)
                    r += (i + 1L) * (word.length() - i);
            return r;
        }
    }

    static class s2064{//Minimized Maximum of Products Distributed to Any Store
        public int minimizedMaximum(int n, int[] quantities){
            int lo = 1, hi = 100_000;
            while(lo < hi){
                int perStore = (lo + hi) / 2, sum = 0;
                for(int i = 0; i < quantities.length && sum <= n; i++)
                    sum += Math.ceil(1.0 * quantities[i] / perStore);
                if(sum > n)
                    lo = perStore + 1;
                else hi = perStore;
            }
            return hi;
        }
    }

    static class s2068{//Check Whether Two Strings are Almost Equivalent
        public boolean checkAlmostEquivalent(String w1, String w2){
            int[] counts = new int[26];
            w1.chars().forEach(c -> counts[c - 'a']++);
            w2.chars().forEach(c -> counts[c - 'a']--);
            return Arrays.stream(counts).allMatch(c -> Math.abs(c) <= 3);
        }
    }

    static class s2070{//Most Beautiful Item for Each Query
        public int[] maximumBeauty(int[][] items, int[] queries){
            TreeMap<Integer, Integer> m = new TreeMap<>();
            m.put(0, 0);
            Arrays.stream(items).sorted(Comparator.comparingInt(i -> i[0])).forEach(i -> m.put(i[0], Math.max(m.lastEntry().getValue(), i[1])));
            return Arrays.stream(queries).map(q -> m.floorEntry(q).getValue()).toArray();
        }
    }

    static class s2073{//Time Needed to Buy Tickets
        public int timeRequiredToBuy(int[] tickets, int k){
            return IntStream.range(0, tickets.length).map(i -> Math.min(tickets[i], i <= k ? tickets[k] : tickets[k] - 1)).sum();
        }
    }

    static class s2074{//Reverse Nodes in Even Length Groups
        public ListNode reverseEvenLengthGroups(ListNode head){
            ListNode curr = head;
            for(int groupSize = 2, size = 0; curr != null && curr.next != null; groupSize += 1, size = 0){
                Stack<ListNode> s = new Stack<>();
                for(ListNode node = curr.next; size < groupSize && node != null; size++, node = node.next)
                    s.push(node);
                if(size % 2 == 0){
                    ListNode next = s.peek().next;
                    while(!s.isEmpty()){
                        curr.next = s.pop();
                        curr = curr.next;
                    }
                    curr.next = next;
                }else for(size = 0; size < groupSize && curr != null; size++)
                    curr = curr.next;
            }
            return head;
        }
    }

    static class s2075{//Decode the Slanted Ciphertext
        public String decodeCiphertext(String encoded, int rows){
            StringBuilder r = new StringBuilder();
            for(int col = 0, cols = encoded.length() / rows; col < cols; col++)
                for(int i = col; i < encoded.length(); i += cols + 1)
                    r.append(encoded.charAt(i));
            return r.toString().stripTrailing();
        }
    }

    static class s2077{//Paths in Maze That Lead to Same Room
        public int numberOfPaths(int n, int[][] corridors){
            List<Set<Integer>> g = IntStream.range(0, n + 1).mapToObj(i -> new HashSet<Integer>()).collect(Collectors.toList());
            for(int[] c : corridors)
                if(c[0] < c[1])
                    g.get(c[0]).add(c[1]);
                else g.get(c[1]).add(c[0]);
            int r = 0;
            for(int[] e : corridors){
                Set<Integer> s1 = g.get(e[0]), s2 = g.get(e[1]);
                for(Integer u : s1)
                    if(s2.contains(u))
                        r++;
            }
            return r;
        }
    }

    static class s2078{//Two Furthest Houses With Different Colors
        public int maxDistance(int[] colors){
            for(int i = 0, n = colors.length - 1; i <= n; i++)
                if(colors[i] != colors[n] || colors[n - i] != colors[0])
                    return n - i;
            return 0;
        }
    }

    static class s2079{//Watering Plants
        public int wateringPlants(int[] plants, int capacity){
            int steps = 0, can = capacity;
            for(int i = 0; i < plants.length; can -= plants[i++])
                if(can < plants[i]){
                    steps += i + i;
                    can = capacity;
                }
            return steps + plants.length;
        }
    }

    static class s2083{//Substrings That Begin and End With the Same Letter
        /**
         * You are given a 0-indexed string s consisting of only lowercase
         * English letters. Return the number of substrings in s that begin
         * and end with the same character. A substring is a contiguous non-empty
         * sequence of characters within a string.
         */
        public long numberOfSubstrings(String s){
            long r = 0;
            for(int i = 0, counts[] = new int[26]; i < s.length(); i++)
                r += ++counts[s.charAt(i) - 'a'];
            return r;
        }
    }

    static class s2085{//Count Common Words With One Occurrence
        public int countWords(String[] words1, String[] words2){
            Map<String, Integer> m1 = new HashMap<>(), m2 = new HashMap<>();
            Arrays.stream(words1).forEach(w -> m1.put(w, m1.getOrDefault(w, 0) + 1));
            Arrays.stream(words2).forEach(w -> m2.put(w, m2.getOrDefault(w, 0) + 1));
            return (int) Arrays.stream(words1).filter(w -> m1.get(w) == 1 && m2.getOrDefault(w, 0) == 1).count();
        }
    }

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

    static class s2087{//Minimum Cost Homecoming of a Robot in a Grid
        public int minCost(int[] start, int[] home, int[] rowCosts, int[] colCosts){
            int cost = 0, rStep = (int) Math.signum(home[0] - start[0]), cStep = (int) Math.signum(home[1] - start[1]);
            for(int r = start[0] + rStep; r != home[0] + rStep; cost += rowCosts[r], r += rStep) ;
            for(int c = start[1] + cStep; c != home[1] + cStep; cost += colCosts[c], c += cStep) ;
            return cost;
        }
    }

    static class s2088{//Count Fertile Pyramids in a Land
        public int countPyramids(int[][] g){
            return count(inverse(g)) + count(g);
        }

        int count(int[][] g){
            int r = 0;
            for(int i = 1; i < g.length; i++)
                for(int j = 1; j < g[0].length - 1; j++)
                    if(g[i][j] >= 1 && g[i - 1][j] >= 1){
                        g[i][j] = Math.min(g[i - 1][j - 1], g[i - 1][j + 1]) + 1;
                        r += g[i][j] - 1;
                    }
            return r;
        }
        int[][] inverse(int[][] g){
            int[][] copy = new int[g.length][g[0].length];
            for(int i = 0; i < g.length; i++)
                copy[g.length - i - 1] = Arrays.copyOf(g[i], g[i].length);
            return copy;
        }
    }

    static class s2090{//K Radius Subarray Averages
        public int[] getAverages(int[] a, int k){
            int[] r = new int[a.length];
            Arrays.fill(r, -1);
            long sum = 0;
            for(int i = 0; i < 2 * k && i < a.length; i++)
                sum += a[i];
            for(int j = k; j < a.length - k; j++){
                sum += a[j + k];
                r[j] = (int) (sum / (2.0 * k + 1));
                sum -= a[j - k];
            }
            return r;
        }
    }

    static class s2091{//Removing Minimum and Maximum From Array
        public int minimumDeletions(int[] a){
            int minIdx = 0, maxIdx = 0;
            for(int i = 1; i < a.length; i++){
                if(a[minIdx] > a[i])
                    minIdx = i;
                if(a[maxIdx] < a[i])
                    maxIdx = i;
            }
            int left = Math.min(minIdx, maxIdx), right = Math.max(minIdx, maxIdx);
            int cutBoth = left + 1 + a.length - right;
            int cutLeft = 1 + right;
            int cutRight = a.length - left;
            return Math.min(cutBoth, Math.min(cutLeft, cutRight));
        }
    }

    static class s2092{//Find All People With Secret
        public List<Integer> findAllPeople(int n, int[][] meetings, int first){
            int[] ds = new int[n];
            Arrays.fill(ds, -1);
            ds[first] = 0;
            Arrays.sort(meetings, Comparator.comparingInt(m -> m[2]));
            for(int i = 0, j = 0; i < meetings.length; i++){
                if(meetings[i][2] != meetings[j][2]){
                    for(int k = j; k < i; k++){
                        if(find(meetings[k][0], ds) != 0)
                            ds[meetings[k][0]] = -1;
                        if(find(meetings[k][1], ds) != 0)
                            ds[meetings[k][1]] = -1;
                    }
                    j = i;
                }
                int p1 = find(meetings[i][0], ds), p2 = find(meetings[i][1], ds);
                if(p1 != p2)
                    ds[Math.max(p1, p2)] = Math.min(p1, p2);
            }
            return IntStream.range(0, n).filter(i -> find(i, ds) == 0).boxed().collect(Collectors.toList());
        }

        int find(int i, int[] ds){
            return ds[i] < 0 ? i : (ds[i] = find(ds[i], ds));
        }
    }

    static class s2093{//Minimum Cost to Reach City With Discounts
        public int minimumCost(int n, int[][] highways, int discounts){
            List<List<int[]>> g = IntStream.range(0, n).mapToObj(i -> new ArrayList<int[]>()).collect(Collectors.toList());
            for(int[] x : highways){
                int a = x[0], b = x[1], c = x[2];
                g.get(a).add(new int[]{b, c});
                g.get(b).add(new int[]{a, c});
            }
            PriorityQueue<int[]> q = new PriorityQueue<>(Comparator.comparingInt(o -> o[0]));
            int[][] dp = new int[n][discounts + 1];
            IntStream.range(0, n).forEach(i -> Arrays.fill(dp[i], Integer.MAX_VALUE));
            for(q.offer(new int[]{0, 0, 0}), dp[0][0] = 0; !q.isEmpty(); ){
                int e[] = q.poll(), cost = e[0], city = e[1], discount = e[2];
                if(city == n - 1)
                    return cost;
                for(int[] x : g.get(city)){
                    int next = x[0], weight = x[1];
                    if(cost + weight < dp[next][discount]){
                        q.offer(new int[]{cost + weight, next, discount});
                        dp[next][discount] = cost + weight;
                    }
                    if(discount < discounts && cost + weight / 2 < dp[next][discount + 1]){
                        q.offer(new int[]{cost + weight / 2, next, discount + 1});
                        dp[next][discount + 1] = cost + weight / 2;
                    }
                }
            }
            return -1;
        }
    }

    static class s2094{//Finding 3-Digit Even Numbers
        public int[] findEvenNumbers(int[] digits){
            int count[] = new int[10];
            Arrays.stream(digits).forEach(d -> count[d]++);
            List<Integer> r = new ArrayList<>();
            for(int n = 100; n < 1_000; n += 2){
                int currCount[] = new int[10];
                for(int m = n; m > 0; m /= 10)
                    currCount[m % 10]++;
                if(IntStream.range(0, 10).allMatch(d -> currCount[d] <= count[d]))
                    r.add(n);
            }
            return r.stream().mapToInt(i -> i).toArray();
        }
    }
    static class s2095{//Delete the Middle Node of a Linked List
        public ListNode deleteMiddle(ListNode head){
            if(head.next == null)
                return null;
            ListNode slow = head, fast = head.next;
            while(fast.next != null && fast.next.next != null){
                slow = slow.next;
                fast = fast.next.next;
            }
            slow.next = slow.next.next;
            return head;
        }
    }

    static class s2096{//Step-By-Step Directions From a Binary Tree Node to Another
        public String getDirections(TreeNode root, int start, int target){
            StringBuilder s = new StringBuilder(), t = new StringBuilder();
            find(start, s, root);
            find(target, t, root);
            int i = 0, maxI = Math.min(s.length(), t.length());
            for(; i < maxI && s.charAt(i) == t.charAt(i); i++) ;
            return "U".repeat(s.length() - i) + t.substring(i);
        }

        boolean find(int val, StringBuilder s, TreeNode node){
            if(node.val == val)
                return true;
            if(node.left != null && find(val, s, node.left))
                s.insert(0, 'L');
            else if(node.right != null && find(val, s, node.right))
                s.insert(0, 'R');
            return s.length() > 0;
        }
    }

    static class s2098{//Subsequence of Size K With the Largest Even Sum
        public long largestEvenSum(int[] a, int k){
            Arrays.sort(a);
            long sum = 0, r = -1;
            int smallestEven = Integer.MAX_VALUE, smallestOdd = Integer.MAX_VALUE;
            int largestEven = -1, largestOdd = -1;
            for(int i = a.length - 1; i >= 0; i--)
                if(i >= a.length - k){
                    sum += a[i];
                    if(a[i] % 2 == 0)
                        smallestEven = Math.min(smallestEven, a[i]);
                    else smallestOdd = Math.min(smallestOdd, a[i]);
                }else if(a[i] % 2 == 0)
                    largestEven = Math.max(largestEven, a[i]);
                else largestOdd = Math.max(largestOdd, a[i]);
            if(sum % 2 == 0)
                return sum;
            if(0 <= largestOdd && smallestEven < Integer.MAX_VALUE)
                r = Math.max(r, sum - smallestEven + largestOdd);
            if(0 <= largestEven && smallestOdd < Integer.MAX_VALUE)
                r = Math.max(r, sum - smallestOdd + largestEven);
            return r;
        }
    }

    static class s2099{//Find Subsequence of Length K With the Largest Sum
        public int[] maxSubsequence(int[] a, int k){
            PriorityQueue<int[]> q = new PriorityQueue<>(Comparator.comparingInt(n -> n[0]));
            for(int i = 0; i < a.length; i++){
                q.offer(new int[]{a[i], i});
                if(q.size() > k)
                    q.remove();
            }
            return q.stream().sorted(Comparator.comparingInt(n -> n[1])).mapToInt(n -> n[0]).toArray();
        }
    }
}