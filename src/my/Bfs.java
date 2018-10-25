package my;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/9/25
 */
public class Bfs {
    class Node {
        int x;
        int y;
        //步数
        int s;
        //父节点在队列中的位置,方便输出日志
        int f;
    }



    int main() {
        int[][] next = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        Queue<Node> que = new PriorityQueue<>();
        int head;
        int tail;
        int[][] a = new int[51][51];
        return 0;

    }
}
