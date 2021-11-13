package challenges;

public class HappyMoney{
    static class s1{
        public static String solution(String s){
            int left = 0, balance = 0;
            for(int i = 0; i < s.length(); i++)
                if(balance == 0 && s.charAt(i) == ')')
                    left++;
                else balance += s.charAt(i) == '(' ? 1 : -1;
            return "(".repeat(left) + s + ")".repeat(balance);
        }
    }

    static class s2{
        public static long solution(long[] prices){
            long maxProfit = 0, maxCurr = 0;
            for(int i = 1; i < prices.length; i++){
                maxCurr = Math.max(0, maxCurr + prices[i] - prices[i - 1]);
                maxProfit = Math.max(maxProfit, maxCurr);
            }
            return maxProfit;
        }
    }

    static class s3{
        public static String solution(String s, String[] replacements){
            StringBuilder r = new StringBuilder();
            for(int i = 0; i < s.length(); i++){
                if(s.charAt(i) == '{' && i + 1 < s.length() && s.charAt(i + 1) == '{'){
                    r.append("{");
                    i++;
                }else if(s.charAt(i) == '{'){
                    int n = 0, j = i + 1;
                    while(j < s.length() && Character.isDigit(s.charAt(j)))
                        n = 10 * n + s.charAt(j++) - '0';
                    if(i + 1 < j && j < s.length() && s.charAt(j) == '}' && n < replacements.length){
                        r.append(replacements[n]);
                        i = j;
                    }else return "";
                }else r.append(s.charAt(i));
            }
            return r.toString();
        }
    }
}
