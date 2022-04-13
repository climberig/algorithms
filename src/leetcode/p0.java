package leetcode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class p0{
    static class s1{//Two Sum
        public int[] twoSum(int[] a, int t){
            Map<Integer, Integer> m = new HashMap<>();
            for(int i = 0; i < a.length; i++)
                if(m.containsKey(t - a[i]))
                    return new int[]{m.get(t - a[i]), i};
                else m.put(a[i], i);
            return null;
        }
    }

    static class s11{//Container With Most Water
        public int maxArea(int[] heights){
            int r = 0;
            for(int lo = 0, hi = heights.length - 1; lo < hi; ){
                int minHeight = Math.min(heights[lo], heights[hi]);
                r = Math.max(r, (hi - lo) * minHeight);
                for(; heights[lo] <= minHeight && lo < hi; lo++) ;
                for(; heights[hi] <= minHeight && lo < hi; hi--) ;
            }
            return r;
        }
    }

    static class s31{//Next Permutation
        public void nextPermutation(int[] a){
            int i = a.length - 1;
            for(; i > 0 && a[i - 1] >= a[i]; i--) ;
            if(i > 0){
                int j = a.length - 1;
                for(; i <= j && a[i - 1] >= a[j]; j--) ;
                swap(a, i - 1, j);
            }
            for(int j = a.length - 1; i < j; i++, j--)
                swap(a, i, j);
        }

        void swap(int[] a, int i, int j){
            int t = a[i];
            a[i] = a[j];
            a[j] = t;
        }
    }

    static class s51{//N-Queens
        public List<List<String>> solveNQueens(int n){
            char[][] b = new char[n][n];
            IntStream.range(0, n).forEach(r -> Arrays.fill(b[r], '.'));
            List<List<String>> r = new ArrayList<>();
            place(b, 0, n, r);
            return r;
        }

        void place(char[][] b, int col, int n, List<List<String>> r){
            if(col == n)
                r.add(IntStream.range(0, n).mapToObj(i -> new String(b[i])).collect(Collectors.toList()));
            else for(int row = 0; row < n; row++)
                if(check(row, col, b)){
                    b[row][col] = 'Q';
                    place(b, col + 1, n, r);
                    b[row][col] = '.';
                }
        }

        boolean check(int row, int col, char[][] b){
            for(int r = 0; r < b.length; r++)
                for(int c = 0; c < col; c++)
                    if(b[r][c] == 'Q' && (r == row || row + c == col + r || row + col == c + r))
                        return false;
            return true;
        }
    }

    static class s52{//N-Queens II
        int r = 0;

        public int totalNQueens(int n){
            place(0, n, new char[n][n]);
            return r;
        }

        void place(int col, int n, char[][] b){
            if(col == n)
                r++;
            else for(int row = 0; row < n; row++)
                if(check(row, col, b)){
                    b[row][col] = 'q';
                    place(col + 1, n, b);
                    b[row][col] = '.';
                }
        }

        boolean check(int row, int col, char[][] b){
            for(int r = 0; r < b.length; r++)
                for(int c = 0; c < col; c++)
                    if(b[r][c] == 'q' && (r == row || r + c == row + col || row + c == col + r))
                        return false;
            return true;
        }
    }

    static class s53{//Maximum Subarray
        public int maxSubArray(int[] a){
            int r = a[0], sum = a[0];
            for(int i = 1; i < a.length; i++){
                sum = Math.max(a[i], sum + a[i]);
                r = Math.max(r, sum);
            }
            return r;
        }
    }

    static class s59{//Spiral Matrix II
        public int[][] generateMatrix(int n){
            int[][] m = new int[n][n];
            for(int r1 = 0, c1 = -1, r2 = n - 1, c2 = n - 1, v = 1; r1 <= r2 && c1 <= c2; ){
                for(int c = ++c1; c <= c2 && r1 <= r2; c++)
                    m[r1][c] = v++;
                for(int r = ++r1; r <= r2 && c1 <= c2; r++)
                    m[r][c2] = v++;
                for(int c = --c2; c >= c1 && r1 <= r2; c--)
                    m[r2][c] = v++;
                for(int r = --r2; r >= r1 && c1 <= c2; r--)
                    m[r][c1] = v++;
            }
            return m;
        }
    }

    static class s69{//Sqrt(x)
        public int mySqrt(int x){
            if(x <= 1)
                return x;
            long lo = 1, hi = x / 2, r = 0;
            while(lo <= hi){
                long mid = (lo + hi) / 2;
                if(mid * mid == x)
                    return (int) mid;
                if(mid * mid < x){
                    r = mid;
                    lo = mid + 1;
                }else hi = mid - 1;
            }
            return (int) r;
        }
    }
}

