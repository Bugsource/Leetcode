package dp;

import util.Util;

import static util.Util.assertEquals;

/**
 * You are a professional robber planning to rob houses along a street.
 * Each house has a certain amount of money stashed, the only constraint stopping you from robbing each of them is that adjacent houses have security systems connected and it will automatically contact the police if two adjacent houses were broken into on the same night.
 * <p>
 * Given an integer array nums representing the amount of money of each house, return the maximum amount of money you can rob tonight without alerting the police.
 * <p>
 * <p>
 * Example 1:
 * <p>
 * Input: nums = [1,2,3,1]
 * <p>
 * Output: 4
 * <p>
 * Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
 * <p>
 * Total amount you can rob = 1 + 3 = 4.
 * <p>
 * Example 2:
 * <p>
 * Input: nums = [2,7,9,3,1]
 * <p>
 * Output: 12
 * <p>
 * Explanation: Rob house 1 (money = 2), rob house 3 (money = 9) and rob house 5 (money = 1).
 * <p>
 * Total amount you can rob = 2 + 9 + 1 = 12.
 */
public class LC198_HouseRobber {
    public static int robV0(int[] nums) {
        if(nums == null) {
            return 0;
        }
        int len = nums.length;
        if(len == 0) {
            return 0;
        }
        // dp数组，维护的是，到第i-1房子，能够获得的最大值
        int[] dp = new int[len];
        dp[0] = nums[0];
        if(len > 1) {
            dp[1] = Math.max(nums[0], nums[1]);
        }
        for(int i = 2; i < len; ++ i) {
            int currSelected = nums[i] + dp[i-2];
            int currNotSelected = dp[i-1];
            dp[i] = Math.max(currSelected,currNotSelected);
        }

        return dp[len-1];
    }

    public static int robV1(int[] nums) {
        if(nums == null) {
            return 0;
        }
        int len = nums.length;
        if(len == 0) {
            return 0;
        }
        // dp数组，维护的是第i间房子为终点，能够获得的最大值
        int[] dp = new int[len + 1];
        dp[0] = 0;
        dp[1] = nums[0];
        for(int i = 1; i < len; ++ i) {
            int currSelected = nums[i] + dp[i-1];
            int currNotSelected = dp[i];
            dp[i+1] = Math.max(currSelected,currNotSelected);
        }

        return dp[len];
    }

    public static int rob(int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }

        // 过程中只需要维护两个状态。类似双指针。顺序是prev2,prev1,nums[i]
        int prev2 = 0;
        int prev1 = 0;
        for(int num : nums) {
            int currSelected = num + prev2;
            int currNotSelected = prev1;
            int currRes = Math.max(currSelected,currNotSelected);
            prev2 = prev1;
            prev1 = currRes;
        }

        return prev1;
    }

    public static void main(String[] args) {
        int[] nums1 = {1};
        assertEquals(1, rob(nums1));
        int[] nums2 = {1,2};
        assertEquals(2, rob(nums2));
        int[] nums3 = {1,2,3,1};
        assertEquals(4, rob(nums3));
        int[] nums4 = {2,7,9,3,1};
        assertEquals(12, rob(nums4));
        int[] nums5 = {2,1,1,2};
        assertEquals(4, rob(nums5));
    }
}
