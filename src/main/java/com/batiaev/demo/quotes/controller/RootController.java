package com.batiaev.demo.quotes.controller;

import com.batiaev.demo.quotes.exception.QuotesException;
import com.batiaev.demo.quotes.model.Currency;
import com.batiaev.demo.quotes.model.Quote;
import com.batiaev.demo.quotes.provider.QuoteProvider;
import com.batiaev.demo.quotes.repository.CurrencyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * RootController
 *
 * @author anton
 * @since 13/07/17
 */
@Slf4j
@RestController
public class RootController {
    private final Map<String, Object> appInfo = new HashMap<>();

    @Autowired
    private CurrencyRepository currencyRepository;

    @Value("${api.version:0.1.0}")
    private String apiVersion;

    private QuoteProvider quoteProvider;

    @Autowired
    public RootController(QuoteProvider quoteProvider) {
        this.quoteProvider = quoteProvider;
    }

    @PostConstruct
    public void init() {
        appInfo.put("status", "ok");
        appInfo.put("api.version", apiVersion);
    }

    @DeleteMapping(value = "/cleanup")
    public void cleanup() {
        quoteProvider.clean();
    }

    @PostMapping(value = "/reload")
    public ResponseEntity<List<Quote>> reload() {
        return ResponseEntity.ok(quoteProvider.getQuotes());
    }

    @GetMapping(value = "/status")
    public ResponseEntity<Map<String, Object>> status() {
        return ResponseEntity.ok(appInfo);
    }

    @GetMapping(value = "/quotes")
    public ResponseEntity<List<Quote>> getQuotes(@RequestParam(value = "date", required = false) LocalDate date) {
        if (date == null)
            return ResponseEntity.ok(quoteProvider.getQuotes());
        else
            return ResponseEntity.ok(quoteProvider.getQuotes(date));
    }

    @GetMapping("/currencies")
    public ResponseEntity<?> getCurrencies() {
        return ResponseEntity.ok(currencyRepository.getAll());
    }

    @GetMapping("/currencies/{code}")
    public ResponseEntity<Currency> getRubCurrency(@PathVariable(value = "code") int code) {

        Currency currency = currencyRepository.get(code);
        if (currency == null) throw new QuotesException("currency not found!");

        return ResponseEntity.ok(currency);
    }

    @GetMapping("/currencies/rub")
    public ResponseEntity<Currency> getRubCurrency() {

        Currency rub = currencyRepository.getRub();
        rub.add(linkTo(methodOn(RootController.class).getRubCurrency()).withSelfRel());
        return ResponseEntity.ok(rub);
    }

    @ExceptionHandler(QuotesException.class)
    @ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Currency not found")
    public void exceptionHandler(QuotesException e) {
        log.error("REST api exception", e);
    }
}
