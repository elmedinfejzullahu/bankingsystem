package com.elmedinf.bankingsystem.repository;

import com.elmedinf.bankingsystem.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findAccountByUsername(String username);
}
