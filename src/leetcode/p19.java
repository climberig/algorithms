package leetcode;

import java.util.*;
import java.util.stream.*;

public class p19{
    static class s1902{//Depth of BST Given Insertion Order
        public int maxDepthBST(int[] order){
            TreeMap<Integer, Integer> m = new TreeMap<>();
            m.put(order[0], 1);
            int r = 1;
            for(int i = 1; i < order.length; i++){
                Map.Entry<Integer, Integer> left = m.floorEntry(order[i]), right = m.ceilingEntry(order[i]);
                int leftDepth = left != null ? left.getValue() : 0, rightDepth = right != null ? right.getValue() : 0;
                int depth = Math.max(leftDepth, rightDepth) + 1;
                r = Math.max(r, depth);
                m.put(order[i], depth);
            }
            return r;
        }
    }

    static class s1908{//Game of Nim
        public boolean nimGame(int[] piles){
            return Arrays.stream(piles).reduce((a, b) -> a ^ b).getAsInt() != 0;
        }
    }

    static class s1911{//Maximum Alternating Subsequence Sum
        public long maxAlternatingSum(int[] a){
            long r = a[0];
            for(int i = 1; i < a.length; i++)
                r += Math.max(a[i] - a[i - 1], 0);
            return r;
        }
    }

    static class s1918{//Kth Smallest Subarray Sum
        public int kthSmallestSubarraySum(int[] a, int k){
            int lo = 1, hi = 20_000 * 50_000;
            while(lo < hi){
                int m = (lo + hi) / 2, floor = 0;
                for(int i = 0, j = 0, sum = 0; i < a.length; )
                    if(sum + a[i] <= m){
                        sum += a[i++];
                        floor += i - j;
                    }else sum -= a[j++];
                if(floor < k)
                    lo = m + 1;
                else hi = m;
            }
            return lo;
        }
    }

    static class s1933{//Check if String Is Decomposable Into Value-Equal Substrings
        public boolean isDecomposable(String s){
            int twoCount = 0;
            for(int i = 1, count = 1; i <= s.length(); i++)
                if(i == s.length() || s.charAt(i - 1) != s.charAt(i)){
                    if((count %= 3) == 1)
                        return false;
                    twoCount += count == 2 ? 1 : 0;
                    count = 1;
                }else count++;
            return twoCount == 1;
        }
    }

    static class s1941{//Check if All Characters Have Equal Number of Occurrences
        public boolean areOccurrencesEqual(String s){
            int[] fr = new int[26];
            s.chars().forEach(c -> fr[c - 'a']++);
            return Arrays.stream(fr).filter(f -> f > 0).allMatch(f -> f == Arrays.stream(fr).max().getAsInt());
        }
    }

    static class s1954{//Minimum Garden Perimeter to Collect Enough Apples
        public long minimumPerimeter(long neededApples){
            int side = 0, lo = 0, hi = 0;
            for(long sum = 0; neededApples > 0; side += 2){
                sum += 2 + hi + 1 + hi - lo;
                hi += 2;
                lo += 1;
                neededApples -= 8L * sum - 4L * (lo + hi);
            }
            return side * 4L;
        }
    }

    static class s1981{//Minimize the Difference Between Target and Chosen Elements
        public int minimizeTheDifference(int[][] mat, int target){
            return minDiff(mat, 0, target, 0, new Integer[mat.length][5_000]);
        }

        int minDiff(int[][] mat, int row, int target, int sum, Integer[][] dp){
            if(row == mat.length)
                return Math.abs(sum - target);
            if(dp[row][sum] != null)
                return dp[row][sum];
            int r = Integer.MAX_VALUE;
            for(int i = 0; i < mat[0].length; i++)
                r = Math.min(r, minDiff(mat, row + 1, target, sum + mat[row][i], dp));
            return dp[row][sum] = r;
        }
    }

    static class s1986{//Minimum Number of Work Sessions to Finish the Tasks
        public int minSessions(int[] tasks, int sessionTime){
            for(int n = 1; n < tasks.length; n++)
                if(canWork(tasks, 0, IntStream.range(0, n).map(i -> sessionTime).toArray()))
                    return n;
            return tasks.length;
        }

