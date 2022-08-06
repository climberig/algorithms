package leetcode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
public class p23{
    static class s2303{//Calculate Amount Paid in Taxes
        public double calculateTax(int[][] brackets, int income){
            double r = 0;
            for(int i = 0, amount = 0; i < brackets.length && amount < income; i++){
                int dollars = Math.min(income - amount, brackets[i][0] - amount);
                r += dollars * brackets[i][1] / 100.0;
                amount += dollars;
            }
            return r;
        }
    }

    static class s2304{//Minimum Path Cost in a Grid
        public int minPathCost(int[][] g, int[][] cost){
            int[] r = g[0];
            for(int i = 1; i < g.length; i++){
                int[] next = new int[g[0].length];
                Arrays.fill(next, Integer.MAX_VALUE);
                for(int j = 0; j < g[0].length; j++)
                    for(int k = 0; k < g[0].length; k++)
                        next[k] = Math.min(next[k], r[j] + g[i][k] + cost[g[i - 1][j]][k]);
                r = next;
            }
            return Arrays.stream(r).min().getAsInt();
        }
    }

    static class s2305{//Fair Distribution of Cookies
        int r = Integer.MAX_VALUE;
        public int distributeCookies(int[] cookies, int k){
            bt(0, cookies, new int[k]);
            return r;
        }

        void bt(int i, int[] cookies, int[] kids){
            if(i == cookies.length){
                int max = 0;
                for(int c : kids)
                    max = Math.max(max, c);
                r = Math.min(r, max);
            }else for(int j = 0; j < kids.length; j++){
                kids[j] += cookies[i];
                bt(i + 1, cookies, kids);
                kids[j] -= cookies[i];
            }
        }
    }

    static class s2309{//Greatest English Letter in Upper and Lower Case
        public String greatestLetter(String str){
            Set<Character> s = new HashSet<>();
            for(char c : str.toCharArray())
                s.add(c);
            for(char c = 'Z'; c >= 'A'; c--)
                if(s.contains(c) && s.contains(Character.toLowerCase(c)))
                    return c + "";
            return "";
        }
    }

    static class s2315{//Count Asterisks
        public int countAsterisks(String s){
            int r = 0, count = 0;
            for(int i = 0; i < s.length(); i++)
                if(s.charAt(i) == '*' && count % 2 == 0)
                    r++;
                else if(s.charAt(i) == '|')
                    count++;
            return r;
        }
    }

    static class s2319{//Check if Matrix Is X-Matrix
        public boolean checkXMatrix(int[][] g){
            for(int i = 0; i < g.length; i++)
                for(int j = 0; j < g.length; j++){
                    boolean diag = i - j == 0 || i + j == g.length - 1;
                    if(diag && g[i][j] == 0)
                        return false;
                    else if(!diag && g[i][j] != 0)
                        return false;
                }
            return true;
        }
    }

    static class s2320{//Count Number of Ways to Place Houses
        public int countHousePlacements(int n){
            long x = 1, o = 1, total = x + o;
            for(int i = 2; i <= n; i++){
                x = o;
                o = total;
                total = (x + o) % 1_000_000_007;
            }
            return (int) ((total * total) % 1_000_000_007);
        }
    }

    static class s2325{//Decode the Message
        public String decodeMessage(String key, String message){
            Map<Character, Character> m = new HashMap<>();
            m.put(' ', ' ');
            char to = 'a';
            for(char from : key.toCharArray())
                if(!m.containsKey(from))
                    m.put(from, to++);
            return message.chars().mapToObj(c -> m.get((char) c) + "").collect(Collectors.joining(""));
        }
    }

    static class s2331{//Evaluate Boolean Binary Tree
        public boolean evaluateTree(TreeNode node){
            if(node.val < 2)
                return node.val == 1;
            boolean left = evaluateTree(node.left), right = evaluateTree(node.right);
            return node.val == 2 ? left | right : left & right;
        }
    }

    static class s2335{//Minimum Amount of Time to Fill Cups
        public int fillCups(int[] amount){
            PriorityQueue<Integer> q = new PriorityQueue<>();
            Arrays.stream(amount).filter(a -> a > 0).forEach(a -> q.offer(-a));
            int r = 0;
            for(; q.size() > 1; r++){
                Integer a = q.poll(), b = q.poll();
                if(++a != 0)
                    q.offer(a);
                if(++b != 0)
                    q.offer(b);
            }
            return q.isEmpty() ? r : r - q.poll();
        }
    }

    static class s2336{//Smallest Number in Infinite Set
        class SmallestInfiniteSet{
            TreeSet<Integer> s = new TreeSet<>();

            public SmallestInfiniteSet(){IntStream.range(1, 1_001).forEach(n -> s.add(n));}

            public int popSmallest(){return s.pollFirst();}

