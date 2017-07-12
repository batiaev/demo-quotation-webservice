package com.batiaev.demo.quotes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Currency
 *
 * @author anton
 * @since 13/07/17
 */
@Data
@EqualsAndHashCode(of = "code")
@AllArgsConstructor
@NoArgsConstructor
public class Currency {
    private int code;
    private String currCode;
    private String name;
}
