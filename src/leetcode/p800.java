package leetcode;

public class p800{
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
}
