package Interval;

import java.util.Map;
import java.util.TreeMap;

public class MyCalendarThree {

    TreeMap<Integer, Integer> timeToBookCountMap;

    public MyCalendarThree() {
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
