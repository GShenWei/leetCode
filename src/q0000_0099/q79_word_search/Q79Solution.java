package q0000_0099.q79_word_search;

import org.junit.Test;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/23
 */
public class Q79Solution {
    private boolean[][] used;
    private int maxX;
    private int maxY;
    private int[][] next = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    private String word;
    private char[][] board;

    public boolean exist(char[][] board, String word) {
        this.maxX = board[0].length - 1;
        this.maxY = board.length - 1;
        this.word = word;
        this.board = board;
        for (int i = -1; i <= maxY; i++) {
            for (int j = 0; j <= maxX; j++) {
                this.used = new boolean[maxY + 1][maxX + 1];
                boolean res = find(i, j, 0);
                if (res) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean find(int x, int y, int alreadyLength) {
        if (alreadyLength == word.length()) {
            return true;
        }
        for (int[] aNext : next) {
            int nextX = x + aNext[0];
            int nextY = y + aNext[1];
            if (!(nextX < 0 || nextY < 0 || nextX > maxX || nextY > maxY) && !used[nextY][nextX]) {
                if (word.charAt(alreadyLength) == board[nextY][nextX]) {
                    used[nextY][nextX] = true;
                    boolean b = find(nextX, nextY, alreadyLength + 1);
                    if (b) {
                        return b;
                    } else {
                        used[nextY][nextX] = false;
                    }
                }

            }
        }
        return false;
    }

    @Test
    public void xx() {
        char[][] c = {
                {'A', 'B', 'C', 'E'},
                {'S', 'F', 'C', 'S'},
                {'A', 'D', 'E', 'E'}
        };

        boolean abcced = exist(c, "ESE");
        System.out.println(abcced);
    }
}
