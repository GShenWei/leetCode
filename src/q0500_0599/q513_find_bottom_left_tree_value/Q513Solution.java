package q0500_0599.q513_find_bottom_left_tree_value;

import my.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Q513Solution {
    static class Pair {
        TreeNode treeNode;
        Integer level;

        Pair(TreeNode treeNode, Integer level) {
            this.treeNode = treeNode;
            this.level = level;
        }
    }

    public int findBottomLeftValue(TreeNode root) {
        LinkedList<Pair> queue = new LinkedList<>();
        queue.add(new Pair(root, 0));
        List<ArrayList<Pair>> res = new ArrayList<>();
        //HashMap<Integer, ArrayList<Pair>> res = new HashMap<>();
        while (!queue.isEmpty()) {
            Pair pop = queue.pop();
            int level = pop.level;
            ArrayList<Pair> k;
            if (res.size() < level + 1) {
                k = new ArrayList<>();
                res.add(k);
            } else {
                k = res.get(level);
            }
            k.add(pop);
            TreeNode treeNode = pop.treeNode;
            int newLevel = level + 1;
            if (treeNode.left != null) {
                queue.add(new Pair(treeNode.left, newLevel));
            }
            if (treeNode.right != null) {
                queue.add(new Pair(treeNode.right, newLevel));
            }
        }
        return res.get(res.size() - 1).get(0).treeNode.val;
    }
}
