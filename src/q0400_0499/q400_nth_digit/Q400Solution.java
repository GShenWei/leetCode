package q0400_0499.q400_nth_digit;

import org.junit.Test;

public class Q400Solution {
    public void ss(int n) {
        int nLength = (n + "").length();
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < nLength; i++) {
            sb.append(1);
        }
        sb.append(0);
        String s = sb.toString();
        System.out.println(s);

        int floor = 0;
        for (int i = 1, c = 10; i < nLength; i++, c *= 10) {
            floor += i * c ;
        }
        System.out.println(floor);
    }

    @Test
    public void x() {
        ss(123);
    }

}
