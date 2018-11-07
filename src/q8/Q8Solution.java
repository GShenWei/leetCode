package q8;

import org.junit.Test;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @name Q8Solution
 * @description
 * @date 2017/5/27
 */
public class Q8Solution {
    //TODO 未解决
    public int myAtoi(String str) {
        str=str.trim();
        boolean isFu = false;
        for(int i=0;i<str.length();i++){
            if(str.charAt(i)=='-'){
                isFu = !isFu;
            }
        }
        str = str.replaceAll("\\D+","P");
        if(isFu){
            str = "-0"+str;
        }
        if (str.length() != 0) {
            if (str.contains(".")) {
                if (str.replaceAll("\\.", "").length() == 0) {
                    return 0;
                }
                str = str.substring(0, str.indexOf("."));
            }
            try {
                return Integer.parseInt(str);
            }catch (Exception e){
                return 0;
            }
        } else {
            return 0;
        }
    }

    @Test
    public void oo() {
        int i = myAtoi("-++--09fdfgffgh435435345435d99");
        System.out.println(i);
    }
}
