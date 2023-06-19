package PriorityQueue;

import java.util.Comparator;
import java.util.PriorityQueue;

public class NumberOfOrdersInTheBacklog {
    private final static int ORDER_TYPE_BUY = 0;
    private final static int ORDER_TYPE_SELL = 1;
    private final static int modulo = 1000000000 + 7;

    public static int getNumberOfBacklogOrders(int[][] orders) {
        PriorityQueue<int[]> sellBackLogs = new PriorityQueue<>(Comparator.comparingInt(o -> o[0]));
        PriorityQueue<int[]> buyBackLogs = new PriorityQueue<>((o1, o2) -> o2[0] - o1[0]);
        for (int[] order : orders) {
            int price = order[0];
            int amount = order[1];
            int orderType = order[2];

            if (orderType == ORDER_TYPE_BUY) {
                buyBackLogs.offer(new int[]{price, amount});
            } else {
                sellBackLogs.offer(new int[]{price, amount});
            }
            while (!sellBackLogs.isEmpty() && !buyBackLogs.isEmpty() && sellBackLogs.peek()[0] <= buyBackLogs.peek()[0]) {
                int sellAmount = sellBackLogs.peek()[1];
                int buyAmount = buyBackLogs.peek()[1];
                if (sellAmount <= buyAmount) {
                    sellBackLogs.poll();
                    buyBackLogs.peek()[1] -= sellAmount;
                } else {
                    buyBackLogs.poll();
                    sellBackLogs.peek()[1] -= buyAmount;
                }
            }
        }
        int amountOfAllType = 0;

        for (int[] sell : sellBackLogs) {
            amountOfAllType = (amountOfAllType + sell[1]) % modulo;
        }
        for (int[] buy : buyBackLogs) {
            amountOfAllType = (amountOfAllType + buy[1]) % modulo;
        }

        return amountOfAllType;
    }

    public static int getNumberOfBacklogOrdersOld(int[][] orders) {
        PriorityQueue<int[]> sellBackLogs = new PriorityQueue<>(Comparator.comparingInt(o -> o[0]));
        PriorityQueue<int[]> buyBackLogs = new PriorityQueue<>((o1, o2) -> o2[0] - o1[0]);

        for (int[] order : orders) {
            int price = order[0];
            int amount = order[1];
            int orderType = order[2];


            if (orderType == ORDER_TYPE_BUY) {
                while (!sellBackLogs.isEmpty() && amount > 0) {
                    int[] lowestSellOrder = sellBackLogs.peek();
                    int sellPrice = lowestSellOrder[0];
                    int sellAmount = lowestSellOrder[1];
                    if (price >= sellPrice) {
                        if (amount < sellAmount) {
                            sellBackLogs.poll();
                            sellBackLogs.add(new int[]{sellPrice, sellAmount - amount});
                            amount = 0;
                        } else {
                            // 买单数量大于等于卖单数量，都把此价格卖单全部匹配，更新买单数量，进入下一次循环
                            sellBackLogs.poll();
                            amount -= sellAmount;
                        }

                    } else {
                        // 买价低于最低卖价，直接退出循环，将所有买单加入队列
                        break;
                    }
                }
                if (amount != 0) {
                    // 未匹配买单加入队列
                    buyBackLogs.add(new int[]{price, amount});
                }
            } else {
                // 尝试匹配卖单
                while (!buyBackLogs.isEmpty() && amount > 0) {
                    int[] highestBuyOrder = buyBackLogs.peek();
                    int buyPrice = highestBuyOrder[0];
                    int buyAmount = highestBuyOrder[1];
                    if (price <= buyPrice) {
                        if (amount < buyAmount) {
                            buyBackLogs.poll();
                            buyBackLogs.add(new int[]{buyPrice, buyAmount - amount});
                            amount = 0;
                        } else {
                            buyBackLogs.poll();
                            amount -= buyAmount;
                        }

                    } else {
                        // 卖价低于最高买价，直接退出循环，将所有卖单加入队列
                        break;
                    }
                }
                if (amount != 0) {
                    // 未匹配买单加入队列
                    sellBackLogs.add(new int[]{price, amount});
                }
            }
        }
        int amountOfAllType = 0;

        for(int[] sell: sellBackLogs) {
            amountOfAllType = (amountOfAllType + sell[1]) % modulo;
        }
        for(int[] buy: buyBackLogs) {
            amountOfAllType = (amountOfAllType + buy[1]) % modulo;
        }

        return amountOfAllType;
    }


    //    for (int[] order : orders) {
//        int price = order[0];
//        int amount = order[1];
//        int orderType = order[2];
//        if (orderType == ORDER_TYPE_BUY) {
//                for (int i = 0; i < amount; ++i) {
//                    if (sellBackLogs.isEmpty()) {
//
//                    } else {
//                        if (sellBackLogs.peek() <= price) {
//                            // 匹配成功
//                            sellBackLogs.poll();
//                        } else {
//                            buyBackLogs.add(price);
//                        }
//                    }
//                }
//        } else {
//            for (int i = 0; i < amount; ++i) {
//                if (buyBackLogs.isEmpty()) {
//                    sellBackLogs.add(price);
//                } else {
//                    if (buyBackLogs.peek() >= price) {
//                        buyBackLogs.poll();
//                    } else {
//                        sellBackLogs.add(price);
//                    }
//                }
//            }
//        }
//    }
    public static void main(String[] args) {
        int[][] testInput1 = {
                {10, 5, 0},
                {15, 2, 1},
                {25, 1, 1},
                {30, 4, 0}
        };

        int testRes1 = getNumberOfBacklogOrders(testInput1);
        assert (testRes1 == 6);
        System.out.println("testRes1:" + testRes1);

        int[][] testInput2 = {
                {7, 1000000000, 1},
                {15, 3, 0},
                {5, 999999995, 0},
                {5, 1, 1}
        };

        int testRes2 = getNumberOfBacklogOrders(testInput2);
//        assert (testRes2 == 6);
        System.out.println("testRes2:" + testRes2);
    }
}
