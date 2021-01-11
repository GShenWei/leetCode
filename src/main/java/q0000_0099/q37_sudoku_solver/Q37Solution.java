package q0000_0099.q37_sudoku_solver;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/26
 */
public class Q37Solution {
    private boolean[][] rowUsed;
    private boolean[][] colUsed;
    private boolean[][] partUsed;
    private final int N = 9;
    private char[][] board;

    public void solveSudoku(char[][] board) {
        this.rowUsed = new boolean[N][N + 1];
        this.colUsed = new boolean[N][N + 1];
        this.partUsed = new boolean[N][N + 1];
        this.board = board;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                char c = board[i][j];
                if (c != '.') {
                    int v = c - '0';
                    rowUsed[i][v] = true;
                    colUsed[j][v] = true;
                    partUsed[getPart(i, j)][v] = true;
                }
            }
        }
        findN(0);
    }

    private boolean findN(int position) {
        if (position == N * N) {
            return true;
        }
        int y = position / N;
        int x = position % N;
        if (board[y][x] == '.') {
            for (int i = 1; i <= N; i++) {
                if (isOk(y, x, i)) {
                    board[y][x] = (char) (i + '0');
                    rowUsed[y][i] = true;
                    colUsed[x][i] = true;
                    partUsed[getPart(y, x)][i] = true;

                    boolean b = findN(position + 1);
                    if (b) {
                        return true;
                    }else{
                        board[y][x] = '.';
                        rowUsed[y][i] = false;
                        colUsed[x][i] = false;
                        partUsed[getPart(y, x)][i] = false;
                    }
                }

            }
        } else {
            return findN(position + 1);
        }
        return false;
    }

    private boolean isOk(int y, int x, int i) {
        return !rowUsed[y][i] && !colUsed[x][i] && !partUsed[getPart(y, x)][i];
    }


    private int getPart(int i, int j) {
        int r = i / 3;
        int c = j / 3;
        return r * 3 + c;
    }

    @Test
    public void yy() {
        char[][] x = {
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'},
        };
        solveSudoku(x);
        for (char[] chars : x) {
            System.out.println(Arrays.toString(chars));
        }

    }

    @Test
    public void xx() {
        System.out.println(getPart(0, 0));
        System.out.println(getPart(8, 8));
        System.out.println(getPart(6, 7));
        System.out.println(getPart(3, 5));
    }

}
