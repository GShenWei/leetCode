package q0900_0999.q965_univalued_binary_tree;

import my.TreeNode;
import my.TreeNodeUtils;
import org.junit.Test;

public class Q965Solution {
    public boolean isUnivalTree(TreeNode root) {
        boolean ok = true;
        if (root.left != null) {
            ok =  isUnivalTree(root.left) && root.val == root.left.val;
        }
        if(ok && root.right!=null){
            ok =  isUnivalTree(root.right) && root.val == root.right.val;
        }
        return ok;
    }

    @Test
    public void xx() {
        Integer[] xx = {1, 1, 1, 1, 1, 1, 2};
        TreeNode treeNode = TreeNodeUtils.create(xx);
        System.out.println(isUnivalTree(treeNode));
    }
}
