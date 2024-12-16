package com.tibss.StockPriceData.Service;

import com.tibss.StockPriceData.Models.Portfolio;
import com.tibss.StockPriceData.Models.Stock;
import com.tibss.StockPriceData.Repository.PortfolioRepository;
import com.tibss.StockPriceData.Repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final StockRepository stockRepository;

    // Constructor-based dependency injection
    @Autowired
    public PortfolioService(PortfolioRepository portfolioRepository, StockRepository stockRepository) {
        this.portfolioRepository = portfolioRepository;
        this.stockRepository = stockRepository;
    }

    // Get portfolio by ID
    public Optional<Portfolio> getPortfolioById(Long portfolioId) {
        return portfolioRepository.findById(portfolioId);
    }

    // Get portfolio by user (assuming you have a user field or similar in the Portfolio model)
    public Portfolio getPortfolioByUser(String username) {
        return portfolioRepository.findByUser(username).orElse(null); // Example method
    }

    // Add stock to the portfolio
    public void addStockToPortfolio(Portfolio portfolio, Stock stock) {
//        // Assuming the Portfolio entity has a List<Stock> field to store the stocks
//        portfolio.getStockList().add(stock);
//        portfolioRepository.save(portfolio); // Save the updated portfolio
    }
}
