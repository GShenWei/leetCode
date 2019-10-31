package q0000_0099.q4;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @name Q4Solution
 * @description
 * @date 2017/5/25
 */
public class Q4Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int len1 = nums1.length;
        int len2 = nums2.length;
        int totalSize = len1 + len2;
        boolean isJI = totalSize % 2 == 1;
        double result = 0;
        List<Integer> li = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (true) {
            if (i < len1 && j < len2) {
                int i1 = nums1[i];
                int j2 = nums2[j];
                if (i1 > j2) {
                    li.add(j2);
                    j++;
                } else if (j2 > i1) {
                    li.add(i1);
                    i++;
                } else {
                    li.add(i1);
                    i++;
                    li.add(j2);
                    j++;
                }
            } else if (i < len1 && j >= len2) {
                int i1 = nums1[i];
                li.add(i1);
                i++;
            } else if (j < len2 && i >= len1) {
                int j2 = nums2[j];
                li.add(j2);
                j++;
            }
            if ((i >= len1 && j >= len2) || li.size() > totalSize / 2.0) {
                if (isJI) {
                    result = li.get((totalSize - 1) / 2);
                } else {
                    result = (li.get(totalSize / 2) + li.get(totalSize / 2 - 1)) / 2.0;
                }
                break;
            }
        }
        return result;
    }

    @Test
    public void oo() {
        int[] a = {1};
        int[] b = {3, 8, 9, 23, 67, 98, 100};
        double medianSortedArrays = findMedianSortedArrays(a, b);
        System.out.println(medianSortedArrays);
    }
}
