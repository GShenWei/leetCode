package q778_swim_in_rising_water;

import java.util.LinkedList;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @name Q778Solution
 * @description
 * @date 2018/2/22
 */
public class Q778Solution {
    //TODO 未解决

    class Node{
        int row;
        int col;
        int value;
    }
    private int[][] grid;
    private Integer[][] isVisit;
    private LinkedList<Node> result = new LinkedList<>();
    private int cols;
    private int rows;

    public int swimInWater(int[][] grid) {
        this.grid = grid;
        this.cols = grid[0].length;
        this.rows = grid.length;
        this.isVisit = new Integer[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Node node= new Node();
                node.row = i;
                node.col = j;
                node.value = grid[i][j];
                if (result.isEmpty()) {
                    result.addFirst(node);
                    isVisit[i][j] = 1;
                } else {
                    Node first = result.getFirst();
                    int firstValue = first.value;
                    Node next = findNextNode(i,j);
                }
            }
        }
        return 0;
    }

    private Node findNextNode(int i, int j) {
        return null;
    }
}
