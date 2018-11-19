package q0100_0199.q110_balanced_binary_tree;

import my.TreeNode;
import my.TreeNodeUtils;
import org.junit.Test;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/19
 */
public class Q110Solution {
    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        } else {
            int hl = getHeight(root.left);
            int hr = getHeight(root.right);
            return Math.abs(hl - hr) <= 1 && isBalanced(root.left) && isBalanced(root.right);
        }
    }

    private int getHeight(TreeNode n) {
        if (n == null) {
            return 0;
        }
        int hl = 1;
        int hr = 1;
        if (n.left != null) {
            hl = getHeight(n.left) + 1;
        }
        if (n.right != null) {
            hr = getHeight(n.right) + 1;
        }
        return Math.max(hr, hl);
    }

    @Test
    public void xx() {
        Integer[] nums = {1, 2, 2, 3, null, null, 3, 4, null, null, null, null, null, null, 4};
        TreeNode node = TreeNodeUtils.create(nums);
        System.out.println(isBalanced(node));
    }
}
