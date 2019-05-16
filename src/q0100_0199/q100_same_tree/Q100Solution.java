package q0100_0199.q100_same_tree;

import my.TreeNode;
import my.TreeNodeUtils;
import org.junit.Test;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/16
 */
public class Q100Solution {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p != null && q != null) {
            return isSameTree(p.left, q.left) && isSameTree(p.right, q.right) && p.val == q.val;
        } else {
            return false;
        }
    }

    @Test
    public void xx() {
        Integer[] n1 = {1, 2};
        Integer[] n2 = {1, 2};
        TreeNode node1 = TreeNodeUtils.create(n1);
        TreeNode node2 = TreeNodeUtils.create(n2);
        System.out.println(isSameTree(node1, node2));

    }
}
