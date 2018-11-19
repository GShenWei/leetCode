package q0000_0099.q12_整数转罗马数字;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/8/2
 */
public class Q12Solution {


    /**
     * 输入确保在
     *
     * @param num 1 到 3999 的范围内
     * @return
     */
    public String intToRoman(int num) {
        StringBuilder sb = new StringBuilder();
        //从个位到高位
        List<String[]> types = new ArrayList<>();
        types.add(new String[]{"I", "V", "X"});
        types.add(new String[]{"X", "L", "C"});
        types.add(new String[]{"C", "D", "M"});
        types.add(new String[]{"M", "_", "_"});
        String numStr = num + "";
        List<Character> chars = new ArrayList<>();
        char[] charArray = numStr.toCharArray();
        //从高位到低位
        for (int i = charArray.length - 1; i >= 0; i--) {
            chars.add(charArray[i]);
        }
        for (int i = chars.size() - 1; i >= 0; i--) {
            Character chr = chars.get(i);
            int n = chr - '0';
            sb.append(resolve(n, types.get(i)));
        }
        return sb.toString();
    }

    private String resolve(int num, String[] type) {
        StringBuilder sb = new StringBuilder();
        if (num == 9) {
            sb.append(type[0]).append(type[2]);
        } else if (num == 4) {
            sb.append(type[0]).append(type[1]);
        } else if (num == 5) {
            sb.append(type[1]);
        } else if (num < 4) {
            for (int i = 0; i < num; i++) {
                sb.append(type[0]);
            }
        } else {
            sb.append(type[1]);
            num = num-5;
            for (int i = 0; i < num; i++) {
                sb.append(type[0]);
            }
        }
        return sb.toString();
    }

    @Test
    public void xx() {
        String s = intToRoman(58);
        System.out.println(s);
        s = intToRoman(1994);
        System.out.println(s);
        s = intToRoman(3);
        System.out.println(s);
        s = intToRoman(4);
        System.out.println(s);
    }

}
