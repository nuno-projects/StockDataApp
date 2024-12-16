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
    private Long id;  // Note the lowercase 'id' (it’s more Java convention)
    @Column(unique = true, nullable = false)
    private String symbol;  // Lowercase 'symbol' to match the query method
    private Double price;
    private Double dividendYield;
    @Column(name = "dcf_fair_value")
    private Double dcfFairValue;
    private Double marketCap;
    private Double totalDebt;
    @Column(name = "total_cash_equivalents")
    private Double totalCashEquivalents;
    private Long numShares;

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
