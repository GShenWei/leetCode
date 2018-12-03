package q0300_0399.q337_house_robber_iii;

import my.TreeNode;
import my.TreeNodeUtils;
import org.junit.Test;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/28
 */
public class Q337Solution {

    public int rob(TreeNode root) {
        int[] ints = robSub(root);
        return Math.max(ints[0], ints[1]);
    }

    private int[] robSub(TreeNode node) {
        int[] res = new int[2];
        if (node == null) {
            return res;
        }
        int[] leftRes = new int[2];
        int[] rightRes = new int[2];
        if (node.left != null) {
            leftRes = robSub(node.left);
        }
        if (node.right != null) {
            rightRes = robSub(node.right);
        }
        //代表当本节点不被抢劫时，而其左右子节点可以被抢，也可以不被抢，取其中较大的情况
        res[0] = Math.max(leftRes[0], leftRes[1]) + Math.max(rightRes[0], rightRes[1]);

        //代表本节点被抢，那么其左右子节点只能不被抢
        res[1] = node.val + leftRes[0] + rightRes[0];
        return res;
    }

    @Test
    public void xx() {
        Integer[] numx = {3, 2, 3, null, 3, null, 1};
        TreeNode node = TreeNodeUtils.create(numx);
        int rob = rob(node);
        System.out.println(rob);
    }
}
