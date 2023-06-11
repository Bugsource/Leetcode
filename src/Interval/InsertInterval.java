package Interval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


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

    public static int[][] insertIntervalByTreeMap (int[][] intervals, int[] newInterval) {
        TreeMap<Integer, Integer> startToEndMap = new TreeMap<>();
        for(int[] interval : intervals) {
            startToEndMap.put(interval[0], interval[1]);
        }
        int mergedStart = newInterval[0];
        int mergedEnd = newInterval[1];
        Map.Entry<Integer, Integer> leftMayOverlapEntry = startToEndMap.floorEntry(mergedStart);
        Map.Entry<Integer, Integer> rightMayOverlapEntry = startToEndMap.floorEntry(mergedEnd);

        if(leftMayOverlapEntry != null && leftMayOverlapEntry.getValue() >= mergedStart) {
            // 存在前半段交集，则将start向前延伸，代表合并
            mergedStart = leftMayOverlapEntry.getKey();
        }
        if(rightMayOverlapEntry != null && rightMayOverlapEntry.getValue() >= mergedEnd) {
            // 存在后半段交集，则将end向后延伸，代表合并
            mergedEnd = rightMayOverlapEntry.getValue();
        }
        // 先删除合并后区间内所有entry，包括合并起点和合并终点，之所以先删除，是遇到了input2这种case，存在start和end相同的区间，先插入区间再删除多余区间会漏删除
        startToEndMap.subMap(mergedStart, true, mergedEnd ,true).clear();
        // 再插入合并区间
        startToEndMap.put(mergedStart, mergedEnd);

        int[][] res = new int[startToEndMap.size()][2];
        int index = 0;
        for(Map.Entry<Integer, Integer> entry : startToEndMap.entrySet()) {
            res[index][0] = entry.getKey();
            res[index][1] = entry.getValue();
            ++ index;
        }
        return res;
    }

    public static void main(String[] a) {
        int[][] input1 = {
                {1,2},{3,5},{6,7},{8,10},{12,16}
        };
        int[] newInterval = {4,8};

        int[][] res = insertIntervalByTreeMap(input1, newInterval);

        System.out.println("output1:" + Arrays.deepToString(res));

        int[][] input2 = {
                {7,10}, {13,13}, {17,17}, {26,29}
        };
        int[] newInterval2 = {13,17};
        int[][] res2 = insertIntervalByTreeMap(input2, newInterval2);
        System.out.println("output2:" + Arrays.deepToString(res2));

    }
}
