package leetcode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class p9{
    static class s905{//Sort Array By Parity
        public int[] sortArrayByParity(int[] a){
            for(int i = 0, j = 0; j < a.length; j++)
                if(a[j] % 2 == 0){
                    int t = a[i];
                    a[i++] = a[j];
                    a[j] = t;
                    ;
                }
            return a;
        }
    }

    static class s914{//X of a Kind in a Deck of Cards
        public boolean hasGroupsSizeX(int[] deck){
            int f[] = new int[10_000], r = 0;
            Arrays.stream(deck).forEach(n -> f[n]++);
            for(int d : f)
                r = gcd(r, d);
            return r > 1;
        }

        int gcd(int a, int b){return b == 0 ? a : gcd(b, a % b);}
    }

    static class s917{//Reverse Only Letters
        public String reverseOnlyLetters(String s){
            char[] a = s.toCharArray();
            for(int lo = 0, hi = a.length - 1; lo < hi; ){
                for(; lo < hi && !Character.isLetter(a[lo]); lo++) ;
                for(; lo < hi && !Character.isLetter(a[hi]); hi--) ;
                char t = a[lo];
                a[lo++] = a[hi];
                a[hi--] = t;
            }
            return new String(a);
        }
    }

    static class s923{//3Sum With Multiplicity
        public int threeSumMulti(int[] a, int target){
            long f[] = new long[101], r = 0;
            Arrays.stream(a).forEach(n -> f[n]++);
            for(int i = 0; i < f.length; i++)
                for(int j = i; j < f.length; j++){
                    int k = target - i - j;
                    if(0 <= k && k < f.length){
                        if(i == j && j == k)
                            r += f[i] * (f[i] - 1) * (f[i] - 2) / 6;
                        else if(i == j)
                            r += f[i] * (f[i] - 1) * f[k] / 2;
                        else if(j < k)
                            r += f[i] * f[j] * f[k];
                    }
                }
            return (int) (r % 1_000_000_007);
        }
    }

    static class s925{
        public boolean isLongPressedName(String name, String typed){
            int i = 0, j = 0;
            while(i < name.length() && j < typed.length() && name.charAt(i) == typed.charAt(j)){
                for(; i < name.length() && j < typed.length() && name.charAt(i) == typed.charAt(j); i++, j++) ;
                for(; j < typed.length() && name.charAt(i - 1) == typed.charAt(j); j++) ;
            }
            return i == name.length() && j == typed.length();
        }
    }
    static class s927{//Three Equal Parts
        public int[] threeEqualParts(int[] a){
            int ones = Arrays.stream(a).sum(), negative[] = {-1, -1};
            if(ones == 0)
                return new int[]{0, 2};
            if(ones % 3 != 0)
                return negative;
            int third = a.length - 1;
            for(int count = ones / 3; count > 0; count -= a[third--]) ;
            int i = find(a, 0, third + 1), j = find(a, i, third + 1);
            return i >= 0 && j >= 0 ? new int[]{i - 1, j} : negative;
        }

        int find(int[] a, int i, int j){
            if(i == -1)
                return -1;
            for(; a[i] == 0; i++) ;
            for(; j < a.length; j++, i++)
                if(a[i] != a[j])
                    return -1;
            return i;
        }
    }

    static class s931{//Minimum Falling Path Sum
        public int minFallingPathSum(int[][] a) {
            for (int i = 1; i < a.length; i++)
                for (int c = 0; c < a.length; c++) {
                    int min = a[i - 1][c];
                    if (c > 0)
                        min = Math.min(min, a[i - 1][c - 1]);
                    if (c < a.length - 1)
                        min = Math.min(min, a[i - 1][c + 1]);
                    a[i][c] += min;
                }
            return Arrays.stream(a[a.length - 1]).min().getAsInt();
        }
    }

    static class s935{//Knight Dialer
        public int knightDialer(int n){
            int m[][] = {{4, 6}, {6, 8}, {7, 9}, {4, 8}, {0, 3, 9}, {}, {0, 1, 7}, {2, 6}, {1, 3}, {2, 4}}, counts[] = new int[10];
            for(Arrays.fill(counts, 1); n > 1; n--){
                int[] nextCounts = new int[10];
                for(int i = 0; i <= 9; i++)
                    for(int d : m[i])
                        nextCounts[d] = (counts[i] + nextCounts[d]) % 1_000_000_007;
                counts = nextCounts;
            }
            return (int) (Arrays.stream(counts).asLongStream().sum() % 1_000_000_007);
        }
    }

    static class s936{//Stamping The Sequence
        public int[] movesToStamp(String stampS, String targetS){
            char[] stamp = stampS.toCharArray(), target = targetS.toCharArray();
            List<Integer> r = new ArrayList<>();
            boolean[] seen = new boolean[target.length];
            for(int allStars = 0, stars = 0; allStars < target.length; allStars += stars, stars = 0){
                for(int i = 0; i <= target.length - stamp.length && allStars + stars < target.length; i++)
                    if(!seen[i] && canReplace(target, i, stamp)){
                        for(int j = 0; j < stamp.length; j++)
                            if(target[j + i] != '*'){
                                target[j + i] = '*';
                                stars++;
                            }
                        seen[i] = true;
                        r.add(i);
                    }
                if(stars == 0)
                    return new int[0];
            }
            return IntStream.range(0, r.size()).map(i -> r.get(r.size() - i - 1)).toArray();
        }

        boolean canReplace(char[] target, int p, char[] stamp){
            for(int i = 0; i < stamp.length; i++)
                if(target[i + p] != '*' && target[i + p] != stamp[i])
                    return false;
            return true;
        }
    }

    static class s937{//Reorder Data in Log Files
        public String[] reorderLogFiles(String[] logs){
            return Arrays.stream(logs).sorted((s1, s2) -> {
                String[] p1 = s1.split(" ", 2), p2 = s2.split(" ", 2);
                boolean digit1 = Character.isDigit(p1[1].charAt(0)), digit2 = Character.isDigit(p2[1].charAt(0));
                if(!digit1 && !digit2){
                    int cmp = p1[1].compareTo(p2[1]);
                    return cmp != 0 ? cmp : p1[0].compareTo(p2[0]);
                }else if(digit1 && digit2)
                    return 0;
                else return digit1 ? 1 : -1;
            }).toArray(String[]::new);
        }
    }

    static class s938{//Range Sum of BST
        public int rangeSumBST(TreeNode root, int low, int high){
            if(root == null)
                return 0;
            if(low <= root.val && root.val <= high)
                return root.val + rangeSumBST(root.left, low, root.val) + rangeSumBST(root.right, root.val, high);
            else if(root.val < low)
                return rangeSumBST(root.right, low, high);
            else return rangeSumBST(root.left, low, high);
        }
    }

    static class s940{//Distinct Subsequences II
        public int distinctSubseqII(String s){
            long ends[] = new long[26], m = 1_000_000_007;
            for(char c : s.toCharArray())
                ends[c - 'a'] = (Arrays.stream(ends).sum() + 1) % m;
            return (int) (Arrays.stream(ends).sum() % m);
        }
    }

    static class s944{//Delete Columns to Make Sorted
        public int minDeletionSize(String[] a) {
            int r = 0;
            for (int j = 0; j < a[0].length(); j++)
                for (int i = 1; i < a.length; i++)
                    if (a[i - 1].charAt(j) > a[i].charAt(j)) {
                        r++;
                        break;
                    }
            return r;
        }
    }

    static class s947{//Most Stones Removed with Same Row or Column
        public int removeStones(int[][] stones){
            int[] uf = IntStream.range(0, stones.length).toArray();
            for(int i = 0; i < stones.length; i++)
                for(int j = i + 1; j < stones.length; j++)
                    if(stones[i][0] == stones[j][0] || stones[i][1] == stones[j][1]){
                        int x = find(i, uf), y = find(j, uf);
                        if(x != y)
                            uf[x] = y;
                    }
            return stones.length - (int) IntStream.range(0, stones.length).filter(x -> x == find(x, uf)).count();
        }

        int find(int x, int[] uf){return x == uf[x] ? x : find(uf[x], uf);}
    }

    static class s948{//Bag of Tokens
        public int bagOfTokensScore(int[] tokens, int power){
            Arrays.sort(tokens);
            int r = 0, score = 0;
            for(int i = 0, j = tokens.length - 1; i <= j && score >= 0; )
                if(power >= tokens[i]){
                    power -= tokens[i++];
                    r = Math.max(r, ++score);
                }else{
                    power += tokens[j--];
                    score--;
                }
            return r;
        }
    }

    static class s952{//Largest Component Size by Common Factor
        public int largestComponentSize(int[] a){
            int[] uf = IntStream.range(0, 100_001).toArray();
            for(int n : a)
                for(int d = 2; d <= Math.sqrt(n); d++)
                    if(n % d == 0)
                        for(Integer q : Arrays.asList(d, n / d)){
                            int x = find(n, uf), y = find(q, uf);
                            if(x != y)
                                uf[x] = y;
                        }
            Map<Integer, Integer> m = new HashMap<>();
            int r = 0;
            for(int n : a){
                int j = find(n, uf);
                m.put(j, m.getOrDefault(j, 0) + 1);
                r = Math.max(r, m.get(j));
            }
            return r;
        }

        int find(int x, int[] uf){
            return x == uf[x] ? x : (uf[x] = find(uf[x], uf));
        }
    }

    static class s953{//Verifying an Alien Dictionary
        public boolean isAlienSorted(String[] words, String order){
            int[] m = new int[26];
            for(int i = 0; i < order.length(); i++)
                m[order.charAt(i) - 'a'] = i;
            for(int i = 1; i < words.length; i++)
                if(bigger(words[i - 1], words[i], m))
                    return false;
            return true;
        }

        boolean bigger(String w1, String w2, int[] order){
            int i = 0, j = 0;
            for(; i < w1.length() && j < w2.length(); i++, j++)
                if(w1.charAt(i) != w2.charAt(i))
                    return order[w1.charAt(i) - 'a'] > order[w2.charAt(i) - 'a'];
            return w1.length() > w2.length();
        }
    }

    static class s954{//Array of Doubled Pairs
        public boolean canReorderDoubled(int[] a){
            Map<Integer, Integer> m = new TreeMap<>();
            Arrays.stream(a).forEach(n -> m.put(n, m.getOrDefault(n, 0) + 1));
            for(int x : m.keySet())
                if(m.get(x) > 0){
                    int x2 = x < 0 ? x / 2 : x * 2;
                    if(x < 0 && x % 2 != 0 || m.get(x) > m.getOrDefault(x2, 0))
                        return false;
                    m.put(x2, m.get(x2) - m.get(x));
                }
            return true;
        }
    }

    static class s955{//Delete Columns to Make Sorted II
        public int minDeletionSize(String[] ss){
            Set<Integer> deleteCols = new HashSet<>();
            for(int row = 1; row < ss.length; row++)
                for(int col = 0; col < ss[row].length(); col++){
                    if(deleteCols.contains(col) || ss[row - 1].charAt(col) == ss[row].charAt(col))
                        continue;
                    if(ss[row - 1].charAt(col) > ss[row].charAt(col)){
                        deleteCols.add(col);
                        row = 0;
                    }
                    break;
                }
            return deleteCols.size();
        }
    }

    static class s956{//Tallest Billboard
        public int tallestBillboard(int[] rods){
            return tallest(rods, 0, 0, 0, new HashMap<>());
        }

        int tallest(int[] rods, int i, int s1, int s2, Map<String, Integer> dp){
            if(i == rods.length)
                return s1 == s2 ? s1 : -1;
            String hash = i + "," + Math.abs(s1 - s2);
            if(dp.containsKey(hash))
                return dp.get(hash) == -1 ? -1 : Math.max(s1, s2) + dp.get(hash);
            int left = tallest(rods, i + 1, s1 + rods[i], s2, dp);
            int right = tallest(rods, i + 1, s1, s2 + rods[i], dp);
            int skip = tallest(rods, i + 1, s1, s2, dp);
            int r = Math.max(skip, Math.max(left, right));
            dp.put(hash, r == -1 ? -1 : r - Math.max(s1, s2));
            return r;
        }
    }

    static class s960{//Delete Columns to Make Sorted III
        public int minDeletionSize(String[] list){
            int n = list[0].length(), dp[] = new int[n];
            for(int i = 0; i < n; i++){
                dp[i] = 1;
                for(int j = 0; j < i; j++)
                    if(ordered(list, j, i))
                        dp[i] = Math.max(dp[i], dp[j] + 1);
            }
            return n - Arrays.stream(dp).max().getAsInt();
        }

        boolean ordered(String[] list, int j, int i){
            for(String s : list)
                if(s.charAt(j) > s.charAt(i))
                    return false;
            return true;
        }
    }

    static class s968{//Binary Tree Cameras
        int r, leaf = 0, covered = 1, leaf_parent = 2;

        public int minCameraCover(TreeNode root){return (dfs(root) == leaf ? 1 : 0) + r;}

        int dfs(TreeNode node){
            if(node == null)
                return covered;
            int left = dfs(node.left), right = dfs(node.right);
            if(left == leaf || right == leaf){
                r++;
                return leaf_parent;
            }
            return left == leaf_parent || right == leaf_parent ? covered : leaf;
        }
    }

    static class s972{//Equal Rational Numbers
        public boolean isRationalEqual(String s, String t){return f(s) == f(t);}

        double f(String s){
            for(int i = s.indexOf('('); i > 0; ){
                String base = s.substring(0, i), rep = s.substring(i + 1, s.length() - 1);
                for(int j = 0; j < 20; j++)
                    base += rep;
                return Double.parseDouble(base);
            }
            return Double.parseDouble(s);
        }
    }

    static class s975{//Odd Even Jump
        public int oddEvenJumps(int[] a){
            int n = a.length, r = 1;
            boolean[] lower = new boolean[n], higher = new boolean[n];
            higher[n - 1] = lower[n - 1] = true;
            TreeMap<Integer, Integer> m = new TreeMap<>();
            m.put(a[n - 1], n - 1);
            for(int i = n - 2; i >= 0; i--){
                Map.Entry<Integer, Integer> hi = m.ceilingEntry(a[i]), lo = m.floorEntry(a[i]);
                if(hi != null)
                    higher[i] = lower[hi.getValue()];
                if(lo != null)
                    lower[i] = higher[lo.getValue()];
                if(higher[i])
                    r++;
                m.put(a[i], i);
            }
            return r;
        }
    }

    static class s982{//Triples with Bitwise AND Equal To Zero
        public int countTriplets(int[] a){
            int m[] = new int[1 << 16], r = 0;
            for(int i : a)
                for(int j : a)
                    m[i & j]++;
            for(int i : a)
                for(int j = 0; j < m.length; j++)
                    if((i & j) == 0)
                        r += m[j];
            return r;
        }
    }

    static class s987{//Vertical Order Traversal of a Binary Tree
        public List<List<Integer>> verticalTraversal(TreeNode root){
            Map<Integer, Map<Integer, List<Integer>>> m = new TreeMap<>();
            traverse(0, 0, root, m);
            List<List<Integer>> r = new ArrayList<>();
            for(Integer col : m.keySet()){
                List<Integer> list = new ArrayList<>();
                for(Integer row : m.get(col).keySet())
                    list.addAll(m.get(col).get(row).stream().sorted().collect(Collectors.toList()));
                r.add(list);
            }
            return r;
        }

        void traverse(int col, int row, TreeNode node, Map<Integer, Map<Integer, List<Integer>>> m){
            if(node != null){
                m.putIfAbsent(col, new TreeMap<>());
                m.get(col).putIfAbsent(row, new ArrayList<>());
                m.get(col).get(row).add(node.val);
                traverse(col - 1, row, node.left, m);
                traverse(col + 1, row + 1, node.right, m);
            }
        }
    }

    static class s992{//Subarrays with K Different Integers
        public int subarraysWithKDistinct(int[] a, int k){
            return atMost(a, k) - atMost(a, k - 1);
        }
        int atMost(int[] a, int k){
            int r = 0, f[] = new int[a.length + 1];
            for(int i = 0, j = 0; j < a.length; j++){
                if(f[a[j]]++ == 0)
                    k--;
                for(; k < 0; i++)
                    if(--f[a[i]] == 0)
                        k++;
                r += j - i + 1;
            }
            return r;
        }
    }

    static class s995{//Minimum Number of K Consecutive Bit Flips
        public int minKBitFlips(int[] a, int k){
            int flipped = 0, r = 0, isFlipped[] = new int[a.length];
            for(int i = 0; i < a.length; i++){
                if(i >= k)
                    flipped ^= isFlipped[i - k];
                if(flipped == a[i]){
                    if(i + k > a.length)
                        return -1;
                    isFlipped[i] = 1;
                    flipped ^= 1;
                    r++;
                }
            }
            return r;
        }
    }

    static class s998{//Maximum Binary Tree II
        public TreeNode insertIntoMaxTree(TreeNode root, int val){
            if(root == null)
                return new TreeNode(val);
            if(root.val < val)
                return new TreeNode(val, root, null);
            root.right = insertIntoMaxTree(root.right, val);
            return root;
        }
    }

    static class s999{//Available Captures for Rook
        public int numRookCaptures(char[][] b){
            for(int i = 0; i < b.length; i++)
                for(int j = 0; j < b.length; j++)
                    if(b[i][j] == 'R')
                        return cap(1, 0, i, j, b) + cap(-1, 0, i, j, b) + cap(0, 1, i, j, b) + cap(0, -1, i, j, b);
            return 0;
        }

        int cap(int di, int dj, int i, int j, char[][] b){
            for(i = i + di, j = j + dj; 0 <= i && i < b.length && 0 <= j && j < b.length && b[i][j] != 'B'; i += di, j += dj)
                if(b[i][j] == 'p')
                    return 1;
            return 0;
        }
    }
}
