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

    // Service method to update user
    public AppUser updateUser(Long id, AppUser updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(updatedUser.getUsername());
                    user.setPortfolios(updatedUser.getPortfolios());
                    user.setTotalInvested(updatedUser.getTotalInvested());
                    // Update other fields as necessary
                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    updatedUser.setId(id);
                    return userRepository.save(updatedUser);
                });
    }

    // Service method to delete user by id
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    // Service method to delete all users
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }
}
