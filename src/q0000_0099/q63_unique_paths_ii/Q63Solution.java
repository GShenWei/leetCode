package q0000_0099.q63_unique_paths_ii;

import org.junit.Test;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/10/29
 */
public class Q63Solution {
    //TODO 未解决
    private int pathCount = 0;
    private int[][] next = {{1, 0}, {0, 1}};
    private int[][] book;
    private int[][] obstacleGrid;

    private int ex;
    private int ey;

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        this.obstacleGrid = obstacleGrid;
        book = new int[obstacleGrid.length][obstacleGrid[0].length];
        ex = obstacleGrid.length - 1;
        ey = obstacleGrid[0].length - 1;
        dfs(0, 0);
        return pathCount;
    }

    private void dfs(int sx, int sy) {
        if (obstacleGrid[sx][sy] == 1) {
            return;
        }
        if (sx == ex && sy == ey) {
            pathCount++;
            return;
        }
        for (int k = 0; k <= 1; k++) {
            int dx = sx + next[k][0];
            int dy = sy + next[k][1];
            if (dx > ex || dy > ey) {
                continue;
            }
            if (book[dx][dy] != 1 && obstacleGrid[dx][dy] != 1) {
                book[dx][dy] = 1;
                dfs(dx, dy);
                book[dx][dy] = 0;
            }
        }
    }

    @Test
    public void xx() {
        int[][] x = {{0,0,0},{0,1,0},{0,0,0}};
        //int[][] x = {{0},{0}};
        //int[][] x = {{1, 0}};
        int i = uniquePathsWithObstacles(x);

        System.out.println(i);
    }
}
