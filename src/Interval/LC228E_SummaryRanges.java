package Interval;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个  无重复元素 的 有序 整数数组 nums。区间 [a,b] 是从 a 到 b（包含）的所有整数的集合。
 * <p>
 * 返回 恰好覆盖数组中所有数字 的 最小有序 区间范围列表 。也就是说，nums 的每个元素都恰好被某个区间范围所覆盖，并且不存在属于某个区间但不属于 nums 的数字 x 。
 * <p>
 * 列表中的每个区间范围 [a,b] 应该按如下格式输出：
 * <p>
 * "a->b" ，如果 a != b
 * "a" ，如果 a == b
 * <p>
 * 示例 1：
 * 输入：nums = [0,1,2,4,5,7]
 * 输出：["0->2","4->5","7"]
 */
public class LC228E_SummaryRanges {
    // 单循环写法
    public static List<String> summaryRanges(int[] nums) {
        List<String> res = new ArrayList<>();
        if(nums == null || nums.length == 0){
            return res;
        }
        int rangeStart = nums[0], rangeEnd = nums[0];
        for(int i = 1; i < nums.length; ++ i){

            if(nums[i] == nums[i-1] + 1) {
                rangeEnd = nums[i];
            }
            else {
                // 开始下一个区间，先把上一个区间加入结果集
                res.add(format(rangeStart, rangeEnd));

                // 最后一个区间，需要额外处理
                rangeStart = nums[i];
                rangeEnd = nums[i];
            }
        }
        // 处理最后一次迭代
        res.add(format(rangeStart, rangeEnd));
        return res;
    }

    public static List<String> summaryRanges1(int[] nums) {
        List<String> res = new ArrayList<>();
        if(nums == null || nums.length == 0){
            return res;
        }
        for(int i = 0; i < nums.length; ++ i){
            int rangeStart = nums[i];

            // 找到当前区间终点；
            // 包括遇到数组边界的情况，所以无需针对最后一个迭代特殊处理
            while(i + 1 < nums.length && nums[i+1] == nums[i] + 1) {
                ++i;
            }

            res.add(format(rangeStart, nums[i]));
        }
        return res;
    }

    private static String format(int rangeStart, int rangeEnd) {
        if(rangeStart == rangeEnd) {
            return String.valueOf(rangeStart);
        } else {
            return rangeStart + "->" + rangeEnd;
        }
    }

    public static void main(String[] a) {
        int[] input1 = new int[]{0,1,2,4,5,7};
        System.out.println("res1:" + summaryRanges1(input1));

        int[] input2 = new int[]{0,2,3,4,6,8,9};
        System.out.println("res2:" + summaryRanges1(input2));
    }
}
