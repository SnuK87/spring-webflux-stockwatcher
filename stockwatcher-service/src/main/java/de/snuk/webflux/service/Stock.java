package de.snuk.webflux.service;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {

    private String symbol;
    private BigDecimal price;
    private BigDecimal priceChange;
    private String lastUpdate;
}
