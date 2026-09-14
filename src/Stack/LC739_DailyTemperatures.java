package Stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import static util.Util.assertArrayEquals;
import static util.Util.assertEquals;

/**
 * Given an array of integers temperatures represents the daily temperatures,
 * return an array answer such that answer[i] is the number of days you have to wait after the ith day to get a warmer temperature.
 * If there is no future day for which this is possible, keep answer[i] == 0 instead.
 */
public class LC739_DailyTemperatures {

    // 目标是为每一天，找到更高温度的下一天，没有的话结果是0
    // 使用单调栈，维护当前仍没有找到下一个更高温的日期
    public static int[] dailyTemperatures(int[] temperatures) {
        int length = temperatures.length;
        if(length == 0) {
            return null;
        }
        int[] res = new int[length];

        // 关键点，栈里维护的是下标，而不是温度值；
        // 只存温度，不知道是哪天的，除非搞个实体类，把温度和下标都包进去
        Deque<Integer> deque = new ArrayDeque<>();
        for(int i = 0; i < length; ++ i) {
            int currTemperature = temperatures[i];
            while(!deque.isEmpty()) {

                int peekIndex = deque.peek();
                if(temperatures[peekIndex] < currTemperature) {
                    res[peekIndex] = i - peekIndex;
                    deque.pop();
                } else {
                    // 当前温度不大于栈顶，则直接跳出，外面统一入栈
                    break;
                }
            }
            deque.push(i);
        }
        return res;
    }

    public static void main(String[] args) {
        int[] nums1 = {73,74,75,71,69,72,76,73};
        int[] res1 = {1,1,4,2,1,1,0,0};
        assertArrayEquals(res1, dailyTemperatures(nums1));
        int[] nums2 = {30,40,50,60};
        int[] res2 = {1,1,1,0};
        assertArrayEquals(res2, dailyTemperatures(nums2));

        int[] nums3 = {30,60,90};
        int[] res3 = {1,1,0};
        assertArrayEquals(res3, dailyTemperatures(nums3));

        int[] nums4 = {30};
        int[] res4 = {0};
        assertArrayEquals(res4, dailyTemperatures(nums4));
    }
}
