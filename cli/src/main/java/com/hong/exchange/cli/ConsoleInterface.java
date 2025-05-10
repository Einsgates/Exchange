package com.hong.exchange.cli;

import com.hong.exchange.book.OrderBook;
import com.hong.exchange.engine.MatchEngine;
import com.hong.exchange.engine.Trade;
import com.hong.exchange.order.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import static com.hong.exchange.order.Order.Side;
import static com.hong.exchange.order.Order.Side.*;

public class ConsoleInterface {

    private static final AtomicInteger orderIdCounter = new AtomicInteger(1);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OrderBook orderBook = new OrderBook();
        List<Trade> trades = new ArrayList<>();
        MatchEngine matchEngine = new MatchEngine(orderBook, trades);

        help();

        while (true) {
            System.out.print("> ");
            String line = scanner.nextLine();
            if (line == null) break;
            String[] parts = line.trim().split("\\s+");
            if (parts.length == 0 || parts[0].isEmpty()) continue;

            String cmd = parts[0].toUpperCase();
            try {
                switch (cmd) {
                    case "ADD":
                        int price = Integer.valueOf(parts[2]);
                        int quantity = Integer.valueOf(parts[3]);
                        Side side = Objects.equals(parts[1], "BUY") ? BUY : SELL;
                        Order order = new Order(orderIdCounter.incrementAndGet(), side, price, quantity);
                        matchEngine.process(order);
                        break;
                    case "BOOK":
                        orderBook.viewOrderBook();
                        break;
                    case "TRADES":
                        if (trades.isEmpty()) {
                            System.out.println("No trades recorded");
                        } else {
                            trades.forEach(Trade::viewTrade);
                        }
                        break;
                    case "HELP":
                        help();
                        break;
                    case "EXIT":
                        System.out.println("Goodbye");
                        scanner.close();
                        return;
                    default:
                        throw new IllegalStateException("Unknown command: " + cmd);
                }
            } catch (IllegalArgumentException iae) {
                System.out.println("Error: " + iae.getMessage());
            }
        }
    }

    private static void help() {
        System.out.println("Instructions for the exchange:");
        System.out.println("-----ADD BUY <price> <quantity>--------> submit a buy order");
        System.out.println("-----ADD SELL <price> <quantity>-------> submit a sell order");
        System.out.println("------------------BOOK-----------------> view the order book");
        System.out.println("-----------------TRADES----------------> view the trades");
        System.out.println("------------------HELP-----------------> view this page");
        System.out.println("------------------EXIT-----------------> quit");
    }
}
