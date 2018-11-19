package q0100_0199.q127_word_ladder;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/13
 */
public class Q127Solution {
    class Pair {
        String word;
        int level;

        public Pair(String word, int level) {
            this.word = word;
            this.level = level;
        }
    }

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {

        LinkedList<Pair> queue = new LinkedList<>();
        int totalSize = wordList.size();
        int[] visited = new int[totalSize];
        queue.add(new Pair(beginWord, 1));
        while (!queue.isEmpty()) {
            Pair pair = queue.pop();
            String word = pair.word;
            int level = pair.level;
            for (int i = 0; i < wordList.size(); i++) {
                if (visited[i] != 1) {
                    String s = wordList.get(i);
                    int d = 0;
                    for (int k = 0; k < word.length(); k++) {
                        if (word.charAt(k) != s.charAt(k)) {
                            d++;
                            if (d > 1) {
                                break;
                            }
                        }
                    }
                    if (d == 1) {
                        visited[i] = 1;
                        if (endWord.equals(s)) {
                            return level + 1;
                        }
                        queue.add(new Pair(s, level + 1));
                    }
                }
            }

        }
        return 0;
    }

    @Test
    public void xx() {
        List<String> strings = Arrays.asList("hot", "cog");
        String begin = "hit";
        String end = "hot";
        System.out.println(ladderLength(begin, end, strings));
    }
}
