package Interval;

import java.util.TreeMap;

/**
 * 返回同一时间段，被多个事件预订的最大次数
 */
public class LC732_MyCalendar3 {

    TreeMap<Integer, Integer> timeToBookCountMap;

    public LC732_MyCalendar3() {
        timeToBookCountMap = new TreeMap<>();
    }

    public int book(int startTime, int endTime) {

        timeToBookCountMap.put(startTime ,timeToBookCountMap.getOrDefault(startTime ,0) + 1);

        timeToBookCountMap.put(endTime, timeToBookCountMap.getOrDefault(endTime, 0) - 1);
        int maxBookCount = 0;
        int currBookCount = 0;
        for(Integer bookCount : timeToBookCountMap.values()) {
            currBookCount += bookCount;
            maxBookCount = Math.max(currBookCount, maxBookCount);
        }
        return maxBookCount;
    }
}
