package q103_binary_tree_zigzag_level_order_traversal;

import my.TreeNode;

import java.util.*;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/12
 */
public class Q103Solution {
    class Pair {
        TreeNode treeNode;
        Integer level;

        public Pair(TreeNode treeNode, Integer level) {
            this.treeNode = treeNode;
            this.level = level;
        }
    }

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        LinkedList<Pair> queue = new LinkedList<>();
        queue.add(new Pair(root, 0));
        while (!queue.isEmpty()) {
            Pair pop = queue.pop();
            int level = pop.level;
            TreeNode treeNode = pop.treeNode;
            if (level == res.size()) {
                res.add(new ArrayList<>());
            }
            List<Integer> thisLevelRes = res.get(level);
            thisLevelRes.add(treeNode.val);
            //新加入的层数,因为层数是1开始的,当为基数的时候就是逆序,为偶数的时候为顺序
            int newLevel = level + 1;
            if (treeNode.left != null) {
                queue.add(new Pair(treeNode.left, newLevel));
            }
            if (treeNode.right != null) {
                queue.add(new Pair(treeNode.right, newLevel));
            }
        }
        //最终再转换一次顺序
        boolean asc = true;
        for (List<Integer> r : res) {
            if (!asc) {
                int size = r.size();
                for (int i = 0; i < size / 2; i++) {
                    int t = r.get(i);
                    r.set(i, r.get(size - 1 - i));
                    r.set(size - 1 - i, t);
                }
                asc = true;
            } else {
                asc = false;
            }
        }

        return res;
    }
}
