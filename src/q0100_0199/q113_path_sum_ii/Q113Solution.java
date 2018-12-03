package q0100_0199.q113_path_sum_ii;

import my.TreeNode;
import my.TreeNodeUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/19
 */
public class Q113Solution {
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        } else {
            TreeNode left = root.left;
            TreeNode right = root.right;
            int val = root.val;
            if (left == null && right == null) {
                if (sum == val) {
                    List<Integer> r = new ArrayList<>();
                    r.add(val);
                    res.add(r);
                }
            } else {
                List<List<Integer>> rs = new ArrayList<>();
                rs.addAll(pathSum(left, sum - val));
                rs.addAll(pathSum(right, sum - val));
                for (List<Integer> re : rs) {
                    List<Integer> x = new ArrayList<>();
                    x.add(val);
                    x.addAll(re);
                    res.add(x);
                }
            }
            return res;
        }
    }

    @Test
    public void xx() {
        //Integer[] nums = {5, 4, 8, 11, null, 13, 4, 7, 2, null, null, null, null, 5, 1};
        Integer[] nums = {22,null,1};
        TreeNode node = TreeNodeUtils.create(nums);
        System.out.println(pathSum(node, 22));
    }
}
