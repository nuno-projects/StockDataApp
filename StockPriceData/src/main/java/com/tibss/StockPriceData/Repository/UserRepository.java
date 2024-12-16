package com.tibss.StockPriceData.Repository;

import com.tibss.StockPriceData.Models.AppUser;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);

    
}
