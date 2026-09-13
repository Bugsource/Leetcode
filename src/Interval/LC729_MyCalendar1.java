package Interval;

import java.util.Map;
import java.util.TreeMap;

/**
 * 预订时间段不能有交集。区间是左闭右开。
 */
public class LC729_MyCalendar1 {
    TreeMap<Integer, Integer> startToEndMap;
    public LC729_MyCalendar1() {
        startToEndMap = new TreeMap<>();
    }

    public boolean book(int start, int end) {
        // 区间是左闭右开，所以选择终点的lower（不包括该点）
        Map.Entry<Integer, Integer> lowerEntryOfEnd = startToEndMap.lowerEntry(end);

        if(lowerEntryOfEnd != null && lowerEntryOfEnd.getValue() > start) {
            return false;
        }

        startToEndMap.put(start, end);
        return true;
    }

    public static void main(String[] a) {
        LC729_MyCalendar1 myCalendarOne = new LC729_MyCalendar1();

//        [[],[47,50],[33,41],[39,45],[33,42],[25,32],[26,35],[19,25],[3,8],[8,13],[18,27]]
        int[][] input1 = {
            {47,50}, {33,41}, {39,45}, {33,42}, {25,32}, {26,35}, {19,25}, {3,8}, {8,13}, {18,27}
        };
        for(int[] input : input1) {
            System.out.println("bookRes:" + myCalendarOne.book(input[0], input[1]));
        }

    }
}
