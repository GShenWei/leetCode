package q1100_q1199.q1109_corporate_flight_bookings;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author chenwei
 * @version 1.0
 * @date 2019-09-20
 */
public class Q1109Solution {
    public int[] corpFlightBookings(int[][] bookings, int n) {
        int[] res = new int[n];
        for (int[] v : bookings) {
            res[v[0] - 1] += v[2];
            if (v[1] < n) {
                res[v[1]] -= v[2];
            }

        }
        for (int i = 1; i < n; i++) {
            res[i] += res[i - 1];
        }
        return res;
    }

    @Test
    public void go() {
        int[][] b = {{1, 2, 10}, {2, 3, 20}, {2, 5, 25}};
        int[] ints = corpFlightBookings(b, 5);
        System.out.println(Arrays.toString(ints));
    }
}
