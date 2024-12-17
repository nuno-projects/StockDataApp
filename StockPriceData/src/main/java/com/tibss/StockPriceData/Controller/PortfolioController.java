package com.tibss.StockPriceData.Controller;

import com.tibss.StockPriceData.Models.Enum.OperationType;
import com.tibss.StockPriceData.Models.Portfolio;
import com.tibss.StockPriceData.Models.Stock;
import com.tibss.StockPriceData.Service.OperationService;
import com.tibss.StockPriceData.Service.PortfolioService;
import com.tibss.StockPriceData.Service.StockService;
import com.tibss.StockPriceData.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final StockService stockService;
    private final OperationService operationService;
    private final UserService userService;

    // Constructor-based dependency injection
    @Autowired
    public PortfolioController(PortfolioService portfolioService, StockService stockService,
                               OperationService operationService, UserService userService) {
        this.portfolioService = portfolioService;
        this.stockService = stockService;
        this.operationService = operationService;
        this.userService = userService;
    }

    // Add a new stock to user's portfolio
    @PostMapping("/addStockToPortfolio")
    public String addStockToUserPortfolio(@RequestParam Long stockId, @RequestParam Long portfolioId) {
        // Use service layer to interact with the repository
        Optional<Portfolio> portfolioOptional = portfolioService.getPortfolioById(portfolioId);

        // Check if the portfolio is found
        if (portfolioOptional.isPresent()) {
            Portfolio portfolio = portfolioOptional.get();

            // Find the stock by ID using StockService
            Optional<Stock> stockOptional = Optional.ofNullable(stockService.getStockById(stockId));

            // Check if the stock is found
            if (stockOptional.isPresent()) {
                Stock stock = stockOptional.get();

                // Add the stock to the portfolio's stock list using PortfolioService
                portfolioService.addStockToPortfolio(portfolio, stock);

                return String.format("The stock %s was added to portfolio %s.", stock.getSymbol(), portfolio.getName());
            } else {
                return String.format("Stock with ID %d not found.", stockId);
            }
        } else {
            return String.format("Portfolio with ID %d not found.", portfolioId);
        }
    }

    // Fetch portfolio by username
    @GetMapping("/getPortfolio")
    public ResponseEntity<Portfolio> getPortfolioByUsername(@RequestParam String username) {
        Optional<Portfolio> portfolioOpt = portfolioService.getPortfolioByUsername(username);

        // Return the portfolio if found
        // Return 404 if not found
        return portfolioOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @GetMapping("/getPortfolios")
    public ResponseEntity<List<Portfolio>> getPortfolios() {
        List<Portfolio> portfolioList = portfolioService.getPortfolios();

        if (portfolioList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // Return 404 if no stocks found
        }

        return ResponseEntity.ok(portfolioList);

    }

    // Fetch portfolio stocks based on the portfolio ID (BUY only)
    @GetMapping("/getPortfolioActiveStocks")
    public ResponseEntity<List<Stock>> getPortfolioActiveStocks(@RequestParam Long portfolioId) {
        List<Stock> stocks = portfolioService.getStocksForPortfolioByOperationType(portfolioId, OperationType.BUY);

        // Return 404 if no stocks are found for the given portfolio
        if (stocks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(stocks);
    }

    // Fetch portfolio stocks based on the portfolio ID (SELL only)
    @GetMapping("/getPortfolioSoldStocks")
    public ResponseEntity<List<Stock>> getPortfolioSoldStocks(@RequestParam Long portfolioId) {
        List<Stock> stocks = portfolioService.getStocksForPortfolioByOperationType(portfolioId, OperationType.SELL);

        // Return 404 if no stocks are found for the given portfolio
        if (stocks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(stocks);
    }

    @PostMapping("/addPortfolio")
    public Portfolio addPortfolio(@RequestBody Portfolio portfolio) {
        return portfolioService.addPortfolio(portfolio);
    }
}
