package q0000_0099.q88_merge_sorted_array;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/1
 */
public class Q88Solution {
    //done
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int k = 0;
        int[] ret = new int[nums1.length];
        for (int i = 0, j = 0; i < m || j < n; ) {
            if (i < m && j < n) {
                if (nums1[i] < nums2[j]) {
                    ret[k++] = nums1[i++];
                } else {
                    ret[k++] = nums2[j++];
                }
            } else {
                if (i < m) {
                    ret[k++] = nums1[i++];
                }
                if (j < n) {
                    ret[k++] = nums2[j++];
                }

            }

        }
        System.arraycopy(ret, 0, nums1, 0, nums1.length);
    }

    @Test
    public void m() {
        int[] n1 = {1, 2, 3, 4, 5, 0};
        int[] n2 = {6};
        merge(n1, 5, n2, 1);
        System.out.println(Arrays.toString(n1));
    }
}
