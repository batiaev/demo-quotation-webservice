package com.batiaev.demo.quotes.repository;

import com.batiaev.demo.quotes.model.Currency;
import org.springframework.stereotype.Repository;

/**
 * CurrencyRepository
 *
 * @author anton
 * @since 13/07/17
 */
@Repository
public interface CurrencyRepository extends IRepository<Currency> {

    int RUB_CODE = 810;

    default Currency getRub() {
        return get(810);
    }
}
