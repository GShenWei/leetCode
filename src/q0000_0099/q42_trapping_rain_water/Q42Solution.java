package q0000_0099.q42_trapping_rain_water;

import org.junit.Test;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/8/21
 */
public class Q42Solution {
    public int trap(int[] height) {
        int result = 0;
        //找到后续第一个比自己大的条目，如果没有找到，那么就取index最后的最大条目
        for (int i = 0, j = 1; i < height.length - 1; ) {
            //如果当前数据为0，那么直接跳过
            if (height[i] == 0) {
                i++;
                j = i + 1;
            } else {
                int maxIndex = -1;
                int max = 0;
                for (; j < height.length; j++) {
                    if (height[j] > height[i]) {
                        maxIndex = j;
                        break;
                    }
                    if (height[j] >= max) {
                        maxIndex = j;
                        max = height[j];
                    }
                }
                if (maxIndex == -1) {
                    break;
                }
                int minHeight = Math.min(height[i], height[maxIndex]);
                for (int k = i + 1; k < maxIndex; k++) {
                    result = result + minHeight - height[k];
                }
                i = maxIndex;
                j = i + 1;
            }
        }
        return result;
    }

    @Test
    public void xx() {
        int[] heights = {0,9,0,1};
        System.out.println(trap(heights));
    }

    @Test
    public void yy() {
        int[] heights = {0};
        for (int i = 0; i < heights.length; i++) {
            System.out.println(heights[i]);
        }
    }
}
