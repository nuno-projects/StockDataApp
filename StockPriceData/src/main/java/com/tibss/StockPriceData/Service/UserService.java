package com.tibss.StockPriceData.Service;

import com.tibss.StockPriceData.Models.Stock;
import com.tibss.StockPriceData.Repository.StockRepository;
import com.tibss.StockPriceData.Repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Service method to get stock by symbol
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

}
