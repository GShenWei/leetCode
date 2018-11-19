package q0000_0099.q7;

import org.junit.Test;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @name Q7Solution
 * @description
 * @date 2017/5/27
 */
public class Q7Solution {
    public int reverse(int x) {
        boolean isZ;
        StringBuffer s;
        if (x >= 0) {
            isZ = true;
            s = new StringBuffer(x + "");
        } else {
            isZ = false;
            s = new StringBuffer(-x + "");
        }
        String result = s.reverse().toString();
        if(!isZ){
            result = "-"+result;
        }
        int re = 0;
        try {
            re = Integer.parseInt(result);
        }catch (Exception e){

        }
        return re;
    }
    @Test
    public void oo(){
        int re = reverse(1534236469);
        System.out.println(re);
    }
}
