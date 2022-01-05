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
}

