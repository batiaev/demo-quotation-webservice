package com.batiaev.demo.quotes.provider;

import com.batiaev.demo.quotes.model.CbrDailyQuoteResponse;
import com.batiaev.demo.quotes.model.Currency;
import com.batiaev.demo.quotes.model.Quote;
import com.batiaev.demo.quotes.repository.CurrencyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

/**
 * Load quotes from Russian Central Bank
 *
 * @author anton
 * @since 13/07/17
 */
@Slf4j
@Service
public class CbrQuoteProvider implements QuoteProvider {

    private final String url = "http://www.cbr.ru/scripts/XML_daily.asp";
    private final String historicalUrl = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=%s";
    private final RestTemplate restTemplate = new RestTemplate();

    private final CurrencyRepository currencyRepository;
    private Map<LocalDate, List<Quote>> quotes = new HashMap<>();

    @Autowired
    public CbrQuoteProvider(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @PostConstruct
    private void init() {
        List<Quote> quotes = getQuotes();
        currencyRepository.getAll().forEach(currency -> log.info(currency.toString()));
    }

    @Override
    public List<Quote> getQuotes() {
        log.info("Load cbr quotes for today...");
        CbrDailyQuoteResponse data = restTemplate.getForObject(url, CbrDailyQuoteResponse.class);

        return getQuotes(data);
    }

    @Override
    public List<Quote> getQuotes(LocalDate date) {
        if (quotes.containsKey(date)) return quotes.get(date);

        log.info("Load cbr quotes at date {}...", date);
        CbrDailyQuoteResponse data = restTemplate.getForObject(format(historicalUrl,
                date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))), CbrDailyQuoteResponse.class);

        return getQuotes(data);
    }

    @Override
    public void clean() {
        quotes.clear();
    }

    private List<Quote> getQuotes(CbrDailyQuoteResponse data) {

        List<Quote> quotes = new ArrayList<>();
        data.getQuotes().forEach(q -> {
            Currency currency = currencyRepository.get(q.getCode());
            if (currency == null) {
                currency = new Currency(q.getCode(), q.getCurrCode(), q.getName());
                currencyRepository.add(currency);
            }
            Quote quote = new Quote();
            quote.setBase(currencyRepository.getRub());
            quote.setCurrency(currency);
            quote.setDate(data.getDate());
            quote.setNominal(q.getNominal());
            quote.setValue(q.getValue());
            quotes.add(quote);
        });

        this.quotes.put(data.getDate(), quotes);
        return quotes;
    }
}
