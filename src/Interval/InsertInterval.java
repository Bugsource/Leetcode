package Interval;

import java.util.ArrayList;
import java.util.List;


/**
 * 解法一，一个循环，但是需要针对边界条件仔细考虑，形式上统一
 */
public class InsertInterval {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> res = new ArrayList<>();
        int comparedStart = newInterval[0];
        int comparedEnd = newInterval[1];
        for(int[] interval: intervals) {
            if(interval[1] < comparedStart) {
                // 当前区间的终点在插入区间的起点前，则当前区间直接加入结果集
                // comparedStart和comparedEnd维持不变，参与下一迭代比较
                res.add(interval);
            } else {
                // 当前区间的终点在插入区间的起点后
                if(interval[0] > comparedEnd) {
                    // 如果当前区间的起点在插入区间的终点后，说明二者没有交叠；
                    // 先将比较区间加入结果集，然后将下一区间作为比较区间，这样做有助于形式统一
                    res.add(new int[] {comparedStart, comparedEnd});
                    comparedStart = interval[0];
                    comparedEnd = interval[1];
                } else {
                    // 当前区间的起点在插入区间的终点前，结合当前区间的终点在插入区间的起点后，必然有交叠
                    comparedStart = Math.min(interval[0], comparedStart);
                    comparedEnd = Math.max(interval[1], comparedEnd);
                }
            }
        }
        // 最后一次迭代后，比较区间没有加入结果集，这里需要追加
        res.add(new int[] {comparedStart, comparedEnd});
        return res.toArray(new int[res.size()][]);
    }
}

/**
 * 解法二：三步骤，三个循环，比较直观
 */

public int[][] insert2(int[][] intervals, int[] newInterval) {
    List<int[]> result = new ArrayList<>();
    int i = 0;
    // add all the intervals ending before newInterval starts
    while (i < intervals.length && intervals[i][1] < newInterval[0]){
        result.add(intervals[i]);
        i++;
    }

    // merge all overlapping intervals to one considering newInterval
    while (i < intervals.length && intervals[i][0] <= newInterval[1]) {
        // we could mutate newInterval here also
        newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
        newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
        i++;
    }

    // add the union of intervals we got
    result.add(newInterval);

    // add all the rest
    while (i < intervals.length){
        result.add(intervals[i]);
        i++;
    }

    return result.toArray(new int[result.size()][]);
}
}
