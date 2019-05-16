package q0200_0299.q200_number_of_islands;

import org.junit.Test;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/23
 */
public class Q200Solution {
    private boolean[][] visited;
    private char[][] grid;
    private int my;
    private int mx;
    private int[][] next = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    public int numIslands(char[][] grid) {
        this.my = grid.length;
        if (my == 0) {
            return 0;
        }
        this.mx = grid[0].length;
        if (mx == 0) {
            return 0;
        }
        this.grid = grid;
        this.visited = new boolean[my][mx];
        int res = 0;
        for (int i = 0; i < my; i++) {
            for (int j = 0; j < mx; j++) {
                if (grid[i][j] == '1' && !visited[i][j]) {
                    res++;
                    dfs(i, j);
                }
            }
        }
        return res;
    }

    /**
     * 这里能保证所进入的y，x是合法的，而且没有将visited[y][x]置false，因为这里就是为了标记所有访问的路径
     *
     * @param y
     * @param x
     */
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
            return grid[y][x] == '1' && !visited[y][x];
        }
        return false;
    }

    @Test
    public void xx() {
        char[][] x = {
                {'1', '1', '1', '1', '0'},
                {'1', '1', '0', '1', '0'},
                {'1', '1', '0', '1', '1'},
                {'0', '0', '0', '1', '0'},
        };
        int i = numIslands(x);
        System.out.println(i);
    }
}
