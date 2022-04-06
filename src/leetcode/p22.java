package leetcode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
public class p22{
    static class s2200{
        public List<Integer> findKDistantIndices(int[] a, int key, int k){
            List<Integer> r = new ArrayList<>();
            for(int i = 0, prev = 0; i < a.length; i++)
                if(a[i] == key){
                    for(int j = Math.max(i - k, prev); j < a.length && j <= i + k; j++)
                        r.add(j);
                    prev = i + k + 1;
                }
            return r;
        }
    }

    static class s2201{//Count Artifacts That Can Be Extracted
        public int digArtifacts(int n, int[][] artifacts, int[][] dig){
            Set<Integer> s = Arrays.stream(dig).map(d -> d[0] * 1000 + d[1]).collect(Collectors.toSet());
            return (int) Arrays.stream(artifacts).filter(a -> uncovered(a[0], a[1], a[2], a[3], s)).count();
        }

        boolean uncovered(int r1, int c1, int r2, int c2, Set<Integer> s){
            for(int i = r1; i <= r2; i++)
                for(int j = c1; j <= c2; j++)
                    if(!s.contains(1000 * i + j))
                        return false;
            return true;
        }
    }

    static class s2202{//Maximize the Topmost Element After K Moves
        public int maximumTop(int[] a, int k){
            if(a.length == 1 && k % 2 == 1)
                return -1;
            if(k > a.length)
                return Arrays.stream(a).max().getAsInt();
            PriorityQueue<Integer> q = new PriorityQueue<>(Comparator.reverseOrder());
            IntStream.range(0, k - 1).forEach(i -> q.offer(a[i]));
            int r = !q.isEmpty() ? q.poll() : -1;
            return k < a.length ? Math.max(r, a[k]) : r;
        }
    }

    static class s2203{//Minimum Weighted Subgraph With the Required Paths
        public long minimumWeight(int n, int[][] edges, int src1, int src2, int dest){
            List<Map<Integer, Integer>> g = IntStream.range(0, n).mapToObj(i -> new HashMap<Integer, Integer>()).collect(Collectors.toList());
            List<Map<Integer, Integer>> g1 = IntStream.range(0, n).mapToObj(i -> new HashMap<Integer, Integer>()).collect(Collectors.toList());
            for(int[] e : edges){
                g.get(e[0]).put(e[1], Math.min(e[2], g.get(e[0]).getOrDefault(e[1], Integer.MAX_VALUE)));
                g1.get(e[1]).put(e[0], Math.min(e[2], g1.get(e[1]).getOrDefault(e[0], Integer.MAX_VALUE)));
            }
            long a[] = dfs(src1, g), b[] = dfs(src2, g), c[] = dfs(dest, g1), r = Long.MAX_VALUE;
            for(int i = 0; i < n; i++)
                if(a[i] != Long.MAX_VALUE && b[i] != Long.MAX_VALUE && c[i] != Long.MAX_VALUE)
                    r = Math.min(r, a[i] + b[i] + c[i]);
            return r == Long.MAX_VALUE ? -1 : r;
        }

        long[] dfs(int start, List<Map<Integer, Integer>> g){
            long[] dist = new long[g.size()];
            Arrays.fill(dist, Long.MAX_VALUE);
            PriorityQueue<long[]> q = new PriorityQueue<>(Comparator.comparingLong(a -> a[0]));
            dist[start] = 0;
            for(q.offer(new long[]{0, start}); !q.isEmpty(); ){
                long p[] = q.poll(), d = p[0], u = p[1];
                Map<Integer, Integer> adj = g.get((int) u);
                for(int v : adj.keySet())
                    if(d + adj.get(v) < dist[v]){
                        dist[v] = d + adj.get(v);
                        q.offer(new long[]{dist[v], v});
                    }
            }
            return dist;
        }
    }

