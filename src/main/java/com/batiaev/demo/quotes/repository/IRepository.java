package com.batiaev.demo.quotes.repository;

import java.util.List;

/**
 * CurrencyRepository
 *
 * @author anton
 * @since 13/07/17
 */
public interface IRepository<T> {

    T get(int code);

    List<T> getAll();

    void add(T data);
}
