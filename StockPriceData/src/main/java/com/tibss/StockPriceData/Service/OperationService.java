package com.tibss.StockPriceData.Service;

import com.tibss.StockPriceData.Models.Operation;
import com.tibss.StockPriceData.Models.Enum.OperationType;
import com.tibss.StockPriceData.Models.Portfolio;
import com.tibss.StockPriceData.Models.Stock;
import com.tibss.StockPriceData.Repository.OperationRepository;
import com.tibss.StockPriceData.Repository.PortfolioRepository;
import com.tibss.StockPriceData.Repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OperationService {

    @Autowired
    private OperationRepository operationRepository;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private PortfolioRepository portfolioRepository;

    public Optional<Operation> getOperationById(Long operationId) {
        return operationRepository.findById(operationId);
    }

    public List<Operation> getOperations() {return operationRepository.findAll();}



    public Operation addOperation(Double amount, Double price, OperationType operationType, Long stockId, Long portfolioId) {
        // Retrieve the stock by its ID
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new RuntimeException("Stock not found with ID: " + stockId));
        // Retrieve the stock by its ID
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new RuntimeException("Portfolio not found with ID: " + stockId));

        Operation operation = new Operation();
        operation.setAmount(amount);
        operation.setPrice(price);
        operation.setTotalCost(amount * price);
        operation.setOperationType(operationType);
        operation.setStock(stock);
        operation.setPortfolio(portfolio);

        operationRepository.save(operation);  // Persist the operation
        return operation;
    }

    // Add method for buy operations
    public void addBuyOperation(Double amount, Double price, Long stockId, Long portfolioId) {
        addOperation(amount, price, OperationType.BUY, stockId, portfolioId);
    }

    // Add method for sell operations
    public void addSellOperation(Double amount, Double price, Long stockId, Long portfolioId) {
        addOperation(amount, price, OperationType.SELL,  stockId, portfolioId);
    }
}
