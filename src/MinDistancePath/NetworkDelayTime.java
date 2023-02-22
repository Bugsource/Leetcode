import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class NetworkDelayTime {

    public static int networkDelayTime(int[][] times, int n, int k) {
        Map<Integer, Map<Integer, Integer>> graph = new HashMap<>();
        for (int[] time : times) {
            graph.computeIfAbsent(time[0], key -> new HashMap<>()).put(time[1], time[2]);
        }

        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1[0], o2[0]));
        priorityQueue.add(new int[]{0, k});
        Set<Integer> visited = new HashSet<>();
        int res = 0;
        while (!priorityQueue.isEmpty()) {
            int[] curr = priorityQueue.poll();
            // distance为当前点距离起点的距离
            int distance = curr[0];
            int vertex = curr[1];

            if (!visited.add(vertex)) {
                continue;
            }

            res = distance;
            --n;
            if (n == 0) {
                // 遍历完n个点，提前结束，否则可能会陷入无意义的冗余计算
                return res;
            }
            if (graph.containsKey(vertex)) {
                for (Map.Entry<Integer, Integer> entry : graph.get(vertex).entrySet()) {
                    priorityQueue.add(new int[]{distance + entry.getValue(), entry.getKey()});
                }
            }
        }
        return n == 0 ? res : -1;
    }

    public static void main(String[] args) {
        int[][] testInput1 = {
                {2, 1, 1},
                {2, 3, 1},
                {3, 4, 1}
        };

        int testRes1 = networkDelayTime(testInput1, 4, 2);
        assert (testRes1 == 2);
        System.out.println("testRes1:" + testRes1);

        int[][] testInput2 = {
                {1, 2, 1}
        };
        int testRes2 = networkDelayTime(testInput2, 2, 1);
        assert (testRes2 == 1);
        System.out.println("testRes2:" + testRes2);


        int[][] testInput3 = {
                {1, 2, 1}
        };
        int testRes3 = networkDelayTime(testInput3, 2, 2);
        assert (testRes3 == -1);
        System.out.println("testRes3:" + testRes3);

        int[][] testInput4 = {
                {1, 2, 1},
                {2, 3, 7},
                {1, 3, 4},
                {2, 1, 2}
        };
        int testRes4 = networkDelayTime(testInput4, 3, 1);
        assert (testRes4 == 4);
        System.out.println("testRes4:" + testRes4);
    }
}
