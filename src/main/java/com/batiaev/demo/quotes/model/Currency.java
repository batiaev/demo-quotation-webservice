package com.batiaev.demo.quotes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

/**
 * Currency
 *
 * @author anton
 * @since 13/07/17
 */
@Data
@EqualsAndHashCode(of = "code", callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Currency extends ResourceSupport {
    private int code;
    private String currCode;
    private String name;
}
