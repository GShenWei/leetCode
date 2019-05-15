package q0000_0099.q56_merge_intervals;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/12/3
 */

//合并相邻的线段
public class Q56Solution {
    public class Interval {
        int start;
        int end;

        Interval() {
            start = 0;
            end = 0;
        }

        Interval(int s, int e) {
            start = s;
            end = e;
        }
    }

    public List<Interval> merge(List<Interval> intervals) {
        //intervals.sort(Comparator.comparingInt(a -> a.start));
        intervals.sort(new Comparator<Interval>() {
            @Override
            public int compare(Interval a, Interval b) {
                return a.start < b.start ? -1 : a.start == b.start ? 0 : 1;
            }
        });
        LinkedList<Interval> merged = new LinkedList<>();
        for (Interval interval : intervals) {
            if (merged.isEmpty() || merged.getLast().end < interval.start) {
                merged.add(interval);
            } else {
                merged.getLast().end = Math.max(merged.getLast().end, interval.end);
            }
        }
        return merged;
    }

    @Test
    public void xx() {
        int[][] nums = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        List<Interval> list = create(nums);
        List<Interval> merge = merge(list);
        for (Interval l : merge) {
            System.out.println(l.start + "," + l.end);
        }
    }

    private List<Interval> create(int[][] nums) {
        List<Interval> res = new ArrayList<>();
        for (int[] n : nums) {
            res.add(new Interval(n[0], n[1]));
        }
        return res;
    }

}
