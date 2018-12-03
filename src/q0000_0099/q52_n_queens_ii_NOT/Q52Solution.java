package q0000_0099.q52_n_queens_ii_NOT;

import org.junit.Test;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @name Q52Solution
 * @description
 * @date 2017/12/14
 */
public class Q52Solution {
    private int[] queen;
    private int res = 0;

    public int totalNQueens(int n) {
        queen = new int[n];
        for (int i = 0; i < n; i++) {
            queen[i] = -1;
        }
        findQueen(0, n);
        return res;
    }

    private boolean isOk(int rowNum, int thisCol) {
        for (int i = 0; i < rowNum; i++) {
            int x1 = queen[i];
            int y1 = i;
            int x2 = thisCol;
            int y2 = rowNum;
            //直线 x=？
            if (x1 == x2) {
                return false;
            }
            //直线 y = x+b
            if (x1 - x2 == y1 - y2) {
                return false;
            }
            //直线 y= -x +b
            if (y1 - y2 == -x1 + x2) {
                return false;
            }
        }
        return true;
    }

    private void findQueen(int rowNum, int n) {
        if (rowNum >= n) {
            return;
        }
        for (int i = 0; i < n; i++) {
            if (isOk(rowNum, i)) {
                //将这个皇后放到第rowNum行的第i列上
                queen[rowNum] = i;
                if (rowNum == n - 1) {
                    res++;
                    //这里return的话上面那个 if(rowNum >=n) return 就没有必要了
                    //return;
                }
                findQueen(rowNum + 1, n);
            }
        }
        //这里只是为了路径正确，当没有解的时候，保证都是-1
        /*int i = rowNum - 1;
        if (i >= 0) {
            queen[i] = -1;
        }*/
    }

    @Test
    public void oo() {
        int i = totalNQueens(14);
        System.out.println(i);
    }

}
