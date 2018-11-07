package q10;

import org.junit.Test;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @name Q10Solution
 * @description
 * @date 2017/6/1
 */
public class Q10Solution {
    //TODO 未解决
    public boolean isMatch(String s, String p) {
        return s.matches(p);
    }

    @Test
    public void oo(){
        boolean aab = isMatch("a", ".a*");
        System.out.println(aab);
    }
}
