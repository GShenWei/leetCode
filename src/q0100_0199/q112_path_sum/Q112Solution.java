package q0100_0199.q112_path_sum;

import my.TreeNode;
import my.TreeNodeUtils;
import org.junit.Test;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/19
 */
public class Q112Solution {

    private boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) {
            return false;
        } else if (root.right == null && root.left == null) {
            return root.val == sum;
        } else {
            return hasPathSum(root.left, sum - root.val) || hasPathSum(root.right, sum - root.val);
        }
    }

    @Test
    public void xx() {
        Integer[] nums = {-2, null, -3};
        TreeNode node = TreeNodeUtils.create(nums);
        System.out.println(hasPathSum(node, -2));
    }
}
