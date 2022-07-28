package leetcode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class p2{
    static class s207{//Course Schedule
        public boolean canFinish(int n, int[][] prereq){
            List<List<Integer>> g = IntStream.range(0, n).mapToObj(i -> new ArrayList<Integer>()).collect(Collectors.toList());
            int[] in = new int[n];
            for(int[] p : prereq){
                g.get(p[1]).add(p[0]);
                in[p[0]]++;
            }
            Queue<Integer> q = IntStream.range(0, n).filter(c -> in[c] == 0).boxed().collect(Collectors.toCollection(LinkedList::new));
            while(!q.isEmpty())
                for(Integer c : g.get(q.poll()))
                    if(--in[c] == 0)
                        q.offer(c);
            return Arrays.stream(in).allMatch(v -> v == 0);
        }
    }

    static class s208{//Implement Trie (Prefix Tree)
        class Trie{
            Trie[] nodes = new Trie[26];
            boolean word;

            public void insert(String w){
                Trie node = this;
                for(char c : w.toCharArray())
                    node = (node.nodes[c - 'a'] = node.nodes[c - 'a'] == null ? new Trie() : node.nodes[c - 'a']);
                node.word = true;
            }

            public boolean search(String w){
                Trie node = find(w);
                return node != null && node.word;
            }

            public boolean startsWith(String prefix){return find(prefix) != null;}

            Trie find(String w){
                Trie node = this;
                for(int i = 0; i < w.length() && node != null; node = node.nodes[w.charAt(i++) - 'a']) ;
                return node;
            }
        }
    }

    static class s216{//Combination Sum III
        public List<List<Integer>> combinationSum3(int k, int n){
            List<List<Integer>> r = new LinkedList<>();
            comb(k, n, 1, new LinkedList<>(), r);
            return r;
        }

        void comb(int k, int n, int i, LinkedList<Integer> list, List<List<Integer>> r){
            if(k == 0){
                if(n == 0)
                    r.add(new ArrayList<>(list));
            }else for(int j = i; j <= 9; j++)
                if(j <= n){
                    list.add(j);
                    comb(k - 1, n - j, j + 1, list, r);
                    list.removeLast();
                }
        }
    }

    static class s225{//Implement Stack using Queues
        class MyStack{
            Queue<Integer> q = new LinkedList<>();

            public void push(int x){
                q.offer(x);
                for(int i = 0; i < q.size() - 1; i++)
                    q.offer(q.remove());
            }

            public int pop(){return q.remove();}

            public int top(){return q.peek();}

            public boolean empty(){return q.isEmpty();}
        }
    }

    static class s226{//Invert Binary Tree
        public TreeNode invertTree(TreeNode root){
            if(root == null)
                return null;
            TreeNode left = root.left;
            root.left = invertTree(root.right);
            root.right = invertTree(left);
            return root;
        }
    }

    static class s228{//Summary Ranges
        public List<String> summaryRanges(int[] a){
            List<String> r = new ArrayList<>();
            for(int i = 0, v; i < a.length; i++){
                for(v = a[i]; i < a.length - 1 && a[i] + 1 == a[i + 1]; i++) ;
                r.add(v + (v != a[i] ? "->" + a[i] : ""));
            }
            return r;
        }
    }

    static class s230{//Kth Smallest Element in a BST
        public int kthSmallest(TreeNode root, int k){
            int count = count(root.left);
            if(k == count + 1)
                return root.val;
            else if(k <= count)
                return kthSmallest(root.left, k);
            else return kthSmallest(root.right, k - 1 - count);
        }

        public int count(TreeNode n){
            return n != null ? 1 + count(n.left) + count(n.right) : 0;
        }
    }

    static class s231{//Power of Two
        public boolean isPowerOfTwo(int n){
            return n > 0 && (n & (n - 1)) == 0;
        }
    }

    static class s232{//Implement Queue using Stacks
        class MyQueue{
            Stack<Integer> in = new Stack<>(), out = new Stack<>();

            public void push(int x){in.push(x);}

            public int pop(){
                peek();
                return out.pop();
            }

            public int peek(){
                if(out.isEmpty())
                    for(; !in.isEmpty(); out.push(in.pop())) ;
                return out.peek();
            }

            public boolean empty(){return in.isEmpty() && out.isEmpty();}
        }
    }

    static class s233{//Number of Digit One
        public int countDigitOne(int n){
            int r = 0;
            for(int left = n, right = 0, m = 1; left > 0; ){
                int d = left % 10;
                left = left / 10;
                r += left * m;
                if(d == 1)
                    r += right + 1;
                else if(d > 1)
                    r += m;
                right = d * m + right;
                m *= 10;
            }
            return r;
        }
    }

    static class s235{//Lowest Common Ancestor of a Binary Search Tree
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q){
            if(root.val < p.val && root.val < q.val)
                return lowestCommonAncestor(root.right, p, q);
            else if(root.val > p.val && root.val > q.val)
                return lowestCommonAncestor(root.left, p, q);
            else return root;
        }
    }

    static class s236{//Lowest Common Ancestor of a Binary Tree
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q){
            if(root == null || root == p || root == q)
                return root;
            TreeNode L = lowestCommonAncestor(root.left, p, q), R = lowestCommonAncestor(root.right, p, q);
            return L == null ? R : R == null ? L : root;
        }
    }

    static class s239{//Sliding Window Maximum
        public int[] maxSlidingWindow(int[] a, int k){
            int[] r = new int[a.length - k + 1];
            Deque<Integer> q = new ArrayDeque<>();
            for(int i = 0, j = 0; i < a.length; i++){
                while(!q.isEmpty() && i - q.peek() + 1 > k)
                    q.poll();
                while(!q.isEmpty() && a[q.peekLast()] < a[i])
                    q.pollLast();
                q.offer(i);
                if(i >= k - 1)
                    r[j++] = a[q.peek()];
            }
            return r;
        }
    }

    static class s242{//Valid Anagram
        public boolean isAnagram(String s, String t){
            int[] f = new int[26];
            s.chars().forEach(c -> f[c - 'a']++);
            t.chars().forEach(c -> f[c - 'a']--);
            return Arrays.stream(f).allMatch(n -> n == 0);
        }
    }

    static class s243{//Shortest Word Distance
        public int shortestDistance(String[] words, String w1, String w2){
            int j1 = -1, j2 = -1, r = Integer.MAX_VALUE;
            for(int i = 0; i < words.length; i++){
                if(words[i].equals(w1))
                    j1 = i;
                else if(words[i].equals(w2))
                    j2 = i;
                if(j1 >= 0 && j2 >= 0)
                    r = Math.min(r, Math.abs(j2 - j1));
            }
            return r;
        }
    }

    static class s244{//Shortest Word Distance II
        class WordDistance{
            final Map<String, List<Integer>> m = new HashMap<>();

            public WordDistance(String[] words){
                IntStream.range(0, words.length).forEach(i -> m.computeIfAbsent(words[i], l -> new ArrayList<>()).add(i));
            }

            public int shortest(String w1, String w2){
                int r = Integer.MAX_VALUE, i1 = 0, i2 = 0;
                for(List<Integer> l1 = m.get(w1), l2 = m.get(w2); i1 < l1.size() && i2 < l2.size(); )
                    if(l1.get(i1) < l2.get(i2))
                        r = Math.min(l2.get(i2) - l1.get(i1++), r);
                    else r = Math.min(l1.get(i1) - l2.get(i2++), r);
                return r;
            }
        }
    }
    static class s245{//Shortest Word Distance III
        public int shortestWordDistance(String[] words, String w1, String w2){
            int r = Integer.MAX_VALUE, i1 = words.length, i2 = -words.length;
            for(int i = 0; i < words.length; i++){
                if(words[i].equals(w1)){
                    if(w1.equals(w2))
                        i2 = i1;
                    i1 = i;
                }else if(words[i].equals(w2))
                    i2 = i;
                r = Math.min(r, Math.abs(i1 - i2));
            }
            return r;
        }
    }

    static class s246{//Strobogrammatic Number
        public boolean isStrobogrammatic(String n){
            Map<Character, Character> m = Map.of('0', '0', '1', '1', '6', '9', '8', '8', '9', '6');
            StringBuilder r = new StringBuilder(n.length());
            for(char c : n.toCharArray()){
                if(!m.containsKey(c))
                    return false;
                else r.append(m.get(c));
            }
            return n.equals(r.reverse().toString());
        }

        public boolean isStrobogrammatic1(String a){
            for(int i = 0, j = a.length() - 1; i <= j; i++, j--)
                if(!"00 11 88 696".contains(a.charAt(i) + "" + a.charAt(j)))
                    return false;
            return true;
        }
    }

    static class s249{//Group Shifted Strings
        public List<List<String>> groupStrings(String[] strs){
            Map<String, List<String>> m = new HashMap<>();
            for(String s : strs)
                m.computeIfAbsent(normalize(s.toCharArray()), l -> new ArrayList<>()).add(s);
            return new ArrayList<>(m.values());
        }

        String normalize(char[] s){
            StringBuilder r = new StringBuilder(s.length);
            for(int i = 0, d = 'z' - s[0]; i < s.length; i++)
                r.append((char) ((s[i] + d) % 26));
            return r.toString();
        }
    }

    static class s250{//Count Univalue Subtrees (A uni-value subtree means all nodes of the subtree have the same value)
        int r = 0;

        public int countUnivalSubtrees(TreeNode root){
            all(root, 0);
            return r;
        }

        boolean all(TreeNode node, int val){
            if(node == null)
                return true;
            if(!all(node.left, node.val) | !all(node.right, node.val))
                return false;
            r++;
            return node.val == val;
        }
    }

    static class s251{//Flatten 2D Vector
        class Vector2D{
            int r = 0, c = -1, v[][];

            public Vector2D(int[][] v){
                this.v = v;
                move();
            }

            public int next(){
                int next = v[r][c];
                move();
                return next;
            }

            public boolean hasNext(){return r < v.length;}

            void move(){
                while(r < v.length && ++c >= v[r].length){
                    r++;
                    c = -1;
                }
            }
        }
    }

    static class s252{//Meeting Rooms
        public boolean canAttendMeetings(int[][] intervals){
            Arrays.sort(intervals, Comparator.comparingInt(i -> i[0]));
            return IntStream.range(1, intervals.length).allMatch(i -> intervals[i - 1][1] <= intervals[i][0]);
        }
    }

    static class s253{//Meeting Rooms II
        public int minMeetingRooms(int[][] intervals){
            TreeMap<Integer, Integer> m = new TreeMap<>();
            for(int[] i : intervals){
                m.put(i[0], m.getOrDefault(i[0], 0) + 1);
                m.put(i[1], m.getOrDefault(i[1], 0) - 1);
            }
            int r = 0, s = 0;
            for(Integer i : m.keySet()){
                s += m.get(i);
                r = Math.max(r, s);
            }
            return r;
        }
    }

    static class s256{//Paint House
        /**
         * There is a row of n houses, where each house can be painted one of three colors: red, blue, or green.
         * The cost of painting each house with a certain color is different. You have to paint all the houses
         * such that no two adjacent houses have the same color.
         */
        public int minCost(int[][] costs){
            int red = 0, blue = 0, green = 0;
            for(int[] cost : costs){
                int r = red, b = blue, g = green;
                red = Math.min(g, b) + cost[0];
                blue = Math.min(r, g) + cost[1];
                green = Math.min(b, r) + cost[2];
            }
            return Math.min(Math.min(red, blue), green);
        }
    }

    static class s258{//Add Digits
        public int addDigits(int n){
            if(n == 0)
                return 0;
            return n % 9 == 0 ? 9 : n % 9;
        }
    }

    static class s261{//Graph Valid Tree
        public boolean validTree(int n, int[][] edges){
            List<List<Integer>> g = new ArrayList<>(n);
            IntStream.range(0, n).forEach(i -> g.add(new ArrayList<>()));
            for(int[] e : edges){
                g.get(e[0]).add(e[1]);
                g.get(e[1]).add(e[0]);
            }
            Set<Integer> seen = new HashSet<>();
            dfs(0, g, seen);
            return seen.size() == n && edges.length == n - 1;
        }

        void dfs(int u, List<List<Integer>> g, Set<Integer> seen){
            if(seen.add(u))
                g.get(u).forEach(v -> dfs(v, g, seen));
        }
    }

    static class s266{//Palindrome Permutation
        public boolean canPermutePalindrome(String s){
            BitSet bs = new BitSet();
            for(byte b : s.getBytes())
                bs.flip(b);
            return bs.cardinality() <= 1;
        }
    }

    static class s268{//Missing Number
        public int missingNumber(int[] a){
            return (1 + a.length) * a.length / 2 - Arrays.stream(a).sum();
        }
    }

    static class s270{//Closest Binary Search Tree Value
        public int closestValue(TreeNode node, double target){
            int r = node.val;
            while(node != null){
                if(Math.abs(node.val - target) < Math.abs(target - r))
                    r = node.val;
                node = node.val < target ? node.right : node.left;
            }
            return r;
        }
    }

    static class s271{//Encode and Decode Strings
        public class Codec{
            public String encode(List<String> strs){
                StringBuilder r = new StringBuilder();
                for(String s : strs)
                    r.append(s.length()).append("/").append(s);
                return r.toString();
            }

            public List<String> decode(String s){
                List<String> r = new ArrayList<>();
                for(int i = 0; i < s.length(); ){
                    int slash = s.indexOf('/', i), len = Integer.parseInt(s.substring(i, slash));
                    i = slash + len + 1;
                    r.add(s.substring(slash + 1, i));
                }
                return r;
            }
        }
    }

    static class s274{//H-Index
        public int hIndex(int[] citations){
            int n = citations.length, buckets[] = new int[n + 1];
            for(int c : citations)
                if(c >= n)
                    buckets[n]++;
                else buckets[c]++;
            for(int i = n, count = 0; i >= 0; i--){
                count += buckets[i];
                if(count >= i)
                    return i;
            }
            return 0;
        }
    }

    static class s277{//Find the Celebrity
        public int findCelebrity(int n){
            int celebrity = 0;
            for(int person = 1; person < n; person++)
                if(knows(celebrity, person))
                    celebrity = person;
            for(int person = 0; person < n; person++)
                if(person != celebrity && !celebrity(celebrity, person))
                    return -1;
            return celebrity;
        }
        boolean celebrity(int celebrity, int person){
            return knows(person, celebrity) && !knows(celebrity, person);
        }

        boolean knows(int a, int b){return true;}
    }

    static class s278{//First Bad Version
        public int firstBadVersion(int n){
            int lo = 1, hi = n;
            while(lo + 1 < hi){
                int m = lo + (hi - lo) / 2;
                if(isBadVersion(m))
                    hi = m;
                else lo = m;
            }
            return isBadVersion(lo) ? lo : hi;
        }

        boolean isBadVersion(int version){return false;}//interface method
    }

    static class s281{//Zigzag Iterator
        public class ZigzagIterator{
            Queue<Iterator<Integer>> q = new LinkedList<>();

            public ZigzagIterator(List<Integer> v1, List<Integer> v2){
                Stream.of(v1, v2).map(List::iterator).filter(Iterator::hasNext).forEach(q::offer);
            }

            public int next(){
                Iterator<Integer> it = q.poll();
                int r = it.next();
                if(it.hasNext())
                    q.offer(it);
                return r;
            }

            public boolean hasNext(){return !q.isEmpty();}
        }
    }

    static class s283{//Move Zeroes
        public void moveZeroes(int[] a){
            int i = 0;
            for(int j = 0; j < a.length; j++)
                if(a[j] != 0)
                    a[i++] = a[j];
            for(; i < a.length; a[i++] = 0) ;
        }
    }

    static class s284{//Peeking Iterator
        class PeekingIterator implements Iterator<Integer>{
            Iterator<Integer> it;
            Integer next;
            public PeekingIterator(Iterator<Integer> it){
                this.it = it;
                if(it.hasNext())
                    next = it.next();
            }

            @Override public Integer next(){
                Integer r = next;
                next = it.hasNext() ? it.next() : null;
                return r;
            }

            public Integer peek(){return next;}

            @Override public boolean hasNext(){return next != null;}
        }
    }

    static class s286{//Walls and Gates
        public void wallsAndGates(int[][] rooms){
            Queue<int[]> q = new LinkedList<>();
            for(int i = 0; i < rooms.length; i++)
                for(int j = 0; j < rooms[0].length; j++)
                    if(rooms[i][j] == 0)
                        q.add(new int[]{i, j});
            for(int dirs[][] = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; !q.isEmpty(); )
                for(int rc[] = q.poll(), i = 0; i < dirs.length; i++){
                    int r = rc[0] + dirs[i][0], c = rc[1] + dirs[i][1];
                    if(0 <= r && r < rooms.length && 0 <= c && c < rooms[0].length && rooms[r][c] == Integer.MAX_VALUE){
                        q.offer(new int[]{r, c});
                        rooms[r][c] = rooms[rc[0]][rc[1]] + 1;
                    }
                }
        }
    }

    static class s288{//Unique Word Abbreviation
        class ValidWordAbbr{
            final Map<String, Set<String>> m = new HashMap<>();

            public ValidWordAbbr(String[] dictionary){
                for(String w : dictionary){
                    String abbr = abbr(w);
                    m.putIfAbsent(abbr, new HashSet<>());
                    m.get(abbr).add(w);
                }
            }

            public boolean isUnique(String word){
                String abbr = abbr(word);
                return m.get(abbr) == null || (m.get(abbr).contains(word) && m.get(abbr).size() == 1);
            }

            String abbr(String w){
                if(w.length() <= 2)
                    return w;
                return w.charAt(0) + String.valueOf(w.length() - 2) + w.charAt(w.length() - 1);
            }
        }
    }

    static class s289{
        public void gameOfLife(int[][] b){
            for(int i = 0; i < b.length; i++)
                for(int j = 0; j < b[0].length; j++){
                    int live = 0, newState = 0, oldState = b[i][j] & 1;
                    for(int x = -1; x <= 1; x++)
                        for(int y = -1; y <= 1; y++)
                            if(x != 0 || y != 0){
                                int ni = i + x, nj = j + y;
                                if(0 <= ni && ni < b.length && 0 <= nj && nj < b[0].length)
                                    live += b[ni][nj] & 1;
                            }
                    if(oldState == 1)
                        newState = live == 2 || live == 3 ? 1 : 0;
                    else if(live == 3)
                        newState = 1;
                    b[i][j] = newState * 2 + oldState;
                }
            for(int i = 0; i < b.length; i++)
                for(int j = 0; j < b[0].length; j++)
                    b[i][j] >>= 1;
        }
    }

    static class s291{//Word Pattern II
        public boolean wordPatternMatch(String p, String s){return match(p, s, new HashMap<>());}

        boolean match(String p, String s, Map<Character, String> m){
            if(p.isEmpty() && s.isEmpty())
                return true;
            if(p.isEmpty() || s.isEmpty())
                return false;
            char c = p.charAt(0);
            if(m.containsKey(c)){
                String pre = m.get(c);
                return s.startsWith(pre) && match(p.substring(1), s.replaceFirst(pre, ""), m);
            }
            for(int i = 1; i <= s.length(); i++){
                String pre = s.substring(0, i), rest = s.substring(i);
                if(!m.containsValue(pre)){
                    m.put(c, pre);
                    if(match(p.substring(1), rest, m))
                        return true;
                    m.remove(c);
                }
            }
            return false;
        }
    }

    static class s292{//Nim Game
        public boolean canWinNim(int n){return n % 4 != 0;}
    }

    static class s293{//Flip Game
        public List<String> generatePossibleNextMoves(String s){
            List<String> r = new ArrayList<>();
            for(int i = 1; i < s.length(); i++)
                if(s.charAt(i - 1) == '+' && s.charAt(i) == '+')
                    r.add(s.substring(0, i - 1) + "--" + s.substring(i + 1));
            return r;
        }
    }

    static class s298{//Binary Tree Longest Consecutive Sequence
        /**
         * Given the root of a binary tree, return the length of the longest consecutive sequence path.
         * The path refers to any sequence of nodes from some starting node to any node in the tree along
         * the parent-child connections. The longest consecutive path needs to be from parent to child (cannot be the reverse).
         */
        int r = 1;
        public int longestConsecutive(TreeNode root){
            dfs(1, root);
            return r;
        }

        void dfs(int len, TreeNode node){
            r = Math.max(len, r);
            if(node.left != null)
                dfs(node.val + 1 == node.left.val ? len + 1 : 1, node.left);
            if(node.right != null)
                dfs(node.val + 1 == node.right.val ? len + 1 : 1, node.right);
        }
    }

    static class s299{//Bulls and Cows
        public String getHint(String secret, String guess){
            int c1[] = new int[10], c2[] = new int[10], a = 0;
            for(int i = 0; i < secret.length(); i++)
                if(secret.charAt(i) != guess.charAt(i)){
                    c1[secret.charAt(i) - '0']++;
                    c2[guess.charAt(i) - '0']++;
                }else a++;
            return a + "A" + IntStream.range(0, c1.length).map(i -> Math.min(c1[i], c2[i])).sum() + "B";
        }
    }
}
