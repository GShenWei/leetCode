package q0100_0199.q130_surrounded_regions;

import org.junit.Test;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/23
 */
public class Q130Solution {
    private boolean[][] visited;
    private char[][] board;
    private int my;
    private int mx;
    private int[][] next = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    /**
     * 深度搜索所有边上为O的点，并将所有搜索到的节点设置成已查看，最终没有被搜索到的点就被X所包围
     * @param board
     */
    public void solve(char[][] board) {
        this.my = board.length;
        if (my <= 2) {
            return;
        }
        this.mx = board[0].length;
        if (mx <= 2) {
            return;
        }
        this.board = board;
        this.visited = new boolean[my][mx];

        for (int i = 0; i < my; i++) {
            if (board[i][0] == 'O') {
                dfs(i, 0);
            }
        }
        for (int j = 0; j < mx; j++) {
            if (board[0][j] == 'O') {
                dfs(0, j);
            }
        }

        for (int i = 0; i < my; i++) {
            if (board[i][mx - 1] == 'O') {
                dfs(i, mx - 1);
            }
        }
        for (int j = 0; j < mx; j++) {
            if (board[my - 1][j] == 'O') {
                dfs(my - 1, j);
            }
        }

        for (int i = 0; i < my; i++) {
            for (int j = 0; j < mx; j++) {
                if (!visited[i][j]) {
                    board[i][j] = 'X';
                }
            }
        }
    }

    private void dfs(int y, int x) {
        visited[y][x] = true;
        for (int[] aNext : next) {
            int dy = y + aNext[0];
            int dx = x + aNext[1];
            if (isOk(dy, dx)) {
                dfs(dy, dx);
            }
        }
    }

    private boolean isOk(int y, int x) {
        if (y < my && x < mx && y >= 0 && x >= 0) {
            return board[y][x] == 'O' && !visited[y][x];
        }
        return false;
    }

    @Test
    public void xx() {
        char[][] g = {
                {'O', 'X', 'X'},
                {'X', 'O', 'X'},
                {'X', 'X', 'X'},

        };
        solve(g);
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g[0].length; j++) {
                System.out.print(g[i][j] + ",");
            }
            System.out.println();
        }
    }

}
