package q0400_0499.q404_sum_of_left_leaves;

import my.TreeNode;
import my.TreeNodeUtils;
import org.junit.Test;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/19
 */
public class Q404Solution {
    private int sum = 0;

    public int sumOfLeftLeaves(TreeNode root) {
        if (root == null) {
            return 0;
        } else if (root.left == null && root.right == null) {
            return 0;
        } else {
            return su(root);
        }
    }

    private int su(TreeNode node) {
        if (node == null) {
            return 0;
        } else {
            TreeNode left = node.left;
            TreeNode right = node.right;
            if (left == null && right == null) {
                sum = sum + node.val;
                return sum;
            }
            if (right != null) {
                right.val = 0;
            }
            su(left);
            su(right);
        }
        return sum;
    }

    @Test
    public void xx() {
        Integer[] nums = {3,9,20,null,null,15,7};
        TreeNode node = TreeNodeUtils.create(nums);
        System.out.println(sumOfLeftLeaves(node));
    }


}
