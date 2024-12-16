package com.tibss.StockPriceData.Controller;

import com.tibss.StockPriceData.Models.Portfolio;
import com.tibss.StockPriceData.Models.Stock;
import com.tibss.StockPriceData.Service.PortfolioService;
import com.tibss.StockPriceData.Service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final StockService stockService;

    // Constructor-based dependency injection
    @Autowired
    public PortfolioController(PortfolioService portfolioService, StockService stockService) {
        this.portfolioService = portfolioService;
        this.stockService = stockService;
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
    public Portfolio getPortfolioByUser(@RequestParam String username) {
        return portfolioService.getPortfolioByUser(username);
    }
}
