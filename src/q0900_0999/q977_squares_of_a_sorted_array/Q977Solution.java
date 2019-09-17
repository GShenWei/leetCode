package q0900_0999.q977_squares_of_a_sorted_array;

import org.junit.Test;

import java.util.Arrays;

public class Q977Solution {
    @Test
    public void x() {
        //int[] a = {-4, -1, 0, 3, 10};
        int[] a = {-5,-4,-3,-2,-1,0};
        System.out.println(Arrays.toString(sortedSquares(a)));
    }

    public int[] sortedSquares(int[] A) {
        int[] a = A;

        //寻找最小的非负数，当全部都是负数的时候，寻找最大的负数，的位置
        int firstIndex = -1;
        if (a[0] >= 0) {
            firstIndex = 0;
        } else if (a[a.length - 1] <= 0) {
            firstIndex = a.length-1;
        }else{
            for (int i = 1; i < a.length; i++) {
                if (a[i - 1] * a[i] <= 0 || i == a.length - 1) {
                    firstIndex = i;
                    break;
                }
            }
        }
        int[] res = new int[a.length];
        int k = 0;
        for (int i = firstIndex, j = firstIndex - 1; i < a.length || j >= 0; ) {
            if (i < a.length && j >= 0) {
                int x = a[i] * a[i];
                int y = a[j] * a[j];
                if (x < y) {
                    res[k++] = x;
                    i++;
                } else if (x > y) {
                    res[k++] = y;
                    j--;
                } else {
                    res[k++] = x;
                    res[k++] = y;
                    j--;
                    i++;
                }
            } else if (i < a.length) {
                int x = a[i] * a[i];
                res[k++] = x;
                i++;
            } else {
                int y = a[j] * a[j];
                res[k++] = y;
                j--;
            }
        }
        return res;
    }

}
