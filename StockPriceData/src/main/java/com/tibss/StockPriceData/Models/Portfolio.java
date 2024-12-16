package com.tibss.StockPriceData.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String Name;

    // Corrected One-to-Many relationship mapping
    @OneToMany(mappedBy = "Portfolio", cascade = CascadeType.ALL)  // "portfolio" refers to the field in Operation
    private List<Operation> OperationList = new ArrayList<>();  // List of operations for this portfolio

    private Double TotalValue;
    private Double DividendYield;

    // Additional methods as needed
}
