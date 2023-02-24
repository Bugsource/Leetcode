public class BuyAndSellStock_121 {

//    Input: prices = [7,1,5,3,6,4]
//    Output: 5

    public int maxProfit(int[] prices) {
        if(prices==null || prices.length == 0){return 0;}
        int buy = prices[0], maxProfit = 0;
        for(int i = 1; i < prices.length; i ++){
            if(prices[i] < buy){
                // 更新最低买价，此时没必要更新最大收益，因为当前价格是最低的
                buy = prices[i];
            }else{
                maxProfit = Math.max(maxProfit, prices[i]-buy);
            }
        }
        return maxProfit;
    }
}


