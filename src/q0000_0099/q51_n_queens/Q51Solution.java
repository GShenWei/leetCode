package q0000_0099.q51_n_queens;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/21
 */
public class Q51Solution {
    private int[] queen;
    List<List<String>> res = new ArrayList<>();

    public List<List<String>> solveNQueens(int n) {
        queen = new int[n];
        for (int i = 0; i < n; i++) {
            queen[i] = -1;
        }
        findQueen(0, n);
        return res;
    }

    private void findQueen(int rowNum, int n) {
        if (rowNum >= n) {
            return;
        }
        for (int i = 0; i < n; i++) {
            if (isOk(rowNum, i)) {
                queen[rowNum] = i;
                if (rowNum == n - 1) {
                    List<String> x = new ArrayList<>();
                    for (int k = 0; k < n; k++) {
                        int index = queen[k];
                        StringBuilder s = new StringBuilder();
                        for (int j = 0; j < index; j++) {
                            s.append(".");
                        }
                        s.append("Q");
                        for (int j = index + 1; j < n; j++) {
                            s.append(".");
                        }
                        x.add(s.toString());
                    }
                    res.add(x);
                }
                findQueen(rowNum + 1, n);
            }
        }
        /*int i = rowNum - 1;
        if (i >= 0) {
            queen[i] = -1;
        }*/
    }

    private boolean isOk(int rowNum, int colNum) {
        for (int i = 0; i < rowNum; i++) {
            int y1 = i;
            int x1 = queen[i];

            int y2 = rowNum;
            int x2 = colNum;

            if (x1 == x2) {
                return false;
            }
            if (y1 - y2 == x1 - x2) {
                return false;
            }
            if (y1 - y2 == -x1 + x2) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void xx() {
        System.out.println(solveNQueens(8));
    }
}
