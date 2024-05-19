package com.elmedinf.bankingsystem.repository;

import com.elmedinf.bankingsystem.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> getAllById(Long id);
}
