package com.tibss.StockPriceData.Models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
@Table
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String Symbol;
    private Double Price;
    private Double DividendYield;
    private Double DcfFairValue;
    private Double MarketCap;
    private Double TotalDebt;
    private Double TotalCashEquivalents;
    private Long NumShares;

//    public Stock( String symbol, Double price, Double dividendYield, Double dcfFairValue,
//                 Double marketCap, Double totalDebt, Double totalCashEquivalents, Long numShares) {
//        Symbol = symbol;
//        Price = price;
//        DividendYield = dividendYield;
//        DcfFairValue = dcfFairValue;
//        MarketCap = marketCap;
//        TotalDebt = totalDebt;
//        TotalCashEquivalents = totalCashEquivalents;
//        NumShares = numShares;
//    }
}
