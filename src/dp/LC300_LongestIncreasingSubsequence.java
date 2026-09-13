package dp;

import java.util.Arrays;

/**
 * Given an integer array nums, return the length of the longest strictly increasing subsequence.
 * <p>
 * Example 1:
 * <p>
 * Input: nums = [10,9,2,5,3,7,101,18]
 * Output: 4
 * Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4.
 */

/**
 * 核心差异：子序列 vs 子串
 *
 * <li>子串（连续）‌：如果题目求的是“最长连续递增子串”，那么 dp[i] 只取决于 dp[i-1]。因为必须连续，只能接在前一个元素后面。</li>
 * <li>子序列（不连续）‌：LIS 允许跳过中间元素。nums[i] 可以接在‌前面任意一个‌比它小的元素 nums[j] 后面。为了得到以 nums[i] 结尾的最长长度，我们必须遍历所有 j < i，找到那个能让序列最长的 dp[j]。</li>
 * <p>
 * dp[i] 的定义是：‌以 nums[i] 结尾‌的最长递增子序列长度。
 * 为了确定这个长度，我们需要知道：‌在 i 之前，哪个比 nums[i] 小的元素 nums[j]，其对应的最长子序列 dp[j] 最长？
 */
public class LC300_LongestIncreasingSubsequence {
    /**
     * do it again with Binary search based algorithm whose time complexity is O(nlogn)
     * @param nums
     * @return
     */
    public static int lengthOfLIS(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        int maxLength = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < i; ++j) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxLength = Math.max(maxLength, dp[i]);
        }
        return maxLength;
    }

    public static void main(String[] args) {

        int[] input = new int[]{10, 9, 2, 5, 3, 7, 101, 18};
        int res1 = lengthOfLIS(input);
        System.out.println("res1:" + res1);

        int[] input2 = new int[]{1};
        System.out.println("res2:" + lengthOfLIS(input2));
    }
}
