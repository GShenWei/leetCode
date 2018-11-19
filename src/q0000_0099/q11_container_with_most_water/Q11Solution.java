package q0000_0099.q11_container_with_most_water;

import org.junit.Test;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/8/21
 */
public class Q11Solution {
    public int maxArea(int[] height) {
        //最左边的
        int i = 0;
        //最右边的
        int j = height.length - 1;
        //目前的体积
        int maxSize = 0;
        for (i = 0; i < j; ) {
            //两条边中间最短的用作为高
            int h = Math.min(height[i], height[j]);
            int currentSize = (j - i) * h;
            maxSize = Math.max(maxSize, currentSize);
            if (height[i] <= height[j]) {
                i++;
            } else {
                j--;
            }
        }
        return maxSize;
    }

    @Test
    public void test() {
        int[] heights = {1,8,6,2,5,4,8,3,7};
        System.out.println(maxArea(heights));
    }
}