    static class s2204{//Distance to a Cycle in Undirected Graph
        public int[] distanceToCycle(int n, int[][] edges){
            int[] degree = new int[n], r = new int[n];
            List<List<Integer>> g = IntStream.range(0, n).mapToObj(i -> new ArrayList<Integer>()).collect(Collectors.toList());
            for(int[] e : edges){
                degree[e[0]]++;
                degree[e[1]]++;
                g.get(e[0]).add(e[1]);
                g.get(e[1]).add(e[0]);
            }
            Queue<Integer> q = IntStream.range(0, n).filter(u -> degree[u] == 1).boxed().collect(Collectors.toCollection(LinkedList::new));
            while(!q.isEmpty()){
                degree[q.peek()]--;
                g.get(q.poll()).stream().filter(u -> degree[u] > 0 && --degree[u] == 1).forEach(q::add);
            }
            boolean[] seen = new boolean[n];
            IntStream.range(0, n).filter(u -> degree[u] > 0).forEach(u -> {
                seen[u] = true;
                q.offer(u);
            });
            for(int d = 1; !q.isEmpty(); d++)
                for(int size = q.size(); size > 0; size--)
                    for(Integer v : g.get(q.poll()))
                        if(!seen[v]){
                            seen[v] = true;
                            r[v] = d;
                            q.offer(v);
                        }
            return r;
        }
    }

    static class s2206{//Divide Array Into Equal Pairs
        public boolean divideArray(int[] a){
            int[] f = new int[501];
            Arrays.stream(a).forEach(n -> f[n]++);
            return Arrays.stream(f).allMatch(n -> n % 2 == 0);
        }
    }

    static class s2207{//Maximize Number of Subsequences in a String
        public long maximumSubsequenceCount(String text, String pattern){
            char c1 = pattern.charAt(0), c2 = pattern.charAt(1);
            return Math.max(count(c1, c2, text, 0, 1), count(c2, c1, text, text.length() - 1, -1));
        }

        long count(char c1, char c2, String text, int start, int step){
            long r = 0, c1Count = 0;
            for(int i = start; 0 <= i && i < text.length(); i += step){
                c1Count += text.charAt(i) == c1 ? 1 : 0;
                r += text.charAt(i) == c2 ? c1Count : 0;
            }
            return r + (c1 != c2 ? c1Count : 0);
        }
    }

    static class s2208{//Minimum Operations to Halve Array Sum
        public int halveArray(int[] a){
            double originSum = Arrays.stream(a).asLongStream().sum(), sum = originSum;
            PriorityQueue<Double> q = new PriorityQueue<>(Comparator.reverseOrder());
            Arrays.stream(a).forEach(n -> q.offer(1.0 * n));
            int r = 0;
            while(sum * 2 > originSum){
                Double n = q.poll();
                sum = sum - n + n / 2;
                q.offer(n / 2);
                r++;
            }
            return r;
        }
    }

    static class s2209{//Minimum White Tiles After Covering With Carpets
        public int minimumWhiteTiles(String floor, int nCarpets, int carpetLen){
            int[][] dp = new int[floor.length() + 1][nCarpets + 1];
            for(int i = 1; i <= floor.length(); i++)
                for(int c = 0; c <= nCarpets; c++){
                    int skip = dp[i - 1][c] + floor.charAt(i - 1) - '0';
                    int cover = c > 0 ? dp[Math.max(0, i - carpetLen)][c - 1] : floor.length();
                    dp[i][c] = Math.min(skip, cover);
                }
            return dp[floor.length()][nCarpets];
        }
    }

    static class s2210{//Count Hills and Valleys in an Array
        public int countHillValley(int[] a){
            int r = 0, left = a[0];
            for(int i = 1; i < a.length - 1; i++)
                if(left < a[i] && a[i] > a[i + 1] || left > a[i] && a[i] < a[i + 1]){
                    r++;
                    left = a[i];
                }
            return r;
        }
    }

    static class s2211{//Count Collisions on a Road
        public int countCollisions(String dir){
            int r = 0, i = 0, right = 0;
            for(; i < dir.length() && dir.charAt(i) == 'L'; i++) ;
            for(; i < dir.length(); i++)
                if(dir.charAt(i) == 'R')
                    right++;
                else{
                    r += dir.charAt(i) == 'S' ? right : right + 1;
                    right = 0;
                }
            return r;
        }
    }

