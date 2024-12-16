package com.tibss.StockPriceData.Controller;

import com.tibss.StockPriceData.Models.Stock;
import com.tibss.StockPriceData.Service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class StockController {

    @Autowired
    private StockService stockService;

    // Fetch stock by ticker
    @GetMapping("/getStock")
    public Stock getStock(@RequestParam String ticker) {
        return stockService.getStockBySymbol(ticker);
    }

    @PostMapping("/newStock")
    public Stock addStock(@RequestBody Stock stock) {
        // Add the stock to the repository (this will insert it into the database)
        return stockService.addStock(stock);
    }
}
