package q0000_0099.q93_restore_ip_addresses;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/21
 */
public class Q93Solution {

    private Set<String> res = new HashSet<>();

    public List<String> restoreIpAddresses(String s) {
        if (s == null || s.length() > 12 || s.length() < 4) {
            return new ArrayList<>();
        }
        getRes(s, 1, "");
        return new ArrayList<>(res);
    }

    private void getRes(String s, int index, String alreadyIp) {
        int length = s.length();
        if (index == 4) {
            if (length == 1) {
                res.add(alreadyIp + "." + s);
            } else if (length > 1 && length <= 3) {
                if (s.startsWith("0")) {
                    return;
                } else {
                    if (length == 3) {
                        int i = Integer.parseInt(s);
                        if (i > 255) {
                            return;
                        }
                    }
                    res.add(alreadyIp + "." + s);
                }
            }
            return;
        }
        for (int i = 1; i <= 3 && i <= length; i++) {
            String newPart = s.substring(0, i);
            if (i != 1 && newPart.startsWith("0")) {
                continue;
            }
            int num = Integer.parseInt(newPart);
            if (num > 255) {
                continue;
            }
            String nextStr = s.substring(i);
            if (!"".equals(alreadyIp) && !alreadyIp.endsWith(".")) {
                alreadyIp = alreadyIp + ".";
            }
            getRes(nextStr, index + 1, alreadyIp + newPart);
        }
    }


    @Test
    public void xx() {
        List<String> strings = restoreIpAddresses("25525511135");
        System.out.println(strings);
    }
}
