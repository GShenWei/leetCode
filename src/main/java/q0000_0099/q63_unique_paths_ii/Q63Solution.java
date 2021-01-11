package q0000_0099.q63_unique_paths_ii;

import org.junit.Test;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/10/29
 */
public class Q63Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int y = obstacleGrid.length;
        int x = obstacleGrid[0].length;
        int[][] steps = new int[y][x];
        if (obstacleGrid[0][0] != 1) {
            steps[0][0] = 1;
        } else {
            return 0;
        }
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                if (obstacleGrid[i][j] == 1) {
                    steps[i][j] = 0;
                    continue;
                }
                if (i - 1 >= 0) {
                    steps[i][j] += steps[i - 1][j];
                }
                if (j - 1 >= 0) {
                    steps[i][j] += steps[i][j - 1];
                }
            }
        }
        return steps[y - 1][x - 1];
    }

    @Test
    public void xx() {
        int[][] x = {{0, 0, 0}, {0, 1, 0}, {0, 0, 0}};
        //int[][] x = {{0},{0}};
        //int[][] x = {{1, 0}};
        int i = uniquePathsWithObstacles(x);

        System.out.println(i);
    }
}
