package dp;

import java.util.Arrays;

public class JumpGame {

    // to be optimized
    public int jump2(int[] nums) {
        if(nums == null) {return 0;}
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for(int i = 1; i < n; ++ i) {

            for(int j = 1; j <= nums[i] && j < n - i; ++ j) {
                dp[i + j] = Math.min(dp[i + j], dp[i] + 1);
            }
        }
        return dp[n-1];
    }
}
