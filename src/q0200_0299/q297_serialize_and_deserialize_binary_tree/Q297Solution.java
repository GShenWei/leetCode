package q0200_0299.q297_serialize_and_deserialize_binary_tree;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/8/22
 */


public class Q297Solution {
    //TODO 未解决

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        String result = levelOrder(root);
        result = result.replaceAll("(null,)*$", "");
        if (result.endsWith(",")) {
            result = result.substring(0, result.lastIndexOf(","));
        }
        return "[" + result + "]";
    }


    /**
     * 层序遍历
     * 递归
     */
    public String levelOrder(TreeNode root) {
        if (root == null) {
            return "";
        }
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        StringBuilder result = new StringBuilder();
        queue.add(root);
        result.append(root.val).append(",");
        while (!queue.isEmpty()) {
            TreeNode temp = queue.poll();
            if (temp.left != null) {
                result.append(temp.left.val).append(",");
                queue.add(temp.left);
            } else {
                result.append("null,");
            }
            if (temp.right != null) {
                result.append(temp.right.val).append(",");
                queue.add(temp.right);
            } else {
                result.append("null,");
            }
        }
        return result.toString();
    }


    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        data = data.replaceAll("[\\[\\] ]", "");
        if (data.length() == 0) {
            return null;
        }
        String[] datas = data.split(",");
        int len = datas.length;
        //这里如果出现.999999999999999999999999999999的极端情况，这个计算会出错
        //int v = (int) Math.floor(Math.log(len + 2) / Math.log(2));
        List<TreeNode> nodeList = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            String intString = datas[i];
            if ("null".equalsIgnoreCase(intString)) {
                nodeList.add(null);
            } else {
                nodeList.add(new TreeNode(Integer.parseInt(intString)));
            }
        }
        len = nodeList.size();
        for (int i = 0; i < len; i++) {
            TreeNode thisNode = nodeList.get(i);
            if (thisNode != null) {
                int leftIndex = 2 * i + 1;
                int rightIndex = 2 * i + 2;
                if (leftIndex >= len) {
                    break;
                } else {
                    thisNode.left = nodeList.get(leftIndex);
                    if (rightIndex < len) {
                        thisNode.right = nodeList.get(rightIndex);
                    }
                }
            }
        }
        return nodeList.get(0);
    }

    @Test
    public void tt() {
        String re = serialize(deserialize("[5,2,3,null,null,2,4,3,1]"));
        System.out.println(re);
    }

    @Test
    public void xx() {
        double x = Math.floor(3.9999999999999);
        System.out.println(x);
    }

    @Test
    public void yy() {
        String data = "[1,2,3,null,null,4,5]";
        data = data.replaceAll("null", "n").replaceAll("[\\[\\], ]", "");
        System.out.println(data);
    }

    @Test
    public void zz() {
        char a = '1';
        int i = Integer.parseInt(a + "");
        System.out.println(i);

    }

    @Test
    public void xy() {
        String result = "1,2,4,";
        result = result.substring(0, result.lastIndexOf(","));
        System.out.println(result);
    }

    @Test
    public void gg() {
        String result = "1,2,null,null,null,";
        result = result.replaceAll("(null,)*$", "");
        System.out.println(result);
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}
