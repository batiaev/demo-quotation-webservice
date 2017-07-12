package com.batiaev.demo.quotes.repository;

import com.batiaev.demo.quotes.model.Currency;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CurrencyRepository
 *
 * @author anton
 * @since 13/07/17
 */
@Repository
public class CurrencyRepositoryImpl implements CurrencyRepository {

    private Map<Integer, Currency> currencies = new HashMap<>();

    {
        currencies.put(RUB_CODE, new Currency(RUB_CODE, "RUB", "Рубль"));
    }

    @Override
    public Currency get(int code) {
        return currencies.get(code);
    }

    @Override
    public List<Currency> getAll() {
        return new ArrayList<>(currencies.values());
    }

    @Override
    public void add(Currency currency) {
        currencies.put(currency.getCode(), currency);
    }

}
