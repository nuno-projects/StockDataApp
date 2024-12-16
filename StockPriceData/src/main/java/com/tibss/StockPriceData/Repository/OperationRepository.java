package com.tibss.StockPriceData.Repository;

import com.tibss.StockPriceData.Models.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Long> {
}
