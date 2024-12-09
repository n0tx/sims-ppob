package com.riki.simsppob.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.riki.simsppob.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Iterable<Transaction> findByUserId(Long userId);

    List<Transaction> findByUserId(Long userId, Pageable pageable);

    long countByUserId(Long userId);
}
