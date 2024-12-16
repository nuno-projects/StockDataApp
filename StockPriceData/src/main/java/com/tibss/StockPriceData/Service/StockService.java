package com.tibss.StockPriceData.Service;

import com.tibss.StockPriceData.Models.Stock;
import com.tibss.StockPriceData.Repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class StockService {

    private final StockRepository stockRepository;

    @Autowired
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    // Service method to get stock by symbol
    public Stock getStockBySymbol(String symbol) {
        return stockRepository.findBySymbol(symbol).orElse(null);
    }

    // Service method to get stock by id
    public Stock getStockById(Long id) {
        return stockRepository.findById(id).orElse(null);  // Fetch stock by ID
    }

    public Stock addStock(Stock stock) {
        // Save the new stock or update it if it already exists
        return stockRepository.save(stock);
    }
}
