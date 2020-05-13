package q0200_0299.q222_count_complete_tree_nodes;

import my.TreeNode;
import my.TreeNodeUtils;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/19
 */
public class Q222Solution {


    @Test
    public void xx() {
        Integer[] x = {1, 2, 2, 3, 4, 4, 3};
        TreeNode node = TreeNodeUtils.create(x);
        System.out.println(countNodes(node));
    }

    class Pair {
        int level;
        TreeNode node;

        Pair(int level, TreeNode node) {
            this.level = level;
            this.node = node;
        }
    }

    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        TreeNode left = root.left;
        TreeNode right = root.right;
        int count = 2;
        //这种算法其实只是处理了很多特例（因为一个完全二叉树很很多的子树都是满二叉树），所以会快
        while (left != null && right != null) {
            count = count << 1;
            left = left.left;
            right = right.right;
        }
        if (left == null && right == null) {
            return count - 1;
        } else {
            return 1 + countNodes(root.right) + countNodes(root.left);
        }
    }

    public int countNodes3(TreeNode root) {
        if (root == null) {
            return 0;
        }
        LinkedList<Pair> queue = new LinkedList<>();
        queue.add(new Pair(1, root));
        int lastLevel = 1;
        while (!queue.isEmpty()) {
            Pair first = queue.poll();
            int level = first.level;
            TreeNode node = first.node;
            if (node.left == null) {
                lastLevel = level + 1;
                break;
            } else if (node.right == null) {
                queue.add(new Pair(level + 1, node.left));
                lastLevel = level + 1;
                break;
            } else {
                queue.add(new Pair(level + 1, node.left));
                queue.add(new Pair(level + 1, node.right));
            }
        }
        if (queue.isEmpty()) {
            return (int) Math.pow(2, lastLevel - 1) - 1;
        } else {
            int lastLevelCount = 0;
            for (Pair p : queue) {
                if (lastLevel == p.level) {
                    lastLevelCount++;
                }
            }
            return (int) Math.pow(2, lastLevel - 1) - 1 + lastLevelCount;
        }
    }

        /*public int countNodes2(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + countNodes2(root.right) + countNodes2(root.left);
    }
*/
}
