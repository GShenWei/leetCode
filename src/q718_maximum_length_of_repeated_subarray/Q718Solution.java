package q718_maximum_length_of_repeated_subarray;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/8/22
 */
public class Q718Solution {
    public int findLength(int[] A, int[] B) {
        int lenA = A.length;
        int lenB = B.length;
        if (lenA < lenB) {
            int[] temp = A;
            A = B;
            B = temp;
            int tempLen = lenA;
            lenA = lenB;
            lenB = tempLen;
        }
        int[][] dp = new int[A.length + 1][B.length + 1];
        int result = 0;
        for (int i = 0; i < lenA; i++) {
            for (int j = 0; j < lenB; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 0;
                }
                if (A[i] == B[j]) {
                    dp[i + 1][j + 1] = dp[i][j] + 1;
                    result = Math.max(result, dp[i + 1][j + 1]);
                }
            }
        }
        return result;
    }

    @Test
    public void xx() {
        //int a[] = {1, 2, 3, 2, 1};
        int[] a = {0, 0, 0, 1};
        int[] b = {0, 0, 0, 0};
        //int b[] = {3, 2, 1, 4, 7};
        System.out.println(findLength(a, b));
    }
}
