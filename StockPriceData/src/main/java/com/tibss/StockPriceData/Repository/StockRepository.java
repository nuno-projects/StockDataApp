package com.tibss.StockPriceData.Repository;

import com.tibss.StockPriceData.Models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findBySymbol(String symbol);

//TODO
//List<Stock> findByPriceBetween(Double minPrice, Double maxPrice); for a range-based search.
//    List<Stock> findByMarketCapGreaterThan(Double minMarketCap); for searching stocks by market cap.
}
