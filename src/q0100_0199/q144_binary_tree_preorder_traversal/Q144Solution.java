package q0100_0199.q144_binary_tree_preorder_traversal;

import my.BTreeCmd;
import my.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/12
 */
public class Q144Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Stack<BTreeCmd> stack = new Stack<>();
        stack.push(new BTreeCmd(BTreeCmd.GO, root));
        while (!stack.isEmpty()) {
            BTreeCmd bTreeCmd = stack.pop();
            TreeNode thisNode = bTreeCmd.node;
            String cmd = bTreeCmd.cmd;
            if (BTreeCmd.PRINT.equals(cmd)) {
                res.add(thisNode.val);
            } else {
                assert (BTreeCmd.GO.equals(cmd));
                TreeNode rightNode = thisNode.right;
                TreeNode leftNode = thisNode.left;
                if (rightNode != null) {
                    stack.push(new BTreeCmd(BTreeCmd.GO, rightNode));
                }
                if (leftNode != null) {
                    stack.push(new BTreeCmd(BTreeCmd.GO, leftNode));
                }
                stack.push(new BTreeCmd(BTreeCmd.PRINT, thisNode));
            }
        }
        return res;
    }

}
