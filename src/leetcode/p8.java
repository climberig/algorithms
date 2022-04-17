package leetcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
public class p8{
    static class s800{//Similar RGB Color
        public String similarRGB(String color){
            String hex = "0123456789abcdef", minS = "";
            StringBuilder r = new StringBuilder("#");
            for(int i = 1; i < color.length(); i += 2){
                int min = Integer.MAX_VALUE, n = Integer.parseInt(color.substring(i, i + 2), 16);
                for(char c : hex.toCharArray()){
                    int val = Integer.parseInt("" + c + c, 16);
                    if(Math.abs(val - n) < min){
                        min = Math.abs(val - n);
                        minS = "" + c + c;
                    }
                }
                r.append(minS);
            }
            return r.toString();
        }
    }

    static class s804{//Unique Morse Code Words
        public int uniqueMorseRepresentations(String[] words){
            String[] m = {".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--.."};
            Set<String> s = new HashSet<>();
            for(String w : words){
                StringBuilder r = new StringBuilder();
                w.chars().forEach(c -> r.append(m[c - 'a']));
                s.add(r.toString());
            }
            return s.size();
        }
    }

    static class s821{//Shortest Distance to a Character
        public int[] shortestToChar(String s, char c){
            int[] r = new int[s.length()];
            for(int i = s.length() - 1, p = 2 * s.length(); i >= 0; i--){
                if(s.charAt(i) == c)
                    p = i;
                r[i] = p - i;
            }
            for(int i = 0, p = -s.length(); i < s.length(); i++){
                if(s.charAt(i) == c)
                    p = i;
                r[i] = Math.min(r[i], i - p);
            }
            return r;
        }
    }

    static class s824{//Goat Latin
        public String toGoatLatin(String s){
            String words[] = s.split(" "), r[] = new String[words.length], end = "a", v = "AaEeIiOoUu";
            for(int i = 0; i < words.length; i++, end += 'a')
                r[i] = (v.indexOf(words[i].charAt(0)) >= 0 ? words[i] : words[i].substring(1) + words[i].charAt(0)) + "ma" + end;
            return String.join(" ", r);
        }
    }

    static class s840{//Magic Squares In Grid
        public int numMagicSquaresInside(int[][] g){
            int r = 0;
            for(int i = 0; i <= g.length - 3; i++)
                for(int j = 0; j <= g[0].length - 3; j++)
                    r += magic(i, j, g) ? 1 : 0;
            return r;
        }

        boolean magic(int x, int y, int[][] g){
            Set<Integer> s = new HashSet<>();
            int rowsSum[] = new int[3], colsSum[] = new int[3], d1 = 0, d2 = 0;
            for(int r = 0; r < 3; r++){
                for(int c = 0; c < 3; c++){
                    if(g[x + r][y + c] < 1 || g[x + r][y + c] > 9 || !s.add(g[x + r][y + c]))
                        return false;
                    rowsSum[r] += g[x + r][y + c];
                    colsSum[c] += g[x + r][y + c];
                }
                d1 += g[x + r][y + r];
                d2 += g[x + r][y + 2 - r];
            }
            return s.size() == 9 && Arrays.stream(rowsSum).allMatch(n -> n == 15) && Arrays.stream(colsSum).allMatch(n -> n == 15) && d1 == 15 && d2 == 15;
        }
    }

    static class s860{//Lemonade Change
        public boolean lemonadeChange(int[] bills){
            int fives = 0, tens = 0;
            for(int i = 0; i < bills.length && fives >= 0; i++)
                if(bills[i] == 5)
                    fives++;
                else if(bills[i] == 10){
                    tens++;
                    fives--;
                }else if(tens > 0){
                    tens--;
                    fives--;
                }else fives -= 3;
            return fives >= 0;
        }
    }

    static class s881{//Boats to Save People
        public int numRescueBoats(int[] people, int limit){
            Arrays.sort(people);
            int lo = 0, hi = people.length - 1, r = 0;
            for(; lo <= hi; hi--, r++)
                if(people[lo] + people[hi] <= limit)
                    lo++;
            return r;
        }
    }

    static class s897{//Increasing Order Search Tree
        public TreeNode increasingBST(TreeNode root){
            return increasingBST(root, null);
        }

        public TreeNode increasingBST(TreeNode root, TreeNode nextBigger){
            if(root == null)
                return nextBigger;
            TreeNode r = increasingBST(root.left, root);
            root.left = null;
            root.right = increasingBST(root.right, nextBigger);
            return r;
        }
    }
}
