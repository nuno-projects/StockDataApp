package com.tibss.StockPriceData.Service;

import com.tibss.StockPriceData.Models.Stock;
import com.tibss.StockPriceData.Repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final Integer numYearsProjection = 10;
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

    // Service method to get all existing stocks
    public List<Stock> getStocks() {
        return stockRepository.findAll();
    }

    public Stock addStock(Stock stock) {
        // Save the new stock or update it if it already exists
        System.out.println("Stock Added: " + stock.getSymbol());
        return stockRepository.save(stock);
    }

    public Double calculateDCFValue(Stock stock) {
        Double fairValue = 0D;

        //TODO Calculate logic

        System.out.println("CalculateDCFValue Log: " + fairValue);
        return fairValue;
    }

    public Double calculateDCFSafetyValue(Stock stock, Float safetyPercentage) {
        Double fairValue = calculateDCFValue(stock);
        Double safetyDCFValue = fairValue * (safetyPercentage / 100);

        System.out.println("CalculateDCFSafetyValue Log: " + safetyDCFValue);
        return safetyDCFValue;
    }

}
