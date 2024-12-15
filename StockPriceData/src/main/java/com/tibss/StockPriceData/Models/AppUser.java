package com.tibss.StockPriceData.Models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table
public class AppUser {

    // Getters and Setters
    @Setter
    @Getter
    @Id
    private Long id;
    private String name;


}
