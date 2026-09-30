package Stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 给你一个数组 prices ，其中 prices[i] 是商店里第 i 件商品的价格。
 * <p>
 * 商店里正在进行促销活动，如果你要买第 i 件商品，那么你可以得到与 prices[j] 相等的折扣，
 * 其中 j 是满足 j > i 且 prices[j] <= prices[i] 的 最小下标 ，如果没有满足条件的 j ，你将没有任何折扣。
 * <p>
 * 请你返回一个数组，数组中第 i 个元素是折扣后你购买商品 i 最终需要支付的价格。
 * <p>
 * 示例 1：
 * <p>
 * 输入：prices = [8,4,6,2,3]
 * 输出：[4,2,4,2,3]
 * 解释：
 * 商品 0 的价格为 price[0]=8 ，你将得到 prices[1]=4 的折扣，所以最终价格为 8 - 4 = 4 。
 * 商品 1 的价格为 price[1]=4 ，你将得到 prices[3]=2 的折扣，所以最终价格为 4 - 2 = 2 。
 * 商品 2 的价格为 price[2]=6 ，你将得到 prices[3]=2 的折扣，所以最终价格为 6 - 2 = 4 。
 * 商品 3 和 4 都没有折扣。
 */

/**
 * 本题和dailyTemperatures本质一样，只是换壳。
 * <p>
 * 具体的，我们可以从前往后处理所有的 nums[i]，使用某类容器装载我们所有的「待打折」的商品下标。假设当前处理到的是 nums[i]：
 * <p>
 * 若其比容器内的任意商品价格要高，其必然不能更新任何一个待打折商品的价格，
 * 将其也加入容器尾部（此时我们发现，若有一个新的商品加入容器，其必然是当前所有待打折商品中的价格最高的，即容器内的商品价格单调递增）；
 * <p>
 * 若其价格不高于容器内的商品价格，其能够更新容器内待打折的商品价格，
 * 并且由于我们容器满足单调递增特性，我们必然能够从尾部开始取出待打折商品来进行更新，直到处理完成或遇到第一个无法更新价格的商品。
 */
public class LC1475_FinalCommodityPrices {
    public int[] finalPrices(int[] prices) {
        int length = prices.length;
        if(length == 0) {
            return null;
        }
        int[] res = new int[length];

        Deque<Integer> stack = new ArrayDeque<>();

        for(int i = 0; i < length; ++ i) {
            // 先赋初值，如果找不到比它小的价格，那就不打折
            res[i] = prices[i];
            int currPrice = prices[i];
            // 维护的是一个单调递增的栈；因为出现一个新值，就会把大于它的出栈，直到栈里的都小于该值
            while(!stack.isEmpty() && prices[stack.peek()] >= currPrice) {
                int peekIndex = stack.pop();
                res[peekIndex] = prices[peekIndex] - currPrice;
            }
            stack.push(i);
        }
        return res;
    }
}
