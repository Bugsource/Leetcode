package Interval;

import java.util.Map;
import java.util.TreeMap;

public class FindRightInterval {

    public int[] findRightInterval(int[][] intervals) {
        // 注意，只有TreeMap有ceilingEntry接口，Map没有
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        for(int i = 0; i < intervals.length; ++ i) {
            // 按照起点排序，并且方便按起点直接查找
            treeMap.put(intervals[i][0], i);
        }
        int[] res = new int[intervals.length];
        for(int i = 0; i < intervals.length; ++ i) {
            // 查找比终点大的最小值
            Map.Entry<Integer, Integer> ceilingEntry = treeMap.ceilingEntry(intervals[i][1]);

            res[i] = ceilingEntry == null ? -1 : ceilingEntry.getValue();
        }
        return  res;
    }
}
