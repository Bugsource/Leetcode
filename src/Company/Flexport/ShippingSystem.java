package Company.Flexport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class ShippingSystem {
    Map<Integer, Set<Voyage>> routeGraph;
    public ShippingSystem() {
        routeGraph = new HashMap<>();
    }

    public void addRoute(Voyage voyage) {
        routeGraph.computeIfAbsent(voyage.srcPortId, key -> new HashSet<>()).add(voyage);
    }

    public boolean existsValidPath(Order order) {
        Integer source = order.srcPortId;
        Queue<Integer> sourceQueue = new LinkedList<>();
        sourceQueue.add(source);
        Set<Integer> visited = new HashSet<>();
        while (!sourceQueue.isEmpty()) {
            Integer currSource = sourceQueue.poll();
            if(Objects.equals(currSource, order.destPortId)) {
                return true;
            }

            Set<Voyage> neighbours = routeGraph.get(currSource);
            if(neighbours != null && !neighbours.isEmpty()) {
                for(Voyage neighbourVoyage : neighbours) {
                    // 在加入邻接边之前判断，可以避免一些无效的加入，应该比先加入再判断到重复性能好点
                    Integer neighbourDestPortId = neighbourVoyage.destPortId;
                    if(!visited.add(neighbourDestPortId)) {
                        sourceQueue.add(neighbourDestPortId);
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取订单最短路径
     * @param order
     * @return 符合订单要求的最短路径的港口id集合，从终点到起点
     */
    public List<Integer> getShortestVoyagePath(Order order) {
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1[0], o2[0]));
        // 优先队列里存放数组，数组里第一个元素是起始点到当前点的最短距离，第二个元素是当前点，第三个元素是最短距离对应的上一个起点
        priorityQueue.add(new int[]{0, order.srcPortId, order.srcPortId});
        Set<Integer> visited = new HashSet<>();
        int minDistance = 0;
        // 存放最短路径时，当前点和上一个点的映射关系
        Map<Integer, Integer> currToPrev = new HashMap<>();
        while (!priorityQueue.isEmpty()) {
            int[] curr = priorityQueue.poll();
            // distance为当前点距离起点的距离
            int distance = curr[0];
            int vertex = curr[1];
            if(vertex == order.destPortId) {
                minDistance = distance;
                break;
            }

            if (routeGraph.containsKey(vertex)) {
                for (Voyage voyage : routeGraph.get(vertex)) {
                    if (visited.add(voyage.destPortId)) {
                        currToPrev.put(voyage.destPortId, voyage.srcPortId);
                        priorityQueue.add(new int[]{distance + voyage.distance, voyage.destPortId, voyage.srcPortId});
                    }
                }
            }
        }
        List<Integer> dstToSrcPath = new ArrayList<>();
        Integer currDest = order.destPortId;
        while(true) {
            Integer prev = currToPrev.get(currDest);
            if(prev == null || prev.equals(currDest)) {
                // 起点的前一个仍是起点
                break;
            }
            dstToSrcPath.add(prev);
        }
        return dstToSrcPath;
    }
}

/**
 * 路径规划问题(给定一个订单，为这个订单推荐可选的航班路径。需要自己设计好订单、航班、港口之间的关系，实现添加航班路线、给定订单推荐航班路径两个个函数 )。
 * 第一小问是给定一个订单，返回是否能为这个订单找到运送路径。
 * 第二个小问是给定一个订单，返回运送这个订单的最短航班路径。
 *
 * 主要考察C++工程项目能力，让写个OOD的项目，大概就是有船只，货物两个class，然后各自的一些属性，包括出发时间、到达时间、城市、重量啥的。
 * 实现一个function，哪个货物上哪条船能在规定时间内送达。
 * 两个follow-up，看能不能优化，再加一些条件啥的.
 */
class Port {
    Integer portId;
    String address;

    public Port(Integer portId, String address) {
        this.portId = portId;
        this.address = address;
    }
}

class Voyage {

    Integer id;

    Integer srcPortId;
    Integer destPortId;
    Integer distance;

    public Voyage(Integer id, Integer srcPortId, Integer destPortId, Integer distance) {
        this.id = id;
        this.srcPortId = srcPortId;
        this.destPortId = destPortId;
        this.distance = distance;
    }
}

class Order {
    Integer id;
    Integer srcPortId;
    Integer destPortId;

    public Order(Integer id, Integer srcPortId, Integer destPortId) {
        this.id = id;
        this.srcPortId = srcPortId;
        this.destPortId = destPortId;
    }
}




