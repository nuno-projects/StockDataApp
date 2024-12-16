package com.tibss.StockPriceData.Repository;

import com.tibss.StockPriceData.Models.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    Optional<Portfolio> findByUser(String username);

    
}
