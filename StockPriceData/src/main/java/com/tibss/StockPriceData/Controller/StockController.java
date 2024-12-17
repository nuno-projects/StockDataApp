package com.tibss.StockPriceData.Controller;

import com.tibss.StockPriceData.Models.Stock;
import com.tibss.StockPriceData.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class StockController {

    @Autowired
    private final PortfolioService portfolioService;
    private final StockService stockService;
    private final OperationService operationService;
    private final UserService userService;
    private final APIService apiService;

    public StockController(PortfolioService portfolioService, StockService stockService,
                           OperationService operationService, UserService userService, APIService apiService, APIService apiService1) {
        this.portfolioService = portfolioService;
        this.stockService = stockService;
        this.operationService = operationService;
        this.userService = userService;
        this.apiService = apiService1;
    }

    // Fetch all stocks
    @GetMapping("/getStocks")
    public ResponseEntity<List<Stock>> getStocks() {
        List<Stock> stockList = stockService.getStocks();

        if (stockList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // Return 404 if no stocks found
        }

        return ResponseEntity.ok(stockList);
    }

    // Fetch stock by ticker
    @GetMapping("/getStockByTicker")
    public Stock getStockByTicker(@RequestParam String ticker) {
        return stockService.getStockBySymbol(ticker);
    }

    @PostMapping("/addStock")
    public Stock newStock(@RequestBody Stock stock) {
        // Add the stock to the repository (this will insert it into the database)
        return stockService.addStock(stock);
    }

    @GetMapping("/loadStocksInfo")
    public String loadStocksInfo(@RequestParam(value = "ticker", required = false) String ticker) {
        // If a specific ticker is provided
        if (ticker != null && !ticker.isEmpty()) {
            Stock stock = apiService.loadStockStatistics(ticker);

            if (stock != null) {
                return "Stock updated: " + stock.getSymbol();
            } else {
                return "Stock not found for ticker: " + ticker;
            }
        }

        // If no ticker is provided, fetch all existing stocks and update them
        List<String> stockSymbols = stockService.getStocks().stream()
                .map(Stock::getSymbol)
                .toList();

        if (stockSymbols.isEmpty()) {
            return "No stocks were found.";
        }

        String tickerString = String.join(",", stockSymbols);
        apiService.loadStockStatistics(tickerString);  // Refresh all stocks' info

        return "Updated stocks: " + tickerString;
    }

}
