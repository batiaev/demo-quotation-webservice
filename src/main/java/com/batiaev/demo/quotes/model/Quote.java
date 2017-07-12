package com.batiaev.demo.quotes.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * Quote
 *
 * @author anton
 * @since 13/07/17
 */
@Data
@EqualsAndHashCode(of = {"currency", "base", "date"})
public class Quote {
    private int nominal;
    private Currency base;
    private double value;
    private Currency currency;
    private LocalDate date;
}