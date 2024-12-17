package com.tibss.StockPriceData.Controller;

import com.tibss.StockPriceData.Models.Enum.OperationType;
import com.tibss.StockPriceData.Models.Operation;
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
public class OperationsController {

    private final PortfolioService portfolioService;
    private final StockService stockService;
    private final OperationService operationService;
    private final UserService userService;

    // Constructor-based dependency injection
    @Autowired
    public OperationsController(PortfolioService portfolioService, StockService stockService,
                                OperationService operationService, UserService userService) {
        this.portfolioService = portfolioService;
        this.stockService = stockService;
        this.operationService = operationService;
        this.userService = userService;
    }


    // Fetch operation by username
    @GetMapping("/getOperation")
    public ResponseEntity<Operation> getOperation(@RequestParam Long operationId) {
        Optional<Operation> operationOpt = operationService.getOperationById(operationId);

        // Return the portfolio if found
        // Return 404 if not found
        return operationOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @GetMapping("/getOperations")
    public ResponseEntity<List<Operation>> getOperations() {
        List<Operation> operationList = operationService.getOperations();

        if (operationList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // Return 404 if no stocks found
        }

        return ResponseEntity.ok(operationList);
    }

    @PostMapping("/addOperation")
    public Operation addOperation(@RequestBody Operation operation) {
        return operationService.addOperation(operation.getAmount(),
                operation.getPrice(), operation.getOperationType(),
                operation.getStock().getId(), operation.getPortfolio().getId());
    }
}
