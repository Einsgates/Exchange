package com.hong.exchange.engine;

import com.hong.exchange.book.OrderBook;
import com.hong.exchange.order.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.hong.exchange.order.Order.Side.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchEngineTest {
    private OrderBook orderBook;
    private List<Trade> trades;
    private MatchEngine engine;

    @BeforeEach
    public void setup() {
        orderBook = new OrderBook();
        trades = new ArrayList<>();
        engine = new MatchEngine(orderBook, trades);
    }

    @Test
    public void testBuyFilled() {
        Order sell1 = new Order(1, SELL, 100, 100);
        orderBook.addOrder(sell1);
        Order sell2 = new Order(2, SELL, 101, 50);
        orderBook.addOrder(sell2);
        assertEquals(orderBook.getAskBook().size(), 2);
        Order incomingOrder = new Order(3, BUY, 100, 100);
//        orderBook.viewOrderBook();
        engine.process(incomingOrder);
//        orderBook.viewOrderBook();
        assertEquals(orderBook.getAskBook().size(), 1);
    }

    @Test
    public void testBuyPartialFilled() {
        Order sell1 = new Order(1, SELL, 100, 100);
        orderBook.addOrder(sell1);
        Order sell2 = new Order(2, SELL, 101, 50);
        orderBook.addOrder(sell2);
        assertEquals(orderBook.getAskBook().size(), 2);
        Order incomingOrder = new Order(3, BUY, 101, 120);
//        orderBook.viewOrderBook();
        engine.process(incomingOrder);
//        orderBook.viewOrderBook();
        assertEquals(orderBook.getAskBook().size(), 1);
    }

    @Test
    public void testMultipleMatch() {
        Order buy1 = new Order(1, BUY, 103, 150);
        Order buy2 = new Order(2, BUY, 102, 100);
        Order buy3 = new Order(3, BUY, 101, 50);
        Order buy4 = new Order(4, BUY, 100, 20);
        Order sell1 = new Order(5, SELL, 104, 140);
        Order sell2 = new Order(6, SELL, 105, 100);
        Order sell3 = new Order(7, SELL, 106, 120);
        Order sell4 = new Order(8, SELL, 107, 40);
        orderBook.addOrder(buy1);
        orderBook.addOrder(buy2);
        orderBook.addOrder(buy3);
        orderBook.addOrder(buy4);
        orderBook.addOrder(sell1);
        orderBook.addOrder(sell2);
        orderBook.addOrder(sell3);
        orderBook.addOrder(sell4);
        assertEquals(orderBook.getAskBook().size(), 4);
        assertEquals(orderBook.getBidBook().size(), 4);

        Order incomingBuy1 = new Order(9, BUY, 104, 100);
        engine.process(incomingBuy1);
        assertEquals(orderBook.getAskBook().get(104).get(0).getQuantity(), 40);

        Order incomingBuy2 = new Order(10, BUY, 104, 50);
        engine.process(incomingBuy2);
        assertEquals(orderBook.getBidBook().get(104).get(0).getPrice(), 104);
        assertEquals(orderBook.getBidBook().get(104).get(0).getQuantity(), 10);

        Order incomingBuy3 = new Order(11, BUY, 106, 230);
        engine.process(incomingBuy3);
        assertEquals(orderBook.getAskBook().size(), 1);
        assertEquals(orderBook.getAskBook().get(107).get(0).getQuantity(), 40);
        assertEquals(orderBook.getBidBook().get(106).get(0).getQuantity(), 10);

//        orderBook.viewOrderBook();
        Order incomingSell1 = new Order(12, SELL, 101, 300);
        engine.process(incomingSell1);
        assertEquals(orderBook.getBidBook().size(), 2);
        assertEquals(orderBook.getBidBook().get(101).get(0).getQuantity(), 20);
    }
}
