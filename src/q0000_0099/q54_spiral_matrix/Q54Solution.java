package q0000_0099.q54_spiral_matrix;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chenwei
 * @date 2019/12/16
 */
public class Q54Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        ArrayList<Integer> res = new ArrayList<>();
        if(matrix.length == 0){
            return res;
        }
        deal(res, matrix, 0, matrix[0].length - 1, 0, matrix.length - 1, 1);
        return res;
    }

    private void deal(ArrayList<Integer> res, int[][] matrix, int minCol, int maxCol, int minRow, int maxRow, int direction) {
        /*if (minCol > maxCol && minRow > maxRow) {
            return;
        }*/
        switch (direction) {
            case 1:
                if (minCol <= maxCol) {
                    for (int i = minCol; i <= maxCol; i++) {
                        res.add(matrix[minRow][i]);
                    }
                    minRow++;
                    deal(res, matrix, minCol, maxCol, minRow, maxRow, 2);
                }
                break;
            case 2:
                if (minRow <= maxRow) {
                    for (int i = minRow; i <= maxRow; i++) {
                        res.add(matrix[i][maxCol]);
                    }
                    maxCol--;
                    deal(res, matrix, minCol, maxCol, minRow, maxRow, 3);
                }
                break;
            case 3:
                if (minCol <= maxCol) {
                    for (int i = maxCol; i >= minCol; i--) {
                        res.add(matrix[maxRow][i]);
                    }
                    maxRow--;
                    deal(res, matrix, minCol, maxCol, minRow, maxRow, 4);
                }
                break;
            case 4:
                if (minRow <= maxRow) {
                    for (int i = maxRow; i >= minRow; i--) {
                        res.add(matrix[i][minCol]);
                    }
                    minCol++;
                    deal(res, matrix, minCol, maxCol, minRow, maxRow, 1);
                }
                break;
            default:
                break;
        }
    }

    @Test
    public void gg() {
        int[][] a = {{1,2}};
        List<Integer> integers = spiralOrder(a);
        String join = integers.stream().map(Object::toString).collect(Collectors.joining(","));
        System.out.println(join);
    }
}
