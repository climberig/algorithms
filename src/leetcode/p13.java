package leetcode;
import java.util.*;
import java.util.stream.*;

public class p13{
    static class s1301{//Number of Paths with Max Score
        public int[] pathsWithMaxScore(List<String> b){
            int n = b.size(), m = b.get(0).length();
            int[][] dp = new int[n][m], counts = new int[n][m], dirs = {{-1, -1}, {-1, 0}, {0, -1}};
            counts[0][0] = 1;
            for(int j = 1; j < m && b.get(0).charAt(j) != 'X'; j++){
                dp[0][j] = dp[0][j - 1] + b.get(0).charAt(j) - '0';
                counts[0][j] = 1;
            }
            for(int i = 1; i < n && b.get(i).charAt(0) != 'X'; i++){
                dp[i][0] = dp[i - 1][0] + b.get(i).charAt(0) - '0';
                counts[i][0] = 1;
            }
            for(int i = 1; i < n; i++)
                for(int j = 1; j < m; j++)
                    if(b.get(i).charAt(j) != 'X'){
                        int score = b.get(i).charAt(j) == 'S' ? 0 : b.get(i).charAt(j) - '0';
                        for(int[] d : dirs){
                            int x = i + d[0], y = j + d[1];
                            if(counts[x][y] > 0){
                                if(dp[x][y] + score > dp[i][j]){
                                    dp[i][j] = dp[x][y] + score;
                                    counts[i][j] = counts[x][y];
                                }else if(dp[x][y] + score == dp[i][j])
                                    counts[i][j] = (counts[i][j] + counts[x][y]) % 1_000_000_007;
                            }
                        }
                    }
            return new int[]{dp[n - 1][m - 1], counts[n - 1][m - 1]};
        }
    }

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

    static class s1340{//Jump Game V
        public int maxJumps(int[] a, int d){
            int r = 1, dp[] = new int[a.length];
            for(int i = 0; i < a.length; i++)
                r = Math.max(r, jump(i, a, d, dp));
            return r;
        }
        int jump(int idx, int[] a, int d, int[] dp){
            if(dp[idx] > 0)
                return dp[idx];
            int r = 1;
            for(int i = idx + 1; i <= Math.min(idx + d, a.length - 1) && a[idx] > a[i]; i++)
                r = Math.max(r, 1 + jump(i, a, d, dp));
            for(int i = idx - 1; i >= Math.max(0, idx - d) && a[idx] > a[i]; i--)
                r = Math.max(r, 1 + jump(i, a, d, dp));
            return dp[idx] = r;
        }
    }

    static class s1346{//Check If N and Its Double Exist
        public boolean checkIfExist(int[] a){
            Set<Integer> s = new HashSet<>();
            for(int n : a){
                if(n % 2 == 0 && s.contains(n / 2) || s.contains(2 * n))
                    return true;
                s.add(n);
            }
            return false;
        }
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

    static class s1349{//Maximum Students Taking Exam
        public int maxStudents(char[][] seats){
            int[][] dp = new int[seats.length][1 << seats[0].length];
            for(int i = 0; i < seats.length; i++)
                Arrays.fill(dp[i], -1);
            return maxSeats(seats, 0, 0, dp);
        }

        int maxSeats(char[][] seats, int row, int prevRowMask, int[][] dp){
            if(row == seats.length)
                return 0;
            if(dp[row][prevRowMask] != -1)
                return dp[row][prevRowMask];
            List<Integer> currRowMasks = new ArrayList<>();
            bt(seats[row], 0, prevRowMask, 0, currRowMasks);
            int r = 0;
            for(int currMask : currRowMasks)
                r = Math.max(r, Integer.bitCount(currMask) + maxSeats(seats, row + 1, currMask, dp));
            return dp[row][prevRowMask] = r;
        }

        void bt(char[] seatRow, int col, int prevRowMask, int currRowMask, List<Integer> masks){
            if(col < seatRow.length){
                bt(seatRow, col + 1, prevRowMask, currRowMask, masks);
                if(seatRow[col] != '#'
                        && (col == 0 || (((currRowMask & (1 << (col - 1))) == 0) && (prevRowMask & (1 << (col - 1))) == 0))
                        && (col == seatRow.length - 1 || ((prevRowMask & (1 << (col + 1))) == 0))){
                    bt(seatRow, col + 1, prevRowMask, currRowMask | (1 << col), masks);
                }
            }else masks.add(currRowMask);
        }
    }

    static class s1354{//Construct Target Array With Multiple Sums
        public boolean isPossible(int[] target){
            Queue<Integer> q = new PriorityQueue<>(Comparator.reverseOrder());
            long sum = 0;
            for(int n : target){
                if(n > 1)
                    q.offer(n);
                sum += n;
            }
            while(!q.isEmpty()){
                if(q.size() == 1 && q.peek() + 1 == sum)
                    return true;
                Integer n = q.poll();
                sum -= n;
                if(sum >= n || sum == 0)
                    return false;
                n = Math.toIntExact(n % sum);
                if(n == 0)
                    return false;
                if(n > 1)
                    q.offer(n);
                sum += n;
            }
            return true;
        }
    }

    static class s1359{//Count All Valid Pickup and Delivery Options
        public int countOrders(int n){
            if(n == 1)
                return 1;
            return (int) (((long) n * (2 * n - 1) * countOrders(n - 1)) % 1_000_000_007);
        }
    }

    static class s1373{//Maximum Sum BST in Binary Tree
        int r = 0;
        public int maxSumBST(TreeNode root){
            post(root);
            return r;
        }
        int[] post(TreeNode node){
            if(node == null)
                return new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE, 0}; //min, max, sum
            int[] left = post(node.left), right = post(node.right);
            if(left == null || right == null || node.val <= left[1] || node.val >= right[0])
                return null;
            int sum = node.val + left[2] + right[2];
            r = Math.max(r, sum);
            return new int[]{Math.min(node.val, left[0]), Math.max(node.val, right[1]), sum};
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

    static class s1380{//Lucky Numbers in a Matrix
        public List<Integer> luckyNumbers(int[][] m){
            int[] rows = IntStream.range(0, m.length).map(i -> Integer.MAX_VALUE).toArray(), cols = new int[m[0].length];
            for(int i = 0; i < rows.length; i++)
                for(int j = 0; j < cols.length; j++){
                    rows[i] = Math.min(rows[i], m[i][j]);
                    cols[j] = Math.max(cols[j], m[i][j]);
                }
            List<Integer> r = new ArrayList<>();
            for(int i = 0; i < rows.length; i++)
                for(int j = 0; j < cols.length; j++)
                    if(rows[i] == cols[j])
                        r.add(m[i][j]);
            return r;
        }
    }

    static class s1383{//Maximum Performance of a Team
        public int maxPerformance(int n, int[] speed, int[] efficiency, int k){
            int[][] es = new int[n][2];
            for(int i = 0; i < n; i++)
                es[i] = new int[]{efficiency[i], speed[i]};
            Arrays.sort(es, (a, b) -> b[0] - a[0]);
            PriorityQueue<Integer> q = new PriorityQueue<>(k);
            long r = 0, speedSum = 0;
            for(int[] e : es){
                q.offer(e[1]);
                speedSum += e[1];
                if(q.size() > k)
                    speedSum -= q.poll();
                r = Math.max(r, speedSum * e[0]);
            }
            return (int) (r % 1_000_000_007);
        }
    }
}
