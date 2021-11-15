package leetcode;

public class a{
    public String decodeCiphertext(String encodedText, int rows){
        StringBuilder r = new StringBuilder();
        for(int col = 0, cols = encodedText.length() / rows; col < cols; col++)
            for(int i = col; i < encodedText.length(); i += cols + 1)
                r.append(encodedText.charAt(i));
        return r.toString().stripTrailing();
    }
}
