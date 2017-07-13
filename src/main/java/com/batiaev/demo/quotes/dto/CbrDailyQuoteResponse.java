package com.batiaev.demo.quotes.dto;

import lombok.Getter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * CbrDailyQuoteResponse
 *
 * @author anton
 * @since 13/07/17
 */
@XmlRootElement(name = "ValCurs")
public class CbrDailyQuoteResponse {
    @Getter
    @XmlAttribute(name = "name")
    private String name;
    @XmlAttribute(name = "Date")
    private String date;
    @Getter
    @XmlElement(name = "Valute")
    private List<CbrQuote> quotes;

    public LocalDate getDate() {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
