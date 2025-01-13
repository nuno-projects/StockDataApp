package com.tibss.StockPriceData.Service;

import com.tibss.StockPriceData.Models.Stock;
import com.tibss.StockPriceData.Repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final Integer numYearsProjection = 10;
    @Autowired
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    // Service method to get stock by symbol
    public Stock getStockBySymbol(String symbol) {
        return stockRepository.findBySymbol(symbol).orElse(null);
    }

    // Service method to get stock by id
    public Stock getStockById(Long id) {
        return stockRepository.findById(id).orElse(null);  // Fetch stock by ID
    }

    // Service method to get all existing stocks
    public List<Stock> getStocks() {
        return stockRepository.findAll();
    }

    public Stock addStock(Stock stock) {
        // Save the new stock or update it if it already exists
        System.out.println("Stock Added: " + stock.getSymbol());
        return stockRepository.save(stock);
    }

    public double calculateStockGrowthPercentage(List<Long> fCFList) {
        long totalFCF = 0;

        for(Long fcf : fCFList ){
            totalFCF += fcf;
        }

        return  (double) totalFCF /fCFList.size();
    }

    public double calculateDCFValue(Stock stock) {
        double fairValue = 0;
        int numFutureYears = 10;
        long lastYearFCF = stock.getFcfList().get(stock.getFcfList().size() - 1); // Last year's FCF
        double expectedGrowth = calculateStockGrowthPercentage(stock.getFcfList()); // Expected growth rate of FCF
        double discountRate = 0.08;  // Discount rate (example: 8%)
        double economyGrow = 0.0245; // Long-term growth rate (example: 2.45%)

        // Calculate the discounted future cash flows
        for (int futureYear = 1; futureYear <= numFutureYears; futureYear++) {
            long futureFCF = (long) (lastYearFCF * Math.pow(1 + expectedGrowth, futureYear));  // Projected FCF for each year
            double discountedFCF = futureFCF / Math.pow(1 + discountRate, futureYear);         // Discount the future FCF to present value
            fairValue += discountedFCF;  // Accumulate the fair value
        }

        // Terminal Value: Estimate the value beyond the forecast period
        long terminalFCF = (long) (lastYearFCF * Math.pow(1 + expectedGrowth, numFutureYears));
        double terminalValue = (terminalFCF * (1 + economyGrow)) / (discountRate - economyGrow);
        double discountedTerminalValue = terminalValue / Math.pow(1 + discountRate, numFutureYears);

        // Add the terminal value to the fair value
        fairValue += discountedTerminalValue;

        // Adjust for Debt and Cash Equivalents to get the equity value
        double enterpriseValue = fairValue;
        double equityValue = enterpriseValue + stock.getTotalCashEquivalents() - stock.getTotalDebt();

        // Calculate DCF per share by dividing equity value by total number of shares
        double dcfPerShare = equityValue / stock.getNumShares();

        System.out.println("DCF Per Share: " + dcfPerShare);
        return dcfPerShare;
    }



    public double calculateDCFSafetyValue(Stock stock, Float safetyPercentage) {
        double fairValue = calculateDCFValue(stock);
        double safetyDCFValue = fairValue * (safetyPercentage / 100);

        System.out.println("CalculateDCFSafetyValue Log: " + safetyDCFValue);
        return safetyDCFValue;
    }

    // Service method to update stock
    public Stock updateStock(Long id, Stock updatedStock) {
        return stockRepository.findById(id)
                .map(stock -> {
                    stock.setPrice(updatedStock.getPrice());
                    stock.setDividendYield(updatedStock.getDividendYield());
                    stock.setFcfList(updatedStock.getFcfList());
                    // Update other fields as necessary
                    return stockRepository.save(stock);
                })
                .orElseGet(() -> {
                    updatedStock.setId(id);
                    return stockRepository.save(updatedStock);
                });
    }

    // Service method to delete stock by id
    public void deleteStockById(Long id) {
        stockRepository.deleteById(id);
    }

    // Service method to delete all stocks
    public void deleteAllStocks() {
        stockRepository.deleteAll();
    }

}
