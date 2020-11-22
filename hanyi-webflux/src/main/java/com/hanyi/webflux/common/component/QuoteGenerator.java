package com.hanyi.webflux.common.component;

import com.hanyi.webflux.entity.Quote;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * <p>
 * 定义生成器
 * </p>
 *
 * @author wenchangwei
 * @since 10:42 上午 2020/11/22
 */
@Component
public class QuoteGenerator {

    private final MathContext mathContext = new MathContext(Short.BYTES);

    private final Random random = new Random();

    private final List<Quote> prices = new ArrayList<>();

    /**
     * 生成行情数据
     */
    public QuoteGenerator() {
        this.prices.add(new Quote("CTXS", 82.26));
        this.prices.add(new Quote("DELL", 63.74));
        this.prices.add(new Quote("GOOG", 847.24));
        this.prices.add(new Quote("MSFT", 65.11));
        this.prices.add(new Quote("ORCL", 45.71));
        this.prices.add(new Quote("RHT", 84.29));
        this.prices.add(new Quote("VMW", 92.21));
    }


    public Flux<Quote> fetchQuoteStream(Duration period) {

        // 需要周期生成值并返回，使用 Flux.interval
        return Flux.interval(period)
                // In case of back-pressure, drop events
                .onBackpressureDrop()
                // For each tick, generate a list of quotes
                .map(this::generateQuotes)
                // "flatten" that List<Quote> into a Flux<Quote>
                .flatMapIterable(quotes -> quotes)
                .log("io.spring.workshop.stockquotes");
    }

    /**
     * Create quotes for all tickers at a single instant.
     */
    private List<Quote> generateQuotes(long interval) {
        final Instant instant = Instant.now();
        return prices.stream()
                .map(baseQuote -> {
                    BigDecimal priceChange = baseQuote.getPrice()
                            .multiply(BigDecimal.valueOf(0.05 * this.random.nextDouble()), this.mathContext);
                    Quote result = new Quote(baseQuote.getTicker(), baseQuote.getPrice().add(priceChange));
                    result.setInstant(instant);
                    return result;
                })
                .collect(Collectors.toList());
    }
}