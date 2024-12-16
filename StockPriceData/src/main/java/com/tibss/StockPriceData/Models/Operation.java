package com.tibss.StockPriceData.Models;

import com.tibss.StockPriceData.Models.Enum.OperationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime operationDate = LocalDateTime.now(); // Automatically set current time
    @ManyToOne
    @JoinColumn(name = "stock_id")  // Foreign key to Stock
    private Stock stock;
    @ManyToOne
    @JoinColumn(name = "portfolio_id")  // Foreign key to Portfolio
    private Portfolio portfolio;  // Many operations belong to one portfolio
    @Enumerated(EnumType.STRING)
    private OperationType operationType;
    private Double amount;
    private Double price;
    private Double totalCost;


}

