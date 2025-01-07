package leetcode;
public class p34{
    static class s3407{//Substring Matching Pattern
        public boolean hasMatch(String s, String p) {
            int k = p.indexOf('*');
            String pre = p.substring(0, k), post = p.substring(k + 1);
            int i = s.indexOf(pre), j = s.indexOf(post, i + pre.length());
            return i >= 0 && j >= 0;
        }
    }
}
