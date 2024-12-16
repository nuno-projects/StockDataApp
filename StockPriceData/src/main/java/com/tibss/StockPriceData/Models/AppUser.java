package com.tibss.StockPriceData.Models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table
public class AppUser {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Portfolio> portfolios = new ArrayList<>();  // List of portfolios for this user
    private Double totalInvested;

}
