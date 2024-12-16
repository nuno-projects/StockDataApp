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
    private Long id;
    private String name;
    @ManyToOne()
    @JoinColumn(name = "user_Id")
    private AppUser user;
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<Operation> operationList = new ArrayList<>();  // List of operations for this portfolio
    private Double totalValue;
    private Double dividendYield;

    // Additional methods as needed
}
