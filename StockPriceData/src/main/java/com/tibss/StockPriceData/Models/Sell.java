package com.tibss.StockPriceData.Models;

import jakarta.persistence.Entity;

@Entity
public class Sell extends Operation {

    private Double totalAmountReceived;  // Total amount received in the Sell operation

    // You can add specific methods related to Sell operation if needed
}

