package com.batiaev.demo.quotes.model;

import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;

/**
 * CbrQuote
 *
 * @author anton
 * @since 13/07/17
 */
public class CbrQuote {
    @Getter
    @XmlElement(name = "NumCode")
    private int code;
    @Getter
    @XmlElement(name = "CharCode")
    private String currCode;
    @Getter
    @XmlElement(name = "Nominal")
    private int nominal;
    @Getter
    @XmlElement(name = "Name")
    private String name;
    @XmlElement(name = "Value")
    private String value;

    public double getValue() {
        return Double.parseDouble(value.replace(",", "."));
    }
}
