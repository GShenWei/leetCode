package my;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2019/4/23
 */
public class HeapSort {
    private void sort(int[] nums) {
        initBigHeap(nums);
        for (int i = nums.length - 1; i > 0; i--) {
            swap(nums, 0, i);
            adjust(nums, i);
        }

    }

    private void initBigHeap(int[] nums) {
        //从最后一个有叶子节点的节点开始做调整
        for (int i = nums.length / 2 - 1; i >= 0; i--) {
            //一定有左节点，但是不一定有右节点
            int leftIndex = i * 2 + 1;
            int rightIndex = i * 2 + 2;
            if (nums[leftIndex] > nums[i]) {
                swap(nums, i, leftIndex);
            }
            if (rightIndex < nums.length) {
                int right = nums[rightIndex];
                if (right > nums[i]) {
                    swap(nums, i, rightIndex);
                }
            }
        }
    }

    /**
     * nums里面的数在最后面的一部分其实不属于堆了
     *
     * @param nums
     * @param length 堆的长度
     */
    private void adjust(int[] nums, int length) {
        int temp = nums[0];

        //正在调整的节点
        int i = 0;
        //从根节点的左孩子节点开始计算
        for (int k = 1; k < length; k = 2 * k + 1) {
            if (k + 1 < length && nums[k] < nums[k + 1]) {
                k++;
            }
            if (nums[k] > temp) {
                swap(nums, i, k);
                //发生了交换，所以下一个调整的节点就是它
                i = k;
            } else {
                break;
            }
        }
    }

    private void swap(int[] nums, int i, int i1) {
        int temp = nums[i];
        nums[i] = nums[i1];
        nums[i1] = temp;
    }

    @Test
    public void xx(){
        int[] nums = {10,6,7,9,3,4,8,2,1};
        sort(nums);
        System.out.println(Arrays.toString(nums));
    }
}
