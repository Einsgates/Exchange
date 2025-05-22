# Exchange
This will be a high performance, low-latency trading system, developed from scratch.

To use this match engine, run the `ConsoleInterface.java`:

- Add order: `ADD BUY/SEll <price> <quantity>`
- View the order book: `BOOK`
- View the trades: `TRADES`
- Instructions on how to use this: `HELP`
- Exit the engine: `EXIT`


This is a full example:
```commandline
> ADD BUY 106 230
> BOOK
Bid Book:
{106=[Order{id=12, BUY, price=106, quantity=10}], 104=[Order{id=11, BUY, price=104, quantity=10}], 103=[Order{id=2, BUY, price=103, quantity=150}], 102=[Order{id=3, BUY, price=102, quantity=100}], 101=[Order{id=4, BUY, price=101, quantity=50}], 100=[Order{id=5, BUY, price=100, quantity=20}]}
Ask Book:
{107=[Order{id=9, SELL, price=107, quantity=40}]}
> ADD SELL 101 300
BOOK
> Bid Book:
{101=[Order{id=4, BUY, price=101, quantity=20}], 100=[Order{id=5, BUY, price=100, quantity=20}]}
Ask Book:
{107=[Order{id=9, SELL, price=107, quantity=40}]}
> TRADES
tradeId=1, buyId=10, sellId=6, price=104, quantity=100, timestamp=1746903417927
tradeId=2, buyId=11, sellId=6, price=104, quantity=40, timestamp=1746903468314
tradeId=3, buyId=12, sellId=7, price=105, quantity=120, timestamp=1746903496528
tradeId=4, buyId=12, sellId=8, price=106, quantity=100, timestamp=1746903496528
tradeId=5, buyId=12, sellId=13, price=106, quantity=10, timestamp=1746903557996
tradeId=6, buyId=11, sellId=13, price=104, quantity=10, timestamp=1746903557996
tradeId=7, buyId=2, sellId=13, price=103, quantity=150, timestamp=1746903557996
tradeId=8, buyId=3, sellId=13, price=102, quantity=100, timestamp=1746903557996
tradeId=9, buyId=4, sellId=13, price=101, quantity=30, timestamp=1746903557996
> HELP
Instructions for the exchange:
-----ADD BUY <price> <quantity>--------> submit a buy order
-----ADD SELL <price> <quantity>-------> submit a sell order
------------------BOOK-----------------> view the order book
-----------------TRADES----------------> view the trades
------------------HELP-----------------> view this page
------------------EXIT-----------------> quit
> EXIT
```

I will show Yifan how to use `Git`