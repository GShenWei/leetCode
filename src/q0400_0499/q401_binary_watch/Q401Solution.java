package q0400_0499.q401_binary_watch;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.text.DecimalFormat;
import java.util.Set;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/22
 */
public class Q401Solution {
    private int[] minutes = {1, 2, 4, 8, 16, 32};
    private int[] hours = {1, 2, 4, 8};
    private boolean[] mUsed = new boolean[minutes.length];
    private boolean[] hUsed = new boolean[hours.length];

   /* private Set<Integer> hourGroup = new HashSet();
    private Set<Integer> minutesGroup = new HashSet<>();*/

    private List<Integer> hourGroup = new ArrayList<>();
    private List<Integer> minutesGroup = new ArrayList<>();

    public List<String> readBinaryWatch(int num) {
        List<String> res = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("00");
        for (int i = 0; i <= num; i++) {
            findHour(0, 0, i, 0);
            findMinute(0, 0, num - i, 0);
            for (Integer hour : hourGroup) {
                for (Integer minute : minutesGroup) {
                    res.add(hour + ":" + df.format(minute));
                }
            }
            hourGroup.clear();
            minutesGroup.clear();
        }

        return res;
    }

    private void findHour(int start, int index, int total, int already) {
        if (index == total) {
            hourGroup.add(already);
            return;
        }
        for (int i = start; i < hours.length; i++) {
            if (!hUsed[i]) {
                already = already + hours[i];
                if (already < 12) {
                    hUsed[i] = true;
                    findHour(i, index + 1, total, already);
                    hUsed[i] = false;
                } else {
                    break;
                }
                already = already - hours[i];
            }
        }
    }

    private void findMinute(int start, int index, int total, int already) {
        if (index == total) {
            minutesGroup.add(already);
            return;
        }
        for (int i = start; i < minutes.length; i++) {
            if (!mUsed[i]) {
                already = already + minutes[i];
                if (already < 60) {
                    mUsed[i] = true;
                    findMinute(i, index + 1, total, already);
                    mUsed[i] = false;
                } else {
                    break;
                }
                already = already - minutes[i];
            }
        }
    }

    @Test
    public void xx() {
        List<String> strings = readBinaryWatch(4);
        System.out.println(strings);

    }
}
