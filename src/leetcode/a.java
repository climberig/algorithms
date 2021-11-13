package leetcode;

public class a{
    public boolean isLongPressedName(String name, String typed){
        int i = 0, j = 0;
        while(i < name.length() && j < typed.length() && name.charAt(i) == typed.charAt(j)){
            for(; i < name.length() && j < typed.length() && name.charAt(i) == typed.charAt(j); i++, j++) ;
            for(; j < typed.length() && name.charAt(i - 1) == typed.charAt(j); j++) ;
        }
        return i == name.length() && j == typed.length();
    }
}
