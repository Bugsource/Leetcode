import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MinMeetingRooms {

    public class Interval {
        int start, end;

        Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    /**
     * 扫描线算法，核心在于对区间排序，遇到起点则cost+1，遇到终点则cost-1，代表一间会议室可以关闭
      * @param intervals
     * @return
     */
    public int minMeetingRooms(List<Interval> intervals) {
        TreeMap<Integer, Integer> timeToCostMap = new TreeMap<>();
        for(Interval interval: intervals) {
            Integer startTime = interval.start;
            Integer endTime = interval.end;
            timeToCostMap.put(startTime, timeToCostMap.getOrDefault(startTime, 0) + 1);
            timeToCostMap.put(endTime, timeToCostMap.getOrDefault(endTime, 0) - 1);
        }
        int res = 0;
        Integer currentRoomCount = 0;
        for(Map.Entry<Integer, Integer> entry: timeToCostMap.entrySet()) {
            currentRoomCount += entry.getValue();
            res = Math.max(res, currentRoomCount);
        }
        return res;
    }
}
