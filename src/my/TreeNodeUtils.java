package my;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/15
 */
public class TreeNodeUtils {
    public static TreeNode create(Integer[] nums) {
        if (nums.length < 1 || nums[0] == null) {
            return null;
        }
        TreeNode[] nodes = new TreeNode[nums.length];
        for (int i = 0; i < nums.length / 2; i++) {
            int leftIndex = i * 2 + 1;
            int rightIndex = i * 2 + 2;

            if (nodes[i] == null) {
                nodes[i] = createOneNode(nums[i]);
            }

            if (nodes[i] == null) {
                continue;
            }
            if (nodes[leftIndex] == null) {
                nodes[leftIndex] = createOneNode(nums[leftIndex]);
            }
            nodes[i].left = nodes[leftIndex];
            if (rightIndex < nums.length) {
                if (nodes[rightIndex] == null) {
                    nodes[rightIndex] = createOneNode(nums[rightIndex]);
                }
                nodes[i].right = nodes[rightIndex];
            }
        }
        return nodes[0];
    }

    private static TreeNode createOneNode(Integer i) {
        if (i == null) {
            return null;
        } else {
            return new TreeNode(i);
        }

    }

    static class Pair {
        TreeNode treeNode;
        Integer level;

        Pair(TreeNode treeNode, Integer level) {
            this.treeNode = treeNode;
            this.level = level;
        }
    }

    @SuppressWarnings("Duplicates")
    private static List<List<Integer>> levelOrder(TreeNode root) {
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
            int newLevel = level + 1;
            if (treeNode.left != null) {
                queue.add(new Pair(treeNode.left, newLevel));
            }
            if (treeNode.right != null) {
                queue.add(new Pair(treeNode.right, newLevel));
            }
        }
        return res;
    }

    public static void printTreeNode(TreeNode root) {
        List<List<Integer>> lists = levelOrder(root);
        List<Integer> res = new ArrayList<>();
        for (List<Integer> l : lists) {
            res.addAll(l);
        }
        System.out.println(res);
    }

    @Test
    public void xx() {
        Integer[] x = {3, 9, 20, null, null, 15, 7};
        TreeNode node = create(x);
        printTreeNode(node);
    }
}
