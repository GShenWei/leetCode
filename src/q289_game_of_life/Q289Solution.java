package q289_game_of_life;

import org.junit.Test;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/10/19
 */
public class Q289Solution {


    public void gameOfLife(int[][] board) {
        int[][] next = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        int row = board.length;
        int col = board[0].length;
        int[][] result = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                int aliveCount = 0;
                for (int[] aNext : next) {
                    int di = i + aNext[0];
                    int dj = j + aNext[1];
                    if (di < 0 || di >= row || dj < 0 || dj >= col) {
                        continue;
                    }
                    if (board[di][dj] == 1) {
                        aliveCount++;
                    }
                }
                if (aliveCount < 2) {
                    result[i][j] = 0;
                } else if (aliveCount == 2) {
                    result[i][j] = board[i][j];
                } else if (aliveCount == 3) {
                    result[i][j] = 1;
                } else {
                    result[i][j] = 0;
                }
            }
        }
        for (int i = 0; i < row; i++) {
            System.arraycopy(result[i], 0, board[i], 0, col);
        }
    }

    @Test
    public void xx() {
        int[][] board = {
                {0, 1, 0},
                {0, 0, 1},
                {1, 1, 1},
                {0, 0, 0}
        };
        gameOfLife(board);
    }
}
