package com.hong.exchange.engine;

public class Trade {

    private final int buyOrderId;
    private final int sellOrderId;
    private final int tradeId;
    private int price;
    private int quantity;
    private final long timestamp;

    public Trade(int buyOrderId, int sellOrderId, int tradeId, int price, int quantity, long timestamp) {
        this.buyOrderId = buyOrderId;
        this.sellOrderId = sellOrderId;
        this.tradeId = tradeId;
        this.price = price;
        this.quantity = quantity;
        this.timestamp = timestamp;
    }

    public void viewTrade() {
        System.out.printf("tradeId=%d, buyId=%d, sellId=%d, price=%d, quantity=%d, timestamp=%s%n", tradeId, buyOrderId, sellOrderId, price, quantity, timestamp);
    }

    public int getBuyOrderId() {
        return buyOrderId;
    }

    public int getSellOrderId() {
        return sellOrderId;
    }

    public int getTradeId() {
        return tradeId;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity() {
        this.quantity = quantity;
    }

    public void setPrice() {
        this.price = price;
    }

}