            public void addBack(int n){s.add(n);}
        }
    }

    static class s2340{//Minimum Adjacent Swaps to Make a Valid Array
        public int minimumSwaps(int[] a){
            int minIdx = a.length - 1, maxIdx = 0;
            for(int i = 0; i < a.length; i++)
                if(a[i] >= a[maxIdx])
                    maxIdx = i;
            for(int i = a.length - 1; i >= 0; i--)
                if(a[i] <= a[minIdx])
                    minIdx = i;
            return minIdx + a.length - 1 - maxIdx - (minIdx > maxIdx ? 1 : 0);
        }
    }

    static class s2341{//Maximum Number of Pairs in Array
        public int[] numberOfPairs(int[] a){
            int[] r = new int[2], f = new int[101];
            Arrays.stream(a).forEach(n -> f[n]++);
            for(int n : f){
                r[0] += n / 2;
                r[1] += n % 2;
            }
            return r;
        }
    }

    static class s2342{//Max Sum of a Pair With Equal Sum of Digits
        public int maximumSum(int[] a){
            Map<Integer, Integer> m = new HashMap<>();
            int r = -1;
            for(int n : a){
                int sum = 0;
                for(int k = n; k > 0; k /= 10)
                    sum += k % 10;
                if(m.containsKey(sum)){
                    r = Math.max(r, n + m.get(sum));
                    m.put(sum, Math.max(m.get(sum), n));
                }else m.put(sum, n);
            }
            return r;
        }
    }

    static class s2347{//Best Poker Hand
        public String bestHand(int[] ranks, char[] suits){
            Arrays.sort(suits);
            if(suits[0] == suits[4])
                return "Flush";
            int[] f = new int[14];
            Arrays.stream(ranks).forEach(r -> f[r]++);
            int maxRank = Arrays.stream(f).max().getAsInt();
            if(maxRank >= 3)
                return "Three of a Kind";
            if(maxRank == 2)
                return "Pair";
            return "High Card";
        }
    }

    static class s2348{//Number of Zero-Filled Subarrays
        public long zeroFilledSubarray(int[] a){
            long r = 0;
            for(int i = 0, f = 0; i < a.length; i++)
                if(a[i] == 0)
                    r += ++f;
                else f = 0;
            return r;
        }
    }

    static class s2349{//Design a Number Container System
        class NumberContainers{
            Map<Integer, TreeSet<Integer>> nToIds = new HashMap<>();
            Map<Integer, Integer> idxToN = new HashMap<>();

            public void change(int idx, int n){
                if(idxToN.containsKey(idx)){
                    Integer prev = idxToN.get(idx);
                    nToIds.get(prev).remove(idx);
                }
                idxToN.put(idx, n);
                nToIds.putIfAbsent(n, new TreeSet<>());
                nToIds.get(n).add(idx);
            }

            public int find(int n){
                TreeSet<Integer> ids = nToIds.get(n);
                return ids == null || ids.isEmpty() ? -1 : ids.first();
            }
        }
    }

    static class s2351{// First Letter to Appear Twice
        public char repeatedCharacter(String s){
            boolean[] appeared = new boolean[26];
            int i = 0;
            for(; i < s.length() && !appeared[s.charAt(i) - 'a']; i++)
                appeared[s.charAt(i) - 'a'] = true;
            return s.charAt(i);
        }
    }

    static class s2352{//Equal Row and Column Pairs
        public int equalPairs(int[][] g){
            int r = 0;
            for(int i = 0; i < g.length; i++)
                for(int j = 0; j < g.length; j++)
                    r += equals(g, i, j);
            return r;
        }
        int equals(int[][] g, int r, int c){
            for(int i = 0; i < g.length; i++)
                if(g[r][i] != g[i][c])
                    return 0;
            return 1;
        }
    }

    static class s2357{//Make Array Zero by Subtracting Equal Amounts
        public int minimumOperations(int[] a){
            return (int) Arrays.stream(a).filter(n -> n > 0).distinct().count();
        }
    }

    static class s2358{//Maximum Number of Groups Entering a Competition
        public int maximumGroups(int[] grades){
            return (int) ((Math.sqrt(1 + 8 * grades.length) - 1) / 2);
        }
    }

    static class s2363{//Merge Similar Items
        public List<List<Integer>> mergeSimilarItems(int[][] a1, int[][] a2){
            Map<Integer, Integer> m = new TreeMap<>();
            Arrays.stream(a1).forEach(a -> m.put(a[0], a[1]));
            Arrays.stream(a2).forEach(a -> m.put(a[0], m.getOrDefault(a[0], 0) + a[1]));
            return m.keySet().stream().map(v -> Arrays.asList(v, m.get(v))).collect(Collectors.toList());
        }
    }
}
