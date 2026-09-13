package dp;

import static util.Util.assertEquals;

/**
 * 在HouseRobber1基础上，限制是第一户和最后一户是连接的，也就是环状的。
 * <p>
 * 关键思路在于，如何将环拆开成线性的，转化成之前的问题。
 * 既然第一间和最后一间不能同时偷，那么只有以下三种情况：
 * <li>偷头不偷尾</li>
 * <li>偷尾不偷头</li>
 * <li>不偷头也不偷尾</li>
 * <p>
 * 最后一种情况，其实被dp[0 -> i-2]和dp[1 -> i-1]包含
 */
public class LC213_HouseRobber2 {

    public static int rob(int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        int n = nums.length;
        if(n == 1) {
            return nums[0];
        }
        int excludeTail = robLinear(nums, 0, n - 2);
        int excludeHead = robLinear(nums, 1, n - 1);
        return Math.max(excludeHead, excludeTail);
    }
    public static int robLinear(int[] nums, int low, int high) {
        if(low > high) {
            return 0;
        }

        // 过程中只需要维护两个状态。类似双指针。顺序是prev2,prev1,nums[i]
        int prev2 = 0;
        int prev1 = 0;
        for(int i = low; i <= high; ++ i) {
            int currSelected = nums[i] + prev2;
            int currNotSelected = prev1;
            int curr = Math.max(currSelected,currNotSelected);
            prev2 = prev1;
            prev1 = curr;
        }

        return prev1;
    }

    public static void main(String[] args) {
        int[] nums1 = {2,3,2};
        assertEquals(3, rob(nums1));
        int[] nums2 = {1,2,3,1};
        assertEquals(4, rob(nums2));
        int[] nums3 = {1,2,3};
        assertEquals(3, rob(nums3));
        int[] nums4 = {1};
        assertEquals(1, rob(nums4));
        int[] nums5 = {2,1};
        assertEquals(2, rob(nums5));
    }
}
