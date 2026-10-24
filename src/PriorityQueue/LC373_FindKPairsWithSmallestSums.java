package PriorityQueue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import static util.Util.assertArrayEquals;

public class LC373_FindKPairsWithSmallestSums {

    private static record PairsInfo(int sum, int row, int column){}
    public static List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> res = new ArrayList<>();
        int columns = nums2.length;
        if(columns == 0) {
            return res;
        }
        PriorityQueue<PairsInfo> pq = new PriorityQueue<>(Comparator.comparingInt(p -> p.sum));

        for (int i = 0; i < nums1.length; ++ i) {
            PairsInfo pairsInfo = new PairsInfo(nums1[i] + nums2[0], i, 0);
            pq.add(pairsInfo);
        }
        int count = 0;
        while(!pq.isEmpty() && count < k) {
            PairsInfo min = pq.poll();
            ++ count;

            int currRow = min.row;
            int currColumn = min.column;
            List<Integer> currRes = List.of(nums1[currRow], nums2[currColumn]);
            res.add(currRes);

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
