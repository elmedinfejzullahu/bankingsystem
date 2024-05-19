package com.elmedinf.bankingsystem.repository;

import com.elmedinf.bankingsystem.models.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {
    Optional<Bank> findByBank(String bank);
}
