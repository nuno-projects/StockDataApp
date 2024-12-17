package com.tibss.StockPriceData.Models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Setter
@Getter
@Entity
@Table
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String symbol;
    private Double price;
    private Double dividendYield;
    @Column(name = "dcf_fair_value")
    private Double dcfFairValue;
    private Double marketCap;
    private Double totalDebt;
    @Column(name = "total_cash_equivalents")
    private Double totalCashEquivalents;
    private Long numShares;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String sectorName;
    private String websiteURL;
    private LocalDateTime updateDate = LocalDateTime.now(); // Automatically set current time

}
