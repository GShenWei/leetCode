package q0500_0599.q576_out_of_boundary_paths;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author chenwei
 * @date 2019/12/13
 */
public class Q576Solution {

    int[] dc = {0, 0, 1, -1};
    int[] dr = {1, -1, 0, 0};

    public int findPaths2(int m, int n, int N, int i, int j) {
        if (N == 0) {
            return 0;
        }
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{i, j, 0});
        int count = 0;
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int step = cur[2];
            if (step >= N) {
                continue;
            }
            for (int k = 0; k < 4; k++) {
                int c = cur[0] + dc[k];
                int r = cur[1] + dr[k];
                if (c == -1 || c == m || r == -1 || r == n) {
                    count++;
                    continue;
                }
                if (c >= 0 && c < m && r >= 0 && r < n) {
                    queue.add(new int[]{c, r, step + 1});
                }
            }
        }
        return count;
    }

    public int findPaths(int m, int n, int N, int i, int j) {
        int[][][] dp = new int[m][n][N + 1];
        for (int k = 1; k <= N; k++) {
            for (int l = 0; l < m; l++) {
                for (int o = 0; o < n; o++) {
                    for (int p = 0; p < 4; p++) {
                        int r = l + dr[p];
                        int c = o + dc[p];
                        if (r >= 0 && r < m && c >= 0 && c < n) {
                            //题目要求输出 mod 1000000007
                            dp[l][o][k] = (dp[l][o][k] + dp[r][c][k - 1]) % 1000000007;
                        } else {
                            //如果经过一步已经在边界上，那么到达这个边界的情况加一
                            dp[l][o][k] += 1;
                        }
                    }
                }
            }
        }
        return dp[i][j][N];
    }


    @Test
    public void gg() {
        int p = findPaths(2, 2, 2, 0, 0);
        int p1 = findPaths(1, 3, 3, 0, 1);
        int p2 = findPaths(7, 6, 13, 0, 2);
        System.out.println(p);
        System.out.println(p1);
        System.out.println(p2);
    }
}
