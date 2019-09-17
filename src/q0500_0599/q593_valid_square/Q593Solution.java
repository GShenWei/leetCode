package q0500_0599.q593_valid_square;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Q593Solution {
    public boolean validSquare(int[] p1, int[] p2, int[] p3, int[] p4) {
        //一共有6条变，只能有两种长度，而且小边a与大边b的关系为 2*a*a = b*b
        Map<Integer, Integer> m = new HashMap<>();
        List<Integer> length2s = new ArrayList<>();
        length2s.add(getLength2(p1, p2));
        length2s.add(getLength2(p1, p3));
        length2s.add(getLength2(p1, p4));
        length2s.add(getLength2(p2, p3));
        length2s.add(getLength2(p2, p4));
        length2s.add(getLength2(p3, p4));
        for (Integer length2 : length2s) {
            Integer c = m.get(length2);
            if (c == null) {
                c = 1;
            } else {
                c++;
            }
            m.put(length2, c);
        }
        if (m.size() == 2) {
            ArrayList<Integer> integers = new ArrayList<>(m.keySet());
            int x = integers.get(0);
            int y = integers.get(1);
            int max = Math.max(x, y);
            int min = Math.min(x, y);
            return max == min * 2;
        }
        return false;
    }


    private int getLength2(int[] p, int[] q) {
        int x = p[0] - q[0];
        int y = p[1] - q[1];
        return x * x + y * y;
    }

    @Test
    public void xx() {
        int[] p1 = {0,0};
        int[] p2 = {1,1};
        int[] p3 = {1,0};
        int[] p4 = {0,1};
        System.out.println(validSquare(p1, p2, p3, p4));
    }

}
