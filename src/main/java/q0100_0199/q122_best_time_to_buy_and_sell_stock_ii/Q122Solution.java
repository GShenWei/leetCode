package q0100_0199.q122_best_time_to_buy_and_sell_stock_ii;

import org.junit.Test;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @date 2019-08-19
 */
public class Q122Solution {
    public int maxProfit(int[] prices) {
        if (prices.length < 3) {
            if (prices.length == 2) {
                if (prices[1] - prices[0] > 0) {
                    return prices[1] - prices[0];
                }
            }
            return 0;
        }
        int res = 0;
        boolean buy = false;
        for (int i = 0; i < prices.length; i++) {
            if (!buy && i < prices.length - 1 && prices[i] < prices[i + 1]) {
                res = res - prices[i];
                buy = true;
            } else if (buy && (i == prices.length - 1 || prices[i] > prices[i + 1])){
                res = res + prices[i];
                buy = false;
            }
        }
        return res;
    }

    @Test
    public void xx() {
        int a[] = {2, 2, 5};
        System.out.println(maxProfit(a));
    }
}
