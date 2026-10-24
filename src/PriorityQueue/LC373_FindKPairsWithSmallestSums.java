package PriorityQueue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

// 两个有序数组，输出和是前k小的组合。
// 其实就是合并k个有序链表的变种，关键在于如何维护下一个元素。
public class LC373_FindKPairsWithSmallestSums {

    // 有的人会存数组1和数组2的元素，以及列坐标，但其实不好，因为每次入堆比较，都要计算；
    // 还有的做法，会只存sum和列坐标，行元素通过sum-当前列元素算出来，是个新思路，不过也没必要
    private record PairsInfo(int sum, int row, int column){}
    public static List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> res = new ArrayList<>();
        int columns = nums2.length;
        if(columns == 0) {
            return res;
        }
        PriorityQueue<PairsInfo> pq = new PriorityQueue<>(Comparator.comparingInt(p -> p.sum));

        // 优化点：堆的大小初始控制在 min(nums1.length, k)。
        // 这是个数学层面的优化，推理一下，超过k行的，其实没必要算了，一定会大于解
        // 终极优化，是判断两个数组的长短，用短一点的入堆，懒得写了
        int limit = Math.min(k, nums1.length);
        for (int i = 0; i < limit; ++ i) {
            PairsInfo pairsInfo = new PairsInfo(nums1[i] + nums2[0], i, 0);
            pq.add(pairsInfo);
        }
        while(!pq.isEmpty() && k > 0) {
            PairsInfo min = pq.poll();
            -- k;// 直接用 k 计数，更简洁

            int currRow = min.row;
            int currColumn = min.column;
            res.add(List.of(nums1[currRow], nums2[currColumn]));

            // 只有当 nums2 还有下一个元素时，才将同一行的下一个元素入堆
            if(currColumn < columns - 1) {
                int nextColumn = currColumn + 1;
                PairsInfo nextPairsInfo = new PairsInfo(nums1[currRow] + nums2[nextColumn], currRow, nextColumn);
                pq.add(nextPairsInfo);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] nums1 = {1,2,4,5,6};
        int[] nums2 = {3,5,7,9};
        kSmallestPairs(nums1, nums2, 3);
//        int[] nums2 = {30,40,50,60};
//        int[] res2 = {1,1,1,0};
//        assertArrayEquals(res2, dailyTemperatures(nums2));
    }
}
