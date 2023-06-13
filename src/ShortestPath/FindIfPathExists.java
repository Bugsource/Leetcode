package ShortestPath;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class FindIfPathExists {

    /**
     *
     * @param n 节点为0到n-1
     * @param edges 双向边数组
     * @param source 起点
     * @param destination 终点
     * @return
     */
    public boolean validPath(int n, int[][] edges, int source, int destination) {

        Map<Integer, List<Integer>> graph = new HashMap<>();

        for(int[] edge: edges) {
            // 双向图
            graph.computeIfAbsent(edge[0], key -> new LinkedList<>()).add(edge[1]);
            graph.computeIfAbsent(edge[1], key -> new LinkedList<>()).add(edge[0]);
        }

        Queue<Integer> sourceQueue = new LinkedList<>();
        sourceQueue.add(source);
        boolean[] visited = new boolean[n];
        while (!sourceQueue.isEmpty()) {
            Integer currSource = sourceQueue.poll();
            if(currSource == destination) {
                return true;
            }

            List<Integer> neighbours = graph.get(currSource);
            if(neighbours != null && !neighbours.isEmpty()) {
                for(Integer neighbour : neighbours) {
                    // 在加入邻接边之前判断，可以避免一些无效的加入，应该比先加入再判断到重复性能好点
                    if(!visited[neighbour]) {
                        sourceQueue.add(neighbour);
                        visited[neighbour] = true;
                    }
                }
            }
        }
        return false;
    }
}