    static class s2212{//Maximum Points in an Archery Competition
        int r[] = new int[12], maxPoints = 0;
        public int[] maximumBobPoints(int numArrows, int[] aliceArrows){
            bt(0, numArrows, aliceArrows, new int[aliceArrows.length], 0);
            return r;
        }

        void bt(int i, int numArrows, int[] aliceArrows, int[] bobArrows, int points){
            if(i >= aliceArrows.length && points > maxPoints){
                maxPoints = points;
                r = bobArrows.clone();
                r[0] += numArrows;
            }else if(i < aliceArrows.length){
                if(numArrows > aliceArrows[i]){
                    bobArrows[i] = aliceArrows[i] + 1;
                    bt(i + 1, numArrows - bobArrows[i], aliceArrows, bobArrows, points + i);
                    bobArrows[i] = 0;
                }
                bt(i + 1, numArrows, aliceArrows, bobArrows, points);
            }
        }
    }

    static class s2214{//Minimum Health to Beat Game
        public long minimumHealth(int[] damage, int armor){
            int maxProtect = Math.min(Arrays.stream(damage).max().getAsInt(), armor);
            return Arrays.stream(damage).asLongStream().sum() + 1 - maxProtect;
        }
    }

    static class s2215{//Find the Difference of Two Arrays
        public List<List<Integer>> findDifference(int[] a1, int[] a2){
            Set<Integer> s1 = Arrays.stream(a1).boxed().collect(Collectors.toSet());
            Set<Integer> s2 = Arrays.stream(a2).filter(n -> !s1.contains(n)).boxed().collect(Collectors.toSet());
            Arrays.stream(a2).forEach(s1::remove);
            return Arrays.asList(new ArrayList<>(s1), new ArrayList<>(s2));
        }
    }

    static class s2216{//Minimum Deletions to Make Array Beautiful
        public int minDeletion(int[] a){
            int r = 0, i = 0;
            for(; i < a.length - 1; i++)
                if(a[i] == a[i + 1])
                    r++;
                else i++;
            return r + a.length - i;
        }
    }

    static class s2217{//Find Palindrome With Fixed Length
        public long[] kthPalindrome(int[] queries, int intLength){
            long r[] = new long[queries.length], start = (long) Math.pow(10, (intLength + 1) / 2 - 1);
            for(int i = 0; i < queries.length; i++){
                String left = String.valueOf(start + queries[i] - 1);
                String right = intLength % 2 == 1 ? left.substring(0, left.length() - 1) : left;
                String s = left + new StringBuilder(right).reverse();
                r[i] = s.length() != intLength ? -1 : Long.parseLong(s);
            }
            return r;
        }
    }

    static class s2218{//Maximum Value of K Coins From Piles
        public int maxValueOfCoins(List<List<Integer>> piles, int k){
            return maxVal(0, piles, k, new Integer[piles.size() + 1][k + 1]);
        }
        int maxVal(int pileIdx, List<List<Integer>> piles, int k, Integer[][] dp){
            if(pileIdx >= piles.size())
                return 0;
            if(dp[pileIdx][k] != null)
                return dp[pileIdx][k];
            int r = maxVal(pileIdx + 1, piles, k, dp);
            for(int i = 0, curr = 0; i < piles.get(pileIdx).size() && i < k; i++){
                curr += piles.get(pileIdx).get(i);
                r = Math.max(r, curr + maxVal(pileIdx + 1, piles, k - i - 1, dp));
            }
            return dp[pileIdx][k] = r;
        }
    }

    static class s2219{//Maximum Sum Score of Array
        public long maximumSumScore(int[] a){
            long r = Long.MIN_VALUE, left = 0, right = Arrays.stream(a).asLongStream().sum();
            for(int n : a){
                left += n;
                r = Math.max(r, Math.max(left, right));
                right -= n;
            }
            return r;
        }
    }

    static class s2220{//Minimum Bit Flips to Convert Number
        public int minBitFlips(int start, int goal){
            return Integer.bitCount(start ^ goal);
        }
    }

