package q0400_0499.q450_delete_node_in_a_bst;

import my.TreeNode;
import my.TreeNodeUtils;
import org.junit.Test;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/20
 */
public class Q450Solution {

    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        } else {
            TreeNode left = root.left;
            TreeNode right = root.right;
            int val = root.val;
            if (val < key) {
                root.right = deleteNode(right, key);
            } else if (val > key) {
                root.left = deleteNode(left, key);
            } else {
                if (left == null) {
                    return right;
                }
                if (right == null) {
                    return left;
                }
                root = min(right);
                root.right = deleteMin(right);
                root.left = left;
            }
            return root;
        }
    }

    private TreeNode deleteMin(TreeNode node) {
        if (node.left == null) {
            return node.right;
        }
        node.left = deleteMin(node.left);
        return node;
    }

    private TreeNode min(TreeNode node) {
        if (node.left == null) {
            return node;
        }
        return min(node.left);
    }


    @Test
    public void xx() {
        Integer[] nums = {4, 2, 6, 1, 3, 5, 7, null, null, 3, 3};
        TreeNode node = TreeNodeUtils.create(nums);
        TreeNodeUtils.printTreeNode(deleteNode(node, 3));
        //System.out.println(deleteNode(node, 3));
    }
}
