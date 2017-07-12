package com.batiaev.demo.quotes.provider;

import com.batiaev.demo.quotes.model.Quote;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * QuoteProvider
 *
 * @author anton
 * @since 13/07/17
 */
@Service
public interface QuoteProvider {

    default List<Quote> getQuotes() {
        return getQuotes(LocalDate.now());
    }

    List<Quote> getQuotes(LocalDate date);
}
