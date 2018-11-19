package q0100_0199.q111_minimum_depth_of_binary_tree;

import my.TreeNode;
import my.TreeNodeUtils;
import org.junit.Test;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/15
 */
public class Q111Solution {
    public int minDepth(TreeNode root) {

        if (root == null) {
            return 0;
        }

        int leftHigh = minDepth(root.left);
        int rightHigh = minDepth(root.right);
        if (leftHigh == 0) {
            return rightHigh + 1;
        }
        if (rightHigh == 0) {
            return leftHigh + 1;
        }
        return Math.min(leftHigh, rightHigh) + 1;
    }

    @Test
    public void xx() {
        Integer[] x = {1, 2};
        TreeNode node = TreeNodeUtils.create(x);

        System.out.println(minDepth(node));
    }
}
