package q0500_0599.q542_01_matrix;

import org.junit.Test;

import java.util.LinkedList;

public class Q542Solution {
    class Ind {
        int x;
        int y;

        Ind(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public int[][] updateMatrix(int[][] matrix) {
        int[][] next = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        LinkedList<Ind> queue = new LinkedList<>();
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] res = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    queue.add(new Ind(i, j));
                    res[i][j] = 0;
                } else {
                    res[i][j] = Integer.MAX_VALUE;
                }
            }
        }
        while (!queue.isEmpty()) {
            Ind pop = queue.pop();
            for (int[] an : next) {
                int dx = pop.x + an[0];
                int dy = pop.y + an[1];
                if (dx >= 0 && dx < m && dy >= 0 && dy < n) {
                    if (res[dx][dy] == Integer.MAX_VALUE || (res[pop.x][pop.y] != Integer.MAX_VALUE && res[dx][dy] > res[pop.x][pop.y] + 1)) {
                        res[dx][dy] = res[pop.x][pop.y] + 1;
                        queue.add(new Ind(dx, dy));
                    }
                }
            }
        }
        return res;
    }

    @Test
    public void xx() {
        //int[][] a = {{0, 0, 0}, {0, 1, 0}, {0, 0, 0}};
        //int[][] a = {{0, 0, 0}, {0, 1, 0}, {1, 1, 1}};
        int[][] a = {{0}, {0}, {0}};
        updateMatrix(a);
    }
}
