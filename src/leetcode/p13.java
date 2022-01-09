package leetcode;
import java.util.*;
import java.util.stream.*;

public class p13{
    static class s1312{//Minimum Insertion Steps to Make a String Palindrome
        public int minInsertions(String s){
            return s.length() - lcs(s, new StringBuilder(s).reverse().toString());
        }

        int lcs(String s1, String s2){
            int n = s1.length(), dp[][] = new int[n + 1][n + 1];
            for(int i = 0; i < n; i++)
                for(int j = 0; j < n; j++)
                    dp[i + 1][j + 1] = s1.charAt(i) == s2.charAt(j) ? dp[i][j] + 1 : Math.max(dp[i + 1][j], dp[i][j + 1]);
            return dp[n][n];
        }
    }

    static class s1320{//Minimum Distance to Type a Word Using Two Fingers
        public int minimumDistance(String word){
            return dfs(word.toCharArray(), 0, -1, -1, new Integer[word.length()][27][27]);
        }

        int dfs(char[] a, int i, int first, int second, Integer[][][] dp){
            if(i == a.length)
                return 0;
            if(dp[i][first + 1][second + 1] != null)
                return dp[i][first + 1][second + 1];
            int r = (first != -1 ? d(first, a[i] - 'A') : 0) + dfs(a, i + 1, a[i] - 'A', second, dp);
            r = Math.min(r, (second != -1 ? d(second, a[i] - 'A') : 0) + dfs(a, i + 1, first, a[i] - 'A', dp));
            return dp[i][first + 1][second + 1] = r;
        }

        int d(int a, int b){
            return Math.abs(a / 6 - b / 6) + Math.abs(a % 6 - b % 6);
        }
    }

    static class s1328{//Break a Palindrome
        public String breakPalindrome(String pali){
            char[] a = pali.toCharArray();
            for(int i = 0; i < a.length / 2; i++)
                if(a[i] != 'a'){
                    a[i] = 'a';
                    return String.valueOf(a);
                }
            a[a.length - 1] = 'b'; //if all 'a'
            return a.length < 2 ? "" : String.valueOf(a);
        }
    }

    static class s1334{//Find the City With the Smallest Number of Neighbors at a Threshold Distance
        public int findTheCity(int n, int[][] edges, int distanceThreshold){
            int d[][] = new int[n][n], r = 0;
            Arrays.stream(d).forEach(row -> Arrays.fill(row, 10_001));
            Arrays.stream(edges).forEach(e -> d[e[0]][e[1]] = d[e[1]][e[0]] = e[2]);
            IntStream.range(0, n).forEach(i -> d[i][i] = 0);
            for(int k = 0; k < n; ++k)
                for(int i = 0; i < n; ++i)
                    for(int j = 0; j < n; ++j)
                        d[i][j] = Math.min(d[i][j], d[i][k] + d[k][j]);
            for(int i = 0, smallest = n; i < n; i++){
                final int u = i, count = (int) IntStream.range(0, n).filter(j -> d[u][j] <= distanceThreshold).count();
                if(count <= smallest){
                    r = i;
                    smallest = count;
                }
            }
            return r;
        }
    }

    static class s1335{//Minimum Difficulty of a Job Schedule
        public int minDifficulty(int[] jobDifficulty, int d){
            if(jobDifficulty.length < d)
                return -1;
            return min(0, jobDifficulty, d, new Integer[jobDifficulty.length][d + 1]);
        }
        int min(int start, int[] jobDifficulty, int d, Integer[][] dp){
            if(jobDifficulty.length == d)
                return Arrays.stream(jobDifficulty).sum();
            if(d == 1){
                int max = jobDifficulty[start];
                for(int i = start + 1; i < jobDifficulty.length; i++)
                    max = Math.max(max, jobDifficulty[i]);
                return max;
            }
            if(dp[start][d] != null)
                return dp[start][d];
            int r = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
            for(int i = start; i < jobDifficulty.length && jobDifficulty.length - i >= d; i++){
                max = Math.max(max, jobDifficulty[i]);
                r = Math.min(r, max + min(i + 1, jobDifficulty, d - 1, dp));
            }
            return dp[start][d] = r;
        }
    }

    static class s1339{//Maximum Product of Splitted Binary Tree
        long r = 0;
        public int maxProduct(TreeNode root){
            long totalSum = sum(root);
            sum(root, totalSum);
            return (int) (r % 1_000_000_007);
        }
        long sum(TreeNode node, long totalSum){
            if(node == null)
                return 0;
            long sum = node.val + sum(node.left, totalSum) + sum(node.right, totalSum);
            r = Math.max(r, sum * (totalSum - sum));
            return sum;
        }

        long sum(TreeNode node){return node != null ? (long) node.val + sum(node.left) + sum(node.right) : 0;}
    }

    static class s1348{//Tweet Counts Per Frequency
        class TweetCounts{
            final Map<String, TreeMap<Integer, Integer>> m = new HashMap<>();
            final Map<String, Integer> f = Map.of("minute", 60, "hour", 3600, "day", 86400);

            public void recordTweet(String tweetName, int time){
                m.putIfAbsent(tweetName, new TreeMap<>());
                m.get(tweetName).put(time, m.get(tweetName).getOrDefault(time, 0) + 1);
            }

            public List<Integer> getTweetCountsPerFrequency(String freq, String tweetName, int startTime, int endTime){
                int sec = f.get(freq), buckets[] = new int[(endTime - startTime) / sec + 1];
                for(Map.Entry<Integer, Integer> e : m.get(tweetName).subMap(startTime, endTime + 1).entrySet()){
                    int idx = (e.getKey() - startTime) / sec;
                    buckets[idx] += e.getValue();
                }
                return Arrays.stream(buckets).boxed().collect(Collectors.toList());
            }
        }
    }

    static class s1375{//Bulb Switcher III
        public int numTimesAllBlue(int[] a){
            int right = 0, r = 0;
            for(int i = 0; i < a.length; i++){
                right = Math.max(right, a[i]);
                r += right == i + 1 ? 1 : 0;
            }
            return r;
        }
    }
}
