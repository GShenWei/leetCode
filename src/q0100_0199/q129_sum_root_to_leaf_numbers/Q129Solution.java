package q0100_0199.q129_sum_root_to_leaf_numbers;

import my.TreeNode;
import my.TreeNodeUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/20
 */
public class Q129Solution {
    public int sumNumbers(TreeNode root) {
        List<String> paths = getPath(root);
        int sum = 0;
        for (String path : paths) {
            int i = Integer.parseInt(path);
            sum += i;
        }
        return sum;
    }

    private List<String> getPath(TreeNode node) {
        List<String> res = new ArrayList<>();
        if (node == null) {
            return res;
        } else {
            TreeNode left = node.left;
            TreeNode right = node.right;
            int val = node.val;
            if (left == null && right == null) {
                String x = val + "";
                res.add(x);
                return res;
            } else {
                List<String> thisRes = new ArrayList<>();
                thisRes.addAll(getPath(left));
                thisRes.addAll(getPath(right));
                for (String re : thisRes) {
                    String r = val + re;
                    res.add(r);
                }
                return res;
            }
        }
    }

    @Test
    public void xx() {
        //Integer[] nums = {5, 4, 8, 11, null, 13, 4, 7, 2, null, null, null, null, 5, 1};
        Integer[] nums = {9, 2, 1};
        TreeNode node = TreeNodeUtils.create(nums);
        System.out.println(sumNumbers(node));
    }
}
