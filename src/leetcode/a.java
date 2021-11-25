package leetcode;

import java.util.Arrays;
public class a{
    public int numberOfCleanRooms(int[][] room){
        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}, seen = new int[room.length][room[0].length];
        for(int r = 0, c = 0, dirIdx = 0; (1 << dirIdx & seen[r][c]) == 0; ){
            seen[r][c] |= 1 << dirIdx;
            if(room[r][c] == 0)
                room[r][c] = -1;
            int nr = r + dirs[dirIdx][0], nc = c + dirs[dirIdx][1];
            if(0 <= nr && nr < room.length && 0 <= nc && nc < room[0].length && room[nr][nc] != 1){
                r = nr;
                c = nc;
            }else dirIdx = (dirIdx + 1) % dirs.length;
        }
        return -Arrays.stream(room).mapToInt(row -> Arrays.stream(row).filter(n -> n < 0).sum()).sum();
    }
}