        boolean canWork(int[] tasks, int taskIdx, int[] remain){
            if(taskIdx == tasks.length)
                return true;
            for(int i = 0; i < remain.length; i++){
                if(i <= taskIdx && remain[i] >= tasks[taskIdx]){
                    remain[i] -= tasks[taskIdx];
                    if(canWork(tasks, taskIdx + 1, remain))
                        return true;
                    remain[i] += tasks[taskIdx];
                }
            }
            return false;
        }
    }

    static class s1991{//Find the Middle Index in Array
        public int findMiddleIndex(int[] a){
            int sum = 0;
            Map<Integer, Integer> m = new HashMap<>();
            for(int i = 0; i < a.length; sum += a[i++])
                m.putIfAbsent(2 * sum + a[i], i);
            return m.getOrDefault(sum, -1);
        }
    }

    static class s1992{//Find All Groups of Farmland
        public int[][] findFarmland(int[][] a){
            List<int[]> r = new ArrayList<>();
            for(int r1 = 0; r1 < a.length; r1++)
                for(int c1 = 0; c1 < a[0].length; c1++)
                    if(a[r1][c1] == 1 && (r1 == 0 || a[r1 - 1][c1] == 0) && (c1 == 0 || a[r1][c1 - 1] == 0)){
                        int r2 = r1, c2 = c1;
                        for(; r2 < a.length && a[r2][c1] == 1; r2++) ;
                        for(; c2 < a[0].length && a[r1][c2] == 1; c2++) ;
                        r.add(new int[]{r1, c1, r2 - 1, c2 - 1});
                    }
            return r.toArray(new int[0][]);
        }
    }

    static class s1993{//Operations on Tree
        class LockingTree{
            List<List<Integer>> children;
            int[] locked, parents;

            public LockingTree(int[] parent){
                parents = parent;
                locked = new int[parent.length];
                children = IntStream.range(0, parent.length).mapToObj(i -> new ArrayList<Integer>()).collect(Collectors.toList());
                for(int i = 1; i < parent.length; i++)
                    children.get(parent[i]).add(i);
            }

            public boolean lock(int n, int user){
                if(locked[n] == 0){
                    locked[n] = user;
                    return true;
                }else return false;
            }

            public boolean unlock(int n, int user){
                if(locked[n] == user){
                    locked[n] = 0;
                    return true;
                }else return false;
            }

            public boolean upgrade(int n, int user){
                int p = n;
                while(p != -1)
                    if(locked[p] > 0)
                        return false;
                    else p = parents[p];
                if(unlockChildren(n) == 0)
                    return false;
                locked[n] = user;
                return true;
            }

            int unlockChildren(int n){
                int r = 0;
                if(locked[n] > 0){
                    locked[n] = 0;
                    r++;
                }
                return r + children.get(n).stream().mapToInt(this::unlockChildren).sum();
            }
        }
    }

    static class s1995{//Count Special Quadruplets
        public int countQuadruplets(int[] a){
            int f[] = new int[301], r = 0;
            for(int i = a.length - 1; i >= 0; f[a[i--]]++)
                for(int j = i - 1; j >= 0; j--)
                    for(int k = j - 1; k >= 0; k--)
                        r += f[a[i] + a[j] + a[k]];
            return r;
        }
    }

    static class s1996{//The Number of Weak Characters in the Game
        public int numberOfWeakCharacters(int[][] properties){
            Arrays.sort(properties, (a, b) -> a[0] != b[0] ? b[0] - a[0] : a[1] - b[1]);
            int r = 0, max = 0;
            for(int[] p : properties){
                if(p[1] < max)
                    r++;
                max = Math.max(max, p[1]);
            }
            return r;
        }
    }

    static class s1999{//Smallest Greater Multiple Made of Two Digits
        public int findInteger(int k, int d1, int d2){
            Set<Integer> digits = new HashSet<>(List.of(d1, d2)), seen = new HashSet<>(digits);
            for(var q = new PriorityQueue<>(digits); !q.isEmpty(); ){
                Integer n = q.poll();
                if(n > k && n % k == 0)
                    return n;
                digits.stream().filter(d -> n <= (Integer.MAX_VALUE - d) / 10).map(d -> 10 * n + d).filter(seen::add).forEach(q::add);
            }
            return -1;
        }
    }
}
