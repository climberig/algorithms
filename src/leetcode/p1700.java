package leetcode;

import java.util.*;
import java.util.stream.*;

public class p1700{
    static class s1722{//Minimize Hamming Distance After Swap Operations
        public int minimumHammingDistance(int[] src, int[] tgt, int[][] swaps){
            List<List<Integer>> g = IntStream.range(0, src.length).mapToObj(i -> new ArrayList<Integer>()).collect(Collectors.toList());
            for(int[] s : swaps){
                g.get(s[0]).add(s[1]);
                g.get(s[1]).add(s[0]);
            }
            int r = 0;
            boolean[] seen = new boolean[src.length];
            for(int i = 0; i < src.length; i++)
                if(!seen[i]){
                    List<Integer> indices = new ArrayList<>();
                    dfs(i, seen, g, indices);
                    Map<Integer, Integer> m = new HashMap<>();
                    for(Integer j : indices)
                        m.put(src[j], m.getOrDefault(src[j], 0) + 1);
                    for(Integer j : indices)
                        if(m.getOrDefault(tgt[j], 0) > 0)
                            m.put(tgt[j], m.get(tgt[j]) - 1);
                        else r++;
                }
            return r;
        }

        void dfs(int i, boolean[] seen, List<List<Integer>> g, List<Integer> indices){
            seen[i] = true;
            indices.add(i);
            for(Integer j : g.get(i))
                if(!seen[j])
                    dfs(j, seen, g, indices);
        }
    }

    static class s1734{//Decode XORed Permutation
        public int[] decode(int[] encoded){
            int allXor = 0, xor = 0, r[] = new int[encoded.length + 1];
            for(int i = 1; i <= encoded.length + 1; i++)
                allXor ^= i;
            for(int e : encoded){
                xor ^= e;
                allXor ^= xor;
            }
            r[0] = allXor;
            for(int i = 1; i < r.length; i++)
                r[i] = encoded[i - 1] ^ r[i - 1];
            return r;
        }
    }

    static class s1738{//Find Kth Largest XOR Coordinate Value
        public int kthLargestValue(int[][] mat, int k){
            int n = mat.length, m = mat[0].length, pre[][] = new int[n + 1][m + 1], r[] = new int[n * m];
            for(int i = 0; i < n; i++)
                for(int j = 0; j < m; j++)
                    r[i * m + j] = pre[i + 1][j + 1] = pre[i + 1][j] ^ pre[i][j + 1] ^ pre[i][j] ^ mat[i][j];
            Arrays.sort(r);
            return r[r.length - k];
        }
    }

    static class s1749{//Maximum Absolute Sum of Any Subarray
        public int maxAbsoluteSum(int[] a){
            int r = 0, maxSum = 0, minSum = 0;
            for(int n : a){
                maxSum = Math.max(0, maxSum + n);
                minSum = Math.min(0, minSum + n);
                r = Math.max(r, Math.max(maxSum, -minSum));
            }
            return r;
        }
    }

    static class s1760{//Minimum Limit of Balls in a Bag
        public int minimumSize(int[] bags, int k){
            int lo = 1, hi = 1_000_000_000;
            while(lo < hi){
                int size = (lo + hi) / 2, ops = 0;
                for(int bag : bags)
                    ops += (bag - 1) / size;
                if(ops > k)//the size is too small
                    lo = size + 1;
                else hi = size;
            }
            return lo;
        }
    }

    static class s1770{//Maximum Score from Performing Multiplication Operations
        public int maximumScore(int[] a, int[] m){
            return max(a, m, 0, a.length - 1, 0, new Integer[m.length][m.length]);
        }

        int max(int[] a, int[] m, int L, int R, int i, Integer[][] dp){
            if(i == m.length)
                return 0;
            if(dp[L][a.length - R - 1] == null)
                dp[L][a.length - R - 1] = Math.max(a[L] * m[i] + max(a, m, L + 1, R, i + 1, dp), a[R] * m[i] + max(a, m, L, R - 1, i + 1, dp));
            return dp[L][a.length - R - 1];
        }
    }

    static class s1775{//Equal Sum Arrays With Minimum Number of Operations
        public int minOperations(int[] a1, int[] a2){
            int s1 = IntStream.of(a1).sum(), s2 = IntStream.of(a2).sum(), r = 0;
            if(s1 > s2)
                return minOperations(a2, a1);
            int[] cnt = new int[6];
            IntStream.of(a1).forEach(n -> ++cnt[6 - n]);
            IntStream.of(a2).forEach(n -> ++cnt[n - 1]);
            for(int i = cnt.length - 1; s1 < s2 && i >= 0; )
                if(cnt[i] > 0){
                    s1 += i;
                    cnt[i]--;
                    r++;
                }else i--;
            return s1 < s2 ? -1 : r;
        }
    }

    static class s1778{//Shortest Path in a Hidden Grid
        public int findShortestPath(GridMaster m){
            char[] dirs = {'U', 'L', 'D', 'R'};
            int distance = 1, N = 500, moves[][] = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}}, g[][] = new int[2 * N + 1][2 * N + 1];
            dfs(g, N, N, m, moves, dirs);
            Queue<int[]> q = new LinkedList<>();
            for(g[N][N] = -1, q.offer(new int[]{N, N}); !q.isEmpty(); distance++)
                for(int size = q.size(); size > 0; size--){
                    int rc[] = q.poll(), r = rc[0], c = rc[1];
                    for(int[] move : moves){
                        int nr = r + move[0], nc = c + move[1];
                        if(g[nr][nc] > 0){
                            if(g[nr][nc] == 2)
                                return distance;
                            g[nr][nc] = -1;
                            q.offer(new int[]{nr, nc});
                        }
                    }
                }
            return -1;
        }

        void dfs(int[][] g, int r, int c, GridMaster m, int[][] moves, char[] dirs){
            if(g[r][c] == 0){
                g[r][c] = m.isTarget() ? 2 : 1;
                for(int i = 0; i < dirs.length; i++)
                    if(m.canMove(dirs[i])){
                        m.move(dirs[i]);
                        dfs(g, r + moves[i][0], c + moves[i][1], m, moves, dirs);
                        m.move(dirs[(i + 2) % 4]);
                    }else g[r + moves[i][0]][c + moves[i][1]] = -1;
            }
        }

        interface GridMaster{
            boolean canMove(char direction);

            void move(char direction);

            boolean isTarget();
        }
    }

    static class s1781{//Sum of Beauty of All Substrings
        public int beautySum(String s){
            int r = 0;
            char[] a = s.toCharArray();
            for(int i = 0; i < s.length(); i++)
                for(int j = i, fr[] = new int[26]; j < s.length(); j++){
                    fr[a[j] - 'a']++;
                    r += beauty(fr);
                }
            return r;
        }

        int beauty(int[] fr){
            int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
            for(int f : fr)
                if(f > 0){
                    min = Math.min(min, f);
                    max = Math.max(max, f);
                }
            return max - min;
        }
    }
}
