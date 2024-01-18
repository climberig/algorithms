package leetcode;
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
}
