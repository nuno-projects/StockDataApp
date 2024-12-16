package com.tibss.StockPriceData.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "operation_type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private LocalDateTime OperationDate = LocalDateTime.now(); // Automatically set current time

    @ManyToOne
    @JoinColumn(name = "Stock_id")  // Foreign key to Stock
    private Stock Stock;

    private Integer Type;
    private Double Amount;
    private Double Price;

    @ManyToOne
    @JoinColumn(name = "Portfolio_id")  // Foreign key to Portfolio
    private Portfolio Portfolio;  // Many operations belong to one portfolio
}
