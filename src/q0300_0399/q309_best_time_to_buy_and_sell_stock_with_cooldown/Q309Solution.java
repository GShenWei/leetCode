package q0300_0399.q309_best_time_to_buy_and_sell_stock_with_cooldown;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/29
 */
public class Q309Solution {
    private int[] prices;
    private Map<String, int[]> resMap;

    public int maxProfit(int[] prices) {
        this.prices = prices;
        this.resMap = new HashMap<>();
        int[] cal = cal(0, 0);
        return Math.max(cal[0], cal[1]);
    }

    private int[] cal(int index, int day) {
        //1代表买入
        //0代表等待
        //2代表出售
        int[] res = new int[3];
        if (index == prices.length) {
            return res;
        }
        if (resMap.containsKey(index + "" + day)) {
            return resMap.get(index + "" + day);
        }
        int[] cal;
        //如果持有天数超过0天，那么可以选择等待和出售
        if (day == 1) {
            //如果出售的话，下面只能是等待
            cal = cal(index + 1, -1);
            res[2] = prices[index] + cal[0];

            //如果等待的话，下面可以出售和购买和等待
            cal = cal(index + 1, 1);
            res[0] = Math.max(cal[0], Math.max(cal[2], cal[1]));
        } else if (day == -1) {
            //说明已经出售过，只能等待，下面可以继续等待和购买
            cal = cal(index + 1, 0);
            res[0] = Math.max(cal[0], cal[1]);
        } else if (day == 0) {
            //购买，下面可以出售和等待
            cal = cal(index + 1, 1);
            res[1] = Math.max(cal[0], cal[2]) - prices[index];

            //等待，下面可以等待和购买
            cal = cal(index + 1, 0);
            res[0] = Math.max(cal[1], cal[0]);
        }
        //System.out.println(Arrays.toString(res));
        resMap.put(index + "" + day, res);
        return res;
    }
    public int maxProfit2(int[] prices) {
        int sell = 0, prev_sell = 0, buy = Integer.MIN_VALUE, prev_buy;
        for (int price : prices) {
            prev_buy = buy;
            buy = Math.max(prev_sell - price, prev_buy);
            prev_sell = sell;
            sell = Math.max(prev_buy + price, prev_sell);
        }
        return sell;
    }


    @Test
    public void xx() {
        int[] nums = {1, 2, 3, 0, 2, 100};
        int i = maxProfit(nums);
        int i2 = maxProfit2(nums);
        System.out.println(i);
        System.out.println(i2);
    }
}
