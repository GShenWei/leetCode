package q0000_0099.q75_sort_color;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/10/31
 */
public class Q75Solution {
    //DONE
    public void sortColors(int[] nums) {
        int zeroRightEdge = -1;
        int twoLeftEdge = nums.length;

        for (int i = 0; i < twoLeftEdge; ) {
            if (nums[i] == 0) {
                zeroRightEdge++;
                swap(nums, i, zeroRightEdge);
                i++;
            } else if (nums[i] == 1) {
                i++;
            } else {
                assert (nums[i] == 2);
                twoLeftEdge--;
                swap(nums, twoLeftEdge, i);
            }
        }
    }

    private void swap(int[] k, int a, int b) {
        int t = k[a];
        k[a] = k[b];
        k[b] = t;
    }

    @Test
    public void xx() {
        int[] k = {2, 2, 1, 0};
        sortColors(k);
        System.out.println(Arrays.toString(k));

    }

}
