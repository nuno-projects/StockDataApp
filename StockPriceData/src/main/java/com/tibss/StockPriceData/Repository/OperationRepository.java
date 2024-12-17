package com.tibss.StockPriceData.Repository;

import com.tibss.StockPriceData.Models.Enum.OperationType;
import com.tibss.StockPriceData.Models.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    List<Operation> findByPortfolioIdAndOperationType(Long portfolioId, OperationType operationType);
}
