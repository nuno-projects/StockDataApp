package com.tibss.StockPriceData.Service;

import com.tibss.StockPriceData.Models.AppUser;
import com.tibss.StockPriceData.Models.Portfolio;
import com.tibss.StockPriceData.Models.Stock;
import com.tibss.StockPriceData.Repository.PortfolioRepository;
import com.tibss.StockPriceData.Repository.StockRepository;
import com.tibss.StockPriceData.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final StockRepository stockRepository;
    private final UserRepository userRepository;
    // Constructor-based dependency injection
    @Autowired
    public PortfolioService(PortfolioRepository portfolioRepository, StockRepository stockRepository, UserRepository userRepository) {
        this.portfolioRepository = portfolioRepository;
        this.stockRepository = stockRepository;
        this.userRepository = userRepository;
    }

    // Get portfolio by ID
    public Optional<Portfolio> getPortfolioById(Long portfolioId) {
        return portfolioRepository.findById(portfolioId);
    }

    // Get portfolio by username
    public Optional<Portfolio> getPortfolioByUsername(String username) {
        // Use Optional to safely handle user not being found
        Optional<AppUser> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            return portfolioRepository.findByUser(userOpt.get());
        } else {
            // Return an empty Optional if user not found
            return Optional.empty();
        }
    }

    // Add stock to the portfolio
    public void addStockToPortfolio(Portfolio portfolio, Stock stock) {
        //TODO portfolio.getStockList().add(stock);
        portfolioRepository.save(portfolio); // Save the updated portfolio
    }
}
