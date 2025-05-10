package com.hong.exchange.book;

import com.hong.exchange.order.Order;

import java.util.*;

import static com.hong.exchange.order.Order.Side;
import static com.hong.exchange.order.Order.Side.*;

public class OrderBook {
    // descending by key (price)
    private Map<Integer, List<Order>> bidBook;
    // ascending by key (price)
    private Map<Integer, List<Order>> askBook;

    public OrderBook() {
        this.bidBook = new TreeMap<>(Comparator.reverseOrder());
        this.askBook = new TreeMap<>();
    }

    public void setBidBook(Map<Integer, List<Order>> bidBook) {
        this.bidBook = bidBook;
    }

    public void setAskBook(Map<Integer, List<Order>>askBook) {
        this.askBook = askBook;
    }

    public void addOrder(Order order) {
        int price = order.getPrice();
        Side side = order.getSide();
        Map<Integer, List<Order>> book = (side == BUY) ? bidBook : askBook;
        book.computeIfAbsent(price, k -> new ArrayList<>()).add(order);
    }

    public void cancelOrder(Order order) {
        int price = order.getPrice();
        Side side = order.getSide();
        Map<Integer, List<Order>> book = (side == BUY) ? bidBook : askBook;
        List<Order> ordersPrice = book.get(price);
        if (ordersPrice != null) {
            ordersPrice.remove(order);
            if (ordersPrice.isEmpty()) {
                book.remove(price);
            }
        }
    }

    public Map<Integer, List<Order>> getBidBook() {
        return bidBook;
    }

    public Map<Integer, List<Order>> getAskBook() {
        return askBook;
    }

    public Order getOrderById(int orderId) {
        for (List<Order> orders : bidBook.values()) {
            for (Order order : orders) {
                if (order.getOrderId() == orderId) return order;
            }
        }
        for (List<Order> orders : askBook.values()) {
            for (Order order : orders) {
                if (order.getOrderId() == orderId) return order;
            }
        }
        return null;
    }
    public void viewOrderBook() {
        System.out.println("Bid Book:");
        System.out.println(bidBook);
        System.out.println("Ask Book:");
        System.out.println(askBook);
    }
}
