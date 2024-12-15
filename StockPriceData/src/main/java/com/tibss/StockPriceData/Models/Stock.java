package com.tibss.StockPriceData.Models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
@Table
public class Stock {

    // Getters and Setters
    @Id
    private Long id;
    private String symbol;
    private Double price;

}
