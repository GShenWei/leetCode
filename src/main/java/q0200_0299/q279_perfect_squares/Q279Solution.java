package q0200_0299.q279_perfect_squares;

import org.junit.Test;

import java.util.LinkedList;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/12
 */
public class Q279Solution {
    class Pair {
        int num;
        int level;

        public Pair(int num, int level) {
            this.num = num;
            this.level = level;
        }
    }

    public int numSquares(int n) {
        LinkedList<Pair> queue = new LinkedList<>();
        queue.add(new Pair(n, 0));
        int[] visit = new int[n + 1];
        visit[n] = 1;

        /*
        1.无论怎么样,一个正整数可以分解成相应数目的1,所以不可能没有解
        2.对于一个数n,可以将其分解成一个数n-i*i 加上一个平方数 i*i,而且这个数和这个之间相差为一个完全平方数
        3.依次这么查找下去,找到第一个能到达n-i*i=0的数为止
        4.所以这里可以用广度优先搜索,广度优先搜索会涉及到队列,将每一层的数据按照顺序依次入队(他们需要有多的一个字段来代表层级)
         */

        while (!queue.isEmpty()) {
            Pair pair = queue.pop();
            int num = pair.num;
            int level = pair.level;
            for (int i = 1; ; i++) {
                int newI = num - i * i;
                if (newI < 0) {
                    break;
                }
                if (newI == 0) {
                    return level + 1;
                }
                if (visit[newI] != 1) {
                    queue.add(new Pair(newI, level + 1));
                    visit[newI] = 1;
                }

            }
        }
        return -1;
    }

    @Test
    public void xx() {
        System.out.println(numSquares(12));
    }
}