    static class s2221{//Find Triangular Sum of an Array
        public int triangularSum(int[] a){
            for(int len = a.length - 1; len > 0; len--){
                int[] b = new int[len];
                for(int i = 0; i < b.length; i++)
                    b[i] = (a[i] + a[i + 1]) % 10;
                a = b;
            }
            return a[0];
        }
    }

    static class s2222{//Number of Ways to Select Buildings
        public long numberOfWays(String s){
            int n = s.length(), all1 = s.chars().map(c -> c - '0').sum(), all0 = s.length() - all1;
            long r = 0;
            for(int i = 0, ones = 0, zero = 0; i < s.length(); i++)
                if(s.charAt(i) == '0'){
                    r += (long) ones * (all1 - ones);
                    zero++;
                }else{
                    r += (long) zero * (all0 - zero);
                    ones++;
                }
            return r;
        }
    }

    static class s2223{//Sum of Scores of Built Strings
        public long sumScores(String s){
            char[] a = s.toCharArray();
            int n = a.length, x = 0, y = 0, z[] = new int[n];
            long r = n;
            for(int i = 1; i < n; i++){
                z[i] = Math.max(0, Math.min(z[i - x], y - i + 1));
                while(i + z[i] < n && a[z[i]] == a[i + z[i]]){
                    x = i;
                    y = i + z[i];
                    z[i]++;
                }
                r += z[i];
            }
            return r;
        }
    }

    static class s2224{//Minimum Number of Operations to Convert Time
        public int convertTime(String from, String to){
            int diff = min(to) - min(from), ops[] = {60, 15, 5, 1}, r = 0;
            for(int i = 0; i < ops.length && diff > 0; diff = diff % ops[i++])
                r += diff / ops[i];
            return r;
        }

        int min(String time){
            String[] t = time.split(":");
            return Integer.parseInt(t[0]) * 60 + Integer.parseInt(t[1]);
        }
    }

    static class s2225{//Find Players With Zero or One Losses
        public List<List<Integer>> findWinners(int[][] matches){
            Map<Integer, Integer> losses = new TreeMap<>();
            for(int[] m : matches){
                losses.put(m[0], losses.getOrDefault(m[0], 0));
                losses.put(m[1], losses.getOrDefault(m[1], 0) + 1);
            }
            List<List<Integer>> r = Arrays.asList(new ArrayList<>(), new ArrayList<>());
            for(Integer player : losses.keySet())
                if(losses.get(player) <= 1)
                    r.get(losses.get(player)).add(player);
            return r;
        }
    }

    static class s2226{//Maximum Candies Allocated to K Children
        public int maximumCandies(int[] candies, long k){
            int lo = 1, hi = 10_000_000, r = 0;
            while(lo <= hi){
                int amount = (lo + hi) / 2;
                long n = 0;
                for(int i = 0; i < candies.length && n < k; i++)
                    n += candies[i] / amount;
                if(n >= k){
                    r = amount;
                    lo = amount + 1;
                }else hi = amount - 1;
            }
            return r;
        }
    }

    static class s2227{//Encrypt and Decrypt Strings
        class Encrypter{
            Map<Character, String> enc = new HashMap<>();
            Map<String, Integer> counts = new HashMap<>();

            public Encrypter(char[] keys, String[] values, String[] dictionary){
                for(int i = 0; i < keys.length; i++)
                    enc.put(keys[i], values[i]);
                for(String word : dictionary){
                    String e = encrypt(word);
                    counts.put(e, counts.getOrDefault(e, 0) + 1);
                }
            }

            public String encrypt(String word){return word.chars().mapToObj(c -> enc.get((char) c)).collect(Collectors.joining());}

            public int decrypt(String word){return counts.getOrDefault(word, 0);}
        }
    }

    static class s2229{//Check if an Array Is Consecutive
        public boolean isConsecutive(int[] a){
            Arrays.sort(a);
            for(int i = 1; i < a.length; i++)
                if(a[i - 1] + 1 != a[i])
                    return false;
            return true;
        }
    }
}
