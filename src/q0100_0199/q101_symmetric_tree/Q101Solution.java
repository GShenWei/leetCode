package q0100_0199.q101_symmetric_tree;

import my.TreeNode;
import my.TreeNodeUtils;
import org.junit.Test;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/16
 */
public class Q101Solution {
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        } else {
            return isMirror(root.left, root.right);
        }
    }

    private boolean isMirror(TreeNode n1, TreeNode n2) {
        if (n1 == null && n2 == null) {
            return true;
        } else if (n1 != null && n2 != null) {
            return n1.val == n2.val && isMirror(n1.left, n2.right) && isMirror(n1.right, n2.left);
        } else {
            return false;
        }
    }

    @Test
    public void xx() {
        Integer[] x = {1, 2, 2, 3, 4, 4, 3};
        TreeNode node = TreeNodeUtils.create(x);
        System.out.println(isSymmetric(node));
    }
}
