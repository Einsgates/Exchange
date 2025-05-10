package com.hong.exchange.engine;

import com.hong.exchange.book.OrderBook;
import com.hong.exchange.order.Order;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.hong.exchange.order.Order.Side.*;

public class MatchEngine {
    private final OrderBook orderBook;
    private final List<Trade> trades;
    private AtomicInteger tradeIdCounter = new AtomicInteger(1);


    public MatchEngine(OrderBook orderBook, List<Trade> trades) {
        this.orderBook = orderBook;
        this.trades = trades;
    }

    public void process(Order incomingOrder) {
        if (incomingOrder.getSide() == BUY) {
            matchBuyOrder(incomingOrder);
        } else {
            matchSellOrder(incomingOrder);
        }

        if (incomingOrder.getQuantity() > 0) {
            orderBook.addOrder(incomingOrder);
        }
    }

    private void matchBuyOrder(Order incomingOrder) {
        var entryIt = orderBook.getAskBook().entrySet().iterator();

        while (entryIt.hasNext() && incomingOrder.getQuantity() > 0) {
            var entry = entryIt.next();
            int askPrice = entry.getKey();

            if (askPrice > incomingOrder.getPrice()) break;

            var orders = entry.getValue();
            var ordersIt = orders.iterator();

            while (ordersIt.hasNext() && incomingOrder.getQuantity() > 0) {
                Order order = ordersIt.next();
                int fillQuantity = Math.min(order.getQuantity(), incomingOrder.getQuantity());
                incomingOrder.setQuantity(incomingOrder.getQuantity() - fillQuantity);
                order.setQuantity(order.getQuantity() - fillQuantity);

                trades.add(new Trade(incomingOrder.getOrderId(), order.getOrderId(), tradeIdCounter.getAndIncrement(), askPrice, fillQuantity, System.currentTimeMillis()));

                if (order.getQuantity() == 0) {
                    ordersIt.remove();
                }
            }

            if (orders.isEmpty()) {
                entryIt.remove();
            }
        }
    }

    private void matchSellOrder(Order incomingOrder) {
        var entryIt = orderBook.getBidBook().entrySet().iterator();

        while (entryIt.hasNext() && incomingOrder.getQuantity() > 0) {
            var entry = entryIt.next();
            int bidPrice = entry.getKey();

            if (bidPrice < incomingOrder.getPrice()) break;

            var orders = entry.getValue();
            var ordersIt = orders.iterator();

            while (ordersIt.hasNext() && incomingOrder.getQuantity() > 0) {
                Order order = ordersIt.next();
                int fillQuantity = Math.min(incomingOrder.getQuantity(), order.getQuantity());
                incomingOrder.setQuantity(incomingOrder.getQuantity() - fillQuantity);
                order.setQuantity(order.getQuantity() - fillQuantity);

                trades.add(new Trade(order.getOrderId(), incomingOrder.getOrderId(), tradeIdCounter.getAndIncrement(), bidPrice, fillQuantity, System.currentTimeMillis()));

                if (order.getQuantity() == 0) {
                    ordersIt.remove();
                }
            }

            if (orders.isEmpty()) {
                entryIt.remove();
            }
        }
    }

    public OrderBook getOrderBook() {
        return orderBook;
    }

    public List<Trade> getTrades() {
        return trades;
    }
}
