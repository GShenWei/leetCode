package q0400_0499.q437_path_sum_iii;

import my.TreeNode;
import my.TreeNodeUtils;
import org.junit.Test;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/20
 */
public class Q437Solution {
    public int pathSum(TreeNode root, int sum) {
        if (root == null) {
            return 0;
        } else {
            int res = 0;
            res += findPath(root, sum);
            res += pathSum(root.left, sum);
            res += pathSum(root.right, sum);
            return res;
        }
    }

    private int findPath(TreeNode node, int sum) {
        if (node == null) {
            return 0;
        }
        int res = 0;
        TreeNode left = node.left;
        TreeNode right = node.right;
        int val = node.val;
        if (sum == val) {
            res += 1;
        }
        res += findPath(left, sum - val);
        res += findPath(right, sum - val);
        return res;
    }

    @Test
    public void xx() {
        Integer[] nums = {10, 5, -3, 3, 2, null, 11, 3, -2, null, 1};
        TreeNode node = TreeNodeUtils.create(nums);
        System.out.println(pathSum(node, 8));
    }
}
