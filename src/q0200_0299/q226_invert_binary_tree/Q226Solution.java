package q0200_0299.q226_invert_binary_tree;

import my.TreeNode;
import my.TreeNodeUtils;
import org.junit.Test;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/15
 */
public class Q226Solution {
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);

        root.left = right;
        root.right = left;
        return root;
    }

    @Test
    public void xx(){
        Integer[] x = {4,2,7,1,3,6,9};
        TreeNode node = TreeNodeUtils.create(x);
        TreeNode node1 = invertTree(node);
        System.out.println(node1);
    }
}
