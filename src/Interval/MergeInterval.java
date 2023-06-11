package Interval;

import java.util.*;

/**
 * Given an array of intervals where intervals[i] = [starti, endi], merge all overlapping intervals,
 * and return an array of the non-overlapping intervals that cover all the intervals in the input.
 *
 * Example1:
 * Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
 * Output: [[1,6],[8,10],[15,18]]
 * Explanation: Since intervals [1,3] and [2,6] overlap, merge them into [1,6].
 *
 * Example2:
 * Input: intervals = [[1,4],[4,5]]
 * Output: [[1,5]]
 * Explanation: Intervals [1,4] and [4,5] are considered overlapping.
 *
 * Constraints:
 * 1 <= intervals.length <= 104
 * intervals[i].length == 2
 * 0 <= starti <= endi <= 10
 */
public class MergeInterval {

    public static int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(o -> o[0]));

        int comparedStart = intervals[0][0];
        int comparedEnd = intervals[0][1];
        List<int[]> res = new ArrayList<>();
        for(int[] interval : intervals) {
            if(interval[0] > comparedEnd) {
                // 当前区间和之前区间不重叠，更新currStart为当前start，作为下次迭代的比较基准
                // 添加当前合并区间到结果集
                res.add(new int[]{comparedStart, comparedEnd});

                comparedStart = interval[0];
                comparedEnd = interval[1];
            }else {
                // 当前区间和上一区间有重叠，合并后的终点取二者大值
                comparedEnd = Math.max(comparedEnd, interval[1]);
            }
        }
        // 处理最后一次迭代的遗漏
        res.add(new int[]{comparedStart, comparedEnd});

        return res.toArray(new int[res.size()][]);
    }

    public int[][] merge2(int[][] intervals) {
        if(intervals.length == 0 || intervals[0].length == 0){return intervals;}
        Arrays.sort(intervals, (i1, i2) -> Integer.compare(i1[0], i2[0]));
        List<int[]> result = new LinkedList<>();
        int[] currMerge = new int[]{intervals[0][0],intervals[0][1]};
        result.add(currMerge);
        for(int[] interval : intervals){
            if(interval[0] <= currMerge[1]){//以当前区间的end作为是否有交叠的判据，有交叠，则尝试更新end
                currMerge[1] = Math.max(currMerge[1], interval[1]);
            }else{
                currMerge = new int[]{interval[0],interval[1]};
                result.add(currMerge);
            }
        }
        return result.toArray(new int[result.size()][]);
    }

    public int[][] merge3(int[][] intervals) {
        TreeMap<Integer, Integer> startToEndTree = new TreeMap<>();

        TreeMap<Integer, Integer> endToStartTree = new TreeMap<>();

        for(int[] interval : intervals) {
            int start = interval[0];
            int end = interval[1];
            // 寻找和当前线段交叠的最近的终点：离当前起点最近的，且比该起点大的终点
            Map.Entry<Integer, Integer> endOverlapNearest = endToStartTree.ceilingEntry(start);
            // 寻找和当前线段交叠的最近的起点：离当前终点最近的，且比该终点小的起点
            Map.Entry<Integer, Integer> startOverlapNearest = startToEndTree.floorEntry(end);

            if(endOverlapNearest == null && startOverlapNearest == null) {
                // 找不到任何交叠对象，直接插入该线段
                startToEndTree.put(start, end);
                endToStartTree.put(end, start);
            } else if(endOverlapNearest == null) {
                // 找不到当前线段的前半段交叠对象，但是找到了后半段交叠对象，则将后半段交叠对象合并

            }
        }
        return intervals;
    }
    public static void main(String[] a) {
        int[][] input1 = {
            {1,3}, {2,6}, {8,10}, {15,18}
        };
        System.out.println("res1:" + merge(input1));

    }
}
