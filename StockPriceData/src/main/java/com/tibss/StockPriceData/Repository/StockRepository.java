package com.tibss.StockPriceData.Repository;

import com.tibss.StockPriceData.Models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
    // You can add custom query methods here if needed
}
