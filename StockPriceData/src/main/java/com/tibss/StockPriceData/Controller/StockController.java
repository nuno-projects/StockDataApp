package com.tibss.StockPriceData.Controller;

import com.tibss.StockPriceData.Models.Stock;
import com.tibss.StockPriceData.Repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController {

    @Autowired
    private StockRepository stockRepository;

    // Add a new stock record
    @PostMapping("/add-stock")
    public String addStock(@RequestParam String symbol, @RequestParam Double price) {
        Stock stock = new Stock();
        stock.setSymbol(symbol);
        stock.setPrice(price);
        stockRepository.save(stock);
        return "Stock added successfully!";
    }

    // Fetch a stock by ID
    @GetMapping("/get-stock")
    public Stock getStock(@RequestParam Long id) {
        return stockRepository.findById(id).orElse(null);
    }
}
