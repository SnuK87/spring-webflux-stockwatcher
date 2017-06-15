package de.snuk.webflux.service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;
import reactor.util.function.Tuple2;

@RestController
@RequestMapping("/classic")
public class StockAPI {

    private List<Stock> stocks = new ArrayList<>();
    private Random random = new Random();

    private DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    public StockAPI() {

	stocks.add(new Stock("ABC", new BigDecimal("232"), new BigDecimal("0"), FORMAT.format(LocalDateTime.now())));
	stocks.add(new Stock("DEF", new BigDecimal("185"), new BigDecimal("0"), FORMAT.format(LocalDateTime.now())));
	stocks.add(new Stock("GHI", new BigDecimal("65"), new BigDecimal("0"), FORMAT.format(LocalDateTime.now())));
	stocks.add(new Stock("JKL", new BigDecimal("24"), new BigDecimal("0"), FORMAT.format(LocalDateTime.now())));
	stocks.add(new Stock("MNO", new BigDecimal("104"), new BigDecimal("0"), FORMAT.format(LocalDateTime.now())));
	stocks.add(new Stock("PQR", new BigDecimal("178"), new BigDecimal("0"), FORMAT.format(LocalDateTime.now())));
	stocks.add(new Stock("STU", new BigDecimal("271"), new BigDecimal("0"), FORMAT.format(LocalDateTime.now())));
	stocks.add(new Stock("VWX", new BigDecimal("49"), new BigDecimal("0"), FORMAT.format(LocalDateTime.now())));
	stocks.add(new Stock("YZ", new BigDecimal("305"), new BigDecimal("0"), FORMAT.format(LocalDateTime.now())));
    }

    @CrossOrigin
    @GetMapping(value = "/test", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Stock> test() {
	Flux<Long> interval = Flux.interval(Duration.ofMillis(500L));

	Flux<Stock> generate = Flux
		.generate(() -> 0, (BiFunction<Integer, SynchronousSink<Stock>, Integer>) (index, sink) -> {
		    Stock current = stocks.get(index);
		    BigDecimal currentPrice = current.getPrice();
		    BigDecimal priceChange = new BigDecimal(random.nextInt(currentPrice.intValue() / 10) + 1);
		    boolean negative = random.nextBoolean();
		    priceChange = negative ? priceChange.negate() : priceChange;

		    BigDecimal setScale = BigDecimal.valueOf(random.nextDouble()).setScale(2, BigDecimal.ROUND_HALF_UP);
		    priceChange = priceChange.add(setScale);

		    BigDecimal newPrice = currentPrice.add(priceChange);

		    Stock newStock = new Stock(current.getSymbol(), newPrice, priceChange,
			    FORMAT.format(LocalDateTime.now()));
		    sink.next(newStock);
		    stocks.set(index, newStock);
		    return ++index % stocks.size();
		}).limitRate(stocks.size());

	Flux<Tuple2<Long, Stock>> zip = Flux.zip(interval, generate);

	return zip.map(Tuple2::getT2);
    }

    @CrossOrigin
    @GetMapping("/all")
    public Flux<Stock> all() {
	return Flux.fromIterable(stocks);
    }
}
