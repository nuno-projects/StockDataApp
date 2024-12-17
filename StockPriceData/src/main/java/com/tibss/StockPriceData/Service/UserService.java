package com.tibss.StockPriceData.Service;

import com.tibss.StockPriceData.Models.AppUser;
import com.tibss.StockPriceData.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public AppUser getUserByUsername(String username) {return userRepository.findByUsername(username).orElse(null);}
    public Optional<AppUser> getUserById(Long userId) {return userRepository.findById(userId);}
    public List<AppUser> getUsers() {return userRepository.findAll();}
    public AppUser addUser(AppUser user) {
        System.out.println("User Added: " + user.getUsername());
        return userRepository.save(user);
    }
}
