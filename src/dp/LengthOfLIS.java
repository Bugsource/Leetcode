package dp;

import java.util.Arrays;

public class LengthOfLIS {
    /**
     * do it again with Binary search based algorithm whose time complexity is O(nlogn)
     * @param nums
     * @return
     */
    public int lengthOfLIS(int[] nums) {
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
        LengthOfLIS l = new LengthOfLIS();

        int[] input = new int[]{10, 9, 2, 5, 3, 7, 101, 18};
        int res1 = l.lengthOfLIS(input);
        System.out.println("res1:" + res1);

        int[] input2 = new int[]{1};
        System.out.println("res2:" + l.lengthOfLIS(input2));
    }
}
