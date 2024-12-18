package com.tibss.StockPriceData.Service;

import com.tibss.StockPriceData.Models.AppUser;
import com.tibss.StockPriceData.Models.Enum.OperationType;
import com.tibss.StockPriceData.Models.Operation;
import com.tibss.StockPriceData.Models.Portfolio;
import com.tibss.StockPriceData.Models.Stock;
import com.tibss.StockPriceData.Repository.OperationRepository;
import com.tibss.StockPriceData.Repository.PortfolioRepository;
import com.tibss.StockPriceData.Repository.StockRepository;
import com.tibss.StockPriceData.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final StockRepository stockRepository;
    private final UserRepository userRepository;

    private final OperationRepository operationRepository;

    // Constructor-based dependency injection
    @Autowired
    public PortfolioService(PortfolioRepository portfolioRepository, StockRepository stockRepository,
                            UserRepository userRepository, OperationRepository operationRepository) {
        this.portfolioRepository = portfolioRepository;
        this.stockRepository = stockRepository;
        this.userRepository = userRepository;
        this.operationRepository = operationRepository;
    }

    // Get portfolio by ID
    public Optional<Portfolio> getPortfolioById(Long portfolioId) {
        return portfolioRepository.findById(portfolioId);
    }

    public List<Portfolio> getPortfolios() {return portfolioRepository.findAll();}

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

    // Method to fetch stocks based on operations of a given portfolio ID
    public List<Stock> getStocksForPortfolioByOperationType(Long portfolioId, OperationType operationType) {
        // Fetch all operations for the given portfolio ID (filter for BUY operations)
        List<Operation> operations = operationRepository.findByPortfolioIdAndOperationType(portfolioId, operationType);

        // Transform operations to a list of stocks
        return operations.stream()
                .map(Operation::getStock)  // Extract stock from each operation
                .distinct()  // Ensure no duplicates (in case the same stock is bought multiple times)
                .collect(Collectors.toList());
    }

    // Add stock to the portfolio
    public void addStockToPortfolio(Portfolio portfolio, Stock stock) {
        //TODO portfolio.getStockList().add(stock); ir atraves das operations de buy
        portfolioRepository.save(portfolio); // Save the updated portfolio
    }

    public Portfolio addPortfolio(Portfolio portfolio) {
        System.out.println("Portfolio Added: " + portfolio.getName());
        return portfolioRepository.save(portfolio);
    }
}
