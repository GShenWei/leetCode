package q0600_0699.q675_cut_off_trees_for_golf_event;

import org.junit.Test;

import java.util.*;

public class Q675Solution {

    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public int cutOffTree(List<List<Integer>> forest) {
        List<int[]> trees = new ArrayList<>();
        for (int r = 0; r < forest.size(); ++r) {
            for (int c = 0; c < forest.get(0).size(); ++c) {
                int v = forest.get(r).get(c);
                if (v > 1) {
                    trees.add(new int[]{v, r, c});
                }
            }
        }

        trees.sort(Comparator.comparingInt(a -> a[0]));

        int ans = 0, sr = 0, sc = 0;
        for (int[] tree : trees) {
            int d = dist(forest, sr, sc, tree[1], tree[2]);
            if (d < 0) {
                return -1;
            }
            ans += d;
            sr = tree[1];
            sc = tree[2];
        }
        return ans;
    }

    /**
     * @param forest 列表
     * @param sr     起始点行号
     * @param sc     起始点列号
     * @param tr     目的地行号
     * @param tc     目的地列号
     * @return 步数
     */
    public int dist(List<List<Integer>> forest, int sr, int sc, int tr, int tc) {
        int rowCount = forest.size();
        int columnCount = forest.get(0).size();
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{sr, sc, 0});
        boolean[][] seen = new boolean[rowCount][columnCount];
        seen[sr][sc] = true;
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            if (cur[0] == tr && cur[1] == tc) {
                return cur[2];
            }
            for (int i = 0; i < 4; i++) {
                int cr = cur[0] + dr[i];
                int cc = cur[1] + dc[i];
                if (cr >= 0 && cc >= 0 && cr < rowCount && cc < columnCount
                        && !seen[cr][cc] && forest.get(cr).get(cc) > 0) {
                    seen[cr][cc] = true;
                    queue.add(new int[]{cr, cc, cur[2] + 1});
                }
            }
        }
        return -1;
    }


    @Test
    public void xx() {
        List<List<Integer>> res = new ArrayList<>();
        res.add(Arrays.asList(81, 95, 36, 103));
        res.add(Arrays.asList(129, 91, 102, 119));
        res.add(Arrays.asList(1, 137, 134, 141));
        res.add(Arrays.asList(125, 34, 69, 71));
        res.add(Arrays.asList(133, 28, 38, 91));
        int i = cutOffTree(res);
        System.out.println(i);
    }
}
