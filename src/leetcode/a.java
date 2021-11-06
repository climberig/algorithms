package leetcode;

public class a {
    public int numRookCaptures(char[][] b) {
        for (int i = 0; i < b.length; i++)
            for (int j = 0; j < b.length; j++)
                if (b[i][j] == 'R')
                    return cap(1, 0, i, j, b) + cap(-1, 0, i, j, b) + cap(0, 1, i, j, b) + cap(0, -1, i, j, b);
        return 0;
    }

    int cap(int di, int dj, int i, int j, char[][] b) {
        for (i = i + di, j = j + dj; 0 <= i && i < b.length && 0 <= j && j < b.length && b[i][j] != 'B'; i += di, j += dj)
            if (b[i][j] == 'p')
                return 1;
        return 0;
    }
}

