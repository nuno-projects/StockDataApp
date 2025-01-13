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
        // If specific tickers are provided (single or comma-separated)
        if (ticker != null && !ticker.isEmpty()) {
            apiService.loadStockStatistics(ticker);  // Pass the tickers directly, even if they don't exist in the database
            return "Stock statistics loaded for: " + ticker;
        }

        // If no specific ticker is provided, load all stocks from the database
        List<String> stockSymbols = stockService.getStocks().stream()
                .map(Stock::getSymbol)
                .toList();

        String tickerString = String.join(",", stockSymbols);

        // If no stocks are found in the database, load all statistics anyway
        if (stockSymbols.isEmpty()) {
            apiService.loadStockStatistics("");  // Call API with empty or default string to load statistics
            return "No stocks found in the database, but statistics loaded for provided tickers.";
        }

        apiService.loadStockStatistics(tickerString);  // Load statistics for all stocks in the database
        return "Updated stocks: " + tickerString;
    }

    // Calculate DCF value for a stock
    @GetMapping("/calculateDCFValue")
    public ResponseEntity<Double> calculateDCFValue(@RequestParam String ticker) {
        Stock stock = stockService.getStockBySymbol(ticker);
        if (stock == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        double dcfValue = stockService.calculateDCFValue(stock);
        return ResponseEntity.ok(dcfValue);
    }

    // Calculate safe DCF value for a stock
    @GetMapping("/calculateSafeDCFValue")
    public ResponseEntity<Double> calculateSafeDCFValue(@RequestParam String ticker, @RequestParam Float safetyPercentage) {
        Stock stock = stockService.getStockBySymbol(ticker);
        if (stock == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        double safeDCFValue = stockService.calculateDCFSafetyValue(stock, safetyPercentage);
        return ResponseEntity.ok(safeDCFValue);
    }

    // Update stock
    @PutMapping("/updateStock")
    public ResponseEntity<Stock> updateStock(@RequestParam Long id, @RequestBody Stock updatedStock) {
        Stock stock = stockService.updateStock(id, updatedStock);
        return ResponseEntity.ok(stock);
    }

    // Delete stock by ID
    @DeleteMapping("/deleteStockById")
    public ResponseEntity<Void> deleteStockById(@RequestParam Long id) {
        stockService.deleteStockById(id);
        return ResponseEntity.noContent().build();
    }

    // Delete all stocks
    @DeleteMapping("/deleteAllStocks")
    public ResponseEntity<Void> deleteAllStocks() {
        stockService.deleteAllStocks();
        return ResponseEntity.noContent().build();
    }
}
