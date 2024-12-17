package com.tibss.StockPriceData.Controller;

import com.tibss.StockPriceData.Models.AppUser;
import com.tibss.StockPriceData.Models.Enum.OperationType;
import com.tibss.StockPriceData.Models.Operation;
import com.tibss.StockPriceData.Models.Portfolio;
import com.tibss.StockPriceData.Models.Stock;
import com.tibss.StockPriceData.Service.OperationService;
import com.tibss.StockPriceData.Service.PortfolioService;
import com.tibss.StockPriceData.Service.StockService;
import com.tibss.StockPriceData.Service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AppUserController {

    private final PortfolioService portfolioService;
    private final StockService stockService;
    private final OperationService operationService;
    private final UserService userService;

    // Constructor-based dependency injection
    @Autowired
    public AppUserController(PortfolioService portfolioService, StockService stockService,
                             OperationService operationService, UserService userService) {
        this.portfolioService = portfolioService;
        this.stockService = stockService;
        this.operationService = operationService;
        this.userService = userService;
    }

    @GetMapping("/getUser")
    public ResponseEntity<AppUser> getUserById(@RequestParam Long userId) {
        Optional<AppUser> userOpt = userService.getUserById(userId);

        // Return the portfolio if found
        // Return 404 if not found
        return userOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @GetMapping("/getUsers")
    public ResponseEntity<List<AppUser>> getUsers() {
        List<AppUser> usersList = userService.getUsers();

        if (usersList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // Return 404 if no users found
        }

        return ResponseEntity.ok(usersList);
    }

    @PostMapping("/addUser")
    public AppUser addUser(@RequestBody AppUser user) {
        return userService.addUser(user);
    }
}
