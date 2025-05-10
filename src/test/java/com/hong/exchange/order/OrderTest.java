package com.hong.exchange.order;

import org.junit.jupiter.api.Test;

import static com.hong.exchange.order.Order.Side.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {
    Order order = new Order(1, BUY, 100, 20);

    @Test
    public void testOrder() {

        assert order.getOrderId() == 1;
        assert order.getSide() == BUY;
        assert order.getPrice() == 100;
        assert order.getQuantity() == 20;
    }

    @Test
    public void testToString() {
        assertEquals("Order{id=1, BUY, price=100, quantity=20}", order.toString());
    }

}
