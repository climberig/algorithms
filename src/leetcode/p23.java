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
}
