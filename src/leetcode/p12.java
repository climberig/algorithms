package leetcode;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class p12{
    static class s1200{//Minimum Absolute Difference
        public List<List<Integer>> minimumAbsDifference(int[] a){
            Arrays.sort(a);
            List<List<Integer>> r = new ArrayList<>();
            for(int i = 1, minDiff = Integer.MAX_VALUE; i < a.length; i++){
                if(a[i] - a[i - 1] < minDiff){
                    minDiff = a[i] - a[i - 1];
                    r.clear();
                }
                if(a[i] - a[i - 1] == minDiff)
                    r.add(Arrays.asList(a[i - 1], a[i]));
            }
            return r;
        }
    }

    static class s1202{//Smallest String With Swaps
        public String smallestStringWithSwaps(String s, List<List<Integer>> pairs){
            List<List<Integer>> g = IntStream.range(0, s.length()).mapToObj(i -> new ArrayList<Integer>()).collect(Collectors.toList());
            for(List<Integer> p : pairs){
                g.get(p.get(0)).add(p.get(1));
                g.get(p.get(1)).add(p.get(0));
            }
            boolean[] seen = new boolean[s.length()];
            char[] r = new char[s.length()];
            for(int i = 0; i < s.length(); i++)
                if(!seen[i]){
                    List<Integer> indices = new ArrayList<>();
                    dfs(i, seen, indices, s, g);
                    Collections.sort(indices);
                    List<Character> chars = indices.stream().map(s::charAt).sorted().collect(Collectors.toList());
                    for(int j = 0; j < chars.size(); j++)
                        r[indices.get(j)] = chars.get(j);
                }
            return new String(r);
        }

        void dfs(int i, boolean[] seen, List<Integer> indices, String s, List<List<Integer>> g){
            seen[i] = true;
            indices.add(i);
            for(Integer j : g.get(i))
                if(!seen[j])
                    dfs(j, seen, indices, s, g);
        }
    }

    static class s1208{//Get Equal Substrings Within Budget
        public int equalSubstring(String s, String t, int maxCost){
            int r = 0;
            for(int i = 0, j = 0; j < s.length(); j++){
                maxCost -= Math.abs(s.charAt(j) - t.charAt(j));
                while(maxCost < 0)
                    maxCost += Math.abs(s.charAt(i) - t.charAt(i++));
                r = Math.max(r, j - i + 1);
            }
            return r;
        }
    }

    static class s1213{//Intersection of Three Sorted Arrays
        public List<Integer> arraysIntersection(int[] a1, int[] a2, int[] a3){
            List<Integer> r = new ArrayList<>();
            for(int i = 0, j = 0, k = 0; i < a1.length && j < a2.length && k < a3.length; )
                if(a1[i] == a2[j] && a2[j] == a3[k]){
                    r.add(a1[i++]);
                    j++;
                    k++;
                }else if(a1[i] < a2[j])
                    i++;
                else if(a2[j] < a3[k])
                    j++;
                else k++;
            return r;
        }
    }

    static class s1216{//Valid Palindrome III
        public boolean isValidPalindrome(String s, int k){
            int len = lcs(s, new StringBuilder(s).reverse().toString(), s.length());
            return s.length() - len <= k;
        }

        int lcs(String s1, String s2, int n){
            int[][] dp = new int[n + 1][n + 1];
            for(int i = 0; i < n; i++)
                for(int j = 0; j < n; j++)
                    if(s1.charAt(i) == s2.charAt(j))
                        dp[i + 1][j + 1] = dp[i][j] + 1;
                    else dp[i + 1][j + 1] = Math.max(dp[i][j + 1], dp[i + 1][j]);
            return dp[n][n];
        }
    }

    static class s1220{//Count Vowels Permutation
        public int countVowelPermutation(int n){
            int[][] m = {{1}, {0, 2}, {0, 1, 3, 4}, {2, 4}, {0}};
            long[] ends = {1, 1, 1, 1, 1};
            while(--n > 0){
                long[] next = new long[ends.length];
                for(int i = 0; i < ends.length; i++)
                    for(int j : m[i])
                        next[j] = (ends[i] + next[j]) % 1_000_000_007;
                ends = next;
            }
            return (int) (Arrays.stream(ends).sum() % 1_000_000_007);
        }
    }

    static class s1228{//Missing Number In Arithmetic Progression
        public int missingNumber(int[] a){
            return (a[0] + a[a.length - 1]) * (a.length + 1) / 2 - Arrays.stream(a).sum();
        }
    }

    static class s1230{//Toss Strange Coins
        public double probabilityOfHeads(double[] prob, int target){
            return heads(0, target, new Double[prob.length + 1][target + 1], prob);
        }

        double heads(int i, int target, Double[][] dp, double[] p){
            if(i == p.length)
                return target == 0 ? 1 : 0;
            if(dp[i][target] != null)
                return dp[i][target];
            double r = heads(i + 1, target, dp, p) * (1 - p[i]);
            if(target > 0)
                r += heads(i + 1, target - 1, dp, p) * p[i];
            return dp[i][target] = r;
        }
    }

    static class s1231{//Divide Chocolate
        public int maximizeSweetness(int[] sweetness, int k){
            int lo = 0, hi = Arrays.stream(sweetness).sum(), r = 0;
            while(lo <= hi){
                int mid = (lo + hi) / 2, m = k + 1, sum = 0;
                for(int i = 0; i < sweetness.length && m > 0; i++)
                    if((sum += sweetness[i]) >= mid){
                        sum = 0;
                        m--;
                    }
                if(m == 0){
                    r = mid;
                    lo = mid + 1;
                }else hi = mid - 1;
            }
            return r;
        }
    }

    static class s1234{//Replace the Substring for Balanced String
        public int balancedString(String s){
            int fr[] = new int[128], r = s.length(), k = s.length() / 4;
            for(int i = 0; i < s.length(); i++)
                fr[s.charAt(i)]++;
            for(int i = 0, j = 0; i < s.length(); i++){
                fr[s.charAt(i)]--;
                while(j < s.length() && fr['Q'] <= k && fr['W'] <= k && fr['E'] <= k && fr['R'] <= k){
                    r = Math.min(r, i - j + 1);
                    fr[s.charAt(j++)]++;
                }
            }
            return r;
        }
    }

    static class s1235{//Maximum Profit in Job Scheduling
        public int jobScheduling(int[] startTime, int[] endTime, int[] profit){
            int[][] jobs = new int[startTime.length][3];
            for(int i = 0; i < startTime.length; i++)
                jobs[i] = new int[]{startTime[i], endTime[i], profit[i]};
            Arrays.sort(jobs, Comparator.comparingInt(j -> j[1]));
            TreeMap<Integer, Integer> dp = new TreeMap<>();
            dp.put(0, 0);
            for(int[] j : jobs)
                dp.put(j[1], Math.max(j[2] + dp.lowerEntry(j[0] + 1).getValue(), dp.lowerEntry(j[1] + 1).getValue()));
            return dp.lastEntry().getValue();
        }
    }

    static class s1236{//Web Crawler
        public List<String> crawl(String startUrl, HtmlParser parser){
            Set<String> r = new HashSet<>();
            int idx = startUrl.indexOf('/', startUrl.indexOf('.'));
            String host = idx > 0 ? startUrl.substring(0, idx) : startUrl;
            Queue<String> q = new LinkedList<>();
            for(q.add(startUrl), r.add(startUrl); !q.isEmpty(); ){
                String url = q.poll();
                for(String subUrl : parser.getUrls(url))
                    if(subUrl.startsWith(host) && r.add(subUrl))
                        q.offer(subUrl);
            }
            return r.stream().toList();
        }
        interface HtmlParser{
            List<String> getUrls(String url);
        }
    }

    static class s1242{//Web Crawler Multithreaded
        public List<String> crawl(String startUrl, HtmlParser htmlParser){
            ExecutorService es = Executors.newFixedThreadPool(10, r -> {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            });
            Set<String> seen = new HashSet<>();
            BlockingDeque<Future<List<String>>> q = new LinkedBlockingDeque<>();
            int idx = startUrl.indexOf('/', startUrl.indexOf('.'));
            String host = idx > 0 ? startUrl.substring(0, idx) : startUrl;
            q.add(es.submit(() -> htmlParser.getUrls(startUrl)));
            seen.add(startUrl);
            try{
                while(!q.isEmpty())
                    for(String url : q.poll().get())
                        if(url.startsWith(host) && seen.add(url))
                            q.add(es.submit(() -> htmlParser.getUrls(url)));
            }catch(InterruptedException | ExecutionException ignored){}
            return new ArrayList<>(seen);
        }
        interface HtmlParser{
            List<String> getUrls(String url);
        }
    }

    static class s1248{//Count Number of Nice Subarrays
        public int numberOfSubarrays(int[] a, int k){
            int r = 0;
            for(int i = 0, j = 0, count = 0; j < a.length; j++){
                if(a[j] % 2 == 1){
                    k--;
                    count = 0;
                }
                while(k == 0){
                    k += a[i++] % 2;
                    count++;
                }
                r += count;
            }
            return r;
        }
    }

    static class s1250{//Check If It Is a Good Array
        public boolean isGoodArray(int[] a){
            int d = a[0];
            for(int i = 0; i < a.length && d != 1; i++)
                d = gcd(a[i], d);
            return d == 1;
        }

        int gcd(int a, int b){return b == 0 ? a : gcd(b, a % b);}
    }

    static class s1254{//Number of Closed Islands
        public int closedIsland(int[][] g){
            for(int c = 0; c < g[0].length; c++){
                sink(0, c, g);
                sink(g.length - 1, c, g);
            }
            for(int r = 0; r < g.length; r++){
                sink(r, 0, g);
                sink(r, g[0].length - 1, g);
            }
            int r = 0;
            for(int i = 0; i < g.length; i++)
                for(int j = 0; j < g[0].length; j++)
                    r += sink(i, j, g) > 0 ? 1 : 0;
            return r;
        }

        int sink(int r, int c, int[][] g){
            if(0 <= r && r < g.length && 0 <= c && c < g[0].length && g[r][c] == 0)
                return (g[r][c] = 1) + sink(r + 1, c, g) + sink(r - 1, c, g) + sink(r, c + 1, g) + sink(r, c - 1, g);
            return 0;
        }
    }

    static class s1259{//Handshakes That Don't Cross
        /**
         * You are given an even number of people numPeople that stand around a circle and each person shakes hands with someone else so that there
         * are numPeople / 2 handshakes total. Return the number of ways these handshakes could occur such that none of the handshakes cross.
         */
        public int numberOfWays(int n){
            long dp[] = new long[n + 1];
            dp[0] = 1;
            for(int i = 2; i <= n; i += 2)
                for(int j = 2; j <= i; j += 2)
                    dp[i] = (dp[i] + (dp[j - 2] * dp[i - j])) % 1_000_000_007;
            return (int) dp[n];
        }
    }

    static class s1263{//Minimum Moves to Move a Box to Their Target Location
        public int minPushBox(char[][] g){
            int user[] = null, box[] = null, target[] = null, dirs[] = {-1, 0, 1, 0, -1}, r = 0;
            for(int i = 0; i < g.length; i++)
                for(int j = 0; j < g[0].length; j++)
                    if(g[i][j] == 'S')
                        user = new int[]{i, j};
                    else if(g[i][j] == 'B')
                        box = new int[]{i, j};
                    else if(g[i][j] == 'T')
                        target = new int[]{i, j};
            Queue<int[]> q = new LinkedList<>();
            boolean[][][][] seen = new boolean[g.length][g[0].length][g.length][g[0].length];
            seen[box[0]][box[1]][user[0]][user[1]] = true;
            for(q.offer(new int[]{box[0], box[1], user[0], user[1]}); !q.isEmpty(); r++)
                for(int i = q.size(); i > 0; i--){
                    int[] e = q.poll(), b = new int[]{e[0], e[1]}, u = new int[]{e[2], e[3]};
                    if(Arrays.equals(b, target))
                        return r;
                    for(int d = 1; d < dirs.length; d++){
                        int x = b[0] + dirs[d - 1], y = b[1] + dirs[d];
                        if(0 <= x && x < g.length && 0 <= y && y < g[0].length && g[x][y] != '#' && !seen[x][y][b[0]][b[1]] &&
                                canReach(g, u, b, new int[]{b[0] - dirs[d - 1], b[1] - dirs[d]}, dirs)){
                            seen[x][y][b[0]][b[1]] = true;
                            q.offer(new int[]{x, y, b[0], b[1]});
                        }
                    }
                }
            return -1;
        }

        boolean canReach(char[][] g, int[] user, int[] box, int[] target, int[] dirs){
            Queue<int[]> q = new ArrayDeque<>();
            boolean[][] seen = new boolean[g.length][g[0].length];
            for(q.offer(user), seen[user[0]][user[1]] = true; !q.isEmpty(); ){
                int[] p = q.poll();
                if(Arrays.equals(p, target))
                    return true;
                for(int d = 1; d < dirs.length; d++){
                    int x = p[0] + dirs[d - 1], y = p[1] + dirs[d];
                    if(0 <= x && x < g.length && 0 <= y && y < g[0].length && g[x][y] != '#' &&
                            !Arrays.equals(new int[]{x, y}, box) && !seen[x][y]){
                        seen[x][y] = true;
                        q.offer(new int[]{x, y});
                    }
                }
            }
            return false;
        }
    }

    static class s1271{//Hexspeak
        public String toHexspeak(String s){
            char[] hex = Long.toHexString(Long.parseLong(s)).toCharArray();
            for(int i = 0; i < hex.length; i++)
                if(hex[i] == '0')
                    hex[i] = 'O';
                else if(hex[i] == '1')
                    hex[i] = 'I';
                else if(Character.isDigit(hex[i]))
                    return "ERROR";
            return new String(hex).toUpperCase();
        }
    }

    static class s1273{//Delete Tree Nodes
        public int deleteTreeNodes(int size, int[] parents, int[] vals){
            List<List<Integer>> g = new ArrayList<>(size);
            IntStream.range(0, size).forEach(i -> g.add(new ArrayList<>()));
            for(int i = 1; i < parents.length; i++)
                g.get(parents[i]).add(i);
            return dfs(g, vals, 0)[1];
        }

        int[] dfs(List<List<Integer>> g, int[] vals, int node){
            int sum = vals[node], count = 1;
            for(Integer child : g.get(node)){
                int[] r = dfs(g, vals, child);
                sum += r[0];
                count += r[1];
            }
            if(sum == 0)
                count = 0;
            return new int[]{sum, count};
        }
    }

    static class s1274{//Number of Ships in a Rectangle
        public int countShips(Sea sea, int[] R, int[] L){
            if(!sea.hasShips(R, L))
                return 0;
            if(R[0] == L[0] && R[1] == L[1])
                return 1;
            int x = (R[0] + L[0]) / 2, y = (R[1] + L[1]) / 2;
            return countShips(sea, new int[]{x, y}, L) +
                    countShips(sea, R, new int[]{x + 1, y + 1}) +
                    countShips(sea, new int[]{x, R[1]}, new int[]{L[0], y + 1}) +
                    countShips(sea, new int[]{R[0], y}, new int[]{x + 1, L[1]});
        }

        interface Sea{
            boolean hasShips(int[] topRight, int[] bottomLeft);
        }
    }

    static class s1278{//Palindrome Partitioning III
        public int palindromePartition(String s, int k){
            return min(s.toCharArray(), 0, k, new Integer[s.length()][k + 1]);
        }

        int min(char[] a, int start, int k, Integer[][] dp){
            if(k == 1)
                return changes(start, a.length - 1, a);
            if(a.length - start == k)
                return 0;
            if(dp[start][k] != null)
                return dp[start][k];
            int r = Integer.MAX_VALUE;
            for(int i = start; i + k <= a.length; i++){
                int changes = changes(start, i, a) + min(a, i + 1, k - 1, dp);
                r = Math.min(r, changes);
            }
            return dp[start][k] = r;
        }

        int changes(int lo, int hi, char[] a){
            int r = 0;
            for(; lo < hi; lo++, hi--)
                if(a[lo] != a[hi])
                    r++;
            return r;
        }
    }

    static class s1284{//Minimum Number of Flips to Convert Binary Matrix to Zero Matrix
        public int minFlips(int[][] m){
            Set<String> seen = new HashSet<>();
            Queue<int[][]> q = new LinkedList<>();
            int flips = 0;
            for(q.add(m), seen.add(str(m)); !q.isEmpty(); flips++)
                for(int size = q.size(); size > 0; size--){
                    int[][] p = q.poll();
                    if(Arrays.stream(p).map(row -> Arrays.stream(row).sum()).allMatch(s -> s == 0))
                        return flips;
                    for(int i = 0; i < m.length; i++)
                        for(int j = 0; j < m[0].length; j++){
                            int[][] s = new int[p.length][p[0].length];
                            for(int k = 0; k < p.length; k++)
                                s[k] = p[k].clone();
                            flip(i, j, s);
                            flip(i + 1, j, s);
                            flip(i - 1, j, s);
                            flip(i, j + 1, s);
                            flip(i, j - 1, s);
                            if(seen.add(str(s)))
                                q.offer(s);
                        }
                }
            return -1;
        }

        void flip(int i, int j, int[][] s){
            if(0 <= i && i < s.length && 0 <= j && j < s[0].length)
                s[i][j] = 1 - s[i][j];
        }

        String str(int[][] m){return Arrays.stream(m).map(Arrays::toString).collect(Collectors.joining(""));}
    }

    static class s1287{//Element Appearing More Than 25% In Sorted Array
        public int findSpecialInteger(int[] a){
            for(int i = 0, t = a.length / 4; i < a.length; i++)
                if(a[i] == a[i + t])
                    return a[i];
            return -1;
        }
    }

    static class s1289{//Minimum Falling Path Sum II
        public int minFallingPathSum(int[][] a){
            for(int r = a.length - 2; r >= 0; r--)
                for(int c = 0; c < a.length; c++){
                    int min = Integer.MAX_VALUE;
                    for(int i = 0; i < a.length; i++)
                        if(i != c)
                            min = Math.min(min, a[r + 1][i]);
                    a[r][c] += min;
                }
            return Arrays.stream(a[0]).min().getAsInt();
        }
    }

    static class s1292{//Maximum Side Length of a Square with Sum Less than or Equal to Threshold
        public int maxSideLength(int[][] m, int threshold){
            int[][] dp = new int[m.length + 1][m[0].length + 1];
            for(int i = 0; i < m.length; i++)
                for(int j = 0; j < m[0].length; j++)
                    dp[i + 1][j + 1] = m[i][j] + dp[i + 1][j] + dp[i][j + 1] - dp[i][j];
            for(int side = Math.min(m.length, m[0].length); side > 0; side--)
                for(int i = 0; i <= m.length - side; i++)
                    for(int j = 0; j <= m[0].length - side; j++)
                        if(dp[i + side][j + side] - dp[i][j + side] - dp[i + side][j] + dp[i][j] <= threshold)
                            return side;
            return 0;
        }
    }

    static class s1293{//Shortest Path in a Grid with Obstacles Elimination
        public int shortestPath(int[][] g, int k){
            int[][] minObstacles = new int[g.length][g[0].length];
            IntStream.range(0, g.length).forEach(i -> Arrays.fill(minObstacles[i], Integer.MAX_VALUE));
            Queue<int[]> q = new LinkedList<>();
            q.add(new int[]{0, 0, 0});
            minObstacles[0][0] = 0;
            for(int dist = 0, dirs[] = {-1, 0, 1, 0, -1}; !q.isEmpty(); dist++)
                for(int size = q.size(); size > 0; size--){
                    int p[] = q.poll(), x = p[0], y = p[1];
                    if(x == g.length - 1 && y == g[0].length - 1)
                        return dist;
                    for(int d = 1; d < dirs.length; d++){
                        int nx = x + dirs[d - 1], ny = y + dirs[d];
                        if(0 <= nx && nx < g.length && 0 <= ny && ny < g[0].length){
                            int obstacles = g[nx][ny] + p[2];
                            if(obstacles <= k && obstacles < minObstacles[nx][ny]){
                                minObstacles[nx][ny] = obstacles;
                                q.offer(new int[]{nx, ny, obstacles});
                            }
                        }
                    }
                }
            return -1;
        }
    }

    static class s1295{//Find Numbers with Even Number of Digits
        public int findNumbers(int[] a){
            return (int) Arrays.stream(a).filter(n -> (int) Math.log10(n) % 2 == 1).count();
        }
    }

    static class s1297{//Maximum Number of Occurrences of a Substring
        public int maxFreq(String s, int maxLetters, int minSize, int maxSize){
            Map<String, Integer> m = new HashMap<>();
            int count[] = new int[26], r = 0;
            for(int i = 0; i < s.length(); i++){
                count[s.charAt(i) - 'a']++;
                if(i >= minSize - 1){
                    int uniq = (int) Arrays.stream(count).filter(f -> f > 0).count();
                    if(uniq <= maxLetters){
                        String sub = s.substring(i - minSize + 1, i + 1);
                        m.put(sub, m.getOrDefault(sub, 0) + 1);
                        r = Math.max(r, m.get(sub));
                    }
                    count[s.charAt(i - minSize + 1) - 'a']--;
                }
            }
            return r;
        }
    }

    static class s1298{//Maximum Candies You Can Get from Boxes
        public int maxCandies(int[] status, int[] candies, int[][] keys, int[][] containedBoxes, int[] initialBoxes){
            Set<Integer> opened = new HashSet<>(), pending = new HashSet<>();
            Queue<Integer> q = new LinkedList<>();
            for(int b : initialBoxes)
                if(status[b] == 1){
                    opened.add(b);
                    q.offer(b);
                }else pending.add(b);
            int r = 0;
            while(!q.isEmpty()){
                Integer currBox = q.poll();
                r += candies[currBox];
                for(int key : keys[currBox]){
                    status[key] = 1;
                    if(pending.contains(key) && opened.add(key))
                        q.offer(key);
                }
                for(int box : containedBoxes[currBox])
                    if(status[box] == 1 && opened.add(box))
                        q.offer(box);
                    else pending.add(box);
            }
            return r;
        }
    }
}
