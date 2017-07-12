package com.batiaev.demo.quotes.controller;

import com.batiaev.demo.quotes.model.Quote;
import com.batiaev.demo.quotes.provider.QuoteProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RootController
 *
 * @author anton
 * @since 13/07/17
 */
@RestController
public class RootController {
    private final Map<String, Object> appInfo = new HashMap<>();

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

    @DeleteMapping
    public void cleanup() {
        quoteProvider.clean();
    }

    @GetMapping(value = "/status")
    public ResponseEntity<Map<String, Object>> status() {
        return ResponseEntity.ok(appInfo);
    }


    @GetMapping(value = "/quotes")
    public ResponseEntity<List<Quote>> quotes(@RequestParam(value = "date", required = false) LocalDate date) {
        if (date == null)
            return ResponseEntity.ok(quoteProvider.getQuotes());
        else
            return ResponseEntity.ok(quoteProvider.getQuotes(date));
    }
}
