package com.example.futurum_technology.repository;

import com.example.futurum_technology.model.EmeraldAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmeraldAccountRepository extends JpaRepository<EmeraldAccount, Long> {
}