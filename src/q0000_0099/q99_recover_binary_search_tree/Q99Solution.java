package q0000_0099.q99_recover_binary_search_tree;

import my.BTreeCmd;
import my.TreeNode;
import my.TreeNodeUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Q99Solution {
    public void recoverTree(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return;
        }
        List<TreeNode> nodes = midTr(root);
        Integer x = null;
        Integer y = null;
        for (int i = 0; i < nodes.size() - 1; i++) {
            TreeNode n1 = nodes.get(i);
            TreeNode n2 = nodes.get(i + 1);
            if (n1.val > n2.val) {
                if (x == null) {
                    x = i;
                } else if (y == null) {
                    y = i + 1;
                }
            }
        }
        if (y == null) {
            y = x + 1;
        }
        int temp = nodes.get(x).val;
        nodes.get(x).val = nodes.get(y).val;
        nodes.get(y).val = temp;
    }

    private List<TreeNode> midTr(TreeNode root) {
        List<TreeNode> res = new ArrayList<>();
        Stack<BTreeCmd> stack = new Stack<>();
        stack.push(new BTreeCmd(BTreeCmd.GO, root));
        while (!stack.isEmpty()) {
            BTreeCmd pop = stack.pop();
            TreeNode thisNode = pop.node;
            if (BTreeCmd.PRINT.equals(pop.cmd)) {
                res.add(thisNode);
            } else {
                TreeNode rightNode = thisNode.right;
                if (rightNode != null) {
                    stack.add(new BTreeCmd(BTreeCmd.GO, rightNode));
                }

                stack.add(new BTreeCmd(BTreeCmd.PRINT, thisNode));

                TreeNode leftNode = thisNode.left;
                if (leftNode != null) {
                    stack.add(new BTreeCmd(BTreeCmd.GO, leftNode));
                }
            }
        }
        return res;
    }

    @Test
    public void xx() {
        Integer[] a = {1, 3, null, null, 2};
        TreeNode root = TreeNodeUtils.create(a);
        recoverTree(root);
        TreeNodeUtils.printTreeNode(root);
    }
}
