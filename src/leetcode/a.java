package leetcode;

public class a {
    public static void main(String[] args) {
        System.out.println(new a().countVowels("igor"));
    }
    public long countVowels(String word) {
        long r = 0;
        for (int i = 0; i < word.length(); i++)
            if ("aeiou".indexOf(word.charAt(i)) >= 0)
                r += (i + 1L) * (word.length() - i);
        return r;
    }
}

