package com.hong.exchange.order;

public class Order {
    private final int orderId;
    private final Side side;
    private final int price;
    private int quantity;
    private final long timestamp;

    public Order(int orderId, Side side, int price, int quantity) {
        this.orderId = orderId;
        this.side = side;
        this.price = price;
        this.quantity = quantity;
        this.timestamp = System.nanoTime();
    }

    public int getOrderId() {
        return orderId;
    }

    public Side getSide() {
        return side;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("Order{id=%d, %s, price=%d, quantity=%d}", orderId, side, price, quantity);
    }

    public enum Side {
        BUY, SELL
    }
}
