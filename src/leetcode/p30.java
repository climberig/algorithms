package leetcode;
import java.util.PriorityQueue;
public class p30{
    static class s3001{//Minimum Moves to Capture The Queen
        public int minMovesToCaptureTheQueen(int rookX, int rookY, int bishopX, int bishopY, int queenX, int queenY) {
            int r = Math.min(moveQueenR(1, 0, rookX, rookY, bishopX, bishopY, queenX, queenY), moveQueenR(-1, 0, rookX, rookY, bishopX, bishopY, queenX, queenY));
            r = Math.min(moveQueenR(0, 1, rookX, rookY, bishopX, bishopY, queenX, queenY), r);
            r = Math.min(moveQueenR(0, -1, rookX, rookY, bishopX, bishopY, queenX, queenY), r);
            r = Math.min(moveQueenB(1, 1, rookX, rookY, bishopX, bishopY, queenX, queenY), r);
            r = Math.min(moveQueenB(-1, -1, rookX, rookY, bishopX, bishopY, queenX, queenY), r);
            r = Math.min(moveQueenB(1, -1, rookX, rookY, bishopX, bishopY, queenX, queenY), r);
            r = Math.min(moveQueenB(-1, 1, rookX, rookY, bishopX, bishopY, queenX, queenY), r);
            return r;
        }

        int moveQueenR(int dx, int dy, int rookX, int rookY, int bishopX, int bishopY, int queenX, int queenY) {
            for (int x = queenX + dx, y = queenY + dy; 1 <= x && x <= 8 && 1 <= y && y <= 8; x += dx, y += dy)
                if (x == bishopX && y == bishopY)
                    return 2;
                else if (x == rookX && y == rookY)
                    return 1;
            return 2;
        }

        int moveQueenB(int dx, int dy, int rookX, int rookY, int bishopX, int bishopY, int queenX, int queenY) {
            for (int x = queenX + dx, y = queenY + dy; 1 <= x && x <= 8 && 1 <= y && y <= 8; x += dx, y += dy)
                if (x == rookX && y == rookY)
                    return 2;
                else if (x == bishopX && y == bishopY)
                    return 1;
            return 2;
        }
    }

    static class s3005{//Count Elements With Maximum Frequency
        public int maxFrequencyElements(int[] a) {
            int freq[] = new int[101], maxFreq = 0, r = 0;
            for (int n : a)
                if (++freq[n] == maxFreq)
                    r += maxFreq;
                else if (freq[n] > maxFreq) {
                    maxFreq = freq[n];
                    r = maxFreq;
                }
            return r;
        }
    }

    static class s3010{//Divide an Array Into Subarrays With Minimum Cost I
        public int minimumCost(int[] a) {
            PriorityQueue<Integer> pq = new PriorityQueue<>();
            for (int i = 1; i < a.length; i++)
                pq.offer(a[i]);
            return a[0] + pq.poll() + pq.poll();
        }
    }

    static class s3019{//Number of Changing Keys
        public int countKeyChanges(String s) {
            int r = 0;
            for (int i = 1; i < s.length(); i++)
                if (Character.toLowerCase(s.charAt(i)) != Character.toLowerCase(s.charAt(i - 1)))
                    r++;
            return r;
        }
    }
}
