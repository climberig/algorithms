package leetcode;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
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
}
