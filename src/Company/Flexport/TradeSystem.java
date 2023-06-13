package Company.Flexport;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.UUID;

/**
 * 就是定义一些client，client有seller， 有buyer，买卖container，然后让你设计一个order系统。
 * 有buyer的order进来，当price>=sell的价格就match到existing seller order最低的那个价格。
 * 有seller的order进来，差不多的操作，match到buyer最高的价格。
 */
public class TradeSystem {
    private static final TradeSystem tradeSystem = new TradeSystem();

    private PriorityQueue<SellOrder> sellOrderPriorityQueue;

    private PriorityQueue<BuyOrder> buyOrderPriorityQueue;

    private TradeSystem() {
        this.sellOrderPriorityQueue = new PriorityQueue<>();
        this.buyOrderPriorityQueue = new PriorityQueue<>();
    }

    public static TradeSystem getTradeSystem() {
        return tradeSystem;
    }

    public void placeBuyOrder(BuyOrder buyOrder) {
        if (this.sellOrderPriorityQueue.isEmpty()
                || this.sellOrderPriorityQueue.peek().getOrderPrice() > buyOrder.getOrderPrice()) {
            // 不能成交，加入买单队列
            this.buyOrderPriorityQueue.add(buyOrder);
            buyOrder.setOrderState(OrderState.READY_TO_TRADE);
        } else {
            // 成交，更新买单和卖单信息
            SellOrder hitSellOrder = this.sellOrderPriorityQueue.poll();
            buyOrder.onSettleDown(hitSellOrder.getOrderPrice());
            hitSellOrder.onSettleDown();
        }
    }

    public void placeSellOrder(SellOrder sellOrder) {
        if (this.buyOrderPriorityQueue.isEmpty()
                || this.buyOrderPriorityQueue.peek().getOrderPrice() < sellOrder.getOrderPrice()) {
            // 不能成交，加入买单队列
            this.sellOrderPriorityQueue.add(sellOrder);
            sellOrder.setOrderState(OrderState.READY_TO_TRADE);
        } else {
            // 成交，更新买单和卖单信息
            BuyOrder hitBuyOrder = this.buyOrderPriorityQueue.poll();
            sellOrder.onSettleDown();
            hitBuyOrder.onSettleDown(sellOrder.getOrderPrice());
        }
    }

    public void tradeMaking() {
        while (!this.buyOrderPriorityQueue.isEmpty() && !this.sellOrderPriorityQueue.isEmpty()
                && this.sellOrderPriorityQueue.peek().getOrderPrice() <= this.buyOrderPriorityQueue.peek().getOrderPrice()) {
            BuyOrder hitBuyOrder = this.buyOrderPriorityQueue.poll();
            SellOrder hitSellOrder = this.sellOrderPriorityQueue.poll();
            hitSellOrder.onSettleDown();
            hitBuyOrder.onSettleDown(hitSellOrder.getOrderPrice());
        }
    }

    public static void main(String[] a) {
        Container container1 = new Container(10, 100);
        Container container2 = new Container(11, 200);
        Container container3 = new Container(12, 20);

        Client client1 = new Client(1, 1000);
        client1.addContainer(container1);
        client1.addContainer(container2);
        client1.addContainer(container3);

        client1.placeSellOrder(10);
        client1.placeSellOrder(11);

        Client client2 = new Client(2, 1000);
        client2.placeBuyOrder(50);
        client2.placeBuyOrder(80);
        client2.placeBuyOrder(150);

        client1.placeSellOrder(12);
    }

}

class Client {
    private Integer money;
    private Map<Integer, Container> idToContainerMap;
    private Integer userId;

    public Client(Integer userId, Integer money) {
        this.money = money;
        idToContainerMap = new HashMap<>();
        this.userId = userId;
    }

    public void addContainer(Container container) {
        idToContainerMap.putIfAbsent(container.getId(), container);
    }

    public void placeSellOrder(Integer containerId) {
        Container container = idToContainerMap.get(containerId);
        if (container != null) {
            SellOrder sellOrder = new SellOrder(userId, UUID.randomUUID().toString(), container.getPrice(), container.getId());
            TradeSystem.getTradeSystem().placeSellOrder(sellOrder);
        }
    }

    public void placeBuyOrder(Integer maxBuyPrice) {
        if (maxBuyPrice > money) {
            throw new RuntimeException("your money is not enough");
        }
        money -= maxBuyPrice;
        BuyOrder buyOrder = new BuyOrder(userId, UUID.randomUUID().toString(), maxBuyPrice);
        TradeSystem.getTradeSystem().placeBuyOrder(buyOrder);
    }
}


class Container {
    private Integer id;
    private Integer price;

    public Container(Integer id, Integer price) {
        this.id = id;
        this.price = price;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getId() {
        return id;
    }

}

class SellOrder implements Comparable<SellOrder> {
    private String orderId;
    private Integer orderPrice;
    private Integer containerId;
    private Integer userId;
    private OrderState orderState;
//    private OrderType orderType;

    public SellOrder(Integer userId, String orderId, Integer orderPrice, Integer containerId) {
        this.userId = userId;
        this.orderId = orderId;
        this.orderPrice = orderPrice;
        this.containerId = containerId;
        this.orderState = OrderState.CREATED;
//        this.orderType = orderType;
    }

//    @Override
//    public int compare(SellOrder o1, SellOrder o2) {
//        return o1.orderPrice - o2.orderPrice;
//    }

    public Integer getOrderPrice() {
        return this.orderPrice;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public void onSettleDown() {
        this.orderState = OrderState.SETTLED_DOWN;
        System.out.println("this buy order had been settled, order price:" + this.orderPrice);
    }

    @Override
    public int compareTo(SellOrder other) {
        return this.getOrderPrice().compareTo(other.orderPrice);
    }
}

class BuyOrder implements Comparable<BuyOrder> {
    private String orderId;
    private Integer orderPrice;
    private Integer userId;
    private OrderState orderState;
    private Integer dealPrice;

//    private OrderType orderType;

    public BuyOrder(Integer userId, String orderId, Integer orderPrice) {
        this.userId = userId;
        this.orderId = orderId;
        this.orderPrice = orderPrice;
        this.orderState = OrderState.CREATED;
//        this.orderType = orderType;
    }

//    @Override
//    public int compare(BuyOrder o1, BuyOrder o2) {
//        // 买单按价格逆序排序
//        return o2.orderPrice - o1.orderPrice;
//    }

    public Integer getOrderPrice() {
        return this.orderPrice;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public void onSettleDown(Integer sellOrderPrice) {
        this.orderState = OrderState.SETTLED_DOWN;
        this.dealPrice = sellOrderPrice;
        System.out.println("this buy order had been settled, deal price:" + this.dealPrice + ", order price:" + this.orderPrice);
    }

    @Override
    public int compareTo(BuyOrder other) {
        return - this.orderPrice.compareTo(other.orderPrice);
    }
}

enum OrderState {
    CREATED,
    READY_TO_TRADE,
    SETTLED_DOWN
}

enum OrderType {
    BUY,
    SALE
}
