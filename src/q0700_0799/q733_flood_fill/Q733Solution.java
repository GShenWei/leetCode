package q0700_0799.q733_flood_fill;

import org.junit.Test;

import java.util.Arrays;

public class Q733Solution {
    private int[][] next = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    private Integer oldColor;

    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        if (oldColor == null) {
            oldColor = image[sr][sc];
            image[sr][sc] = newColor;
        }
        int mx = image.length;
        int my = image[0].length;
        for (int[] aNext : next) {
            int dx = sr + aNext[0];
            int dy = sc + aNext[1];
            if (dx < mx && dy < my && dx >= 0 && dy >= 0 && image[dx][dy] == oldColor && image[dx][dy] != newColor) {
                image[dx][dy] = newColor;
                floodFill(image, dx, dy, newColor);
            }
        }
        return image;
    }

    @Test
    public void xx() {
        /*int[][] image = {{1, 1, 1}, {1, 1, 0}, {1, 0, 1}};

        int sr = 1, sc = 1, newColor = 2;*/
        int[][] image = {{0, 0, 0}, {0, 1, 0}};

        int sr = 1, sc = 1, newColor = 2;

        int[][] ints = floodFill(image, sr, sc, newColor);

        for (int[] anInt : ints) {
            System.out.println(Arrays.toString(anInt));
        }
    }
}
