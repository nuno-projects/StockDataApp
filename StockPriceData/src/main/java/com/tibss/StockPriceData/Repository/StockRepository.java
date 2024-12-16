package com.tibss.StockPriceData.Repository;

import com.tibss.StockPriceData.Models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface StockRepository extends JpaRepository<Stock, Long> {
    // Method to find a stock by its symbol
    Optional<Stock> findBySymbol(String symbol);

//    // Method to find stocks whose price is between a specific range
//    List<Stock> findByPriceBetween(Double minPrice, Double maxPrice);
//
//    // Method to find stocks with a market cap greater than a specified value
//    List<Stock> findByMarketCapGreaterThan(Double minMarketCap);
}
