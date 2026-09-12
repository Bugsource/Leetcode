package TwoPointer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]] such that i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0.
 * <p>
 * Notice that the solution set must not contain duplicate triplets.
 */
public class LC15_ThreeSum {

    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        int len = nums.length;
        List<List<Integer>> solutions = new ArrayList<>();
        for(int i = 0; i < len-2; i ++){
            if(i > 0 && nums[i] == nums[i-1]) {
                // 如果i端点和前面重复，那之前已经搜索过了，跳过
                continue;
            }
            if(nums[i] > 0) {
                // 最小的都大于0，那么右边的数肯定都大于0，加起来不可能等于0
                break;
            }
            int target = -nums[i];
            int low = i + 1, high = len -1;
            while(low < high) {
                int temp = nums[low] + nums[high];
                if(temp < target) {
                    ++ low;
                } else if(temp > target) {
                    -- high;
                } else {
                    solutions.add(List.of(nums[i], nums[low], nums[high]));
                    ++ low;
                    -- high;
                    // 这个是在i确定了之后，双指标扫描后面的区间时，跳过重复的数字；和前面跳过重复i，是两个场景
                    while(low < high && nums[low] == nums[low-1]) {++low;}
                    while(low < high && nums[high] == nums[high+1]) {--high;}
                }
            }
        }
        return solutions;
    }

}
