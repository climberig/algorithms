package leetcode;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class p5{
    static class s500{//Keyboard Row
        public String[] findWords(String[] words){
            List<String> r = new ArrayList<>(), rows = Arrays.asList("qwertyuiop", "asdfghjkl", "zxcvbnm");
            for(String w : words)
                for(String row : rows)
                    if(w.toLowerCase().chars().allMatch(c -> row.indexOf(c) >= 0))
                        r.add(w);
            return r.toArray(new String[0]);
        }
    }

    static class s507{//Perfect Number
        public boolean checkPerfectNumber(int n){
            int sum = 1;
            for(int d = 2; d <= Math.sqrt(n); d++)
                if(n % d == 0)
                    sum = sum + n / d + d;
            return n > 1 && n == sum;
        }
    }

    static class s520{//Detect Capital
        public boolean detectCapitalUse(String word){
            int capitalCount = 0;
            for(char c : word.toCharArray())
                capitalCount += Character.isUpperCase(c) ? 1 : 0;
            return capitalCount == 0 || (capitalCount == 1 && Character.isUpperCase(word.charAt(0))) || capitalCount == word.length();
        }

        public boolean detectCapitalUse1(String word){
            return word.matches("[A-Z]+|[a-z]+|[A-Z][a-z]+");
        }
    }

    static class s521{//Longest Uncommon Subsequence I
        public int findLUSlength(String a, String b){
            return a.equals(b) ? -1 : Math.max(a.length(), b.length());
        }
    }

    static class s522{//Longest Uncommon Subsequence II
        public int findLUSlength(String[] strs){
            int r = -1;
            for(int i = 0; i < strs.length; i++){
                final int k = i;
                if(IntStream.range(0, strs.length).filter(j -> k != j).noneMatch(j -> subsequence(strs[k], strs[j])))
                    r = Math.max(r, strs[i].length());
            }
            return r;
        }

        boolean subsequence(String s1, String s2){
            int i = 0;
            for(int j = 0; j < s2.length() && i < s1.length(); j++)
                if(s1.charAt(i) == s2.charAt(j))
                    i++;
            return i == s1.length();
        }
    }

    static class s530{//Minimum Absolute Difference in BST
        Integer min = Integer.MAX_VALUE, prev = null;

        public int getMinimumDifference(TreeNode root){
            if(root == null)
                return min;
            getMinimumDifference(root.left);
            if(prev != null)
                min = Math.min(min, root.val - prev);
            prev = root.val;
            getMinimumDifference(root.right);
            return min;
        }
    }

    static class s541{//Reverse String II
        public String reverseStr(String s, int k){
            char[] a = s.toCharArray();
            for(int i = 0; i < s.length(); i += 2 * k)
                for(int lo = i, hi = Math.min(a.length - 1, i + k - 1); lo < hi; lo++, hi--){
                    char t = a[lo];
                    a[lo] = a[hi];
                    a[hi] = t;
                }
            return new String(a);
        }
    }

    static class s543{//Diameter of Binary Tree
        int r = 0;
        public int diameterOfBinaryTree(TreeNode root){
            depth(root);
            return r;
        }

        int depth(TreeNode node){
            if(node == null)
                return 0;
            int left = depth(node.left), right = depth(node.right);
            r = Math.max(r, left + right);
            return 1 + Math.max(left, right);
        }
    }

    static class s561{//Array Partition I
        public int arrayPairSum(int[] a){
            Arrays.sort(a);
            int sum = 0;
            for(int i = 0; i < a.length; i += 2)
                sum += a[i];
            return sum;
        }
    }

    static class s565{//Array Nesting
        public int arrayNesting(int[] a){
            int r = 0;
            boolean[] seen = new boolean[a.length];
            for(int i = 0; i < a.length; i++)
                if(!seen[i]){
                    int len = 0;
                    for(int j = i; !seen[j]; len++, j = a[j])
                        seen[j] = true;
                    r = Math.max(r, len);
                }
            return r;
        }
    }

    static class s572{//Subtree of Another Tree
        public boolean isSubtree(TreeNode root, TreeNode sub){
            if(root == null)
                return false;
            return sub(root, sub) || isSubtree(root.left, sub) || isSubtree(root.right, sub);
        }

        boolean sub(TreeNode root, TreeNode sub){
            if(root == null && sub == null)
                return true;
            if(root == null || sub == null)
                return false;
            return root.val == sub.val && sub(root.left, sub.left) && sub(root.right, sub.right);
        }
    }

    static class s594{//Longest Harmonious Subsequence
        public int findLHS(int[] a){
            Map<Integer, Integer> m = new HashMap<>();
            Arrays.stream(a).forEach(n -> m.put(n, m.getOrDefault(n, 0) + 1));
            int r = 0;
            for(Integer n : m.keySet())
                r = Math.max(r, m.get(n) + m.getOrDefault(n + 1, -m.get(n)));
            return r;
        }
    }

    static class s598{//Range Addition II
        public int maxCount(int m, int n, int[][] ops){
            for(int[] op : ops){
                m = Math.min(m, op[0]);
                n = Math.min(n, op[1]);
            }
            return m * n;
        }
    }

    static class s599{//Minimum Index Sum of Two Lists
        public String[] findRestaurant(String[] list1, String[] list2){
            int minSum = Integer.MAX_VALUE;
            Map<String, Integer> list1m = IntStream.range(0, list1.length).boxed().collect(Collectors.toMap(i -> list1[i], i -> i));
            Map<Integer, List<String>> r = new HashMap<>();
            for(int i = 0; i < list2.length; i++)
                if(list1m.containsKey(list2[i])){
                    int sum = list1m.get(list2[i]) + i;
                    r.computeIfAbsent(sum, l -> new ArrayList<>()).add(list2[i]);
                    minSum = Math.min(minSum, sum);
                }
            return r.get(minSum).toArray(new String[0]);
        }
    }
}
