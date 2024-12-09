package com.riki.simsppob.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.riki.simsppob.model.Balance;

public interface BalanceRepository extends JpaRepository<Balance, Long> {

    @Query(nativeQuery = true, value ="SELECT id, user_id, balance_amount FROM balances WHERE user_id = :id LIMIT 1")
    Optional<Balance> findByUserId(@Param("id") Long id);
}
