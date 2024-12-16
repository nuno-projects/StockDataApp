package com.tibss.StockPriceData.Models;

import jakarta.persistence.Entity;

@Entity
public class Buy extends Operation {

    private Double totalAmountSpent;  // Total amount spent in the Buy operation

    // You can add specific methods related to Buy operation if needed
}
