import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class Itinerary {

    public static List<String> findItinerary(List<List<String>> tickets) {
        Map<String, PriorityQueue<String>> srcToOrderedDestMap = new HashMap<>();
        LinkedList<String> routes = new LinkedList<>();
        for(List<String> ticket : tickets) {
            if(ticket.size() != 2) {
                // 票的信息有误，没有准确包含起点和终点两个信息
                return new ArrayList<>();
            }
            srcToOrderedDestMap.computeIfAbsent(ticket.get(0), key -> new PriorityQueue<>()).add(ticket.get(1));
//            String src = ticket.get(0);
//            if(srcToOrderedDestMap.containsKey(src)) {
//                PriorityQueue<String> destPQ = srcToOrderedDestMap.get(src);
//                destPQ.add(ticket.get(1));
//            } else {
//                PriorityQueue<String> destPQ = new PriorityQueue<>();
//                destPQ.add(ticket.get(1));
//                srcToOrderedDestMap.put(src, destPQ);
//            }
        }
        Stack<String> stack = new Stack<>();
        stack.push("JFK");
        while (!stack.isEmpty()) {
            String currentSrc = stack.peek();
            if (srcToOrderedDestMap.containsKey(currentSrc) && !srcToOrderedDestMap.get(currentSrc).isEmpty()) {
                // 优先队列为空，则说明到了终点；否则从优先队列中弹出最小值，加入待遍历队列
                stack.push(srcToOrderedDestMap.get(currentSrc).poll());
            } else {
                // get stuck, add the element on top of stack to the end of routes.
                // Then try to search another route from the previous src, i.e. doing the backtracking.
                routes.addFirst(stack.pop());
            }

        }
       return routes;
    }

    public static void main(String[] args) {
        List<List<String>> testInput1 = new ArrayList<>();
//        [["MUC","LHR"],["JFK","MUC"],["SFO","SJC"],["LHR","SFO"]]
        testInput1.add(new ArrayList<>(Arrays.asList("MUC","LHR")));
        testInput1.add(new ArrayList<>(Arrays.asList("JFK","MUC")));
        testInput1.add(new ArrayList<>(Arrays.asList("SFO","SJC")));
        testInput1.add(new ArrayList<>(Arrays.asList("LHR","SFO")));
        List<String> testRes1 = findItinerary(testInput1);
        List<String> testCorrectRes1 = Arrays.asList("JFK","MUC","LHR","SFO","SJC");
        assert (testRes1.equals(testCorrectRes1));
        System.out.println("testRes1:" + testRes1);

        List<List<String>> testInput2 = new ArrayList<>();
        testInput2.add(new ArrayList<>(Arrays.asList("JFK","SFO")));
        testInput2.add(new ArrayList<>(Arrays.asList("JFK","ATL")));
        testInput2.add(new ArrayList<>(Arrays.asList("SFO","ATL")));
        testInput2.add(new ArrayList<>(Arrays.asList("ATL","JFK")));
        testInput2.add(new ArrayList<>(Arrays.asList("ATL","SFO")));
        List<String> testRes2 = findItinerary(testInput2);
        List<String> testCorrectRes2 = Arrays.asList("JFK","ATL","JFK","SFO","ATL","SFO");
        assert (testRes2.equals(testCorrectRes2));
        System.out.println("testRes2:" + testRes2);

    }
}
