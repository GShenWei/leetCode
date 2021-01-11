package q0400_0499.q417_pacific_atlantic_water_flow;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/23
 */
public class Q417Solution {
    private Pair[][] used;

    private int[][] matrix;
    private int[][] next = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    private int mx;
    private int my;

    class Pair {
        int p = 0;
        int a = 0;

        boolean isOk() {
            return p > 0 && a > 0;
        }
    }

    /**
     * 这道题可以转变思路，直接倒过来判断海水蔓延到山上的，重复被两个海水蔓延的地方就是所得
     *
     * @param matrix
     * @return
     */
    public List<int[]> pacificAtlantic(int[][] matrix) {
        int my = matrix.length;
        List<int[]> res = new ArrayList<>();
        if (my == 0) {
            return res;
        }
        int mx = matrix[0].length;

        this.mx = mx;
        this.my = my;
        this.used = new Pair[my][mx];
        this.matrix = matrix;
        for (int i = 0; i < used.length; i++) {
            for (int j = 0; j < used[i].length; j++) {
                used[i][j] = new Pair();
            }
        }
        for (int x = 0; x < mx; x++) {
            dfs(x, 0, 'P');
        }
        for (int y = 0; y < my; y++) {
            dfs(0, y, 'P');
        }
        for (int x = 0; x < mx; x++) {
            dfs(x, my - 1, 'A');
        }
        for (int y = 0; y < my; y++) {
            dfs(mx - 1, y, 'A');
        }
        for (int i = 0; i < used.length; i++) {
            for (int j = 0; j < used[i].length; j++) {
                if (used[i][j].isOk()) {
                    int[] g = {i, j};
                    res.add(g);
                }
            }
        }
        return res;
    }

    private void dfs(int x, int y, char c) {
        if (c == 'P') {
            used[y][x].p++;
        } else {
            used[y][x].a++;
        }

        for (int[] aNext : next) {
            int dx = x + aNext[0];
            int dy = y + aNext[1];
            if (dx < mx && dy < my && dx >= 0 && dy >= 0 && matrix[dy][dx] >= matrix[y][x]) {
                if (c == 'P' && used[dy][dx].p != 0) {
                    continue;
                }
                if (c == 'A' && used[dy][dx].a != 0) {
                    continue;
                }
                dfs(dx, dy, c);
            }

        }
    }

    @Test
    public void xx() {
        int[][] u = {
                {1, 2, 2, 3, 5},
                {3, 2, 3, 4, 4},
                {2, 4, 5, 3, 1},
                {6, 7, 1, 4, 5},
                {5, 1, 1, 2, 4},
        };

        /*int[][] u = {
                {1},
                {3},
                {2},
                {6},
                {5},
        };*/
        List<int[]> ints = pacificAtlantic(u);
        for (int[] anInt : ints) {
            System.out.println(Arrays.toString(anInt));
        }

    }
}
