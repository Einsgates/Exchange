package com.hong.exchange.book;

import com.hong.exchange.order.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.hong.exchange.order.Order.Side.*;
import static org.junit.jupiter.api.Assertions.*;

public class OrderBookTest {
    private OrderBook orderBook;

    @BeforeEach
    public void setup() {
        orderBook = new OrderBook();
    }

    @Test
    public void testAddOrder() {
        Order order = new Order(1, BUY, 100, 10);
        orderBook.addOrder(order);
        Assertions.assertNotNull(orderBook);
        Map<Integer, List<Order>> bidBook = orderBook.getBidBook();
        Assertions.assertNotNull(bidBook);
        Assertions.assertTrue(bidBook.containsKey(100));
        assertEquals(BUY, bidBook.get(100).get(0).getSide());
        assertEquals(1, bidBook.get(100).get(0).getOrderId());
        assertEquals(100, bidBook.get(100).get(0).getPrice());
        assertEquals(10, bidBook.get(100).get(0).getQuantity());
        orderBook.viewOrderBook();
    }

    @Test
    public void testAddMultipleOrders() {
        for (int i = 2; i < 10; i++) {
            Order order = new Order(i, SELL, 100 + i, 10+(i%3));
            orderBook.addOrder(order);
        }
        Assertions.assertNotNull(orderBook);
        Map<Integer, List<Order>> askBook = orderBook.getAskBook();
        Assertions.assertNotNull(askBook);
        for (int i = 2; i < 10; i++) {
            Assertions.assertTrue(askBook.containsKey(100 + i));
        }
        orderBook.viewOrderBook();
    }

    @Test
    public void testCancelOrder() {
        for (int i = 2; i < 5; i++) {
            Order order = new Order(i, SELL, 100 + i, 10+(i%3));
            orderBook.addOrder(order);
        }
        Assertions.assertNotNull(orderBook.getOrderById(4));
        orderBook.viewOrderBook();
        orderBook.cancelOrder(orderBook.getOrderById(4));
        orderBook.viewOrderBook();
        Assertions.assertNull(orderBook.getOrderById(4));
    }
}
